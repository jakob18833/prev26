package prev26lang.phase.imrgen;

import prev26lang.phase.abstr.AST;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.memory.Memory;

import java.util.Vector;

public class ImrGenerator {

    public static void visit(AST.Nodes<AST.FullDefn> topLevelDefinitions) {
        for (var defn: topLevelDefinitions) {
            if (defn instanceof AST.DefFunDefn defFunDefn) ImrGenerator.visit(defFunDefn);
        }
    }


    public static IMR.Stmt visit(AST.DefFunDefn defFunDefn) {
        MEM.Frame frame = Memory.frameAttr.get(defFunDefn);
        MEM.Label entry = new MEM.Label();
        MEM.Label exit = new MEM.Label();
        IMR.Stmt bodyStmt = new IMR.MOVE(new IMR.TEMP(frame.RV), defFunDefn.expr.accept(new ExprVisitor(frame), null));

        Vector<IMR.Stmt> funStmtVector = new Vector<>();
        funStmtVector.add(new IMR.LABEL(entry));
        funStmtVector.add(bodyStmt);
        // Not sure if this is needed
        funStmtVector.add(new IMR.JUMP(new IMR.NAME(exit)));
        funStmtVector.add(new IMR.LABEL(exit));
        IMR.Stmt funStmt = new IMR.STMTS(funStmtVector);

        ImrGen.bodyEntryLabelAttr.put(defFunDefn, entry);
        ImrGen.bodyExitLabelAttr.put(defFunDefn, exit);
        ImrGen.genStmtIMRAttr.put(defFunDefn, funStmt);
        return funStmt;
    }
}
