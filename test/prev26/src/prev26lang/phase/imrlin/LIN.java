package prev26lang.phase.imrlin;

import prev26lang.phase.imrgen.IMR;
import prev26lang.phase.memory.MEM;

import java.util.Vector;

public class LIN {

    public static abstract class Chunk {
    }

    /**
     * A chuck of data.
     */
    public static class DataChunk extends Chunk {

        /** The label where data is placed at. */
        public final MEM.Label label;

        /** The size of data. */
        public final long size;

        /** The initial value. */
        public final String init;

        public DataChunk(MEM.AbsAccess absAccess) {
            this.label = absAccess.label;
            this.size = absAccess.size;
            this.init = absAccess.init;
        }

    }

    /**
     * A chuck of code.
     */
    public static class CodeChunk extends Chunk {

        /** A frame of a function. */
        public final MEM.Frame frame;

        /** The statements of a function body. */
        private final Vector<IMR.Stmt> stmts;

        /**
         * The function's body entry label, i.e., the label the prologue jumps to.
         */
        public final MEM.Label entryLabel;

        /**
         * The function's body exit label, i.e., the label at which the epilogue starts.
         */
        public final MEM.Label exitLabel;

        /**
         * Constructs a new code chunk.
         *
         * @param frame      A frame of a function.
         * @param stmts      The statements of a function body.
         * @param entryLabel The function's body entry label, i.e., the label the
         *                   prologue jumps to.
         * @param exitLabel  The function's body exit label, i.e., the label at which
         *                   the epilogue starts.
         */
        public CodeChunk(MEM.Frame frame, Vector<IMR.Stmt> stmts, MEM.Label entryLabel, MEM.Label exitLabel) {
            this.frame = frame;
            this.stmts = new Vector<IMR.Stmt>(stmts);
            this.entryLabel = entryLabel;
            this.exitLabel = exitLabel;
        }

        /**
         * Returns the statements of a function body.
         *
         * @return The statements of a function body.
         */
        public Vector<IMR.Stmt> stmts() {
            return new Vector<IMR.Stmt>(stmts);
        }

    }

}

