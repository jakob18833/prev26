package prev26lang.phase.memory;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TYP;

public class ArgsSizeVisitor implements AST.FullVisitor<Long, Boolean> {
    long longestCall = 8L;

    @Override
    public Long visit(AST.CallExpr callExpr, Boolean arg) {
        // We have to look for nested funtions and their argsSizes
        callExpr.argExprs.accept(this, arg);
        callExpr.funExpr.accept(this, arg);

        long argsSize = 8L;
        TYP.FunType funType = (TYP.FunType) SemAn.ofTypeAttr.get(callExpr.funExpr).actualType();

        for (var par: funType.parTypes) {
            argsSize += SizeofType.sizeof(par, callExpr);
        }
        if (argsSize > longestCall) longestCall = argsSize;
        return null;
    }

    @Override
    public Long visit(AST.DefFunDefn defFunDefn, Boolean firstTime) {
        if (firstTime) {
            defFunDefn.expr.accept(this, false);
            return longestCall;
        } else return null;
    }
}
