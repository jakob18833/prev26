package prev26lang.phase.seman;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;
import prev26lang.phase.abstr.Abstr;

import java.util.*;



public class TypeConstructor implements AST.FullVisitor<Void, Void> {
    AST.Visitor<TYP.Type, Void> typePreviewer = new TypePreviewer();
    @Override
    public Void visit(AST.Nodes<? extends AST.Node> nodes, Void arg) {

        if (nodes.size() == 0) return null;

        // AST root, let expression
        if (nodes.first() instanceof AST.FullDefn) {
            // first pass

            for (AST.Node node : nodes) {
                if (node instanceof AST.TypDefn typDefnNode) {
                    TYP.NameType t = new TYP.NameType(typDefnNode.name);
                    SemAn.isTypeAttr.put(node, t);
                }
            }

            //second pass
            for (AST.Node node: nodes) {
                // we are interested just in typDefn nodes, not varDefn
                if (node instanceof AST.TypDefn typDefnNode) {
                    TYP.NameType t = (TYP.NameType) SemAn.isTypeAttr.get(node);

                    // If the type defined in typDefnNode is NameType, we will walk NameType definitions to
                    // the first non NameType. We detect cycles here.
                    HashSet<AST.TypDefn> visited = new HashSet<>();
                    AST.TypDefn helper = typDefnNode;
                    while ( helper.type instanceof AST.NameType nameTypeHelper) {
//                        Report.info("Currrently at typDefn " + helper.name + ", visited=" + visited);
                        // get the definition
                        AST.Defn nextHelper = SemAn.defAtAttr.get(nameTypeHelper);
                        // if the definition statement is one of variable, parameter, or a function, we throw it.
                        // Note that we can still use AST.FunType, but either case, it will be defined
                        // inside a TypDefn node.
                        if (! (nextHelper instanceof AST.TypDefn)) throw new Report.Error(typDefnNode, "Cannot define a type with a reference to variable!");
                        // Check for cyclic types
                        else if (visited.contains(nextHelper)) {
                            throw new Report.Error(typDefnNode, "Cyclic type detected!");
                        } else {

                            visited.add((AST.TypDefn) nextHelper);
                            helper = (AST.TypDefn) nextHelper;
                        }
                    }
                    // Here we can assume helper is now a reference to a TypDefn, which contains type which is not NameType.
                    // We store its placeholder type.
                    TYP.Type placeholder = helper.type.accept(typePreviewer, null);
//                    Report.info("Setting the actual type of " + t + " to " + placeholder);
                    t.setActType(placeholder);
                }
            }
            // third pass
            for (AST.Node node : nodes) {
                node.accept(this, arg);
            }

        } else {
            for (AST.Node node: nodes) {
                node.accept(this, arg);
            }
        }

        return null;

    }

    @Override
    public Void visit(AST.TypDefn typDefn, Void arg) {

        typDefn.type.accept(this, arg);
        // Get the NameType object we stored in the first pass and provide info about actual type
        TYP.NameType typDefnType = (TYP.NameType) SemAn.isTypeAttr.get(typDefn);
        TYP.Type actType = SemAn.isTypeAttr.get(typDefn.type);
        typDefnType.setActType(actType);
        return arg;
    }


    // TYPES


    @Override
    public Void visit(AST.AtomType atomType, Void arg) {
        switch (atomType.type) {

            case AST.AtomType.Type.INT:
                SemAn.isTypeAttr.put(atomType, TYP.IntType.type);
                break;
            case AST.AtomType.Type.BOOL:
                SemAn.isTypeAttr.put(atomType, TYP.BoolType.type);
                break;
            case AST.AtomType.Type.CHAR:
                SemAn.isTypeAttr.put(atomType, TYP.CharType.type);
                break;
            case AST.AtomType.Type.VOID:
                SemAn.isTypeAttr.put(atomType, TYP.VoidType.type);
        }
        return null;
    }

    @Override
    public Void visit(AST.PtrType ptrType, Void arg) {
        ptrType.baseType.accept(this, arg);
        TYP.Type baseType = SemAn.isTypeAttr.get(ptrType.baseType);
        if (! TypeEquivalence.equivalent(baseType, TYP.VoidType.type)) {
            SemAn.isTypeAttr.put(ptrType, new TYP.PtrType(baseType));
        } else {
            throw new Report.Error(ptrType, "Cannot have a pointer to Void!");
        }
        return null;
    }

    @Override
    public Void visit(AST.ArrType arrType, Void arg) {
        arrType.elemType.accept(this, arg);
        TYP.Type elementType = SemAn.isTypeAttr.get(arrType.elemType);
        long numElems;
        try {
            numElems = Long.parseLong(arrType.numElems);
        } catch (NumberFormatException e) {
            throw new Report.Error(Abstr.locAttr.get(arrType), "Array number of elements too large!");
        }
        if (0 < numElems && ! TypeEquivalence.equivalent(elementType, TYP.VoidType.type)) {
            SemAn.isTypeAttr.put(arrType, new TYP.ArrType(elementType, numElems));
        } else {
            throw new Report.Error(arrType, "Illegal type for array or non positive number of elements!");
        }
        return null;
    }

    @Override
    public Void visit(AST.StrType strType, Void arg) {
        for (var component: strType.comps) component.type.accept(this, arg);
        List<TYP.Type> compTypes = new ArrayList<>();
        for (var node: strType.comps) {
            TYP.Type compType = SemAn.isTypeAttr.get(node.type);
            if (TypeEquivalence.equivalent(compType, TYP.VoidType.type)) {
                throw new Report.Error(strType, "Struct type cannot contain components of type Void!");
            }
            compTypes.add(compType);
        }
        SemAn.isTypeAttr.put(strType, new TYP.StrType(compTypes));
        return null;
    }

    @Override
    public Void visit(AST.UniType uniType, Void arg) {
        for (var component: uniType.comps) component.type.accept(this, arg);
        List<TYP.Type> compTypes = new ArrayList<>();
        for (var component: uniType.comps) {
            TYP.Type compType = SemAn.isTypeAttr.get(component.type);
            if (TypeEquivalence.equivalent(compType, TYP.VoidType.type)) {
                throw new Report.Error(uniType, "Struct type cannot contain components of type Void!");
            }
            compTypes.add(compType);
        }
        SemAn.isTypeAttr.put(uniType, new TYP.UniType(compTypes));
        return null;
    }

    @Override
    public Void visit(AST.FunType funType, Void arg) {
        Set<Class<? extends TYP.Type>> allowedTypesPar = Set.of(TYP.IntType.class, TYP.CharType.class, TYP.BoolType.class, TYP.PtrType.class, TYP.FunType.class);
        Set<Class<? extends TYP.Type>> allowedTypesReturn = Set.of(TYP.IntType.class, TYP.CharType.class, TYP.BoolType.class, TYP.VoidType.class, TYP.PtrType.class, TYP.FunType.class);
        // accept children
        funType.resType.accept(this, arg);
        funType.parTypes.forEach(par -> par.accept(this, arg));

        // store children's types
        LinkedHashMap<AST.Type, TYP.Type> parTypes = new LinkedHashMap<>();
        funType.parTypes.forEach(par -> parTypes.put(par, SemAn.isTypeAttr.get(par).actualType()));
        TYP.Type resType = SemAn.isTypeAttr.get(funType.resType);

        for (var entry: parTypes.entrySet()) {
            Class<? extends TYP.Type> actualTypeClass = entry.getValue().actualType().getClass();
            if (! allowedTypesPar.contains(actualTypeClass)) {
                throw new Report.Error(Abstr.locAttr.get(entry.getKey()), "Illegal parameter type for FunType!");
            }
        }
        if (! allowedTypesReturn.contains(resType.actualType().getClass())) {
            throw new Report.Error(Abstr.locAttr.get(funType.resType), "Illegal return type for FunType!");
        }
        SemAn.isTypeAttr.put(funType, new TYP.FunType(parTypes.values().stream().toList(), resType));
        return null;
    }

    @Override
    public Void visit(AST.NameType nameType, Void arg) {

//        Report.info("Visiting nameType with name=" + nameType.name);
//        Report.info(SemAn.isTypeAttr.get(nameType).toString());

        // We get the node, where our nameType is defined. Because we
        // never visit any AST.NameType nodes with variable definitions,
        // we can cast the type.
        AST.TypDefn typDefn = (AST.TypDefn) SemAn.defAtAttr.get(nameType);
        SemAn.isTypeAttr.put(nameType, SemAn.isTypeAttr.get(typDefn));
        return null;
    }



}
