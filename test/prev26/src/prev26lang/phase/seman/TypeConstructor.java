package prev26lang.phase.seman;

import prev26lang.common.report.Report;
import prev26lang.phase.abstr.AST;

import java.util.*;

public class TypeConstructor implements AST.FullVisitor<TYP.Type, Void> {
    AST.Visitor<TYP.Type, Void> typePreviewer = new TypePreviewer();
    @Override
    public TYP.Type visit(AST.Nodes<? extends AST.Node> nodes, Void arg) {

        if (nodes.size() == 0) return null;

        if (nodes.first() instanceof AST.FullDefn) {
            // first pass
            for (AST.Node node : nodes) {
                if (node instanceof AST.TypDefn typDefnNode) {
                    TYP.NameType type = new TYP.NameType(typDefnNode.name);
                    SemAn.isTypeAttr.put(node, type);


                }
            }
            // Set placeholder types. This is necessary to do in this manner for 2 reasons:
            // 1. typ a = (::a)
            // This is allowed, but if no actualType() was set on 'a' beforehand, we will get an error
            // when recursing into FunDefn and checking matchesGroupOrVoid on return type.

            // So in the light of 1., we try just try setting the actualType directly, without following
            // the chain of renamings. Then we hit into a different problem:
            // 2.
            // typ a = b
            // typ b = a
            // fun main():int=let var x: a in 1 end
            // This should throw an error, but we get a compiler crash instead.
            // When we want to structurally compare varDefn.type to Void,
            // we call its actualType, which is NameType of 'b'. The actualType of that is NameType of 'a',
            // and Slivko's code throws an error.
            for (AST.Node node: nodes) {
                // we are interested just in typDefn nodes, not varDefn
                if (node instanceof AST.TypDefn typDefnNode) {
                    TYP.NameType t = (TYP.NameType) SemAn.isTypeAttr.get(node);

                    // If the type defined in typDefnNode is NameType, we will walk NameType definitions to
                    // the first non NameType. We detect cycles here.
                    HashSet<AST.TypDefn> visited = new HashSet<>();
                    AST.TypDefn helper = typDefnNode;
                    while ( helper.type instanceof AST.NameType nameTypeHelper) {
                        AST.Defn nextHelper = SemAn.defAtAttr.get(nameTypeHelper);
                        // if the definition statement is one of variable, or a function, we throw an error.
                        if (! (nextHelper instanceof AST.TypDefn)) throw new Report.Error(typDefnNode, "Cannot define a type with a reference to variable!");
                        else if (visited.contains(nextHelper)) {
                            throw new Report.Error(typDefnNode, "Cyclic type detected in TypeConstructor!");
                        } else {
                            visited.add((AST.TypDefn) nextHelper);
                            helper = (AST.TypDefn) nextHelper;
                        }
                    }

                    // Here we can assume helper is now a reference to a TypDefn, which contains type which is not NameType.
                    // We store its placeholder type.
                    TYP.Type placeholder = helper.type.accept(typePreviewer, null);
                    t.setActType(placeholder);
                }
            }

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
    public TYP.Type visit(AST.TypDefn typDefn, Void arg) {

        TYP.Type actType = typDefn.type.accept(this, arg);
        // Get the NameType object we stored in the first pass and append the actual type
        TYP.NameType storedNameType = (TYP.NameType) SemAn.isTypeAttr.get(typDefn);
        storedNameType.setActType(actType);
        return storedNameType;
    }


    // TYPES

    @Override
    public TYP.Type visit(AST.AtomType atomType, Void arg) {
        TYP.Type type = switch (atomType.type) {
            case AST.AtomType.Type.INT -> TYP.IntType.type;
            case AST.AtomType.Type.BOOL -> TYP.BoolType.type;
            case AST.AtomType.Type.CHAR -> TYP.CharType.type;
            case AST.AtomType.Type.VOID -> TYP.VoidType.type;
        };
        SemAn.isTypeAttr.put(atomType, type);
        return type;
    }

    @Override
    public TYP.Type visit(AST.PtrType ptrType, Void arg) {
        TYP.Type baseType = ptrType.baseType.accept(this, arg);
        // Check if it is directly (not structurally) equivalent to Void
        if (baseType instanceof TYP.VoidType) {
            throw new Report.Error(ptrType, "Cannot have a pointer to Void!");
        }
        TYP.PtrType type = new TYP.PtrType(baseType);
//        System.out.println(type.getClass().getSimpleName());
//        System.out.println(baseType.getClass().getSimpleName());
        SemAn.isTypeAttr.put(ptrType, type);

        return type;
    }

    @Override
    public TYP.Type visit(AST.ArrType arrType, Void arg) {
        TYP.Type elementType = arrType.elemType.accept(this, arg);
        long numElems;
        try {
            numElems = Long.parseLong(arrType.numElems);
        } catch (NumberFormatException e) {
            throw new Report.Error(arrType, "Array number of elements overflow!");
        }
        if (0 < numElems && ! TypeEquivalence.equivalentToVoid(elementType)) {
            TYP.Type type = new TYP.ArrType(elementType, numElems);
            SemAn.isTypeAttr.put(arrType, type);
            return type;
        } else {
            throw new Report.Error(arrType, "Illegal type for array or non positive number of elements!");
        }
    }

    @Override
    public TYP.Type visit(AST.StrType strType, Void arg) {
        List<TYP.Type> compTypes = new ArrayList<>();
        for (var component: strType.comps) compTypes.add(component.type.accept(this, arg));
        for (var node: strType.comps) {
            if (compTypes.stream().anyMatch(TypeEquivalence::equivalentToVoid)) {
                throw new Report.Error(strType, "Struct cannot have components structurally equivalent to void!");
            }
        }
        TYP.Type type = new TYP.StrType(compTypes);
        SemAn.isTypeAttr.put(strType, type);
        return type;
    }

    @Override
    public TYP.Type visit(AST.UniType uniType, Void arg) {
        List<TYP.Type> compTypes = new ArrayList<>();
        for (var component: uniType.comps) compTypes.add(component.type.accept(this, arg));
        for (var node: uniType.comps) {
            if (compTypes.stream().anyMatch(TypeEquivalence::equivalentToVoid)) {
                throw new Report.Error(uniType, "Union cannot have components structurally equivalent to void!");
            }
        }
        TYP.Type type = new TYP.UniType(compTypes);
        SemAn.isTypeAttr.put(uniType, type);
        return type;
    }

    @Override
    public TYP.Type visit(AST.FunType funType, Void arg) {
        // accept children
        LinkedHashMap<AST.Type, TYP.Type> parTypes = new LinkedHashMap<>();
        TYP.Type resType = funType.resType.accept(this, arg);
        funType.parTypes.forEach(par -> parTypes.put(par, par.accept(this, arg)));

        if (!parTypes.values().stream().allMatch(TypeEquivalence::matchesGroup)) {
            throw new Report.Error(funType, "Illegal parameter types for a funType!");
        }
        if (!TypeEquivalence.matchesGroupOrVoid(resType)) throw new Report.Error(funType, "Illegal return type for a funType!");
        TYP.Type type = new TYP.FunType(parTypes.values().stream().toList(), resType);
        SemAn.isTypeAttr.put(funType, type);
        return type;
    }

    @Override
    public TYP.Type visit(AST.NameType nameType, Void arg) {

//        Report.info("Visiting nameType with name=" + nameType.name);
//        Report.info(SemAn.isTypeAttr.get(nameType).toString());

        // We get the node, where our nameType is defined. If we arrive at something else than a TypDefn, we
        // throw an error.

        AST.TypDefn typDefn = null;
        try {
            typDefn = (AST.TypDefn) SemAn.defAtAttr.get(nameType);
        } catch (ClassCastException e) {
            throw new Report.Error(nameType, "NameType defined not in typDefn found in TypeConstructor!");
        }
//        System.out.println("defined at " + typDefn);
//        System.out.println("type: " + SemAn.isTypeAttr.get(typDefn.type));
        // We just take the nameType of the definition and let it fill in the actualType itself.
        TYP.Type type = SemAn.isTypeAttr.get(typDefn);
        SemAn.isTypeAttr.put(nameType, type);
        return type;
    }



}
