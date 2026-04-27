package prev26lang.phase.asmgen;

import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.imrlin.LIN;
import prev26lang.phase.memory.MEM;

import java.util.ArrayList;
import java.util.List;

public class AsmVisitor implements IMR.Visitor<ASM.InstructionArgument, Void> {

    List<ASM.Instruction> instructions = new ArrayList<>();
    ASM.TempRegFactory tempRegFactory = new ASM.TempRegFactory();
    private MEM.Frame frame;

    /**
     * Singleton temp pinned to RISC-V a0 (x10) by the register allocator.
     * Used to read the return value immediately after a call instruction.
     */
    public static final MEM.Temp A0 = new MEM.Temp();

    public List<ASM.Instruction> visit(LIN.CodeChunk codeChunk) {
        this.frame = codeChunk.frame;
        instructions.add(new ASM.LabelInstr(codeChunk.frame.label));
        for (var canTree: codeChunk.stmts()) {
            canTree.accept(this, null);
        }
        return instructions;
    }

    /** Ensures arg is in a register, emitting li/la if it is a Constant/Label. */
    private ASM.TempReg toTempReg(ASM.InstructionArgument arg) {
        if (arg instanceof ASM.TempReg tr) return tr;
        ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
        if (arg instanceof ASM.Constant c) instructions.add(new ASM.li(t, c));
        else if (arg instanceof ASM.Label l) instructions.add(new ASM.la(t, l));
        return t;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.CONST constant, Void visArg) {
        return new ASM.Constant(constant.value);
    }

    @Override
    public ASM.InstructionArgument visit(IMR.TEMP temp, Void visArg) {
        return tempRegFactory.get(temp.temp);
    }

    @Override
    public ASM.InstructionArgument visit(IMR.NAME name, Void visArg) {
        return new ASM.Label(name.label);
    }

    @Override
    public ASM.InstructionArgument visit(IMR.BINOP binOp, Void visArg) {
        ASM.InstructionArgument fst = binOp.fstExpr.accept(this, visArg);
        ASM.InstructionArgument snd = binOp.sndExpr.accept(this, visArg);

        // Constant folding
        if (fst instanceof ASM.Constant fc && snd instanceof ASM.Constant sc) {
            long v1 = fc.value, v2 = sc.value;
            return switch (binOp.oper) {
                case ADD -> new ASM.Constant(v1 + v2);
                case SUB -> new ASM.Constant(v1 - v2);
                case MUL -> new ASM.Constant(v1 * v2);
                case DIV -> new ASM.Constant(v1 / v2);
                case MOD -> new ASM.Constant(v1 % v2);
                case OR  -> new ASM.Constant(v1 | v2);
                case AND -> new ASM.Constant(v1 & v2);
                case EQU -> new ASM.Constant(v1 == v2 ? 1 : 0);
                case NEQ -> new ASM.Constant(v1 != v2 ? 1 : 0);
                case LTH -> new ASM.Constant(v1 <  v2 ? 1 : 0);
                case GTH -> new ASM.Constant(v1 >  v2 ? 1 : 0);
                case LEQ -> new ASM.Constant(v1 <= v2 ? 1 : 0);
                case GEQ -> new ASM.Constant(v1 >= v2 ? 1 : 0);
            };
        }

        ASM.TempReg rd = tempRegFactory.get(new MEM.Temp());

        // Immediate forms when snd is a constant
        if (snd instanceof ASM.Constant sc) {
            ASM.TempReg rs1 = toTempReg(fst);
            switch (binOp.oper) {
                case ADD -> { instructions.add(new ASM.addi(rd, rs1, sc)); return rd; }
                case OR -> { instructions.add(new ASM.ori(rd, rs1, sc)); return rd; }
                case AND -> { instructions.add(new ASM.andi(rd, rs1, sc)); return rd; }
                case LTH -> { instructions.add(new ASM.slti(rd, rs1, sc)); return rd; }
                case EQU -> {
                    ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                    instructions.add(new ASM.xori(t, rs1, sc));
                    instructions.add(new ASM.seqz(rd, t));
                    return rd;
                }
                case NEQ -> {
                    ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                    instructions.add(new ASM.xori(t, rs1, sc));
                    instructions.add(new ASM.snez(rd, t));
                    return rd;
                }
                default -> {} // fall through to register form
            }
        }

        // Commutative immediate forms when fst is a constant
        if (fst instanceof ASM.Constant fc) {
            ASM.TempReg rs1 = toTempReg(snd);
            switch (binOp.oper) {
                case ADD -> { instructions.add(new ASM.addi(rd, rs1, fc)); return rd; }
                case OR -> { instructions.add(new ASM.ori(rd, rs1, fc)); return rd; }
                case AND -> { instructions.add(new ASM.andi(rd, rs1, fc)); return rd; }
                case EQU -> {
                    ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                    instructions.add(new ASM.xori(t, rs1, fc));
                    instructions.add(new ASM.seqz(rd, t));
                    return rd;
                }
                case NEQ -> {
                    ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                    instructions.add(new ASM.xori(t, rs1, fc));
                    instructions.add(new ASM.snez(rd, t));
                    return rd;
                }
                default -> {} // materialize and use register form
            }
        }

        // Register form (also reached by fall-through from the immediate sections)
        ASM.TempReg rs1 = toTempReg(fst);
        ASM.TempReg rs2 = toTempReg(snd);
        switch (binOp.oper) {
            case ADD -> instructions.add(new ASM.add(rd, rs1, rs2));
            case SUB -> instructions.add(new ASM.sub(rd, rs1, rs2));
            case MUL -> instructions.add(new ASM.mul(rd, rs1, rs2));
            case DIV -> instructions.add(new ASM.div(rd, rs1, rs2));
            case MOD -> instructions.add(new ASM.rem(rd, rs1, rs2));
            case OR  -> instructions.add(new ASM.or(rd, rs1, rs2));
            case AND -> instructions.add(new ASM.and(rd, rs1, rs2));
            case LTH -> instructions.add(new ASM.slt(rd, rs1, rs2));
            case GTH -> instructions.add(new ASM.slt(rd, rs2, rs1));
            case LEQ -> {
                ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                instructions.add(new ASM.slt(t, rs2, rs1));
                instructions.add(new ASM.xori(rd, t, new ASM.Constant(1)));
            }
            case GEQ -> {
                ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                instructions.add(new ASM.slt(t, rs1, rs2));
                instructions.add(new ASM.xori(rd, t, new ASM.Constant(1)));
            }
            case EQU -> {
                ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                instructions.add(new ASM.xor(t, rs1, rs2));
                instructions.add(new ASM.seqz(rd, t));
            }
            case NEQ -> {
                ASM.TempReg t = tempRegFactory.get(new MEM.Temp());
                instructions.add(new ASM.xor(t, rs1, rs2));
                instructions.add(new ASM.snez(rd, t));
            }
        }
        return rd;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.UNOP unOp, Void visArg) {
        ASM.InstructionArgument sub = unOp.subExpr.accept(this, visArg);
        if (sub instanceof ASM.Constant c) {
            return switch (unOp.oper) {
                case NEG -> new ASM.Constant(-c.value);
                case NOT -> new ASM.Constant(c.value == 0 ? 1 : 0);
            };
        }
        ASM.TempReg rs = toTempReg(sub);
        ASM.TempReg rd = tempRegFactory.get(new MEM.Temp());
        switch (unOp.oper) {
            case NEG -> instructions.add(new ASM.neg(rd, rs));
            case NOT -> instructions.add(new ASM.seqz(rd, rs));
        }
        return rd;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.MEM1 mem, Void visArg) {
        ASM.TempReg addr = toTempReg(mem.addr.accept(this, visArg));
        ASM.TempReg rd = tempRegFactory.get(new MEM.Temp());
        instructions.add(new ASM.lb(rd, new ASM.Constant(0), addr));
        return rd;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.MEM8 mem, Void visArg) {
        ASM.TempReg addr = toTempReg(mem.addr.accept(this, visArg));
        ASM.TempReg rd = tempRegFactory.get(new MEM.Temp());
        instructions.add(new ASM.ld(rd, new ASM.Constant(0), addr));
        return rd;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.SEXPR sExpr, Void visArg) {
        sExpr.stmt.accept(this, visArg);
        return sExpr.expr.accept(this, visArg);
    }

    @Override
    public ASM.InstructionArgument visit(IMR.CALL call, Void visArg) {
        // Outgoing args live at SP = FP - frame.size, at offsets 0, 8, 16, ...
        ASM.TempReg fp = tempRegFactory.get(frame.FP);
        ASM.TempReg sp = tempRegFactory.get(new MEM.Temp());
        instructions.add(new ASM.addi(sp, fp, new ASM.Constant(-frame.size)));

        // Evaluate and store each argument (left-to-right per IMR spec)
        for (int i = 0; i < call.args.size(); i++) {
            ASM.TempReg arg = toTempReg(call.args.get(i).accept(this, visArg));
            instructions.add(new ASM.sd(arg, new ASM.Constant(call.offs.get(i)), sp));
        }

        // Issue the call
        ASM.InstructionArgument addr = call.addr.accept(this, visArg);
        if (addr instanceof ASM.Label label) {
            instructions.add(new ASM.call(label));
        } else {
            ASM.TempReg ra = tempRegFactory.get(new MEM.Temp());
            instructions.add(new ASM.jalr(ra, toTempReg(addr), new ASM.Constant(0)));
        }

        // Copy return value from a0 into a fresh temp to prevent aliasing across calls
        ASM.TempReg a0 = tempRegFactory.get(A0);
        ASM.TempReg result = tempRegFactory.get(new MEM.Temp());
        instructions.add(new ASM.mv(result, a0));
        return result;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.STMTS stmts, Void visArg) {
        for (IMR.Stmt stmt : stmts.stmts) {
            stmt.accept(this, visArg);
        }
        return null;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.ESTMT eStmt, Void visArg) {
        eStmt.expr.accept(this, visArg);
        return null;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.LABEL label, Void visArg) {
        instructions.add(new ASM.LabelInstr(label.label));
        return null;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.JUMP jump, Void visArg) {
        ASM.InstructionArgument addr = jump.addr.accept(this, visArg);
        if (addr instanceof ASM.Label label) {
            instructions.add(new ASM.j(label));
        } else {
            instructions.add(new ASM.jr(toTempReg(addr)));
        }
        return null;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.CJUMP cjump, Void visArg) {
        ASM.TempReg cond = toTempReg(cjump.cond.accept(this, visArg));
        ASM.Label posLabel = (ASM.Label) cjump.posAddr.accept(this, visArg);
        ASM.Label negLabel = (ASM.Label) cjump.negAddr.accept(this, visArg);
        instructions.add(new ASM.bnez(cond, posLabel, negLabel));
        return null;
    }

    @Override
    public ASM.InstructionArgument visit(IMR.MOVE move, Void visArg) {
        if (move.dst instanceof IMR.TEMP dstTemp) {
            ASM.TempReg dst = tempRegFactory.get(dstTemp.temp);
            ASM.InstructionArgument src = move.src.accept(this, visArg);
            if (src instanceof ASM.Constant c) instructions.add(new ASM.li(dst, c));
            else if (src instanceof ASM.Label l) instructions.add(new ASM.la(dst, l));
            else instructions.add(new ASM.mv(dst, (ASM.TempReg) src));
        } else if (move.dst instanceof IMR.MEM8 mem8) {
            ASM.TempReg addr = toTempReg(mem8.addr.accept(this, visArg));
            ASM.TempReg src = toTempReg(move.src.accept(this, visArg));
            instructions.add(new ASM.sd(src, new ASM.Constant(0), addr));
        } else if (move.dst instanceof IMR.MEM1 mem1) {
            ASM.TempReg addr = toTempReg(mem1.addr.accept(this, visArg));
            ASM.TempReg src = toTempReg(move.src.accept(this, visArg));
            instructions.add(new ASM.sb(src, new ASM.Constant(0), addr));
        }
        return null;
    }
}
