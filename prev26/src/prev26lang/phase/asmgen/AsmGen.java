package prev26lang.phase.asmgen;

import prev26lang.phase.Phase;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.imrlin.ImrLin;
import prev26lang.phase.imrlin.LIN;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

public class AsmGen {

    private static final Predicate<AST.Node> validForMachineInstructions =
            (AST.Node node) -> (node instanceof AST.DefFunDefn);
    public static final AST.Attribute<List<ASM.Instruction>> machineInstructionAttr = new AST.Attribute<>(validForMachineInstructions);


    public static void generateASM() {
        Vector<LIN.CodeChunk> codeChunks = ImrLin.codeChunkAttr.values();
        for (var entry: ImrLin.codeChunkAttr.reverseMap.entrySet()) {
            var chunk = entry.getKey();
            var node = (AST.DefFunDefn) entry.getValue();
            AsmVisitor asmVisitor = new AsmVisitor();
            List<ASM.Instruction> instructions = asmVisitor.visit(chunk);

            // * log the instructions
            ImrLin.logCanonicalTrees(chunk.stmts(), chunk.frame, node.name);
            machineInstructionAttr.put(node, instructions);
            for (var instruction: instructions) {
                System.out.println(instruction);
            }
        }
    }
}
