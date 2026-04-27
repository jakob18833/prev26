package prev26lang.phase.memory;


import prev26lang.phase.abstr.AST;
import prev26lang.phase.imrgen.ExprVisitor;
import prev26lang.phase.seman.SemAn;

import java.util.Arrays;
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

    class CharStream {
        private final String s;
        private int pos = 0;

        CharStream(String s) { this.s = s; }

        boolean hasNext() { return pos < s.length(); }
        char peek() { return s.charAt(pos); }
        char next() { return s.charAt(pos++); }
        String nextTwo() { return s.substring(pos, pos += 2); };

    }



    private String parseString(String c) {
        StringBuilder out = new StringBuilder();
        CharStream cs = new CharStream(c.substring(1, c.length()-1));
        while (cs.hasNext()) {
            char token = cs.next();
            if (token == '\\') {
                char token2 = cs.next();
                if (token2 == 'x') {
                    out.append((char) Long.parseLong(cs.nextTwo(), 16));
                } else if (token2 == '\\') out.append("\\");
                else if (token2 == '\"') out.append("\"");
            } else {
                out.append(token);
            }
        }
        out.append('\0');
        return out.toString();
    }

    @Override
    public Void visit(AST.AtomExpr atomExpr, Void arg) {
        if (atomExpr.type.equals(AST.AtomExpr.Type.STR)) {
            Memory.stringAttr.put(
                    atomExpr,
                    new MEM.AbsAccess(
                            atomExpr.value.length() - 1,
                            new MEM.Label(),
                            parseString(atomExpr.value)
                    )
            );
        }
        return null;
    }
}