package prev26lang.phase.imrlin;

import prev26lang.common.report.Report;
import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.memory.MEM;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/** Interpreter for testing purposes. */
public class Interpreter {

	private final Scanner scanner = new Scanner(System.in);
	private final Random random = new Random();
	private boolean debug;

	private HashMap<Long, Byte> memory;
	private HashMap<MEM.Temp, Long> temps;
	private HashMap<MEM.Label, Long> dataLabels;
	private HashMap<MEM.Label, Integer> jumpLabels;
	private HashMap<MEM.Label, LIN.CodeChunk> callLabels;

	private MEM.Temp SP, FP, RV, HP;

	private void dbg(String fmt, Object... args) {
		if (debug) System.out.printf("[DBG] " + fmt + "\n", args);
	}

	public Interpreter(Vector<LIN.DataChunk> dataChunks, Vector<LIN.CodeChunk> codeChunks, Boolean debug) {
		this.debug = debug;
		memory = new HashMap<>();
		temps = new HashMap<>();
		dataLabels = new HashMap<>();
		jumpLabels = new HashMap<>();
		callLabels = new HashMap<>();

		SP = new MEM.Temp();
		tempST(SP, 1_000_000_000_000_000L);
		HP = new MEM.Temp();
		tempST(HP, 0L);

		dbg("=== DATA SEGMENTS ===");
		for (LIN.DataChunk chunk : dataChunks) {
			long addr = tempLD(HP, false);
			dataLabels.put(chunk.label, addr);
			dbg("  DATA  %-20s  addr=%-12d  size=%d%s",
				chunk.label.name, addr, chunk.size,
				chunk.init != null
					? "  init=\"" + chunk.init.replace("\n", "\\n") + "\""
					: "  (uninitialized)");
			if (chunk.init != null)
				for (int i = 0; i < chunk.init.length(); i++)
					memST1(addr + i, (byte) chunk.init.charAt(i), false);
			tempST(HP, tempLD(HP, false) + chunk.size, false);
		}
		dbg("=== END DATA SEGMENTS ===");

		for (LIN.CodeChunk chunk : codeChunks) {
			callLabels.put(chunk.frame.label, chunk);
			Vector<IMR.Stmt> stmts = chunk.stmts();
			for (int i = 0; i < stmts.size(); i++)
				if (stmts.get(i) instanceof IMR.LABEL lbl)
					jumpLabels.put(lbl.label, i);
		}
	}

	// -------------------------------------------------------------------------
	// Memory
	// -------------------------------------------------------------------------

	private void memST(long addr, long value) { memST(addr, value, debug); }
	private void memST(long addr, long value, boolean log) {
		if (log) dbg("  MEM8[%d] <- %d", addr, value);
		for (int b = 0; b < 8; b++, value >>= 8)
			memory.put(addr + b, (byte)(value & 0xFF));
	}

	private void memST1(long addr, byte value) { memST1(addr, value, debug); }
	private void memST1(long addr, byte value, boolean log) {
		if (log) dbg("  MEM1[%d] <- %d", addr, value);
		memory.put(addr, value);
	}

	private long memLD(long addr) { return memLD(addr, debug); }
	private long memLD(long addr, boolean log) {
		long value = 0;
		for (int b = 7; b >= 0; b--) {
			Byte raw = memory.get(addr + b);
			if (raw == null) {
				raw = (byte) random.nextLong();
				Report.warning("INTERPRETER: Uninitialized memory at " + (addr + b));
			}
			value = (value << 8) | (raw & 0xFFL);
		}
		if (log) dbg("  MEM8[%d] -> %d", addr, value);
		return value;
	}

	private byte memLD1(long addr) { return memLD1(addr, debug); }
	private byte memLD1(long addr, boolean log) {
		Byte raw = memory.get(addr);
		if (raw == null) {
			raw = (byte) random.nextLong();
			Report.warning("INTERPRETER: Uninitialized memory at " + addr);
		}
		if (log) dbg("  MEM1[%d] -> %d", addr, raw);
		return raw;
	}

	// -------------------------------------------------------------------------
	// Temporaries
	// -------------------------------------------------------------------------

	private String tempName(MEM.Temp t) {
		if (t == SP) return "SP";
		if (t == FP) return "FP";
		if (t == RV) return "RV";
		if (t == HP) return "HP";
		return "T" + t.temp;
	}

	private void tempST(MEM.Temp t, long value) { tempST(t, value, debug); }
	private void tempST(MEM.Temp t, long value, boolean log) {
		temps.put(t, value);
		if (log) dbg("  %-4s <- %d", tempName(t), value);
	}

	private long tempLD(MEM.Temp t) { return tempLD(t, debug); }
	private long tempLD(MEM.Temp t, boolean log) {
		Long value = temps.get(t);
		if (value == null)
			throw new Report.Error("Uninitialized temporary " + tempName(t));
		if (log) dbg("  %-4s -> %d", tempName(t), value);
		return value;
	}

	// -------------------------------------------------------------------------
	// Expression evaluator
	// -------------------------------------------------------------------------

	private class ExprInterpreter implements IMR.Visitor<Long, Object> {

		@Override public Long visit(IMR.CALL n, Object a) { throw new Report.InternalError(); }
		@Override public Long visit(IMR.SEXPR n, Object a) { throw new Report.InternalError(); }
		@Override public Long visit(IMR.CONST n, Object a) { return n.value; }
		@Override public Long visit(IMR.MEM8 n, Object a) { return memLD(n.addr.accept(this, null)); }
		@Override public Long visit(IMR.MEM1 n, Object a) { return (long)(memLD1(n.addr.accept(this, null)) & 0xFF); }
		@Override public Long visit(IMR.TEMP n, Object a) { return tempLD(n.temp); }
		@Override public Long visit(IMR.NAME n, Object a) { return dataLabels.get(n.label); }

		@Override
		public Long visit(IMR.UNOP n, Object a) {
			long v = n.subExpr.accept(this, null);
			return switch (n.oper) {
				case NOT -> v == 0 ? 1L : 0L;
				case NEG -> -v;
				default  -> throw new Report.InternalError();
			};
		}

		@Override
		public Long visit(IMR.BINOP n, Object a) {
			long l = n.fstExpr.accept(this, null);
			long r = n.sndExpr.accept(this, null);
			return switch (n.oper) {
				case OR  -> (l != 0) | (r != 0) ? 1L : 0L;
				case AND -> (l != 0) & (r != 0) ? 1L : 0L;
				case EQU -> l == r ? 1L : 0L;
				case NEQ -> l != r ? 1L : 0L;
				case LEQ -> l <= r ? 1L : 0L;
				case GEQ -> l >= r ? 1L : 0L;
				case LTH -> l <  r ? 1L : 0L;
				case GTH -> l >  r ? 1L : 0L;
				case ADD -> l + r;
				case SUB -> l - r;
				case MUL -> l * r;
				case DIV -> l / r;
				case MOD -> l % r;
				default  -> throw new Report.InternalError();
			};
		}
	}

	// -------------------------------------------------------------------------
	// Statement interpreter
	// -------------------------------------------------------------------------

	private class StmtInterpreter implements IMR.Visitor<MEM.Label, Object> {

		private final ExprInterpreter expr = new ExprInterpreter();

		@Override
		public MEM.Label visit(IMR.LABEL n, Object a) {
			dbg("LABEL  %s:", n.label.name);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.JUMP n, Object a) {
			MEM.Label target = ((IMR.NAME) n.addr).label;
			dbg("JUMP   -> %s", target.name);
			return target;
		}

		@Override
		public MEM.Label visit(IMR.CJUMP n, Object a) {
			dbg("CJUMP  %s", n);
			long cond = n.cond.accept(expr, null);
			MEM.Label taken = cond != 0
				? ((IMR.NAME) n.posAddr).label
				: ((IMR.NAME) n.negAddr).label;
			dbg("  cond=%d  -> %s", cond, taken.name);
			return taken;
		}

		@Override
		public MEM.Label visit(IMR.ESTMT n, Object a) {
			dbg("ESTMT  %s", n);
			if (n.expr instanceof IMR.CALL c) call(c);
			else n.expr.accept(expr, null);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.MOVE n, Object a) {
			dbg("MOVE   %s", n);
			if (n.dst instanceof IMR.MEM8 dst)
				memST(dst.addr.accept(expr, null), srcValue(n.src));
			else if (n.dst instanceof IMR.MEM1 dst)
				memST1(dst.addr.accept(expr, null), (byte)(srcValue(n.src) & 0xFF));
			else if (n.dst instanceof IMR.TEMP dst)
				tempST(dst.temp, srcValue(n.src));
			else
				throw new Report.InternalError();
			return null;
		}

		private long srcValue(IMR.Expr src) {
			if (src instanceof IMR.CALL c) {
				call(c);
				return memLD(tempLD(SP));
			}
			return src.accept(expr, null);
		}

		@Override
		public MEM.Label visit(IMR.STMTS n, Object a) {
			throw new Report.InternalError();
		}
	}

	// -------------------------------------------------------------------------
	// Function dispatch
	// -------------------------------------------------------------------------

	private void call(IMR.CALL imcCall) {
		ExprInterpreter expr = new ExprInterpreter();
		long spBase = tempLD(SP, false);
		long offset = 0;
		int argIdx = 0;
		for (IMR.Expr arg : imcCall.args) {
			long val = arg.accept(expr, null);
			dbg("  arg[%d] = %d  -> MEM[SP+%d]", argIdx++, val, offset);
			memST(spBase + offset, val);
			offset += 8;
		}

		if (!(imcCall.addr instanceof IMR.NAME named)) {
			Report.warning("Cannot interpret function without a label!");
			return;
		}

		switch (named.label.name) {
			case "_new" -> {
				long size = memLD(spBase + 8, false);
				long heap = tempLD(HP);
				tempST(HP, heap + size);
				memST(spBase, heap, false);
			}
			case "_del" -> { /* no-op */ }
			case "_exit" -> System.exit(1);
			case "_putint" -> System.out.printf("%d", memLD(spBase + 8, false));
			case "_getint" -> memST(spBase, scanner.nextLong(), false);
			case "_putchar" -> System.out.printf("%c", (char)(memLD1(spBase + 8, false) & 0xFF));
			case "_getchar" -> {
				char c = '\n';
				try { c = (char) System.in.read(); } catch (Exception ignored) {}
				memST(spBase, (long) c, false);
			}
			default -> funCall(named.label);
		}
	}

	// -------------------------------------------------------------------------
	// Execution
	// -------------------------------------------------------------------------

	public void funCall(MEM.Label label) {
		LIN.CodeChunk chunk = callLabels.get(label);
		MEM.Frame frame = chunk.frame;
		Vector<IMR.Stmt> stmts = chunk.stmts();

		// Prologue
		dbg("CALL %s  (frame size=%d)", label.name, frame.size);
		HashMap<MEM.Temp, Long> savedTemps = temps;
		MEM.Temp savedFP = FP, savedRV = RV;
		temps = new HashMap<>(temps);
		FP = frame.FP;
		RV = frame.RV;
		tempST(frame.FP, tempLD(SP));
		tempST(SP, tempLD(SP) - frame.size);
		int stmtOffset = jumpLabels.get(chunk.entryLabel);

		// Body
		StmtInterpreter si = new StmtInterpreter();
		MEM.Label next = null;
		int pc = 0;
		while (next != chunk.exitLabel) {
			if (debug && ++pc == 1_000_000) {
				dbg("!! Execution limit reached in %s, aborting", label.name);
				break;
			}
			if (next != null) {
				Integer idx = jumpLabels.get(next);
				if (idx == null) throw new Report.InternalError();
				stmtOffset = idx;
			}
			next = stmts.get(stmtOffset++).accept(si, null);
		}

		// Epilogue
		long rv = tempLD(frame.RV, false);
		memST(tempLD(frame.FP, false), rv, false);
		long hp = tempLD(HP, false);
		temps = savedTemps;
		tempST(HP, hp, false);
		FP = savedFP;
		RV = savedRV;
		dbg("RETURN %s  rv=%d", label.name, rv);
	}

	public long run(String entryLabel) {
		for (MEM.Label label : callLabels.keySet())
			if (label.name.equals(entryLabel)) {
				funCall(label);
				long rv = memLD(tempLD(SP));
				return rv;
			}
		throw new Report.InternalError();
	}
}
