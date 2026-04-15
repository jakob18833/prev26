package prev26lang.phase.imrgen;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.memory.Memory;
import prev26lang.phase.memory.SizeofType;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TYP;

public class AddrVisitor implements AST.FullVisitor<IMR.Expr, Void> {

    MEM.Frame frame;
    public AddrVisitor(MEM.Frame frame) {
        this.frame = frame;
    }
    @Override
    public IMR.Expr visit(AST.AtomExpr atomExpr, Void arg) {
        if (atomExpr.type.equals(AST.AtomExpr.Type.STR)) {
            MEM.AbsAccess access = Memory.stringAttr.get(atomExpr);
            return new IMR.NAME(access.label);
        } else return null;
    }

    @Override
    public IMR.Expr visit(AST.NameExpr nameExpr, Void arg) {
        // NameExpr could be one of the following:
        // 1. Variable
        // 2. Type
        // 3. Component
        // 4. Function
        // If it is a function or type (which it shouldn't be), we handle it differently, as its access atribute is not set.
        AST.Defn definedAt = SemAn.defAtAttr.get(nameExpr);
        if (definedAt instanceof AST.DefFunDefn defFunDefn) {
            MEM.Frame frame = Memory.frameAttr.get(defFunDefn);
            return new IMR.NAME(frame.label);
        } else if(definedAt instanceof AST.ExtFunDefn extFunDefn) {
            return new IMR.NAME(new MEM.Label());
        } else if(definedAt instanceof AST.TypDefn) {
            return new IMR.CONST(0L);
        }
        // We can now get its access attr
        MEM.Access access = Memory.accessAttr.get(definedAt);
        switch (access) {
            // remove this when you know whether we need it or not
            case null -> throw new Report.Error(nameExpr, "Called AddrVisitor on node with null access attribute!");
            case MEM.AbsAccess absAccess -> {
                return new IMR.NAME(absAccess.label);
            }
            case MEM.RelAccess relAccess -> {
                long varDepth = relAccess.depth;
                // walk the static link chain
                IMR.Expr FPAddress = new IMR.TEMP(frame.FP);
                for (long i = varDepth; i <= frame.depth; i++) {
                    FPAddress = new IMR.MEM8(FPAddress);
                }
                FPAddress = new IMR.BINOP(IMR.BINOP.Oper.ADD, FPAddress, new IMR.CONST(relAccess.offset));
                return FPAddress;
            }
            default -> throw new Report.InternalError();
        }
    }

    @Override
    public IMR.Expr visit(AST.ArrExpr arrExpr, Void arg) {
        IMR.Expr arrAddress = arrExpr.arrExpr.accept(this, arg);
        IMR.Expr indexExpr = arrExpr.idx.accept(new ExprVisitor(frame), arg);
        TYP.Type elementType = ((TYP.ArrType) SemAn.ofTypeAttr.get(arrExpr.arrExpr).actualType()).elemType;
        long elementSize = SizeofType.sizeof(elementType, null);
        IMR.Expr offset = new IMR.BINOP(IMR.BINOP.Oper.MUL, indexExpr, new IMR.CONST(elementSize));
        return new IMR.BINOP(IMR.BINOP.Oper.ADD, arrAddress, offset);
    }

    @Override
    public IMR.Expr visit(AST.CompExpr compExpr, Void arg) {
        IMR.Expr recordAddress = compExpr.recExpr.accept(this, arg);
        ImrGen.genExprIMRAttr.put(compExpr.recExpr, recordAddress);
        TYP.Type recordType = SemAn.ofTypeAttr.get(compExpr.recExpr).actualType();
        AST.RecType recAST = (AST.RecType) SemAn.isTypeAttr.reverseGet(recordType);
        for (var compDefn: recAST.comps) {
            if (compDefn.name.equals(compExpr.name)) {
                long recordOffset = ((MEM.RelAccess) Memory.accessAttr.get(compDefn)).offset;
                return new IMR.BINOP(IMR.BINOP.Oper.ADD, recordAddress, new IMR.CONST(recordOffset));
            }
        }
        return null;
    }

    @Override
    public IMR.Expr visit(AST.SfxExpr sfxExpr, Void arg) {
        return sfxExpr.subExpr.accept(new ExprVisitor(frame), null);
    }
}
