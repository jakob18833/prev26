package prev26lang.phase.imrgen;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.memory.Memory;
import prev26lang.phase.memory.SizeofType;
import prev26lang.phase.seman.SemAn;
import prev26lang.phase.seman.TYP;
import prev26lang.phase.seman.TypeEquivalence;

import java.util.Vector;

public class ExprVisitor implements AST.FullVisitor<IMR.Expr, Void> {
    MEM.Frame frame;
    long MAX_CONST = Long.MAX_VALUE;
    long SL_CONST = 0xEEEE_EEEEL;
    long VOID_CONST = 0xCCCC_CCCCL;

    public ExprVisitor(MEM.Frame frame) {
        this.frame = frame;
    }

    // Writes to the genExpr!!
    IMR.Expr memoryHelper(AST.Node node) {
        IMR.Expr address = node.accept(new AddrVisitor(frame), null);
        TYP.Type type = SemAn.ofTypeAttr.get(node);
        IMR.Expr result;
        if (type.actualType() instanceof TYP.BoolType || type.actualType() instanceof TYP.CharType) {
            result = new IMR.MEM1(address);
        } else result = new IMR.MEM8(address);
        ImrGen.genExprIMRAttr.put(node, result);

        return result;
    }


    private long parseChar(String c) {
        if (c.charAt(1) == '\\') {
            if (c.charAt(2) == 'x') {
                return (long) Long.parseLong(c.substring(3, 5), 16);
            } else if (c.charAt(2) == '\\') return 92L;
            else if (c.charAt(2) == '\'') return 39L;
            else throw new Report.Error("WTF");
        } else return (long) c.charAt(1);
    }


    @Override
    public IMR.Expr visit(AST.AtomExpr atomExpr, Void arg) {
        IMR.Expr result = switch (atomExpr.type) {
            case INT -> new IMR.CONST(Long.parseLong(atomExpr.value));
            case CHAR -> new IMR.CONST(parseChar(atomExpr.value));
            case BOOL -> atomExpr.value.equals("true") ? new IMR.CONST(1L) : new IMR.CONST(0L);
            case VOID -> new IMR.CONST(VOID_CONST);
            case PTR -> new IMR.CONST(0L);
            case STR -> atomExpr.accept(new AddrVisitor(frame), null);
        };

        ImrGen.genExprIMRAttr.put(atomExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.PfxExpr pfxExpr, Void arg) {
        IMR.Expr result = switch (pfxExpr.oper) {
            case ADD -> pfxExpr.subExpr.accept(this, arg);
            case SUB -> new IMR.UNOP(IMR.UNOP.Oper.NEG, pfxExpr.subExpr.accept(this, arg));
            case NOT -> new IMR.UNOP(IMR.UNOP.Oper.NOT, pfxExpr.subExpr.accept(this, arg));
            case PTR -> pfxExpr.subExpr.accept(new AddrVisitor(frame), null);
        };
        ImrGen.genExprIMRAttr.put(pfxExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.BinExpr binExpr, Void arg) {
        IMR.BINOP.Oper oper = switch (binExpr.oper) {
            case OR -> IMR.BINOP.Oper.OR;
            case AND -> IMR.BINOP.Oper.AND;
            case EQU -> IMR.BINOP.Oper.EQU;
            case NEQ -> IMR.BINOP.Oper.NEQ;
            case LTH -> IMR.BINOP.Oper.LTH;
            case GTH -> IMR.BINOP.Oper.GTH;
            case LEQ -> IMR.BINOP.Oper.LEQ;
            case GEQ -> IMR.BINOP.Oper.GEQ;
            case MUL -> IMR.BINOP.Oper.MUL;
            case DIV -> IMR.BINOP.Oper.DIV;
            case MOD -> IMR.BINOP.Oper.MOD;
            case ADD -> IMR.BINOP.Oper.ADD;
            case SUB -> IMR.BINOP.Oper.SUB;
        };
        IMR.Expr result = new IMR.BINOP(oper, binExpr.fstExpr.accept(this, arg), binExpr.sndExpr.accept(this, arg));
        ImrGen.genExprIMRAttr.put(binExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.SfxExpr sfxExpr, Void arg) {
        return memoryHelper(sfxExpr);
    }

    @Override
    public IMR.Expr visit(AST.NameExpr nameExpr, Void arg) {
        return memoryHelper(nameExpr);
    }

    @Override
    public IMR.Expr visit(AST.Exprs exprs, Void arg) {
        Vector<IMR.Stmt> stmts = new Vector<>();
        for (var i = 0; i < exprs.exprs.size() - 1; i++) {
            stmts.add(new IMR.ESTMT(exprs.exprs.get(i).accept(this, arg)));
        }
        IMR.Expr result = new IMR.SEXPR(new IMR.STMTS(stmts), exprs.exprs.last().accept(this, arg));
        ImrGen.genExprIMRAttr.put(exprs, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.ArrExpr arrExpr, Void arg) {
        return memoryHelper(arrExpr);
    }

    @Override
    public IMR.Expr visit(AST.CompExpr compExpr, Void arg) {
        // Just to set the attributes
        compExpr.recExpr.accept(this, arg);

        return memoryHelper(compExpr);
    }


    @Override
    public IMR.Expr visit(AST.CallExpr callExpr, Void arg) {

        // We have mulitple scenarios:
        // 1. callExpr.funExpr is a regular NameExpr, which is linked to some definition.
        //  a) It is linked to a ExtFunDefn. We create a new label and set SL to anything.
        //  b) It is linked to a DefFunDefn. We compare the depths to set SL correctly.
        // 2. callExpr.funExpr is stored in an array, structure, union, is a pointer dereference or a local variable.
        //  We have no way of knowing what to put for SL. We hope the programmer knows what he is doing.
        AST.Node funDefn = null;
        try {
            funDefn = SemAn.defAtAttr.get(callExpr.funExpr);
        } catch (Report.InternalError _) {
        }
        TYP.FunType funType = (TYP.FunType) SemAn.ofTypeAttr.get(callExpr.funExpr).actualType();

        IMR.Expr funAddress = null;
        IMR.Expr SL = new IMR.CONST(SL_CONST);

        // 1. scenario - we read the address
        if (funDefn instanceof AST.FunDefn) {

            funAddress = callExpr.funExpr.accept(new AddrVisitor(frame), arg);
            ImrGen.genExprIMRAttr.put(callExpr.funExpr, funAddress);

            // 1b
            if (funDefn instanceof AST.DefFunDefn defFunDefn) {
                MEM.Frame otherFrame = Memory.frameAttr.get(defFunDefn);
                if (otherFrame.depth != 0) {
                    SL = new IMR.TEMP(this.frame.FP);
                    for (int i = 0; i <= this.frame.depth - otherFrame.depth; i++) {
                        SL = new IMR.MEM8(SL);
                    }
                }
            }
            // 2. scenario - we read the expression
        } else {
            funAddress = callExpr.funExpr.accept(this, arg);
            ImrGen.genExprIMRAttr.put(callExpr.funExpr, funAddress);
        }
        Vector<Long> offsets = new Vector<>();
        Vector<IMR.Expr> arguments = new Vector<>();
        offsets.add(0L);
        arguments.add(SL);

        long offset = 8L;
        for (var type : funType.parTypes) {
            offsets.add(offset);
            offset += SizeofType.sizeofRoundType(type, null);
        }
        for (var argExpr : callExpr.argExprs) {
            arguments.add(argExpr.accept(this, arg));
        }
        IMR.Expr result = new IMR.CALL(funAddress, offsets, arguments);
        ImrGen.genExprIMRAttr.put(callExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.CastExpr castExpr, Void arg) {
        IMR.Expr result = castExpr.expr.accept(this, arg);
        if (TypeEquivalence.equivalent(SemAn.isTypeAttr.get(castExpr.type), TYP.BoolType.type)) {
            result = new IMR.BINOP(IMR.BINOP.Oper.MOD, result, new IMR.CONST(2L));
        } else if (TypeEquivalence.equivalent(SemAn.isTypeAttr.get(castExpr.type), TYP.CharType.type)) {
            result = new IMR.BINOP(IMR.BINOP.Oper.MOD, result, new IMR.CONST(256L));
        }
        ImrGen.genExprIMRAttr.put(castExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.SizeExpr sizeExpr, Void arg) {
        TYP.Type type = SemAn.isTypeAttr.get(sizeExpr.type);
        IMR.Expr result = new IMR.CONST(SizeofType.sizeof(type, null));
        ImrGen.genExprIMRAttr.put(sizeExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.AsgnExpr asgnExpr, Void arg) {
        IMR.Expr address = memoryHelper(asgnExpr.fstExpr);
        IMR.Expr value = asgnExpr.sndExpr.accept(this, arg);
        IMR.Stmt stmt = new IMR.MOVE(address, value);
        IMR.Expr result = new IMR.SEXPR(stmt, new IMR.CONST(0L));
        ImrGen.genExprIMRAttr.put(asgnExpr, result);
        return result;
    }


    @Override
    public IMR.Expr visit(AST.IfThenExpr ifThenExpr, Void arg) {
        // Condition is a variable of type bool.
        MEM.Label positiveLabel = new MEM.Label();
        MEM.Label endLabel = new MEM.Label();
        IMR.Stmt jump = new IMR.CJUMP(
                ifThenExpr.condExpr.accept(this, arg),
                new IMR.NAME(positiveLabel),
                new IMR.NAME(endLabel)
        );
        Vector<IMR.Stmt> stmts = new Vector<>();
        stmts.add(jump);
        stmts.add(new IMR.LABEL(positiveLabel));
        stmts.add(new IMR.ESTMT(ifThenExpr.thenExpr.accept(this, arg)));
        stmts.add(new IMR.JUMP(new IMR.NAME(endLabel)));
        stmts.add(new IMR.LABEL(endLabel));
        IMR.Stmt ifStmt = new IMR.STMTS(stmts);
        IMR.Expr result = new IMR.SEXPR(ifStmt, new IMR.CONST(0L));
        ImrGen.genExprIMRAttr.put(ifThenExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.IfThenElseExpr ifThenElseExpr, Void arg) {
        // Labels
        MEM.Label positiveLabel = new MEM.Label();
        MEM.Label negativeLabel = new MEM.Label();
        MEM.Label endLabel = new MEM.Label();
        // Jump
        IMR.Stmt jump = new IMR.CJUMP(
                ifThenElseExpr.condExpr.accept(this, arg),
                new IMR.NAME(positiveLabel),
                new IMR.NAME(negativeLabel)
        );
        // Positive statement

        // Gluing together
        Vector<IMR.Stmt> ifStmtVector = new Vector<>();
        ifStmtVector.add(jump);
        ifStmtVector.add(new IMR.LABEL(positiveLabel));
        ifStmtVector.add(new IMR.ESTMT(ifThenElseExpr.thenExpr.accept(this, arg)));
        ifStmtVector.add(new IMR.JUMP(new IMR.NAME(endLabel)));
        ifStmtVector.add(new IMR.LABEL(negativeLabel));
        ifStmtVector.add(new IMR.ESTMT(ifThenElseExpr.elseExpr.accept(this, arg)));
        ifStmtVector.add(new IMR.JUMP(new IMR.NAME(endLabel)));
        ifStmtVector.add(new IMR.LABEL(endLabel));
        IMR.Stmt ifStmt = new IMR.STMTS(ifStmtVector);
        IMR.Expr result = new IMR.SEXPR(ifStmt, new IMR.CONST(0L));
        ImrGen.genExprIMRAttr.put(ifThenElseExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.WhileExpr whileExpr, Void arg) {

        MEM.Label startLabel = new MEM.Label();
        MEM.Label positiveLabel = new MEM.Label();
        MEM.Label endLabel = new MEM.Label();
        // Jump
        IMR.Stmt jump = new IMR.CJUMP(
                whileExpr.condExpr.accept(this, arg),
                new IMR.NAME(positiveLabel),
                new IMR.NAME(endLabel)
        );
        // Positive statement
        // Gluing together
        Vector<IMR.Stmt> whileStmtVector = new Vector<>();
        whileStmtVector.add(new IMR.LABEL(startLabel));
        whileStmtVector.add(jump);
        whileStmtVector.add(new IMR.LABEL(positiveLabel));
        whileStmtVector.add(new IMR.ESTMT(whileExpr.expr.accept(this, arg)));
        whileStmtVector.add(new IMR.JUMP(new IMR.NAME(startLabel)));
        whileStmtVector.add(new IMR.LABEL(endLabel));
        IMR.Stmt whileStmt = new IMR.STMTS(whileStmtVector);
        IMR.Expr result = new IMR.SEXPR(whileStmt, new IMR.CONST(0L));
        ImrGen.genExprIMRAttr.put(whileExpr, result);
        return result;
    }

    @Override
    public IMR.Expr visit(AST.LetExpr letExpr, Void arg) {
        for (var defn : letExpr.defns) {
            if (defn instanceof AST.DefFunDefn defFunDefn) {
                ImrGenerator.visit(defFunDefn);
            }
        }
        IMR.Expr result = letExpr.expr.accept(this, arg);
        ImrGen.genExprIMRAttr.put(letExpr, result);
        return result;
    }
}
