package prev26lang.phase.seman;

import java.util.*;

import prev26lang.Compiler;
import prev26lang.common.report.*;
import prev26lang.phase.abstr.*;

/**
 * Name resolver.
 * <p>
 * The name resolver connects each node of a abstract syntax tree where a name
 * is used with the node where it is defined. The only exceptions are struct and
 * union component names which are connected with their definitions by the type
 * resolver. The Booleans of the name resolver are stored in
 * {@link prev26lang.phase.seman.SemAn#defAtAttr}.
 *
 * @author bostjan.slivnik@fri.uni-lj.si
 */
public class NameResolver implements AST.FullVisitor<Void, Void> {

    /**
     * Constructs a new name resolver.
     */
    public NameResolver() {

    }

    /**
     * The symbol table.
     */
    private SymbTable symbTable = new SymbTable();


    // BASIC IDEA: every node takes care of its children and their scopes, but does not write its own definition into the symbol table.


    private void insert(AST.Defn defn) {
//        Report.info("Trying to insert definition of '" + defn.name + "' with type " + defn.type + " and location " + Abstr.locAttr.get(defn) + " into the symb table on level " + symbTable.currDepth);
        try {
            this.symbTable.ins(defn.name, defn);
        } catch (SymbTable.CannotInsNameException e) {
            throw new Report.Error(Abstr.locAttr.get(defn), "Cannot insert definition of '" + defn.name + "' with type " + defn.type +
                    " into symbol table on level " + this.symbTable.currDepth);
        }
    }

    private void find(String name, AST.Node node) {
//        Report.info("Trying to find definition of '" + name + "' for node " + node + " with location " + Abstr.locAttr.get(node) + " into the symb table on level " + symbTable.currDepth);

        try {
            AST.Defn defn = this.symbTable.fnd(name);
            SemAn.defAtAttr.put(node, defn);
        } catch (SymbTable.CannotFndNameException e) {
            throw new Report.Error(Abstr.locAttr.get(node), "Cannot find name '" + name + "'. Current level: " + this.symbTable.currDepth);
        }
    }


    // ----- Trees -----

    @Override
    public Void visit(AST.Nodes<? extends AST.Node> nodes, Void arg) {

        if (nodes.size() == 0) return null;

        if (nodes.first() instanceof AST.FullDefn) {
            for (AST.Node node : nodes) {
                insert((AST.FullDefn) node);
            }
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }
        } else if (nodes.first() instanceof AST.ParDefn) {
            for (AST.Node node : nodes) {
                insert((AST.ParDefn) node);
            }
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }
        } else if (nodes.first() instanceof AST.Expr) {
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }
        } else if (nodes.first() instanceof AST.Type) {
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }
        } else if (nodes.first() instanceof AST.CompDefn) {
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }
        }

        return null;
    }

//
//    @Override
//    public Void visit(AST.TypDefn typDefn, Void arg) {
//
//        typDefn.type.accept(this, arg);
//        return null;
//    }
//
//    @Override
//    public Void visit(AST.VarDefn varDefn, Void arg) {
//        varDefn.type.accept(this, arg);
//        return null;
//    }
//
//    @Override
//    public Void visit(AST.CompDefn compDefn, Void arg) {
//        compDefn.type.accept(this, arg);
//        return null;
//
//    }

    @Override
    public Void visit(AST.ExtFunDefn extFunDefn, Void arg) {
        symbTable.newScope();

        extFunDefn.pars.accept(this, arg);

        extFunDefn.type.accept(this, arg);

        symbTable.oldScope();
        return null;
    }

    @Override
    public Void visit(AST.DefFunDefn defFunDefn, Void arg) {
        symbTable.newScope();

        defFunDefn.pars.accept(this, arg);

        defFunDefn.type.accept(this, arg);

        symbTable.newScope();
        defFunDefn.expr.accept(this, arg);
        symbTable.oldScope();

        symbTable.oldScope();

        return null;
    }

//    @Override
//    public Void visit(AST.ParDefn parDefn, Void arg) {
//        parDefn.type.accept(this, arg);
//        return null;
//    }

    //    // ----- Types -----

//
//    @Override
//    public Void visit(AST.StrType strType, Void arg) {
//        strType.comps.accept(this, arg);
//        return null;
//    }
//
//    @Override
//    public Void visit(AST.UniType uniType, Void arg) {
//
//        uniType.comps.accept(this, arg);
//        return null;
//    }


    @Override
    public Void visit(AST.NameType nameType, Void arg) {
        find(nameType.name, nameType);
        return null;
    }

//
//    // ----- Expressions -----
//




    @Override
    public Void visit(AST.NameExpr nameExpr, Void arg) {
        find(nameExpr.name, nameExpr);
        return null;
    }

    @Override
    public Void visit(AST.LetExpr letExpr, Void arg) {
        symbTable.newScope();

        letExpr.defns.accept(this, arg);
        letExpr.expr.accept(this, arg);

        symbTable.oldScope();
        return null;
    }


    // ===== SYMBOL TABLE =====

    /**
     * A symbol table.
     */
    public class SymbTable {

        /**
         * A symbol table record denoting a definition of a name within a certain scope.
         */
        private class ScopedDefn {

            /**
             * The depth of the scope the definition belongs to.
             */
            public final int depth;

            /**
             * The definition.
             */
            public final AST.Defn defn;

            /**
             * Constructs a new record denoting a definition of a name within a certain
             * scope.
             *
             * @param depth The depth of the scope the definition belongs to.
             * @param defn  The definition.
             */
            public ScopedDefn(int depth, AST.Defn defn) {
                this.depth = depth;
                this.defn = defn;
            }

        }

        /**
         * A mapping of names into lists of records denoting definitions at different
         * scopes. At each moment during the lifetime of a symbol table, the definition
         * list corresponding to a particular name contains all definitions that name
         * within currently active scopes: the definition at the inner most scope is the
         * first in the list and is visible, the other definitions are hidden.
         */
        private final HashMap<String, LinkedList<ScopedDefn>> allDefnsOfAllNames;

        /**
         * The list of scopes. Each scope is represented by a list of names defined
         * within it.
         */
        private final LinkedList<LinkedList<String>> scopes;

        /**
         * The depth of the currently active scope.
         */
        private int currDepth;

        /**
         * Whether the symbol table can no longer be modified or not.
         */
        private boolean lock;

        /**
         * Constructs a new symbol table.
         */
        public SymbTable() {
            allDefnsOfAllNames = new HashMap<String, LinkedList<ScopedDefn>>();
            scopes = new LinkedList<LinkedList<String>>();
            currDepth = 0;
            lock = false;
            newScope();
        }

        /**
         * Returns the depth of the currently active scope.
         *
         * @return The depth of the currently active scope.
         */
        public int currDepth() {
            return currDepth;
        }

        /**
         * Inserts a new definition of a name within the currently active scope or
         * throws an exception if this name has already been defined within this scope.
         * Once the symbol table is locked, any attempt to insert further definitions
         * Booleans in an internal error.
         *
         * @param name The name.
         * @param defn The definition.
         * @throws CannotInsNameException Thrown if this name has already been defined
         *                                within the currently active scope.
         */
        public void ins(String name, AST.Defn defn) throws CannotInsNameException {
            if (lock)
                throw new Report.InternalError();

            LinkedList<ScopedDefn> allDefnsOfName = allDefnsOfAllNames.get(name);
            if (allDefnsOfName == null) {
                allDefnsOfName = new LinkedList<ScopedDefn>();
                allDefnsOfAllNames.put(name, allDefnsOfName);
            }

            if (!allDefnsOfName.isEmpty()) {
                ScopedDefn defnOfName = allDefnsOfName.getFirst();
                if (defnOfName.depth == currDepth)
                    throw new CannotInsNameException();
            }

            allDefnsOfName.addFirst(new ScopedDefn(currDepth, defn));
            scopes.getFirst().addFirst(name);
        }

        /**
         * Returns the currently visible definition of the specified name. If no
         * definition of the name exists within these scopes, an exception is thrown.
         *
         * @param name The name.
         * @return The definition.
         * @throws CannotFndNameException Thrown if the name is not defined within the
         *                                currently active scope or any scope enclosing
         *                                it.
         */
        public AST.Defn fnd(String name) throws CannotFndNameException {
            LinkedList<ScopedDefn> allDefnsOfName = allDefnsOfAllNames.get(name);
            if (allDefnsOfName == null)
                throw new CannotFndNameException();

            if (allDefnsOfName.isEmpty())
                throw new CannotFndNameException();

            return allDefnsOfName.getFirst().defn;
        }

        /**
         * Used for selecting the range of scopes.
         */
        public enum XScopeSelector {
            /**
             * All live scopes.
             */
            ALL,
            /**
             * Currently active scope.
             */
            ACT,
        }

        /**
         * Constructs a new scope within the currently active scope. The newly
         * constructed scope becomes the currently active scope.
         */
        public void newScope() {
            if (lock)
                throw new Report.InternalError();

            currDepth++;
            scopes.addFirst(new LinkedList<String>());
        }

        /**
         * Destroys the currently active scope by removing all definitions belonging to
         * it from the symbol table. Makes the enclosing scope the currently active
         * scope.
         */
        public void oldScope() {
            if (lock)
                throw new Report.InternalError();

            if (currDepth == 0)
                throw new Report.InternalError();

            for (String name : scopes.getFirst()) {
                allDefnsOfAllNames.get(name).removeFirst();
            }
            scopes.removeFirst();
            currDepth--;
        }

        /**
         * Prevents further modifications of this symbol table.
         */
        public void lock() {
            lock = true;
        }

        /**
         * An exception thrown when the name cannot be inserted into a symbol table.
         */
        @SuppressWarnings("serial")
        public class CannotInsNameException extends Exception {

            /**
             * Constructs a new exception.
             */
            private CannotInsNameException() {
            }

        }

        /**
         * An exception thrown when the name cannot be found in the symbol table.
         */
        @SuppressWarnings("serial")
        public class CannotFndNameException extends Exception {

            /**
             * Constructs a new exception.
             */
            private CannotFndNameException() {
            }

        }

    }

}
