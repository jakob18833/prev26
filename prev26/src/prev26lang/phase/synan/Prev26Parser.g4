parser grammar Prev26Parser;

@header {

    package prev26lang.phase.synan;

    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;
    import prev26lang.phase.abstr.*;

}

@members {

    private Location loc(final Token tok) { return new Location((LexAn.LocLogToken)tok); }
    private Location loc(final Locatable loc) { return new Location(loc); }
    private Location loc(final Token tok1, final Token tok2) { return new Location((LexAn.LocLogToken)tok1, (LexAn.LocLogToken)tok2); }
    private Location loc(final Token tok1, final Locatable loc2) { if (loc2 == null) return null; return new Location((LexAn.LocLogToken)tok1, loc2); }
    private Location loc(final Locatable loc1, final Token tok2) { if (loc1 == null) return null; return new Location(loc1, (LexAn.LocLogToken)tok2); }
    private Location loc(final Locatable loc1, final Locatable loc2) { if ((loc1 == null) || (loc2 == null)) return null; return new Location(loc1, loc2); }

}

options{
    tokenVocab=Prev26Lexer;
}
source
    returns [AST.Nodes<AST.FullDefn> ast] :
        p=prog EOF
            { $ast = $p.ast; }
;

// WARNING - pd and pd1 are required in order not to redefine variables. Cannot be a locals variable for some stupid reason.
par_list_nodes
    returns [AST.Nodes<AST.ParDefn> nodes]
    locals [List<AST.ParDefn> pars = new ArrayList<>(); ;] :
        n1=NAME COLON t=type
            { AST.ParDefn pd = new AST.ParDefn($n1.text, $t.ast); $pars.add(pd); Abstr.locAttr.put(pd, loc($n1, $t.stop)); }
        (COMMA n=NAME COLON t=type { AST.ParDefn pd1 = new AST.ParDefn($n.text, $t.ast); $pars.add(pd1); Abstr.locAttr.put(pd1, loc($n, $t.stop)); })*
    { $nodes = new AST.Nodes<>($pars); Abstr.locAttr.put($nodes, loc($n1, $t.stop)); }
;

par_list_zero_nodes returns [AST.Nodes<AST.ParDefn> nodes] :
    { $nodes = new AST.Nodes<>(); Abstr.locAttr.put($nodes, loc(getCurrentToken())); }
    | a=par_list_nodes
        { $nodes = $a.nodes; }
;

expr_list
    returns [AST.Expr ast]
    locals [List<AST.Expr> exprs = new ArrayList<>(); Token start;] :
        e=expr
            { $exprs.add($e.ast); $start = $e.start; }
        (COMMA e=expr { $exprs.add($e.ast); })*
    { AST.Nodes<AST.Expr> nodes = new AST.Nodes<>($exprs); $ast = new AST.Exprs(nodes);
    Abstr.locAttr.put($ast, loc($start, $e.stop)); Abstr.locAttr.put(nodes, loc($start, $e.stop)); }
;

expr_list_zero
    returns [AST.Expr ast] :
        { AST.Nodes<AST.Expr> nodes = new AST.Nodes<>(); $ast = new AST.Exprs(nodes);
        Abstr.locAttr.put(nodes, loc(getCurrentToken())); Abstr.locAttr.put($ast, loc(getCurrentToken())); }
        | e=expr_list
            { $ast = $e.ast; }
;



expr_list_zero_nodes
    returns [AST.Nodes<AST.Expr> nodes]
    locals [List<AST.Expr> exprs = new ArrayList<>(); Token start; ] :
        { $nodes = new AST.Nodes<>(); Abstr.locAttr.put($nodes, loc(getCurrentToken())); }
        | e=expr
            { $exprs.add($e.ast); $start = $e.start; }
        (COMMA e=expr { $exprs.add($e.ast); })*
    { $nodes = new AST.Nodes<>($exprs); Abstr.locAttr.put($nodes, loc($start, $e.stop)); }
;

// Component of a record or struct
comp_list_nodes
    returns [AST.Nodes<AST.CompDefn> nodes]
    locals [List<AST.CompDefn> comps = new ArrayList<>()] :
        n1=NAME COLON t=type
            { AST.CompDefn cd = new AST.CompDefn($n1.text, $t.ast); $comps.add(cd); Abstr.locAttr.put(cd, loc($n1, $t.stop)); }
        (COMMA n=NAME COLON t=type
            { cd = new AST.CompDefn($n.text, $t.ast); $comps.add(cd); Abstr.locAttr.put(cd, loc($n, $t.stop)); } )*
    { $nodes = new AST.Nodes<>($comps); Abstr.locAttr.put($nodes, loc($n1, $t.stop)); } ;

comp_list_zero_nodes
    returns [AST.Nodes<AST.CompDefn> nodes] :
        { $nodes = new AST.Nodes<>(); Abstr.locAttr.put($nodes, loc(getCurrentToken())); }
        | c=comp_list_nodes
            { $nodes = $c.nodes; }
;

type_list_nodes
    returns [AST.Nodes<AST.Type> nodes]
    locals [List<AST.Type> types = new ArrayList<>(); Token start; ] :
        t=type
            { $types.add($t.ast); $start = $t.start; }
        (COMMA t=type { $types.add($t.ast); })*
    { $nodes = new AST.Nodes<>($types); Abstr.locAttr.put($nodes, loc($start, $t.stop)); }
;

type_list_zero_nodes
    returns [AST.Nodes<AST.Type> nodes] :
        { $nodes = new AST.Nodes<>(); Abstr.locAttr.put($nodes, loc(getCurrentToken())); }
        | t=type_list_nodes
            { $nodes = $t.nodes; }
;

defn_list_nodes
    returns [AST.Nodes<AST.FullDefn> nodes]
    locals [List<AST.FullDefn> defns = new ArrayList<>(); Token start;] :
        d=defn
            { $defns.add($d.ast); $start = $d.start; }
        (d=defn { $defns.add($d.ast); })*
    { $nodes = new AST.Nodes<>($defns); Abstr.locAttr.put($nodes, loc($start, $d.stop)); }
;

prog returns [AST.Nodes<AST.FullDefn> ast]
    locals [List<AST.FullDefn> defs = new ArrayList<>(); Token start; ] :
        d=defn
            { $defs.add($d.ast); $start = $d.start; }
        (d=defn { $defs.add($d.ast); })*
    { $ast = new AST.Nodes<>($defs); Abstr.locAttr.put($ast, loc($start, $d.stop));}
;

// definitions
defn returns [AST.FullDefn ast] :
    TYP n=NAME DEFINE t=type                                                    // SYN:2
        { $ast = new AST.TypDefn($n.text, $t.ast);
          Abstr.locAttr.put($ast, loc($TYP, $t.stop)); }
    | VAR n=NAME COLON t=type                                                   // SYN:3
        { $ast = new AST.VarDefn($n.text, $t.ast);
          Abstr.locAttr.put($ast, loc($VAR, $t.stop)); }
    | FUN n=NAME LEFTBR a=par_list_zero_nodes RIGHTBR COLON t=type              // SYN:4
        { $ast = new AST.ExtFunDefn($n.text, $a.nodes, $t.ast);
          Abstr.locAttr.put($ast, loc($FUN, $t.stop)); }
    | FUN n=NAME LEFTBR a=par_list_zero_nodes RIGHTBR COLON t=type DEFINE e=expr_list  // SYN:5
        { $ast = new AST.DefFunDefn($n.text, $a.nodes, $t.ast, $e.ast);
          Abstr.locAttr.put($ast, loc($FUN, $e.stop)); }
    ;

// types
type
    returns [AST.Type ast] :
        tok=INT   { $ast = new AST.AtomType(AST.AtomType.Type.INT);  Abstr.locAttr.put($ast, loc($tok, $tok)); }  // SYN:6
        | tok=CHAR  { $ast = new AST.AtomType(AST.AtomType.Type.CHAR); Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=BOOL  { $ast = new AST.AtomType(AST.AtomType.Type.BOOL); Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=VOID  { $ast = new AST.AtomType(AST.AtomType.Type.VOID); Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | n=NAME    { $ast = new AST.NameType($n.text); Abstr.locAttr.put($ast, loc($n, $n)); }                   // SYN:7

        | tok=LEFTSQBR INTCONST RIGHTSQBR t=type                                    // SYN:8
            { $ast = new AST.ArrType($t.ast, $INTCONST.text); Abstr.locAttr.put($ast, loc($tok, $t.stop)); }
        | CARET t=type                                                               // SYN:9
            { $ast = new AST.PtrType($t.ast); Abstr.locAttr.put($ast, loc($CARET, $t.stop)); }
        | LEFTBR a=comp_list_nodes RIGHTBR                                           // SYN:10
            { $ast = new AST.StrType($a.nodes); Abstr.locAttr.put($ast, loc($LEFTBR, $RIGHTBR)); }
        | LEFTCBR a1=comp_list_zero_nodes RIGHTCBR                                    // SYN:11
            { $ast = new AST.UniType($a1.nodes); Abstr.locAttr.put($ast, loc($LEFTCBR, $RIGHTCBR)); }
        | LEFTBR COLON tl=type_list_zero_nodes COLON t=type RIGHTBR                  // SYN:12
            { $ast = new AST.FunType($tl.nodes, $t.ast); Abstr.locAttr.put($ast, loc($LEFTBR, $RIGHTBR)); }
        | LEFTBR t=type RIGHTBR                                                      // SYN:13
    { $ast = $t.ast; }
;


expr
    returns [AST.Expr ast] :
        e=defineExpr { $ast = $e.ast; }
;

defineExpr
    returns [AST.Expr ast] :
        e=typeExpr { $ast = $e.ast; }
        | e1=typeExpr DEFINE e2=typeExpr
            { $ast = new AST.AsgnExpr($e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
    ;

typeExpr
    returns [AST.Expr ast] :
        e=logicOrExpr
            { $ast = $e.ast; }
        | e=logicOrExpr AS t=type
            { $ast = new AST.CastExpr($t.ast, $e.ast);
            Abstr.locAttr.put($ast, loc($e.start, $t.stop)); }
;

logicOrExpr
    returns [AST.Expr ast] :
        e1=logicAndExpr { $ast = $e1.ast; }
        (OR e2=logicAndExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.OR, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        )*
;

logicAndExpr
    returns [AST.Expr ast] :
        e1=comparisonExpr { $ast = $e1.ast; }
        (AND e2=comparisonExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.AND, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        )*
;

comparisonExpr
    returns [AST.Expr ast] :
        e1=additiveExpr { $ast = $e1.ast; }
        ( EQUALS e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.EQU, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        | NOTEQUALS e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.NEQ, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        | LESSER e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.LTH, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        | GREATER e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.GTH, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        | LEQUALS e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.LEQ, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        | MEQUALS e2=additiveExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.GEQ, $e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $e2.stop)); }
        )?
;

additiveExpr
    returns [AST.Expr ast] :
        e=multiplicativeExpr { $ast = $e.ast; }
        ( PLUS e2=multiplicativeExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.ADD, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e.start, $e2.stop)); }
        | MINUS e2=multiplicativeExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.SUB, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e.start, $e2.stop)); }
        )*
;

multiplicativeExpr
    returns [AST.Expr ast] :
        e=prefixExpr { $ast = $e.ast; }
        ( MULTIPLY e2=prefixExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.MUL, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e.start, $e2.stop)); }
        | DIVIDE e2=prefixExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.DIV, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e.start, $e2.stop)); }
        | MODULO e2=prefixExpr
            { $ast = new AST.BinExpr(AST.BinExpr.Oper.MOD, $ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e.start, $e2.stop)); }
        )*
;

prefixExpr
    returns [AST.Expr ast] :
        e1=postfixExpr { $ast = $e1.ast; }
        | tok=NOT e=prefixExpr
            { $ast = new AST.PfxExpr(AST.PfxExpr.Oper.NOT, $e.ast);
            Abstr.locAttr.put($ast, loc($tok, $e.stop)); }
        | tok=MINUS i=INTCONST
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.INT, "-" + $i.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=PLUS i=INTCONST
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.INT, "+" + $i.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=PLUS e=prefixExpr
            { $ast = new AST.PfxExpr(AST.PfxExpr.Oper.ADD, $e.ast);
            Abstr.locAttr.put($ast, loc($tok, $e.stop)); }
        | tok=MINUS e=prefixExpr
            { $ast = new AST.PfxExpr(AST.PfxExpr.Oper.SUB, $e.ast);
            Abstr.locAttr.put($ast, loc($tok, $e.stop)); }
        | tok=CARET e=prefixExpr
            { $ast = new AST.PfxExpr(AST.PfxExpr.Oper.PTR, $e.ast);
            Abstr.locAttr.put($ast, loc($tok, $e.stop)); }
        | tok=SIZEOF t=type
            { $ast = new AST.SizeExpr($t.ast);
            Abstr.locAttr.put($ast, loc($tok, $t.stop)); }
;

postfixExpr
    returns [AST.Expr ast] :
        e=atomExpr { $ast = $e.ast; }
        | e1=postfixExpr LEFTSQBR e2=expr RIGHTSQBR
            { $ast = new AST.ArrExpr($e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $RIGHTSQBR)); }
        | e1=postfixExpr CARET
            { $ast = new AST.SfxExpr(AST.SfxExpr.Oper.PTR, $e1.ast);
            Abstr.locAttr.put($ast, loc($e1.start, $CARET)); }
        | e1=postfixExpr PERIOD NAME
            { $ast = new AST.CompExpr($e1.ast, $NAME.text);
            Abstr.locAttr.put($ast, loc($e1.start, $NAME)); }
        | e1=postfixExpr LEFTBR elist=expr_list_zero_nodes RIGHTBR
            { $ast = new AST.CallExpr($e1.ast, $elist.nodes);
            Abstr.locAttr.put($ast, loc($e1.start, $RIGHTBR)); }
;

atomExpr
    returns [AST.Expr ast] :
        IF e1=expr THEN e2=expr_list END
            { $ast = new AST.IfThenExpr($e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($IF, $END)); }
        | IF e1=expr THEN e2=expr_list ELSE e3=expr_list END
            { $ast = new AST.IfThenElseExpr($e1.ast, $e2.ast, $e3.ast);
            Abstr.locAttr.put($ast, loc($IF, $END)); }
        | WHILE e1=expr DO e2=expr_list END
            { $ast = new AST.WhileExpr($e1.ast, $e2.ast);
            Abstr.locAttr.put($ast, loc($WHILE, $END)); }
        | LET d1=defn_list_nodes IN e2=expr_list END
            { $ast = new AST.LetExpr($d1.nodes, $e2.ast);
            Abstr.locAttr.put($ast, loc($LET, $END)); }
        | LEFTBR e=expr_list RIGHTBR
            { $ast = $e.ast; }

        | tok=INTCONST
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.INT, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }

        | tok=TRUE
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.BOOL, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=FALSE
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.BOOL, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=CHARCONST
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.CHAR, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=STRING
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.STR, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=NONE
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.VOID, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=NIL
            { $ast = new AST.AtomExpr(AST.AtomExpr.Type.PTR, $tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
        | tok=NAME
            { $ast = new AST.NameExpr($tok.text);
            Abstr.locAttr.put($ast, loc($tok, $tok)); }
;
