package prev26lang;

import prev26lang.common.report.Location;
import prev26lang.common.report.Report;
import prev26lang.phase.abstr.Abstr;
import prev26lang.phase.imrgen.ImrGen;
import prev26lang.phase.imrgen.ImrGenerator;
import prev26lang.phase.imrlin.ImrLin;
import prev26lang.phase.imrlin.Interpreter;
import prev26lang.phase.imrlin.LIN;
import prev26lang.phase.imrlin.LinVisitor;
import prev26lang.phase.lexan.LexAn;
import prev26lang.phase.memory.Layouter;
import prev26lang.phase.memory.Memory;
import prev26lang.phase.seman.NameResolver;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TypeChecker;
import prev26lang.phase.seman.TypeConstructor;
import prev26lang.phase.synan.SynAn;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.function.Function;

/**
 * The Prev26 compiler.
 *
 * @author bostjan.slivnik@fri.uni-lj.si
 */
public class Compiler {

    /**
     * (Default) values of command line options.
     */
    private static final HashMap<String, String> cmdLineOpts = new HashMap<String, String>(Map.ofEntries( //
            Map.entry("--src-file-name", ""), // ---: the name of the source file
            Map.entry("--dst-file-name", ""), // ---: the name of the destination file
            Map.entry("--target-phase", "all"), // -: the name of the last performed phase
            Map.entry("--logged-phase", "all"), // -: the name of the logged phase (or none or all)
            Map.entry("--xml", ""), // -------------: the name of xml files (without phase name and extension)
            Map.entry("--xsl", "../lib/xsl/"), // --: the directory with the xsl templates for the logs
            Map.entry("--dev-mode", "on") // -------: development mode (on or off)
    ));

    /**
     * All valid phases name of the compiler.
     */
    private static final Vector<String> phaseNames = new Vector<String>(Arrays.asList( //
            "none", // ---: no phase
            "lexan", // --: lexical analysis
            "synan",
            "abstr",
            "seman", // --: semantic analysis
            "memory",
            "imrgen",
            "imrlin",
            "all" // -----: putting it all together
    ));

    /**
     * Specifies whether the compiler is run in the development mode.
     */
    private static boolean devMode = false;

    /**
     * Returns information on whether the compiler is run in the development mode.
     *
     * @return {@code true} if the compiler is run in the development mode,
     * {@code false} otherwise.
     */
    public static final boolean devMode() {
        return devMode;
    }

    /**
     * Returns the value of a command line option.
     *
     * @param cmdLineOptName Command line option name.
     * @return Command line option value.
     */
    public static final String cmdLineOptValue(final String cmdLineOptName) {
        String cmdLineOptValue = cmdLineOpts.get(cmdLineOptName);
        if (cmdLineOptValue == null)
            throw new Report.InternalError();
        return cmdLineOpts.get(cmdLineOptName);
    }

    /**
     * (Unused but included to keep javadoc happy.)
     */
    private Compiler() {
        throw new Report.InternalError();
    }

    /**
     * The compiler's main driver running all phases one after another.
     *
     * @param cmdLineArgs Command line arguments (see {@link prev26lang}).
     */
    public static void main(final String[] cmdLineArgs) {
        try {

            prev26lang.common.report.Report.info("This is Prev26 compiler:");

            // Scan the command line.
            scanCommandLine(cmdLineArgs);
            // Check the command line option values.
            checkCommandLine();




            // Start with compiling
            while (true) {

                if (cmdLineOpts.get("--target-phase").equals("none")) break;

                // === LEXICAL ANALYSIS ===
                if (cmdLineOpts.get("--target-phase").equals("lexan")) {
                    try (final LexAn lexan = new LexAn()) {
                        lexan.lexer.removeErrorListeners();
                        lexan.lexer.addErrorListener(Utility.errorListener);
                        // Read all tokens one by one.
                        LexAn.LocLogToken token;
                        while (true) {
                            token = lexan.lexer.nextToken();

                            if (token.getType() == LexAn.LocLogToken.EOF) {
                                break;
                            }
                        }
                    }
                    break;
                }
                // === SYNTAX ANALYSIS ==

                try (LexAn lexan = new LexAn(); SynAn synan = new SynAn(lexan)) {
                    synan.parser.removeErrorListeners();
                    synan.parser.addErrorListener(Utility.errorListener);
                    lexan.lexer.removeErrorListeners();
                    lexan.lexer.addErrorListener(Utility.errorListener);
                    SynAn.tree = synan.parser.source();
                    synan.log(SynAn.tree);
                }
                if (cmdLineOpts.get("--target-phase").equals("synan")) {
                  break;
                }

                // === ABSTRACT SYNTAX ===
                try (Abstr abstr = new Abstr()) {
                    Abstr.tree = SynAn.tree.ast;
                    Abstr.locAttr.lock();
                    SynAn.tree = null;
                    (new Abstr.Logger(abstr.xmlLogger)).visit(Abstr.tree);
                }
                if (cmdLineOpts.get("--target-phase").equals("abstr")) break;

                // === SEMANTIC ANALYSIS ===
                try (SemAn seman = new SemAn()) {
                    (new NameResolver()).visit(Abstr.tree);
                    (new TypeConstructor()).visit(Abstr.tree);
                    TypeChecker typeChecker = new TypeChecker();
                    Abstr.tree.accept(typeChecker, true);
                    SemAn.defAtAttr.lock();
                    SemAn.ofTypeAttr.lock();
                    SemAn.isTypeAttr.lock();
                    SemAn.isConstAttr.lock();
                    SemAn.isAddrAttr.lock();
                    (new SemAn.Logger(seman.xmlLogger)).visit(Abstr.tree);
                }
                if (cmdLineOpts.get("--target-phase").equals("seman"))
                    break;

                // === MEMORY LAYOUT ===
                try (Memory memory = new Memory()) {
                    (new Layouter()).visit(Abstr.tree);
                    Memory.frameAttr.lock();
                    Memory.accessAttr.lock();
                    Memory.stringAttr.lock();
                    (new Memory.Logger(memory.xmlLogger)).visit(Abstr.tree);
                }
                if (cmdLineOpts.get("--target-phase").equals("memory")) break;

                // === GENERATION OF INTERMEDIATE REPRESENTATION ===
                try (ImrGen imrGen = new ImrGen()) {
                    ImrGenerator.visit(Abstr.tree);
                    (new ImrGen.Logger(imrGen.xmlLogger)).visit(Abstr.tree);
                }
                if (cmdLineOpts.get("--target-phase").equals("imrgen")) break;

                try (ImrLin imrLin = new ImrLin()) {
                    LinVisitor linVisitor = new LinVisitor();
                    linVisitor.visit(Abstr.tree);
                    (new ImrLin.Logger(imrLin.xmlLogger)).visit(Abstr.tree);
                    Vector<LIN.DataChunk> dataChunks = ImrLin.dataChunkAttr.values();
                    Vector<LIN.CodeChunk> codeChunks = ImrLin.codeChunkAttr.values();

                    System.out.println("************************");
                    System.out.println("Interpreted program:");
                    Interpreter interpreter = new Interpreter(dataChunks, codeChunks, false);
                    long value = interpreter.run("_main");
                    System.out.println("Function _main returned " + value);
                    System.out.println("************************");
                }


                break;
            }


            // Let's hope we ever come this far.
            // But beware:
            // 1. The generated translation of the source file might be erroneous :-o
            // 2. The source file might not be what the programmer intended it to be ;-)
            Report.info("Done.");

        } catch (final Report.Error error) {
            System.err.println(error.getMessage());
            System.exit(1);
        }
    }

    private static void checkCommandLine() { // Source file name.
        if (cmdLineOpts.get("--src-file-name") == "") {
            try {
                // Source file has not been specified, so consider using the last modified
                // p26 file in the working directory.
                final String currWorkDir = new File(".").getCanonicalPath();
                FileTime latestTime = FileTime.fromMillis(0);
                Path latestPath = null;
                for (final Path path : java.nio.file.Files.walk(Paths.get(currWorkDir))
                        .filter(path -> path.toString().endsWith(".p26")).toArray(Path[]::new)) {
                    final FileTime time = Files.getLastModifiedTime(path);
                    if (time.compareTo(latestTime) > 0) {
                        latestTime = time;
                        latestPath = path;
                    }
                }
                if (latestPath != null) {
                    cmdLineOpts.put("--src-file-name", latestPath.toString());
                    Report.warning("Source file not specified, using '" + latestPath.toString() + "'.");
                }
            } catch (final IOException __) {
                throw new Report.Error("Source file not specified.");
            }

            if (cmdLineOpts.get("--src-file-name") == "") {
                throw new Report.Error("Source file not specified.");
            }
        }
        // Destination file name.
        if (cmdLineOpts.get("--dst-file-name") == "") {
            cmdLineOpts.put("--dst-file-name",
                    cmdLineOpts.get("--src-file-name").replaceFirst("\\.[^./]*$", ".asm"));
        }
        if (cmdLineOpts.get("--xml") == "") {
            cmdLineOpts.put("--xml", cmdLineOpts.get("--src-file-name").replaceFirst("\\.[^./]*$", ""));
        }
        // Development mode (on or off).
        switch (cmdLineOpts.get("--dev-mode")) {
            case "on":
                devMode = true;
                break;
            case "off":
                devMode = false;
                break;
            default:
                Report.warning("Illegal value '" + cmdLineOpts.get("--devMode") + "' for --dev-mode switch.");
                break;
        }
    }

    private static void scanCommandLine(final String[] cmdLineArgs) {
//        Report.info("cmdLineArgs: " + Arrays.toString(cmdLineArgs));
        HashSet<String> usedCmdLineOpts = new HashSet<String>();
        for (int argc = 0; argc < cmdLineArgs.length; argc++) {
            if (cmdLineArgs[argc].startsWith("--")) {
                // Command line option.
                final String cmdLineOptName = cmdLineArgs[argc].replaceFirst("=.*", "");
                final String cmdLineOptValue = cmdLineArgs[argc].replaceFirst("^[^=]*=", "");
                if (!cmdLineOpts.containsKey(cmdLineOptName)) {
                    Report.warning("Unknown command line option '" + cmdLineOptName + "'.");
                    continue;
                }
                if (!usedCmdLineOpts.contains(cmdLineOptName)) {
                    // Not yet successfully specified command line option.

                    // Check the value of the command line option.
                    if (cmdLineOptName.equals("--target-phase") && (!phaseNames.contains(cmdLineOptValue))) {
                        Report.warning("Illegal phase specification in '" + cmdLineArgs[argc] + "' ignored.");
                        continue;
                    }
                    if (cmdLineOptName.equals("--logged-phase") && (!phaseNames.contains(cmdLineOptValue))) {
                        Report.warning("Illegal phase specification in '" + cmdLineArgs[argc] + "' ignored.");
                        continue;
                    }
                    if (cmdLineOptName.equals("--dev-mode") && (!cmdLineOptValue.matches("on|off"))) {
                        Report.warning("Illegal value in '" + cmdLineArgs[argc] + "' ignored.");
                        continue;
                    }

                    cmdLineOpts.put(cmdLineOptName, cmdLineOptValue);
                    usedCmdLineOpts.add(cmdLineOptValue);
                } else {
                    // Repeated specification of a command line option.
                    Report.warning("Command line option '" + cmdLineArgs[argc] + "' ignored.");
                    continue;
                }
            } else {
                // Source file name.
                if (cmdLineOpts.get("--src-file-name") == "") {
                    cmdLineOpts.put("--src-file-name", cmdLineArgs[argc]);
                    usedCmdLineOpts.add("--src-file-name");
                } else {
                    Report.warning("Source file '" + cmdLineArgs[argc] + "' ignored.");
                    continue;
                }
            }
        }
    }
}
