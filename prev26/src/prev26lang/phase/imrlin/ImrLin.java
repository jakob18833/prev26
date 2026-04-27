package prev26lang.phase.imrlin;

import prev26lang.Compiler;
import prev26lang.common.logger.XMLLogger;
import prev26lang.common.report.Report;
import prev26lang.phase.Phase;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.imrgen.ImrGen;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.memory.Memory;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

public class ImrLin extends Phase {
    public ImrLin() {
        super("imrlin");
    }

    private static final Predicate<AST.Node> validForCodeChunk =
            (AST.Node node) -> (node instanceof AST.DefFunDefn);
    public static final AST.Attribute<LIN.CodeChunk> codeChunkAttr = new AST.Attribute<>(validForCodeChunk);

    private static final Predicate<AST.Node> validForDataChunk =
            (AST.Node node) -> (node instanceof AST.VarDefn) || (node instanceof AST.AtomExpr);
    public static final AST.Attribute<LIN.DataChunk> dataChunkAttr = new AST.Attribute<>(validForDataChunk);


    public static void logCanonicalTrees(Vector<IMR.Stmt> canonicalTrees, MEM.Frame frame, String funName) {
        System.out.println("========================");
        System.out.println("Function " + funName);
        System.out.println("Label: " + frame.label.name);
        System.out.println("FP: " + frame.FP + "    RV: " + frame.RV);
        System.out.println("Depth: " + frame.depth);
        System.out.println();
        for (var stmt: canonicalTrees) System.out.println(stmt);
        System.out.println("========================");
        System.out.println();
        System.out.println();
    }

    public static class Logger extends ImrGen.Logger {

        public Logger(final XMLLogger xmlLogger) {
            super(xmlLogger);
        }

        @Override
        public void logAttrs(final XMLLogger xmlLogger, final AST.Node node) {
            super.logAttrs(xmlLogger, node);
            if (node instanceof AST.DefFunDefn) {
                LIN.CodeChunk chunk = codeChunkAttr.get(node);
                if (chunk != null) {
                    xmlLogger.begElement("linimr");
                    for (IMR.Stmt stmt : chunk.stmts()) {
                        stmt.log(xmlLogger);
                    }
                    xmlLogger.endElement();
                } else {
                    if (!Compiler.devMode()) throw new Report.InternalError();
                }
            }
        }
    }
}
