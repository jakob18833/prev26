package prev26lang.phase.memory;


import prev26lang.phase.abstr.AST;
import prev26lang.phase.seman.SemAn;

import java.util.Stack;

public class Layouter implements AST.FullVisitor<Void, Void> {

    private int depth = 0;
    private final Stack<Long> offset = new Stack<>();



    @Override
    public Void visit(AST.DefFunDefn defFunDefn, Void arg) {
        depth++;

        // "write yourself, then change the value."
        offset.push(8L);
        defFunDefn.pars.accept(this, arg);
        offset.pop();

        // "change the value, then write yourself"
        offset.push(0L);
        defFunDefn.expr.accept(this, arg);
        offset.pop();
        depth--;

        long locsSize = defFunDefn.accept(new LocsSizeVisitor(), true);
        long argsSize = defFunDefn.accept(new ArgsSizeVisitor(), true);
        MEM.Label label = depth == 0 ? new MEM.Label(defFunDefn.name) : new MEM.Label();
        Memory.frameAttr.put(
                defFunDefn,
                new MEM.Frame(
                    label,
                    depth,
                    locsSize,
                    argsSize,
                    2 * 8L + locsSize + argsSize // return address + old FP
                )
        );
        return arg;
    }
    @Override
    public Void visit(AST.ExtFunDefn extFunDefn, Void arg) {
        // "write yourself, then change the value."
        offset.push(8L);
        extFunDefn.pars.accept(this, arg);
        offset.pop();
        return arg;
    }

    @Override
    public Void visit(AST.ParDefn parDefn, Void arg) {
        long size = SizeofType.sizeof(SemAn.isTypeAttr.get(parDefn.type), parDefn);
        long sizeRound = SizeofType.sizeofRound(size);
        Memory.accessAttr.put(
                parDefn,
                new MEM.RelAccess(
                        size,
                        offset.peek(),
                        depth
                )
        );
        offset.push(offset.pop() + sizeRound);
        return null;
    }



    @Override
    public Void visit(AST.StrType strType, Void arg) {
        offset.push(0L);
        for (AST.CompDefn compDefn: strType.comps) {
            long size = SizeofType.sizeof(SemAn.isTypeAttr.get(compDefn.type), compDefn);
            long sizeRound = SizeofType.sizeofRound(size);
            Memory.accessAttr.put(
                compDefn,
                new MEM.RelAccess(
                        size,
                        offset.peek(),
                        -1L
                ));
            offset.push(offset.pop() + sizeRound);
        }
        offset.pop();
        return null;
    }
    @Override
    public Void visit(AST.UniType uniType, Void arg) {
        for (AST.CompDefn compDefn: uniType.comps) {
            long size = SizeofType.sizeof(SemAn.isTypeAttr.get(compDefn.type), compDefn);
            Memory.accessAttr.put(
                compDefn,
                new MEM.RelAccess(
                        size,
                        0L,
                        -1L
                ));
        }
        return null;
    }


    @Override
    public Void visit(AST.VarDefn varDefn, Void arg) {
        // if type is record type defined inline, visit it
        varDefn.type.accept(this, arg);
        // top level
        long size = SizeofType.sizeof(SemAn.isTypeAttr.get(varDefn.type), varDefn);
        long sizeRound = SizeofType.sizeofRound(size);
        if (depth == 0) {
            Memory.accessAttr.put(
                    varDefn,
                    new MEM.AbsAccess(size, new MEM.Label(varDefn.name))
            );
        } else {
            offset.push(offset.pop() - sizeRound);
            Memory.accessAttr.put(
                    varDefn,
                    new MEM.RelAccess(
                            size,
                            offset.peek(),
                            depth
                    )
            );
        }
        return null;
    }

    @Override
    public Void visit(AST.AtomExpr atomExpr, Void arg) {
        if (atomExpr.type.equals(AST.AtomExpr.Type.STR)) {
            Memory.stringAttr.put(
                    atomExpr,
                    new MEM.AbsAccess(
                            atomExpr.value.length() - 2,
                            new MEM.Label(),
                            atomExpr.value
                    )
            );
        }
        return null;
    }
}