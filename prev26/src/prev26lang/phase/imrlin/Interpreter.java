package prev26lang.phase.imrlin;

import prev26lang.common.report.Report;
import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.memory.MEM;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Interpreter - for testing purposes only.
 */
public class Interpreter {

	private Scanner scanner = new Scanner(System.in);

	private boolean debug = false;

	private Random random;

	private HashMap<Long, Byte> memory;

	private HashMap<MEM.Temp, Long> temps;

	private HashMap<MEM.Label, Long> dataMemLabels;

	private HashMap<MEM.Label, Integer> jumpMemLabels;

	private HashMap<MEM.Label, LIN.CodeChunk> callMemLabels;

	private MEM.Temp SP;

	private MEM.Temp FP;

	private MEM.Temp RV;

	private MEM.Temp HP;

	public Interpreter(Vector<LIN.DataChunk> dataChunks, Vector<LIN.CodeChunk> codeChunks) {
		random = new Random();

		this.memory = new HashMap<Long, Byte>();
		this.temps = new HashMap<MEM.Temp, Long>();

		SP = new MEM.Temp();
		tempST(SP, 0x7FFFFFFFFFFFFFF8l);
		HP = new MEM.Temp();
		tempST(HP, 0x2000000000000000l);

		this.dataMemLabels = new HashMap<MEM.Label, Long>();
		for (LIN.DataChunk dataChunk : dataChunks) {
			if (debug) {
				System.out.printf("### %s @ %d\n", dataChunk.label.name, tempLD(HP, false));
			}
			this.dataMemLabels.put(dataChunk.label, tempLD(HP, false));
			if (dataChunk.init != null) {
				for (int c = 0; c < dataChunk.init.length() - 2; c++)
					memST(tempLD(HP, false) + 8 * c, (long) dataChunk.init.charAt(c + 1), false);
				memST(tempLD(HP, false) + 8 * (dataChunk.init.length() - 2), 0L, false);
			}
			tempST(HP, tempLD(HP, false) + dataChunk.size, debug);
		}
		if (debug)
			System.out.printf("###\n");

		this.jumpMemLabels = new HashMap<MEM.Label, Integer>();
		this.callMemLabels = new HashMap<MEM.Label, LIN.CodeChunk>();
		for (LIN.CodeChunk codeChunk : codeChunks) {
			this.callMemLabels.put(codeChunk.frame.label, codeChunk);
			Vector<IMR.Stmt> stmts = codeChunk.stmts();
			for (int stmtOffset = 0; stmtOffset < stmts.size(); stmtOffset++) {
				if (stmts.get(stmtOffset) instanceof IMR.LABEL)
					jumpMemLabels.put(((IMR.LABEL) stmts.get(stmtOffset)).label, stmtOffset);
			}
		}
	}

	private void memST(Long address, Long value) {
		memST(address, value, debug);
	}

	private void memST(Long address, Long value, boolean debug) {
		if (debug)
			System.out.printf("### [%d] <- %d\n", address, value);
		for (int b = 0; b <= 7; b++) {
			long longval = value % 0x100;
			byte byteval = (byte) longval;
			memory.put(address + b, byteval);
			value = value >> 8;
		}
	}

	private void memST1(Long address, Long value) {
		if (debug)
			System.out.printf("### [%d] <1- %d\n", address, value);
		memory.put(address, (byte) (value & 0xFF));
	}

	private Long memLD1(Long address) {
		Byte byteval = memory.get(address);
		if (byteval == null)
			byteval = (byte) (random.nextLong() / 0x100);
		long longval = (long) byteval;
		long value = longval < 0 ? longval + 0x100 : longval;
		if (debug)
			System.out.printf("### %d <1- [%d]\n", value, address);
		return value;
	}

	private Long memLD(Long address) {
		return memLD(address, debug);
	}

	private Long memLD(Long address, boolean debug) {
		Long value = 0L;
		for (int b = 7; b >= 0; b--) {
			Byte byteval = memory.get(address + b);
			if (byteval == null) {
				byteval = (byte) (random.nextLong() / 0x100);
				// throw new Report.Error("INTERPRETER: Uninitialized memory location " +
				// (address + b) + ".");
			}
			long longval = (long) byteval;
			value = (value * 0x100) + (longval < 0 ? longval + 0x100 : longval);
		}
		if (debug)
			System.out.printf("### %d <- [%d]\n", value, address);
		return value;
	}

	private void tempST(MEM.Temp temp, Long value) {
		tempST(temp, value, debug);
	}

	private void tempST(MEM.Temp temp, Long value, boolean debug) {
		temps.put(temp, value);
		if (debug) {
			if (temp == SP) {
				System.out.printf("### SP <- %d\n", value);
				return;
			}
			if (temp == FP) {
				System.out.printf("### FP <- %d\n", value);
				return;
			}
			if (temp == RV) {
				System.out.printf("### RV <- %d\n", value);
				return;
			}
			if (temp == HP) {
				System.out.printf("### HP <- %d\n", value);
				return;
			}
			System.out.printf("### T%d <- %d\n", temp.temp, value);
			return;
		}
	}

	private Long tempLD(MEM.Temp temp) {
		return tempLD(temp, debug);
	}

	private Long tempLD(MEM.Temp temp, boolean debug) {
		Long value = temps.get(temp);
		if (value == null) {
			value = random.nextLong();
			throw new Report.Error("Uninitialized temporary variable T" + temp.temp + ".");
		}
		if (debug) {
			if (temp == SP) {
				System.out.printf("### %d <- SP\n", value);
				return value;
			}
			if (temp == FP) {
				System.out.printf("### %d <- FP\n", value);
				return value;
			}
			if (temp == RV) {
				System.out.printf("### %d <- RV\n", value);
				return value;
			}
			if (temp == HP) {
				System.out.printf("### %d <- HP\n", value);
				return value;
			}
			System.out.printf("### %d <- T%d\n", value, temp.temp);
			return value;
		}
		return value;
	}

	private class ExprInterpreter implements IMR.Visitor<Long, Object> {

		@Override
		public Long visit(IMR.BINOP imcBinop, Object arg) {
			Long fstExpr = imcBinop.fstExpr.accept(this, null);
			Long sndExpr = imcBinop.sndExpr.accept(this, null);
			switch (imcBinop.oper) {
			case OR:
				return (fstExpr != 0) | (sndExpr != 0) ? 1L : 0L;
			case AND:
				return (fstExpr != 0) & (sndExpr != 0) ? 1L : 0L;
			case EQU:
				return (fstExpr == sndExpr) ? 1L : 0L;
			case NEQ:
				return (fstExpr != sndExpr) ? 1L : 0L;
			case LEQ:
				return (fstExpr <= sndExpr) ? 1L : 0L;
			case GEQ:
				return (fstExpr >= sndExpr) ? 1L : 0L;
			case LTH:
				return (fstExpr < sndExpr) ? 1L : 0L;
			case GTH:
				return (fstExpr > sndExpr) ? 1L : 0L;
			case ADD:
				return fstExpr + sndExpr;
			case SUB:
				return fstExpr - sndExpr;
			case MUL:
				return fstExpr * sndExpr;
			case DIV:
				return fstExpr / sndExpr;
			case MOD:
				return fstExpr % sndExpr;
			}
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.CALL imcCall, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.CONST imcConst, Object arg) {
			return imcConst.value;
		}

		@Override
		public Long visit(IMR.MEM1 imcMem, Object arg) {
			return memLD1(imcMem.addr.accept(this, null));
		}

		@Override
		public Long visit(IMR.MEM8 imcMem, Object arg) {
			return memLD(imcMem.addr.accept(this, null));
		}

		@Override
		public Long visit(IMR.NAME imcName, Object arg) {
			return dataMemLabels.get(imcName.label);
		}

		@Override
		public Long visit(IMR.SEXPR imcSExpr, Object arg) {
			throw new Report.InternalError();
		}

		@Override
		public Long visit(IMR.TEMP imcMemTemp, Object arg) {
			return tempLD(imcMemTemp.temp);
		}

		@Override
		public Long visit(IMR.UNOP imcUnop, Object arg) {
			Long subExpr = imcUnop.subExpr.accept(this, null);
			switch (imcUnop.oper) {
			case NOT:
				return (subExpr == 0) ? 1L : 0L;
			case NEG:
				return -subExpr;
			}
			throw new Report.InternalError();
		}

	}

	private class StmtInterpreter implements IMR.Visitor<MEM.Label, Object> {

		@Override
		public MEM.Label visit(IMR.CJUMP imcCJump, Object arg) {
			if (debug)
				System.out.println(imcCJump);
			Long cond = imcCJump.cond.accept(new ExprInterpreter(), null);
			return (cond != 0) ? ((IMR.NAME)imcCJump.posAddr).label : ((IMR.NAME)imcCJump.negAddr).label;
		}

		@Override
		public MEM.Label visit(IMR.ESTMT imcEStmt, Object arg) {
			if (debug)
				System.out.println(imcEStmt);
			if (imcEStmt.expr instanceof IMR.CALL) {
				call((IMR.CALL) imcEStmt.expr);
				return null;
			}
			imcEStmt.expr.accept(new ExprInterpreter(), null);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.JUMP imcJump, Object arg) {
			if (debug)
				System.out.println(imcJump);
			return ((IMR.NAME)imcJump.addr).label;
		}

		@Override
		public MEM.Label visit(IMR.LABEL imcMemLabel, Object arg) {
			if (debug)
				System.out.println(imcMemLabel);
			return null;
		}

		@Override
		public MEM.Label visit(IMR.MOVE imcMove, Object arg) {
			if (debug)
				System.out.println(imcMove);
			if (imcMove.dst instanceof IMR.MEM8) {
				Long dst = ((IMR.MEM8) imcMove.dst).addr.accept(new ExprInterpreter(), null);
				Long src;
				if (imcMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imcMove.src);
					src = memLD(tempLD(SP));
				} else
					src = imcMove.src.accept(new ExprInterpreter(), null);
				memST(dst, src);
				return null;
			}
			if (imcMove.dst instanceof IMR.MEM1) {
				Long dst = ((IMR.MEM1) imcMove.dst).addr.accept(new ExprInterpreter(), null);
				Long src;
				if (imcMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imcMove.src);
					src = memLD1(tempLD(SP));
				} else
					src = imcMove.src.accept(new ExprInterpreter(), null);
				memST1(dst, src);
				return null;
			}
			if (imcMove.dst instanceof IMR.TEMP) {
				IMR.TEMP dst = (IMR.TEMP) (imcMove.dst);
				Long src;
				if (imcMove.src instanceof IMR.CALL) {
					call((IMR.CALL) imcMove.src);
					src = memLD(tempLD(SP));
				} else
					src = imcMove.src.accept(new ExprInterpreter(), null);
				tempST(dst.temp, src);
				return null;
			}
			throw new Report.InternalError();
		}

		@Override
		public MEM.Label visit(IMR.STMTS imcStmts, Object arg) {
			if (debug)
				System.out.println(imcStmts);
			throw new Report.InternalError();
		}

		private void call(IMR.CALL imcCall) {
			Long offset = 0L;
			for (IMR.Expr callArg : imcCall.args) {
				Long callValue = callArg.accept(new ExprInterpreter(), null);
				memST(tempLD(SP) + offset, callValue);
				offset += 8;
			}
			MEM.Label addr = ((IMR.NAME) imcCall.addr).label;
			if (addr.name.equals("_new")) {
				Long size = memLD(tempLD(SP, false) + 1 * 8, false);
				Long heapAddr = tempLD(HP);
				tempST(HP, heapAddr + size);
				memST(tempLD(SP), heapAddr, false);
				return;
			}
			if (addr.name.equals("_del")) {
				return;
			}
			if (addr.name.equals("_exit")) {
				System.exit(1);
			}
			if (addr.name.equals("_putint")) {
				Long c = memLD(tempLD(SP, false) + 1 * 8, false);
				System.out.printf("%d", c);
				return;
			}
			if (addr.name.equals("_getint")) {
				Long l = scanner.nextLong();
				memST(tempLD(SP), (long) l, false);
				return;
			}
			if (addr.name.equals("_putchar")) {
				Long c = memLD(tempLD(SP, false) + 1 * 8, false);
				System.out.printf("%c", (char) ((long) c) % 0x100);
				return;
			}
			if (addr.name.equals("_getchar")) {
				char c = '\n';
				try {
					c = (char) System.in.read();
				} catch (Exception __) {
				}
				memST(tempLD(SP), (long) c, false);
				return;
			}
			funCall(addr);
		}

	}

	public void funCall(MEM.Label entryMemLabel) {

		HashMap<MEM.Temp, Long> storedMemTemps;
		MEM.Temp storedFP = null;
		MEM.Temp storedRV = null;

		LIN.CodeChunk chunk = callMemLabels.get(entryMemLabel);
		MEM.Frame frame = chunk.frame;
		Vector<IMR.Stmt> stmts = chunk.stmts();
		int stmtOffset;

		/* PROLOGUE */
		{
			if (debug)
				System.out.printf("###\n### CALL: %s\n", entryMemLabel.name);

			// Store registers and FP.
			storedMemTemps = temps;
			temps = new HashMap<MEM.Temp, Long>(temps);
			// Store RA.
			// Create a stack frame.
			FP = frame.FP;
			RV = frame.RV;
			tempST(frame.FP, tempLD(SP));
			tempST(SP, tempLD(SP) - frame.size);
			// Jump to the body.
			stmtOffset = jumpMemLabels.get(chunk.entryLabel);
		}

		/* BODY */
		{
			int pc = 0;
			MEM.Label label = null;

			while (label != chunk.exitLabel) {
				if (debug) {
					pc++;
					System.out.printf("### %s (%d):\n", chunk.frame.label.name, pc);
					if (pc == 1000000)
						break;
				}

				if (label != null) {
					Integer offset = jumpMemLabels.get(label);
					if (offset == null) {
						throw new Report.InternalError();
					}
					stmtOffset = offset;
				}

				label = stmts.get(stmtOffset).accept(new StmtInterpreter(), null);

				stmtOffset += 1;
			}
		}

		/* EPILOGUE */
		{
			// Store the result.
			memST(tempLD(frame.FP), tempLD(frame.RV));
			// Destroy a stack frame.
			tempST(SP, tempLD(SP) + frame.size);
			// Restore registers and FP.
			FP = storedFP;
			RV = storedRV;
			Long hp = tempLD(HP);
			temps = storedMemTemps;
			tempST(HP, hp);
			// Restore RA.
			// Return.

			if (debug)
				System.out.printf("### RETURN: %s\n###\n", entryMemLabel.name);
		}

	}

	public long run(String entryMemLabel) {
		for (MEM.Label label : callMemLabels.keySet()) {
			if (label.name.equals(entryMemLabel)) {
				funCall(label);
				return memLD(tempLD(SP));
			}
		}
		throw new Report.InternalError();
	}

}
