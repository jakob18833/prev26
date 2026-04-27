package prev26lang.phase.asmgen;

import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.memory.MEM;

import java.util.*;

public class ASM {

    // Constant, register, temporary, label
    public static abstract class InstructionArgument {
    }

    public static class Constant extends InstructionArgument {
        long value;

        public Constant(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return Long.toString(value);
        }
    }

    public enum RegisterCode {
        zero, ra, sp, gp, tp, t0, t1, t2, s0, s1, a0, a1,
        a2, a3, a4, a5, a6, a7, s2, s3, s4, s5, s6, s7, s8, s9, s10,
        s11, t3, t4, t5, t6, pc
    }

    public static EnumMap<RegisterCode, Integer> registers = new EnumMap<>(
            Map.ofEntries(
                    Map.entry(RegisterCode.zero, 0),
                    Map.entry(RegisterCode.ra, 1),
                    Map.entry(RegisterCode.sp, 2),
                    Map.entry(RegisterCode.gp, 3),
                    Map.entry(RegisterCode.tp, 4),
                    Map.entry(RegisterCode.t0, 5),
                    Map.entry(RegisterCode.t1, 6),
                    Map.entry(RegisterCode.t2, 7),
                    Map.entry(RegisterCode.s0, 8),
                    Map.entry(RegisterCode.s1, 9),
                    Map.entry(RegisterCode.a0, 10),
                    Map.entry(RegisterCode.a1, 11),
                    Map.entry(RegisterCode.a2, 12),
                    Map.entry(RegisterCode.a3, 13),
                    Map.entry(RegisterCode.a4, 14),
                    Map.entry(RegisterCode.a5, 15),
                    Map.entry(RegisterCode.a6, 16),
                    Map.entry(RegisterCode.a7, 17),
                    Map.entry(RegisterCode.s2, 18),
                    Map.entry(RegisterCode.s3, 19),
                    Map.entry(RegisterCode.s4, 20),
                    Map.entry(RegisterCode.s5, 21),
                    Map.entry(RegisterCode.s6, 22),
                    Map.entry(RegisterCode.s7, 23),
                    Map.entry(RegisterCode.s8, 24),
                    Map.entry(RegisterCode.s9, 25),
                    Map.entry(RegisterCode.s10, 26),
                    Map.entry(RegisterCode.s11, 27),
                    Map.entry(RegisterCode.t3, 28),
                    Map.entry(RegisterCode.t4, 29),
                    Map.entry(RegisterCode.t5, 30),
                    Map.entry(RegisterCode.t6, 31),
                    Map.entry(RegisterCode.pc, 32)
            )
    );

    private static class Register extends InstructionArgument {

        RegisterCode code;
        Integer ordinal;

        public Register(RegisterCode code) {
            this.code = code;
            this.ordinal = registers.get(code);
        }

        @Override
        public String toString() {
            return code.name();
        }
    }

    public static class TempRegFactory {
        HashMap<MEM.Temp, TempReg> tempMap = new HashMap<>();

        public TempReg get(MEM.Temp temp) {
            if (tempMap.get(temp) == null) {
                tempMap.put(temp, new TempReg(temp));
            }
            return tempMap.get(temp);
        }
    }

    public static class TempReg extends InstructionArgument {
        MEM.Temp temp;
        Register register = null;

        private TempReg(MEM.Temp temp) {
            this.temp = temp;
        }

        public void setRegister(Register register) {
            this.register = register;
        }

        @Override
        public String toString() {
            if (register == null) return temp.toString();
            else return register.toString();
        }
    }

    public static class Label extends InstructionArgument {
        MEM.Label label;

        public Label(MEM.Label label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label.toString();
        }
    }

    public static class OffsetTempReg extends InstructionArgument {
        Constant offset;
        TempReg tempReg;

        public OffsetTempReg(Constant offset, TempReg tempReg) {
            this.offset = offset;
            this.tempReg = tempReg;
        }

        @Override
        public String toString() {
            return offset + "(" + tempReg + ")";
        }
    }

    public abstract static class Instruction {

        String instr;
        List<InstructionArgument> instrArgs;
        List<TempReg> writes = new ArrayList<>();
        List<TempReg> reads = new ArrayList<>();
        List<InstructionArgument> jumps = new ArrayList<>();
        Boolean simpleMove = false;

        public Instruction(String instr, List<InstructionArgument> instrArgs) {
            this.instr = instr;
            this.instrArgs = instrArgs;
        }

        @Override
        public String toString() {
            StringBuilder argString = new StringBuilder(instrArgs.getFirst().toString());
            for (int i = 1; i < instrArgs.size(); i++) {
                argString.append(", ").append(instrArgs.get(i));
            }
            return instr + " " + argString.toString();
        }
    }

    // Arithmetic

    /** {@code rd = rs1 + rs2} */
    public static class add extends Instruction {
        public add(TempReg rd, TempReg rs1, TempReg rs2) {
            super("add", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 + imm} */
    public static class addi extends Instruction {
        public addi(TempReg rd, TempReg rs1, Constant imm) {
            super("addi", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = rs1 - rs2} */
    public static class sub extends Instruction {
        public sub(TempReg rd, TempReg rs1, TempReg rs2) {
            super("sub", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = (rs1 + rs2)[31:0]}, sign-extended to 64 bits */
    public static class addw extends Instruction {
        public addw(TempReg rd, TempReg rs1, TempReg rs2) {
            super("addw", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = (rs1 + imm)[31:0]}, sign-extended to 64 bits */
    public static class addiw extends Instruction {
        public addiw(TempReg rd, TempReg rs1, Constant imm) {
            super("addiw", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = (rs1 - rs2)[31:0]}, sign-extended to 64 bits */
    public static class subw extends Instruction {
        public subw(TempReg rd, TempReg rs1, TempReg rs2) {
            super("subw", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 * rs2} (lower 64 bits) */
    public static class mul extends Instruction {
        public mul(TempReg rd, TempReg rs1, TempReg rs2) {
            super("mul", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 / rs2} (signed integer division) */
    public static class div extends Instruction {
        public div(TempReg rd, TempReg rs1, TempReg rs2) {
            super("div", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 % rs2} (signed remainder) */
    public static class rem extends Instruction {
        public rem(TempReg rd, TempReg rs1, TempReg rs2) {
            super("rem", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 % rs2} (unsigned remainder) */
    public static class remu extends Instruction {
        public remu(TempReg rd, TempReg rs1, TempReg rs2) {
            super("remu", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    // Logical

    /** {@code rd = rs1 & rs2} */
    public static class and extends Instruction {
        public and(TempReg rd, TempReg rs1, TempReg rs2) {
            super("and", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 & imm} */
    public static class andi extends Instruction {
        public andi(TempReg rd, TempReg rs1, Constant imm) {
            super("andi", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = rs1 | rs2} */
    public static class or extends Instruction {
        public or(TempReg rd, TempReg rs1, TempReg rs2) {
            super("or", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 | imm} */
    public static class ori extends Instruction {
        public ori(TempReg rd, TempReg rs1, Constant imm) {
            super("ori", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = rs1 ^ rs2} */
    public static class xor extends Instruction {
        public xor(TempReg rd, TempReg rs1, TempReg rs2) {
            super("xor", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 ^ imm} */
    public static class xori extends Instruction {
        public xori(TempReg rd, TempReg rs1, Constant imm) {
            super("xori", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    // Shifts

    /** {@code rd = rs1 << rs2} (logical left shift) */
    public static class sll extends Instruction {
        public sll(TempReg rd, TempReg rs1, TempReg rs2) {
            super("sll", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 << imm} (logical left shift by immediate) */
    public static class slli extends Instruction {
        public slli(TempReg rd, TempReg rs1, Constant imm) {
            super("slli", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = rs1 >>> rs2} (logical right shift) */
    public static class srl extends Instruction {
        public srl(TempReg rd, TempReg rs1, TempReg rs2) {
            super("srl", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 >>> imm} (logical right shift by immediate) */
    public static class srli extends Instruction {
        public srli(TempReg rd, TempReg rs1, Constant imm) {
            super("srli", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = rs1 >> rs2} (arithmetic right shift, sign-extending) */
    public static class sra extends Instruction {
        public sra(TempReg rd, TempReg rs1, TempReg rs2) {
            super("sra", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = rs1 >> imm} (arithmetic right shift by immediate, sign-extending) */
    public static class srai extends Instruction {
        public srai(TempReg rd, TempReg rs1, Constant imm) {
            super("srai", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    // Compare

    /** {@code rd = (rs1 < rs2) ? 1 : 0} (signed) */
    public static class slt extends Instruction {
        public slt(TempReg rd, TempReg rs1, TempReg rs2) {
            super("slt", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** {@code rd = (rs1 < imm) ? 1 : 0} (signed) */
    public static class slti extends Instruction {
        public slti(TempReg rd, TempReg rs1, Constant imm) {
            super("slti", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = (rs1 < rs2) ? 1 : 0} (unsigned) */
    public static class sltu extends Instruction {
        public sltu(TempReg rd, TempReg rs1, TempReg rs2) {
            super("sltu", List.of(rd, rs1, rs2));
            writes.add(rd);
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    // Load

    /** Load 64-bit doubleword: {@code rd = mem[rs1 + imm]} */
    public static class ld extends Instruction {
        public ld(TempReg rd, Constant imm, TempReg rs1) {
            super("ld", List.of(rd, new OffsetTempReg(imm, rs1)));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** Load 32-bit word (sign-extended): {@code rd = mem[rs1 + imm][31:0]} */
    public static class lw extends Instruction {
        public lw(TempReg rd, Constant imm, TempReg rs1) {
            super("lw", List.of(rd, new OffsetTempReg(imm, rs1)));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** Load 16-bit halfword (sign-extended): {@code rd = mem[rs1 + imm][15:0]} */
    public static class lh extends Instruction {
        public lh(TempReg rd, Constant imm, TempReg rs1) {
            super("lh", List.of(rd, new OffsetTempReg(imm, rs1)));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** Load 8-bit byte (sign-extended): {@code rd = mem[rs1 + imm][7:0]} */
    public static class lb extends Instruction {
        public lb(TempReg rd, Constant imm, TempReg rs1) {
            super("lb", List.of(rd, new OffsetTempReg(imm, rs1)));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    // Store

    /** Store 64-bit doubleword: {@code mem[rs1 + imm] = rs2} */
    public static class sd extends Instruction {
        public sd(TempReg rs2, Constant imm, TempReg rs1) {
            super("sd", List.of(new OffsetTempReg(imm, rs1), rs2));
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** Store 32-bit word: {@code mem[rs1 + imm] = rs2[31:0]} */
    public static class sw extends Instruction {
        public sw(TempReg rs2, Constant imm, TempReg rs1) {
            super("sw", List.of(new OffsetTempReg(imm, rs1), rs2));
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** Store 16-bit halfword: {@code mem[rs1 + imm] = rs2[15:0]} */
    public static class sh extends Instruction {
        public sh(TempReg rs2, Constant imm, TempReg rs1) {
            super("sh", List.of(new OffsetTempReg(imm, rs1), rs2));
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    /** Store 8-bit byte: {@code mem[rs1 + imm] = rs2[7:0]} */
    public static class sb extends Instruction {
        public sb(TempReg rs2, Constant imm, TempReg rs1) {
            super("sb", List.of(new OffsetTempReg(imm, rs1), rs2));
            reads.add(rs1);
            reads.add(rs2);
        }
    }

    // Branches — both label (positive) and fallthrough (negative) added to jumps

    /** Branch if {@code rs1 == rs2} */
    public static class beq extends Instruction {
        public beq(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("beq", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 != rs2} */
    public static class bne extends Instruction {
        public bne(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bne", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 < rs2} (signed) */
    public static class blt extends Instruction {
        public blt(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("blt", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 >= rs2} (signed) */
    public static class bge extends Instruction {
        public bge(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bge", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 < rs2} (unsigned) */
    public static class bltu extends Instruction {
        public bltu(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bltu", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 >= rs2} (unsigned) */
    public static class bgeu extends Instruction {
        public bgeu(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bgeu", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    // Pseudo branches

    /** Branch if {@code rs1 == 0} */
    public static class beqz extends Instruction {
        public beqz(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("beqz", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 != 0} */
    public static class bnez extends Instruction {
        public bnez(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("bnez", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 <= 0} */
    public static class blez extends Instruction {
        public blez(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("blez", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 > 0} */
    public static class bgtz extends Instruction {
        public bgtz(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("bgtz", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 < 0} (pseudo: {@code blt rs1, zero, label}) */
    public static class bltz extends Instruction {
        public bltz(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("bltz", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 >= 0} (pseudo: {@code bge rs1, zero, label}) */
    public static class bgez extends Instruction {
        public bgez(TempReg rs1, Label trueLabel, Label falseLabel) {
            super("bgez", List.of(rs1, trueLabel));
            reads.add(rs1);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 > rs2} (pseudo: {@code blt rs2, rs1, label}) */
    public static class bgt extends Instruction {
        public bgt(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bgt", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 <= rs2} (pseudo: {@code bge rs2, rs1, label}) */
    public static class ble extends Instruction {
        public ble(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("ble", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 > rs2} unsigned (pseudo: {@code bltu rs2, rs1, label}) */
    public static class bgtu extends Instruction {
        public bgtu(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bgtu", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    /** Branch if {@code rs1 <= rs2} unsigned (pseudo: {@code bgeu rs2, rs1, label}) */
    public static class bleu extends Instruction {
        public bleu(TempReg rs1, TempReg rs2, Label trueLabel, Label falseLabel) {
            super("bleu", List.of(rs1, rs2, trueLabel));
            reads.add(rs1);
            reads.add(rs2);
            jumps.add(trueLabel);
            jumps.add(falseLabel);
        }
    }

    // Jumps

    /** Unconditional jump to label (pseudo: {@code jal zero, label}) */
    public static class j extends Instruction {
        public j(Label label) {
            super("j", List.of(label));
            jumps.add(label);
        }
    }

    /** Jump and link: {@code rd = pc + 4}, then jump to label */
    public static class jal extends Instruction {
        public jal(TempReg rd, Label label) {
            super("jal", List.of(rd, label));
            writes.add(rd);
            jumps.add(label);
        }
    }

    /** Jump and link register: {@code rd = pc + 4}, then jump to {@code rs1 + imm} */
    public static class jalr extends Instruction {
        public jalr(TempReg rd, TempReg rs1, Constant imm) {
            super("jalr", List.of(rd, rs1, imm));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** Return from function (pseudo: {@code jalr zero, ra, 0}) */
    public static class ret extends Instruction {
        public ret() {
            super("ret", List.of());
        }
    }

    /** Jump to register: {@code pc = rs1} (pseudo: {@code jalr zero, rs1, 0}) */
    public static class jr extends Instruction {
        public jr(TempReg rs1) {
            super("jr", List.of(rs1));
            reads.add(rs1);
        }
    }

    /** Call function at label (pseudo: saves return address in {@code ra}) */
    public static class call extends Instruction {
        public call(Label label) {
            super("call", List.of(label));
            jumps.add(label);
        }
    }

    // Pseudo moves

    /** Copy register: {@code rd = rs1} */
    public static class mv extends Instruction {
        public mv(TempReg rd, TempReg rs1) {
            super("mv", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
            simpleMove = true;
        }
    }

    /** Negate: {@code rd = -rs1} */
    public static class neg extends Instruction {
        public neg(TempReg rd, TempReg rs1) {
            super("neg", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** Bitwise complement: {@code rd = ~rs1} */
    public static class not extends Instruction {
        public not(TempReg rd, TempReg rs1) {
            super("not", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    // Pseudo compares

    /** {@code rd = (rs1 == 0) ? 1 : 0} (pseudo: {@code sltiu rd, rs1, 1}) */
    public static class seqz extends Instruction {
        public seqz(TempReg rd, TempReg rs1) {
            super("seqz", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = (rs1 != 0) ? 1 : 0} (pseudo: {@code sltu rd, zero, rs1}) */
    public static class snez extends Instruction {
        public snez(TempReg rd, TempReg rs1) {
            super("snez", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = (rs1 < 0) ? 1 : 0} (pseudo: {@code slt rd, rs1, zero}) */
    public static class sltz extends Instruction {
        public sltz(TempReg rd, TempReg rs1) {
            super("sltz", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    /** {@code rd = (rs1 > 0) ? 1 : 0} (pseudo: {@code slt rd, zero, rs1}) */
    public static class sgtz extends Instruction {
        public sgtz(TempReg rd, TempReg rs1) {
            super("sgtz", List.of(rd, rs1));
            writes.add(rd);
            reads.add(rs1);
        }
    }

    // Pseudo loads

    /** Load immediate: {@code rd = imm} (any 64-bit constant; assembler expands as needed) */
    public static class li extends Instruction {
        public li(TempReg rd, Constant imm) {
            super("li", List.of(rd, imm));
            writes.add(rd);
        }
    }

    /** Load address of label into rd */
    public static class la extends Instruction {
        public la(TempReg rd, Label label) {
            super("la", List.of(rd, label));
            writes.add(rd);
        }
    }

    // Misc pseudo

    /** No operation (pseudo: {@code addi zero, zero, 0}) */
    public static class nop extends Instruction {
        public nop() {
            super("nop", List.of());
        }
    }

    // Big constants

    /** Load upper immediate: {@code rd = imm << 12} (loads bits [31:12]) */
    public static class lui extends Instruction {
        public lui(TempReg rd, Constant imm) {
            super("lui", List.of(rd, imm));
            writes.add(rd);
        }
    }

    // System

    /** Environment call — transfers control to the OS/runtime */
    public static class ecall extends Instruction {
        public ecall() {
            super("ecall", List.of());
        }
    }

    /** Environment breakpoint — transfers control to a debugger */
    public static class ebreak extends Instruction {
        public ebreak() {
            super("ebreak", List.of());
        }
    }

    /** Assembly label marker — emits {@code name:} in the output */
    public static class LabelInstr extends Instruction {
        public final MEM.Label label;

        public LabelInstr(MEM.Label label) {
            super(label.name + ":", List.of());
            this.label = label;
        }

        @Override
        public String toString() {
            return label.name + ":";
        }
    }

}
