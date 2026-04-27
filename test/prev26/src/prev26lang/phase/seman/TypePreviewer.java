package prev26lang.phase.seman;
import prev26lang.phase.abstr.AST;

import java.util.ArrayList;


public class TypePreviewer implements AST.Visitor<TYP.Type, Void> {
    TYP.Type placeholder = TYP.IntType.type;

    @Override
    public TYP.Type visit(AST.AtomType atomType, Void arg) {
        return switch (atomType.type) {
            case AST.AtomType.Type.INT -> TYP.IntType.type;
            case AST.AtomType.Type.BOOL -> TYP.BoolType.type;
            case AST.AtomType.Type.CHAR -> TYP.CharType.type;
            case AST.AtomType.Type.VOID -> TYP.VoidType.type;
        };
    }

    @Override
    public TYP.Type visit(AST.ArrType arrType, Void arg) {
        return new TYP.ArrType(placeholder, 1L);
    }

    @Override
    public TYP.Type visit(AST.PtrType ptrType, Void arg) {
        return new TYP.PtrType(placeholder);
    }

    @Override
    public TYP.Type visit(AST.StrType strType, Void arg) {
        return new TYP.StrType(new ArrayList<>());
    }

    @Override
    public TYP.Type visit(AST.UniType uniType, Void arg) {
        return new TYP.UniType(new ArrayList<>());
    }

    @Override
    public TYP.Type visit(AST.FunType funType, Void arg) {
        return new TYP.FunType(new ArrayList<>(), placeholder);
    }

    @Override
    public TYP.Type visit(AST.NameType nameType, Void arg) {
        return new TYP.NameType("placeholder");
    }
}