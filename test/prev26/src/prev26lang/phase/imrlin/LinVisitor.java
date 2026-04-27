package prev26lang.phase.imrlin;

import prev26lang.phase.abstr.AST;
import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.imrgen.ImrGen;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.memory.Memory;

import java.util.Vector;


public class LinVisitor implements AST.FullVisitor<Void, Void> {
    Linearizator lin = new Linearizator();

    @Override
    public Void visit(AST.Nodes<? extends AST.Node> nodes, Void arg) {
        if (nodes.size() > 0 && nodes.first() instanceof AST.FullDefn) {
            for (var defn : nodes) {
                if (defn instanceof AST.DefFunDefn defFunDefn) {
                    IMR.Stmt stmt = ImrGen.genStmtIMRAttr.get(defFunDefn);
                    MEM.Label entry = ImrGen.bodyEntryLabelAttr.get(defFunDefn);
                    MEM.Label exit = ImrGen.bodyExitLabelAttr.get(defFunDefn);
                    MEM.Frame frame = Memory.frameAttr.get(defFunDefn);

                    Vector<IMR.Stmt> canonicalTrees = lin.linearize(stmt);
                    ImrLin.logCanonicalTrees(canonicalTrees, frame, defFunDefn.name);
                    LIN.CodeChunk codeChunk = new LIN.CodeChunk(frame, canonicalTrees, entry, exit);
                    ImrLin.codeChunkAttr.put(defFunDefn, codeChunk);
                } else if (defn instanceof AST.VarDefn varDefn) {
                    if (Memory.accessAttr.get(varDefn) instanceof MEM.AbsAccess absAccess) {
                        ImrLin.dataChunkAttr.put(varDefn, new LIN.DataChunk(absAccess));
                    }
                }
            }
        }
        return AST.FullVisitor.super.visit(nodes, arg);
    }

    @Override
    public Void visit(AST.AtomExpr atomExpr, Void arg) {
        if (atomExpr.type.equals(AST.AtomExpr.Type.STR)) {
            MEM.AbsAccess absAccess = Memory.stringAttr.get(atomExpr);
            ImrLin.dataChunkAttr.put(atomExpr, new LIN.DataChunk(absAccess));
        }
        return null;
    }
}
