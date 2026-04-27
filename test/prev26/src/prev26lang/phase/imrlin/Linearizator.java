package prev26lang.phase.imrlin;

import prev26lang.phase.abstr.AST;
import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.imrgen.ImrGen;
import prev26lang.phase.memory.MEM;
import prev26lang.phase.seman.SemAn;

import java.util.Vector;

public class Linearizator implements IMR.Visitor<IMR.Expr, Boolean> {
    private Vector<IMR.Stmt> canonicalTrees;
    public Vector<IMR.Stmt> linearize(IMR.Stmt stmt) {
        canonicalTrees = new Vector<>();
        stmt.accept(this, null);
        return canonicalTrees;
    }

    // =====================
    // EXPRESSIONS
    // =====================

    @Override
    public IMR.Expr visit(IMR.CONST constant, Boolean arg) {
        return constant;
    }

    @Override
    public IMR.Expr visit(IMR.UNOP unOp, Boolean arg) {
        return new IMR.UNOP(unOp.oper, unOp.subExpr.accept(this, false));
    }
    @Override
    public IMR.Expr visit(IMR.BINOP binOp, Boolean arg) {
        return new IMR.BINOP(binOp.oper, binOp.fstExpr.accept(this, false), binOp.sndExpr.accept(this, false));
    }

    @Override
    public IMR.Expr visit(IMR.CALL call, Boolean arg) {
        Vector<IMR.Expr> args = new Vector<>();
        // SL is not linearized
        args.add(call.args.firstElement());

        for (int i = 1; i < call.args.size(); i++) {
            IMR.Expr imrExpr = call.args.get(i);
            AST.Expr astExpr = (AST.Expr) ImrGen.genExprIMRAttr.reverseGet(imrExpr);
            if (SemAn.isConstAttr.get(astExpr)) {
                args.add(imrExpr);
            } else {
                MEM.Temp memTemp = new MEM.Temp();
                IMR.TEMP imrTemp = new IMR.TEMP(memTemp);
                args.add(imrTemp);

                canonicalTrees.add(new IMR.MOVE(imrTemp, imrExpr.accept(this, false)));
            }
        }
        IMR.CALL newCall = new IMR.CALL(call.addr, call.offs, args);
        if (arg == true) return newCall;
        else {
            MEM.Temp temp = new MEM.Temp();
            IMR.TEMP imrTemp = new IMR.TEMP(temp);
            canonicalTrees.add(new IMR.MOVE(imrTemp, newCall));
            return imrTemp;
        }
    }

    @Override
    public IMR.Expr visit(IMR.MEM1 mem, Boolean arg) {
        return new IMR.MEM1(mem.addr.accept(this, false));
    }

    @Override
    public IMR.Expr visit(IMR.MEM8 mem, Boolean arg) {
        return new IMR.MEM8(mem.addr.accept(this, false));
    }
    
    @Override
    public IMR.Expr visit(IMR.NAME name, Boolean arg) {
        return name;
    }
    @Override
    public IMR.Expr visit(IMR.TEMP temp, Boolean arg) {
        return temp;
    }

    @Override
    public IMR.Expr visit(IMR.SEXPR sExpr, Boolean arg) {
        sExpr.stmt.accept(this, false);
        return sExpr.expr.accept(this, false);
    }


    // =====================
    // STATEMENTS
    // =====================


    @Override
    public IMR.Expr visit(IMR.STMTS stmts, Boolean arg) {
        for (IMR.Stmt stmt : stmts.stmts) {
            stmt.accept(this, false);
        }
        return null;
    }

    @Override
    public IMR.Expr visit(IMR.ESTMT eStmt, Boolean arg) {

        IMR.Expr expr = eStmt.expr.accept(this, true);
        // The only IMR.Expr that can have side effects after linearization.
        if (expr instanceof IMR.CALL) canonicalTrees.add(new IMR.ESTMT(expr));

        return null;
    }

    @Override
    public IMR.Expr visit(IMR.CJUMP cjump, Boolean arg) {
        canonicalTrees.add(
                new IMR.CJUMP(
                        cjump.cond.accept(this, false),
                        cjump.posAddr.accept(this, false),
                        cjump.negAddr.accept(this, false)
                )
        );
        return null;
    }

    @Override
    public IMR.Expr visit(IMR.JUMP jump, Boolean arg) {
        canonicalTrees.add(new IMR.JUMP(jump.addr.accept(this, false)));
        return null;
    }





    @Override
    public IMR.Expr visit(IMR.LABEL label, Boolean arg) {
        canonicalTrees.add(label);
        return null;
    }



    @Override
    public IMR.Expr visit(IMR.MOVE move, Boolean arg) {
        canonicalTrees.add(new IMR.MOVE(move.dst.accept(this, false), move.src.accept(this, true)));
        return null;
    }





}
