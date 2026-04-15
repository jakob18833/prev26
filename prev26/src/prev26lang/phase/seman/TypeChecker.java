package prev26lang.phase.seman;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;

import java.util.*;


public class TypeChecker implements AST.FullVisitor<TYP.Type, Boolean> {
    TYP.VoidType voidType = TYP.VoidType.type;
    @Override
    public TYP.Type visit(AST.Nodes<? extends AST.Node> nodes, Boolean topLevel) {
        // root of the ast
        if (topLevel) containsMain(nodes);

        TYP.Type returnType = null;
        for (AST.Node node : nodes) returnType = node.accept(this, false);
        return returnType;
    }

    private static void containsMain(AST.Nodes<? extends AST.Node> nodes) {
        boolean main = false;
        for (var node : nodes) {
            if (node instanceof AST.FunDefn funDefn) {
                boolean returnsInt = (funDefn.type instanceof AST.AtomType atomType && atomType.type == AST.AtomType.Type.INT);
                boolean zeroParameters = funDefn.pars.size() == 0;
                boolean namedMain = funDefn.name.equals("main");
                if (returnsInt && zeroParameters && namedMain) {
                    if (main) {
                        throw new Report.Error(node, "Cannot have two main functions!");
                    }
                    main = true;
                }
            }
        }
        if (!main) throw new Report.Error(nodes, "No main function found!");
    }

    @Override
    public TYP.Type visit(AST.TypDefn typDefn, Boolean arg) {
        AST.FullVisitor.super.visit(typDefn, arg);
        // We avoid setting the ofType because keyChecker in SemAn.java doesn't allow it
        // SemAn.ofTypeAttr.put(typDefn, voidType);
        return null;
    }

    @Override
    public TYP.Type visit(AST.VarDefn varDefn, Boolean arg) {
        varDefn.type.accept(this, arg);
        TYP.Type varType = SemAn.isTypeAttr.get(varDefn.type);
//        System.out.println(varType.getClass().getSimpleName());
        if (TypeEquivalence.equivalentToVoid(varType)) throw new Report.Error(varDefn, "Cannot define a variable of type equivalent to Void!");
        SemAn.ofTypeAttr.put(varDefn, voidType);
        return voidType;
    }

    
    private TYP.Type visitFunDefnHelper(AST.FunDefn funDefn, Boolean arg) {
        // accept children for parameters and return value and store their ISTYPE (op. parameter's OFTYPE = parameter's type ISTYPE).
        // For funDefn.type, its return value (its OFTYPE) is not his ISTYPE, we must look separately.
        LinkedHashMap<AST.ParDefn, TYP.Type> parTypes = new LinkedHashMap<>();
        for (var par: funDefn.pars)  {
            parTypes.put(par, par.accept(this, arg));
        }
        funDefn.type.accept(this, arg);
        TYP.Type returnType = SemAn.isTypeAttr.get(funDefn.type);


        // check if children types are correct
        if (! parTypes.values().stream().allMatch(TypeEquivalence::matchesGroup)) {
                throw new Report.Error(funDefn, "Illegal parameter type(s) for FunDefn!");
        }
        if (! TypeEquivalence.matchesGroupOrVoid(returnType)) {
            throw new Report.Error(funDefn, "Illegal return type for FunType!");
        }

        // visit expr and check the last expr type
        if (funDefn instanceof AST.DefFunDefn defFunDefn) {
            TYP.Type lastExprType = defFunDefn.expr.accept(this, arg);
            if (!TypeEquivalence.equivalent(lastExprType, returnType))
                throw new Report.Error(funDefn, "Function return type and last expression type mismatch!");
        }

        // Set the attributes
        SemAn.ofTypeAttr.put(funDefn, voidType);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.DefFunDefn defFunDefn, Boolean arg) {
        return visitFunDefnHelper(defFunDefn, arg);
    }

    @Override
    public TYP.Type visit(AST.ExtFunDefn extFunDefn, Boolean arg) {
        return visitFunDefnHelper(extFunDefn, arg);
    }

    @Override
    public TYP.Type visit(AST.ParDefn parDefn, Boolean arg) {
        // OFTYPE for ParDefn is the same as ISTYPE for its type
        parDefn.type.accept(this, arg);
        var type = SemAn.isTypeAttr.get(parDefn.type);
        SemAn.ofTypeAttr.put(parDefn, type);

        return type;
    }

    @Override
    public TYP.Type visit(AST.CompDefn compDefn, Boolean arg) {
        // OFTYPE for CompDefn is the same as ISTYPE for its type
        compDefn.type.accept(this, arg);
        var type = SemAn.isTypeAttr.get(compDefn.type);
        SemAn.ofTypeAttr.put(compDefn, type);

        return type;
    }

    @Override
    public TYP.Type visit(AST.AtomType atomType, Boolean arg) {
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.ArrType arrType, Boolean arg) {
        arrType.elemType.accept(this, arg);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.PtrType ptrType, Boolean arg) {
        ptrType.baseType.accept(this, arg);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.StrType strType, Boolean arg) {
        AST.FullVisitor.super.visit(strType, arg);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.UniType uniType, Boolean arg) {
        AST.FullVisitor.super.visit(uniType, arg);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.FunType funType, Boolean arg) {
        // we checked for void parameters in TypeConstructor, nothing to do here.
        AST.FullVisitor.super.visit(funType, arg);
        return voidType;
    }

    @Override
    public TYP.Type visit(AST.NameType nameType, Boolean arg) {
        // NameType must refer to a TypDefn. We check that in TypeConstructor.
        // As per TYP:2, this has no OFTYPE.
        AST.FullVisitor.super.visit(nameType, arg);
        return null;
    }


    // Exprs

    // TYP:14, 15, 16, 17, 18, 19, 20
    @Override
    public TYP.Type visit(AST.AtomExpr atomExpr, Boolean arg) {
        switch (atomExpr.type) {
            case INT:
                try {
                    Long.parseLong(atomExpr.value);
                } catch (NumberFormatException e) {
                    throw new Report.Error(atomExpr, "Int type overflow!");
                }
                SemAn.ofTypeAttr.put(atomExpr, TYP.IntType.type);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return TYP.IntType.type;
            case BOOL:
                SemAn.ofTypeAttr.put(atomExpr, TYP.BoolType.type);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return TYP.BoolType.type;
            case CHAR:
                SemAn.ofTypeAttr.put(atomExpr, TYP.CharType.type);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return TYP.CharType.type;
            case STR:
                TYP.Type type = new TYP.PtrType(TYP.CharType.type);
                SemAn.ofTypeAttr.put(atomExpr, type);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return type;
            case VOID:
                SemAn.ofTypeAttr.put(atomExpr, voidType);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return voidType;
            case PTR:
                TYP.Type type2 = new TYP.PtrType(voidType);
                SemAn.ofTypeAttr.put(atomExpr, type2);
                SemAn.isConstAttr.put(atomExpr, true);
                SemAn.isAddrAttr.put(atomExpr, false);
                return type2;
            default:
                throw new Report.Error(atomExpr, "Unrecognised AtomExpr!");
        }
    }

    // TYP:21, 22, 28
    @Override
    public TYP.Type visit(AST.PfxExpr pfxExpr, Boolean arg) {

        TYP.Type exprType = pfxExpr.subExpr.accept(this, arg);
        if (Set.of(AST.PfxExpr.Oper.ADD, AST.PfxExpr.Oper.SUB).contains(pfxExpr.oper)) {
            if (!exprType.actualType().equals(TYP.IntType.type)) throw new Report.Error(pfxExpr, "Wrong operand type for prefix expression!");
            SemAn.ofTypeAttr.put(pfxExpr, TYP.IntType.type);
            SemAn.isConstAttr.put(pfxExpr, SemAn.isConstAttr.get(pfxExpr.subExpr));
            SemAn.isAddrAttr.put(pfxExpr, false);
            return TYP.IntType.type;
        } else if(pfxExpr.oper.equals(AST.PfxExpr.Oper.NOT)) {
            if (!exprType.actualType().equals(TYP.BoolType.type)) throw new Report.Error(pfxExpr, "Wrong operand type for prefix expression!");
            SemAn.ofTypeAttr.put(pfxExpr, TYP.BoolType.type);
            SemAn.isConstAttr.put(pfxExpr, SemAn.isConstAttr.get(pfxExpr.subExpr));
            SemAn.isAddrAttr.put(pfxExpr, false);
            return TYP.BoolType.type;
        } else if(pfxExpr.oper.equals(AST.PfxExpr.Oper.PTR)) {
            // subExpr ne sme biti tip
            if (!SemAn.isAddrAttr.get(pfxExpr.subExpr)) throw new Report.Error(pfxExpr, "Cannot have a pointer to a non addressable type!");
            TYP.Type pType = new TYP.PtrType(exprType);
            SemAn.ofTypeAttr.put(pfxExpr, pType);
            SemAn.isConstAttr.put(pfxExpr, false);
            SemAn.isAddrAttr.put(pfxExpr, false);
            
            return pType;
        } else {
            throw new RuntimeException("Forgot sth!");
        }
    }
    @Override
    public TYP.Type visit(AST.SfxExpr sfxExpr, Boolean arg) {
        TYP.Type pointerExpression = sfxExpr.subExpr.accept(this, arg);
        if (pointerExpression == null) throw new Report.Error(sfxExpr, "Dereferenced expression has no OFTYPE!");
        if (!TypeEquivalence.matchesPtr(pointerExpression)) throw new Report.Error(sfxExpr, "Cannot dereference sth that is not a pointer!");
        TYP.PtrType castPointerExpression = (TYP.PtrType) pointerExpression.actualType();
        if (SemAn.isConstAttr.get(sfxExpr.subExpr)) throw new Report.Error(sfxExpr, "Cannot dereference a constant type!");
        if (TypeEquivalence.equivalentToVoid(castPointerExpression.baseType)) throw new Report.Error(sfxExpr, "Cannot dereference a void pointer!");

        SemAn.ofTypeAttr.put(sfxExpr, castPointerExpression.baseType);
        SemAn.isConstAttr.put(sfxExpr, false);
        SemAn.isAddrAttr.put(sfxExpr, true);
        return castPointerExpression.baseType;

    }


    // TYP:23, 24, 25
    @Override
    public TYP.Type visit(AST.BinExpr binExpr, Boolean arg) {
        TYP.Type firstExprType = binExpr.fstExpr.accept(this, arg);
        TYP.Type secondExprType = binExpr.sndExpr.accept(this, arg);
        Set<AST.BinExpr.Oper> arithmeticOperators = Set.of(
                AST.BinExpr.Oper.MUL,
                AST.BinExpr.Oper.DIV,
                AST.BinExpr.Oper.MOD,
                AST.BinExpr.Oper.ADD,
                AST.BinExpr.Oper.SUB
        );
        Set<AST.BinExpr.Oper> comparisonOperators = Set.of(
                AST.BinExpr.Oper.EQU,
                AST.BinExpr.Oper.NEQ,
                AST.BinExpr.Oper.LTH,
                AST.BinExpr.Oper.GTH,
                AST.BinExpr.Oper.LEQ,
                AST.BinExpr.Oper.GEQ
        );
        Set<AST.BinExpr.Oper> logicalOperators = Set.of(
                AST.BinExpr.Oper.AND,
                AST.BinExpr.Oper.OR
        );
        if (arithmeticOperators.contains(binExpr.oper)) {
            boolean e1Int = TypeEquivalence.equivalent(firstExprType, TYP.IntType.type);
            boolean e2Int = TypeEquivalence.equivalent(secondExprType, TYP.IntType.type);
            if (!e1Int || !e2Int) throw new Report.Error(binExpr, "Binary arithmetic expression with non-int operands!");
            boolean isConst = SemAn.isConstAttr.get(binExpr.fstExpr) && SemAn.isConstAttr.get(binExpr.sndExpr);
            SemAn.ofTypeAttr.put(binExpr, TYP.IntType.type);
            SemAn.isConstAttr.put(binExpr, isConst);
            SemAn.isAddrAttr.put(binExpr, false);
            return TYP.IntType.type;

        } else if (comparisonOperators.contains(binExpr.oper)) {
            boolean e1Valid = TypeEquivalence.matchesGroup(firstExprType);
            boolean e2Valid = TypeEquivalence.matchesGroup(secondExprType);
            if (!e1Valid || !e2Valid)
                throw new Report.Error(binExpr, "Binary comparison expression with illegal type of operands!");
            boolean isConst = SemAn.isConstAttr.get(binExpr.fstExpr) && SemAn.isConstAttr.get(binExpr.sndExpr);
            SemAn.ofTypeAttr.put(binExpr, TYP.BoolType.type);
            SemAn.isConstAttr.put(binExpr, isConst);
            SemAn.isAddrAttr.put(binExpr, false);
            return TYP.BoolType.type;

        } else if (logicalOperators.contains(binExpr.oper)){
            boolean e1Bool = TypeEquivalence.equivalent(firstExprType, TYP.BoolType.type);
            boolean e2Bool = TypeEquivalence.equivalent(secondExprType, TYP.BoolType.type);
            if (!e1Bool || !e2Bool) throw new Report.Error(binExpr, "Binary logical expression with non-boolean operands!");
            boolean isConst = SemAn.isConstAttr.get(binExpr.fstExpr) && SemAn.isConstAttr.get(binExpr.sndExpr);
            SemAn.ofTypeAttr.put(binExpr, TYP.BoolType.type);
            SemAn.isConstAttr.put(binExpr, isConst);
            SemAn.isAddrAttr.put(binExpr, false);
            return TYP.BoolType.type;

        } else {
            throw new RuntimeException("Forgot sth!");
        }
    }

    // TYP:26
    @Override
    public TYP.Type visit(AST.ArrExpr arrExpr, Boolean arg) {
        TYP.Type e1Type = arrExpr.arrExpr.accept(this, arg);
        TYP.Type e2Type = arrExpr.idx.accept(this, arg);
        if (!TypeEquivalence.equivalent(e2Type, TYP.IntType.type)) throw new Report.Error(arrExpr, "Array index with non integer type!");
        if (!SemAn.isAddrAttr.get(arrExpr.arrExpr)) throw new Report.Error(arrExpr, "Array with non addressable elements!");
        if (!(e1Type.actualType() instanceof TYP.ArrType arrType))
            throw new Report.Error(arrExpr, "Illegal array expression!");
        SemAn.ofTypeAttr.put(arrExpr, arrType.elemType);
        SemAn.isConstAttr.put(arrExpr, false);
        SemAn.isAddrAttr.put(arrExpr, true);
        return arrType.elemType;
    }

    // TYP:29, 30
    @Override
    public TYP.Type visit(AST.CompExpr compExpr, Boolean arg) {
        TYP.Type exprType = compExpr.recExpr.accept(this, arg).actualType();
        // to catch for example (a && b).c
        if (! (exprType instanceof TYP.RecType)) throw new Report.Error(compExpr, "Illegal type of expression for component access!");
        // Use the AST.RecType instead of TYP.RecType to get component names
        AST.RecType recType;
        try {
//            Report.info(SemAn.isTypeAttr.reverseGet(exprType).toString());
            // This cast should always succeed.
            recType = (AST.RecType) SemAn.isTypeAttr.reverseGet(exprType);
        } catch (ClassCastException e) {
            throw new Report.Error(compExpr, "This should not happen! Internal bug..");
        }

        AST.CompDefn component = null;
        for (AST.CompDefn comp : recType.comps) {
            if (comp.name.equals(compExpr.name)) component = comp;
        }

        if (component == null) throw new Report.Error(compExpr, "No field with the name '" + compExpr.name + "' exists in this record.");

        TYP.Type componentType = SemAn.isTypeAttr.get(component.type);
        SemAn.ofTypeAttr.put(compExpr, componentType);
        SemAn.isConstAttr.put(compExpr, false);
        SemAn.isAddrAttr.put(compExpr, true);
        return componentType;
    }



    // TYP:31
    @Override
    public TYP.Type visit(AST.CallExpr callExpr, Boolean arg) {
        TYP.FunType funType;
        try {
            funType = (TYP.FunType) callExpr.funExpr.accept(this, arg).actualType();
        } catch (ClassCastException e) {
            throw new Report.Error(callExpr, "Illegal expression type for a function call!");
        }

        List<TYP.Type> argumentList = new ArrayList<>();
        callExpr.argExprs.forEach(argument -> argumentList.add(argument.accept(this, arg)));
        if (funType.parTypes.size() != argumentList.size()) throw new Report.Error(callExpr, "Wrong number of parameters for this function call!");
        for (int i = 0; i < argumentList.size(); i++) {
            if (!TypeEquivalence.equivalent(argumentList.get(i), funType.parTypes.get(i))) throw new Report.Error(callExpr, "Illegal type for argument");
        }
        SemAn.ofTypeAttr.put(callExpr, funType.resType);
        SemAn.isConstAttr.put(callExpr, false);
        SemAn.isAddrAttr.put(callExpr, false);
        return funType.resType;
    }

    @Override
    public TYP.Type visit(AST.SizeExpr sizeExpr, Boolean arg) {
        sizeExpr.type.accept(this, arg);
        if (TypeEquivalence.equivalentToVoid(SemAn.isTypeAttr.get(sizeExpr.type))) throw new Report.Error(sizeExpr, "Cannot take sizeof of Void type!");
        SemAn.ofTypeAttr.put(sizeExpr, TYP.IntType.type);
        SemAn.isConstAttr.put(sizeExpr, true);
        SemAn.isAddrAttr.put(sizeExpr, false);
        return TYP.IntType.type;
    }
    // TYP:33
    @Override
    public TYP.Type visit(AST.CastExpr castExpr, Boolean arg) {
        TYP.Type exprType = castExpr.expr.accept(this, arg);
        castExpr.type.accept(this, arg);
        TYP.Type typeType = SemAn.isTypeAttr.get(castExpr.type);
        if (exprType == null) throw new Report.Error(castExpr, "Left side of the cast has no OFTYPE");
        // if the left side is a nameType
//        if (exprType == null) throw new Report.Error(castExpr, "Expression has no ofType and cannot be cast!");
        if (TypeEquivalence.equivalentToVoid(exprType) || TypeEquivalence.equivalentToVoid(typeType)) {
            throw new Report.Error(castExpr, "Cannot cast void to sth or cannot cast sth to void!");
        }
        SemAn.ofTypeAttr.put(castExpr, typeType);
        SemAn.isConstAttr.put(castExpr, SemAn.isConstAttr.get(castExpr.expr));
        SemAn.isAddrAttr.put(castExpr, SemAn.isAddrAttr.get(castExpr.expr));
        return typeType;
    }


    // TYP:34
    @Override
    public TYP.Type visit(AST.Exprs exprs, Boolean arg) {
        ArrayList<TYP.Type> exprTypes = new ArrayList<>();
        exprs.exprs.forEach(expr -> exprTypes.add(expr.accept(this, arg)));
        boolean isConst = true;
        for (AST.Expr expr : exprs.exprs) {
            isConst = isConst && SemAn.isConstAttr.get(expr);
        }
        SemAn.ofTypeAttr.put(exprs, exprTypes.getLast());
        SemAn.isConstAttr.put(exprs, isConst);
        SemAn.isAddrAttr.put(exprs, SemAn.isAddrAttr.get(exprs.exprs.last()));
        return exprTypes.getLast();
    }

    // TYP:35
    @Override
    public TYP.Type visit(AST.AsgnExpr asgnExpr, Boolean arg) {
        var e1Type = asgnExpr.fstExpr.accept(this, arg);
        var e2Type = asgnExpr.sndExpr.accept(this, arg);
        if (!TypeEquivalence.equivalent(e1Type, e2Type)) throw new Report.Error(asgnExpr, "Assignment types are not structurally equivalent!");
        if (!TypeEquivalence.matchesGroup(e1Type) || !TypeEquivalence.matchesGroup(e2Type)) {
            throw new Report.Error(asgnExpr, "Illegal expression type for a assignment expression!");
        }
        if (!SemAn.isAddrAttr.get(asgnExpr.fstExpr)) throw new Report.Error(asgnExpr, "Left expression of a assignment expression must be addressable!");
        SemAn.ofTypeAttr.put(asgnExpr, voidType);
        SemAn.isConstAttr.put(asgnExpr, false);
        SemAn.isAddrAttr.put(asgnExpr, false);
        return voidType;

    }

    // TYP:36
    @Override
    public TYP.Type visit(AST.WhileExpr whileExpr, Boolean arg) {
        TYP.Type conditionType = whileExpr.condExpr.accept(this, arg).actualType();
        whileExpr.expr.accept(this, arg);
        if (!(conditionType instanceof TYP.BoolType)) throw new Report.Error(whileExpr, "Condition must be a boolean!");
        SemAn.ofTypeAttr.put(whileExpr, voidType);
        SemAn.isConstAttr.put(whileExpr, false);
        SemAn.isAddrAttr.put(whileExpr, false);
        return voidType;
    }

    // TYP:37
    @Override
    public TYP.Type visit(AST.IfThenExpr ifThenExpr, Boolean arg) {
        var conditionType = ifThenExpr.condExpr.accept(this, arg).actualType();
        ifThenExpr.thenExpr.accept(this, arg);
        if (!(conditionType instanceof TYP.BoolType)) throw new Report.Error(ifThenExpr, "Condition must be a boolean!");
        SemAn.ofTypeAttr.put(ifThenExpr, voidType);
        SemAn.isConstAttr.put(ifThenExpr, false);
        SemAn.isAddrAttr.put(ifThenExpr, false);
        return voidType;
    }

    // TYP:38
    @Override
    public TYP.Type visit(AST.IfThenElseExpr ifThenElseExpr, Boolean arg) {
        var conditionType = ifThenElseExpr.condExpr.accept(this, arg);
        ifThenElseExpr.thenExpr.accept(this, arg);
        ifThenElseExpr.elseExpr.accept(this, arg);
        if (!(conditionType instanceof TYP.BoolType)) throw new Report.Error(ifThenElseExpr, "Condition must be a boolean!");
        SemAn.ofTypeAttr.put(ifThenElseExpr, voidType);
        SemAn.isConstAttr.put(ifThenElseExpr, false);
        SemAn.isAddrAttr.put(ifThenElseExpr, false);
        return voidType;
    }
    // TYP:39
    @Override
    public TYP.Type visit(AST.LetExpr letExpr, Boolean arg) {
        letExpr.defns.accept(this, arg);
        TYP.Type finalExprType = letExpr.expr.accept(this, arg);

        SemAn.ofTypeAttr.put(letExpr, finalExprType);
        SemAn.isConstAttr.put(letExpr, false);
        SemAn.isAddrAttr.put(letExpr, false);
        return finalExprType;
    }

    @Override
    public TYP.Type visit(AST.NameExpr nameExpr, Boolean arg) {
        AST.Defn definedAt = SemAn.defAtAttr.get(nameExpr);
        switch (definedAt) {
            // Use of a variable
            case AST.VarDefn varDefn -> {
                TYP.Type type = SemAn.isTypeAttr.get(varDefn.type);
                SemAn.ofTypeAttr.put(nameExpr, type);
                SemAn.isConstAttr.put(nameExpr, false);
                SemAn.isAddrAttr.put(nameExpr, true);
                return type;
            }
            // Use of a type
            case AST.TypDefn typDefn -> {
                return null;
            }

            case AST.FunDefn funDefn -> {
                List<TYP.Type> parTypes = new ArrayList<>();
                TYP.Type returnType = SemAn.isTypeAttr.get(funDefn.type);
                // we went through these parameters in TypeConstructor
                funDefn.pars.forEach(par -> parTypes.add(SemAn.isTypeAttr.get(par.type)));
                TYP.FunType type = new TYP.FunType(parTypes, returnType);
                SemAn.ofTypeAttr.put(nameExpr, type);
                SemAn.isConstAttr.put(nameExpr, false);
                SemAn.isAddrAttr.put(nameExpr, false);
                return type;
            }
            case AST.ParDefn parDefn -> {
                TYP.Type type = SemAn.isTypeAttr.get(parDefn.type);
                SemAn.ofTypeAttr.put(nameExpr, type);
                SemAn.isConstAttr.put(nameExpr, false);
                SemAn.isAddrAttr.put(nameExpr, true);
                return type;
            }
            case AST.CompDefn compDefn -> {

                // When we create a CompExpr in AST, we do not create a separate node for the component name (that is, a NameType).
                // The reason for that is we cannot programatically fetch a component. So arriving in this situation means something is wrong
                // with the compiler.
                throw new RuntimeException("Suspicious NameType");
            }
            case null, default -> throw new Report.Error("Forgot sth!");
        }
    }



}
