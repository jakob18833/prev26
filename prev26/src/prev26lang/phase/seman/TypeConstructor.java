package prev26lang.phase.seman;

import prev26lang.phase.abstr.AST;

public class TypeConstructor implements AST.FullVisitor<Void, Void> {
    @Override
    public Void visit(AST.Nodes<? extends AST.Node> nodes, Void arg) {
        return AST.FullVisitor.super.visit(nodes, arg);
    }

    @Override
    public Void visit(AST.TypDefn typDefn, Void arg) {
        return AST.FullVisitor.super.visit(typDefn, arg);
    }

    @Override
    public Void visit(AST.VarDefn varDefn, Void arg) {
        return AST.FullVisitor.super.visit(varDefn, arg);
    }

    @Override
    public Void visit(AST.DefFunDefn defFunDefn, Void arg) {
        return AST.FullVisitor.super.visit(defFunDefn, arg);
    }

    @Override
    public Void visit(AST.ExtFunDefn extFunDefn, Void arg) {
        return AST.FullVisitor.super.visit(extFunDefn, arg);
    }

    @Override
    public Void visit(AST.ParDefn parDefn, Void arg) {
        return AST.FullVisitor.super.visit(parDefn, arg);
    }

    @Override
    public Void visit(AST.CompDefn compDefn, Void arg) {
        return AST.FullVisitor.super.visit(compDefn, arg);
    }

    @Override
    public Void visit(AST.AtomType atomType, Void arg) {
        return AST.FullVisitor.super.visit(atomType, arg);
    }

    @Override
    public Void visit(AST.ArrType arrType, Void arg) {
        return AST.FullVisitor.super.visit(arrType, arg);
    }

    @Override
    public Void visit(AST.PtrType ptrType, Void arg) {
        return AST.FullVisitor.super.visit(ptrType, arg);
    }

    @Override
    public Void visit(AST.StrType strType, Void arg) {
        return AST.FullVisitor.super.visit(strType, arg);
    }

    @Override
    public Void visit(AST.UniType uniType, Void arg) {
        return AST.FullVisitor.super.visit(uniType, arg);
    }

    @Override
    public Void visit(AST.FunType funType, Void arg) {
        return AST.FullVisitor.super.visit(funType, arg);
    }

    @Override
    public Void visit(AST.NameType nameType, Void arg) {
        return AST.FullVisitor.super.visit(nameType, arg);
    }

    @Override
    public Void visit(AST.ArrExpr arrExpr, Void arg) {
        return AST.FullVisitor.super.visit(arrExpr, arg);
    }

    @Override
    public Void visit(AST.AsgnExpr asgnExpr, Void arg) {
        return AST.FullVisitor.super.visit(asgnExpr, arg);
    }

    @Override
    public Void visit(AST.AtomExpr atomExpr, Void arg) {
        return AST.FullVisitor.super.visit(atomExpr, arg);
    }

    @Override
    public Void visit(AST.BinExpr binExpr, Void arg) {
        return AST.FullVisitor.super.visit(binExpr, arg);
    }

    @Override
    public Void visit(AST.CallExpr callExpr, Void arg) {
        return AST.FullVisitor.super.visit(callExpr, arg);
    }

    @Override
    public Void visit(AST.CastExpr castExpr, Void arg) {
        return AST.FullVisitor.super.visit(castExpr, arg);
    }

    @Override
    public Void visit(AST.CompExpr compExpr, Void arg) {
        return AST.FullVisitor.super.visit(compExpr, arg);
    }

    @Override
    public Void visit(AST.NameExpr nameExpr, Void arg) {
        return AST.FullVisitor.super.visit(nameExpr, arg);
    }

    @Override
    public Void visit(AST.PfxExpr pfxExpr, Void arg) {
        return AST.FullVisitor.super.visit(pfxExpr, arg);
    }

    @Override
    public Void visit(AST.SfxExpr sfxExpr, Void arg) {
        return AST.FullVisitor.super.visit(sfxExpr, arg);
    }

    @Override
    public Void visit(AST.SizeExpr sizeExpr, Void arg) {
        return AST.FullVisitor.super.visit(sizeExpr, arg);
    }

    @Override
    public Void visit(AST.Exprs exprs, Void arg) {
        return AST.FullVisitor.super.visit(exprs, arg);
    }

    @Override
    public Void visit(AST.IfThenExpr ifThenExpr, Void arg) {
        return AST.FullVisitor.super.visit(ifThenExpr, arg);
    }

    @Override
    public Void visit(AST.IfThenElseExpr ifThenElseExpr, Void arg) {
        return AST.FullVisitor.super.visit(ifThenElseExpr, arg);
    }

    @Override
    public Void visit(AST.WhileExpr whileExpr, Void arg) {
        return AST.FullVisitor.super.visit(whileExpr, arg);
    }

    @Override
    public Void visit(AST.LetExpr letExpr, Void arg) {
        return AST.FullVisitor.super.visit(letExpr, arg);
    }
}
