lexer grammar Prev26Lexer;

@header {
	package prev26lang.phase.lexan;

	import prev26lang.common.report.*;
}

@members {
    @Override
	public LexAn.LocLogToken nextToken() {
	    LexAn.LocLogToken token = (LexAn.LocLogToken) super.nextToken();
	    super._token = token;
		return token;
	}

	public void notifyListenersCustom(String tokenText, String errorText) {
        ANTLRErrorListener listener = this.getErrorListenerDispatch();
        String msg = errorText + ": " + tokenText;
        listener.syntaxError(this, tokenText, this._tokenStartLine, this._tokenStartCharPositionInLine, msg, null);
	}

}



// Wholenumber constants
INTCONST : (
    '0' |
    [1-9] [0-9]* |
    [0-9]+ { notifyListenersCustom(super.getText(), "Invalid intconst"); }
) ;

// Chars and strings
CHARCONST : (
    QUOTE C1 QUOTE |
    QUOTE ESCAPE1 QUOTE |
    QUOTE BACKSLASH NOT_QUOTE* QUOTE { notifyListenersCustom(super.getText(), "Invalid escape sequence"); } |
    QUOTE NOT_QUOTE* QUOTE { notifyListenersCustom(super.getText(), "Invalid charconst value"); } |
    QUOTE NOT_QUOTE* { notifyListenersCustom(super.getText(), "Unterminated charconst"); }
) ;

STRING : (
    DOUBLEQUOTE STRING_CHAR* DOUBLEQUOTE |
    DOUBLEQUOTE NOT_DOUBLEQUOTE* DOUBLEQUOTE { notifyListenersCustom(super.getText(), "Invalid string value"); } |
    DOUBLEQUOTE NOT_DOUBLEQUOTE* { notifyListenersCustom(super.getText(), "Unterminated string"); }
) ;

fragment ESCAPE1_ELEMENT : BACKSLASH | QUOTE | HEX ;
fragment ESCAPE2_ELEMENT : BACKSLASH | DOUBLEQUOTE | HEX ;
fragment ESCAPE1 : BACKSLASH ESCAPE1_ELEMENT ;
fragment ESCAPE2 : BACKSLASH ESCAPE2_ELEMENT ;
fragment STRING_CHAR : C2 | ESCAPE2 ;
fragment HEX : 'x' [0-9A-F] [0-9A-F] ;
fragment C1 : [\u0020-\u0026\u0028-\u005B\u005D-\u007E] ;
fragment C2 : [\u0020-\u0021\u0023-\u005B\u005D-\u007E] ;
fragment NOT_QUOTE : [\u0021-\u0026\u0028-\u007F] ;
fragment NOT_DOUBLEQUOTE : [\u0020-\u0021\u0023-\u007F] ;
fragment BACKSLASH : '\\' ;
fragment QUOTE : '\'' ;
fragment DOUBLEQUOTE : '"' ;



// Symbols
PERIOD : '.' ;
COMMA : ',' ;
COLON : ':' ;
DEFINE : '=' ;
PLUS : '+' ;
MINUS : '-' ;
MULTIPLY : '*' ;
DIVIDE : '/' ;
MODULO : '%' ;
EQUALS : '==' ;
NOTEQUALS : '!=' ;
LEQUALS : '<=' ;
MEQUALS : '>=' ;
LESSER : '<' ;
GREATER : '>' ;
LEFTBR : '(' ;
RIGHTBR : ')' ;
LEFTSQBR : '[' ;
RIGHTSQBR : ']' ;
LEFTCBR : '{' ;
RIGHTCBR : '}' ;
CARET : '^' ;

// Reserved words
AND     : 'and' ;
AS      : 'as' ;
BOOL    : 'bool' ;
DO      : 'do' ;
CHAR    : 'char' ;
ELSE    : 'else' ;
END     : 'end' ;
FALSE   : 'false' ;
FUN     : 'fun' ;
IF      : 'if' ;
IN      : 'in' ;
INT     : 'int' ;
LET     : 'let' ;
NIL     : 'nil' ;
NONE    : 'none' ;
NOT     : 'not' ;
OR      : 'or' ;
SIZEOF  : 'sizeof' ;
THEN    : 'then' ;
TRUE    : 'true' ;
TYP     : 'typ' ;
VAR     : 'var' ;
VOID    : 'void' ;
WHILE   : 'while' ;

// Names
NAME : [A-Za-z_] [A-Za-z0-9_]* ;

// Comment
COMMENT : '//' ~[\n]* '\n' -> skip ;

WS : [ \n\r\t]+ -> skip ;


