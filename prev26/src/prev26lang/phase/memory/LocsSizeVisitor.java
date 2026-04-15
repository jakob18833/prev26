package prev26lang.phase.memory;

import prev26lang.phase.abstr.AST;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TYP;

public class LocsSizeVisitor implements AST.FullVisitor<Long, Boolean> {
    long sum = 0L;

    @Override
    public Long visit(AST.VarDefn varDefn, Boolean arg) {
        sum += SizeofType.sizeof(SemAn.isTypeAttr.get(varDefn.type), varDefn);
        return null;
    }

    @Override
    public Long visit(AST.DefFunDefn defFunDefn, Boolean firstTime) {
        if (firstTime) {
            defFunDefn.expr.accept(this, false);
            return sum;
        } else return null;
    }
}
