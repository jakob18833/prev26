// Generated from Prev26Parser.g4 by ANTLR 4.13.2


    package prev26lang.phase.synan;

    import java.util.*;
    import prev26lang.common.report.*;
    import prev26lang.phase.lexan.*;
    import prev26lang.phase.abstr.*;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"doclint:missing", "all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class Prev26Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INTCONST=1, CHARCONST=2, STRING=3, PERIOD=4, COMMA=5, COLON=6, DEFINE=7, 
		PLUS=8, MINUS=9, MULTIPLY=10, DIVIDE=11, MODULO=12, EQUALS=13, NOTEQUALS=14, 
		LEQUALS=15, MEQUALS=16, LESSER=17, GREATER=18, LEFTBR=19, RIGHTBR=20, 
		LEFTSQBR=21, RIGHTSQBR=22, LEFTCBR=23, RIGHTCBR=24, CARET=25, AND=26, 
		AS=27, BOOL=28, DO=29, CHAR=30, ELSE=31, END=32, FALSE=33, FUN=34, IF=35, 
		IN=36, INT=37, LET=38, NIL=39, NONE=40, NOT=41, OR=42, SIZEOF=43, THEN=44, 
		TRUE=45, TYP=46, VAR=47, VOID=48, WHILE=49, NAME=50, COMMENT=51, WS=52;
	public static final int
		RULE_source = 0, RULE_par_list_nodes = 1, RULE_par_list_zero_nodes = 2, 
		RULE_expr_list = 3, RULE_expr_list_zero = 4, RULE_expr_list_zero_nodes = 5, 
		RULE_comp_list_nodes = 6, RULE_comp_list_zero_nodes = 7, RULE_type_list_nodes = 8, 
		RULE_type_list_zero_nodes = 9, RULE_defn_list_nodes = 10, RULE_prog = 11, 
		RULE_defn = 12, RULE_type = 13, RULE_expr = 14, RULE_defineExpr = 15, 
		RULE_typeExpr = 16, RULE_logicOrExpr = 17, RULE_logicAndExpr = 18, RULE_comparisonExpr = 19, 
		RULE_additiveExpr = 20, RULE_multiplicativeExpr = 21, RULE_prefixExpr = 22, 
		RULE_postfixExpr = 23, RULE_atomExpr = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"source", "par_list_nodes", "par_list_zero_nodes", "expr_list", "expr_list_zero", 
			"expr_list_zero_nodes", "comp_list_nodes", "comp_list_zero_nodes", "type_list_nodes", 
			"type_list_zero_nodes", "defn_list_nodes", "prog", "defn", "type", "expr", 
			"defineExpr", "typeExpr", "logicOrExpr", "logicAndExpr", "comparisonExpr", 
			"additiveExpr", "multiplicativeExpr", "prefixExpr", "postfixExpr", "atomExpr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'.'", "','", "':'", "'='", "'+'", "'-'", "'*'", 
			"'/'", "'%'", "'=='", "'!='", "'<='", "'>='", "'<'", "'>'", "'('", "')'", 
			"'['", "']'", "'{'", "'}'", "'^'", "'and'", "'as'", "'bool'", "'do'", 
			"'char'", "'else'", "'end'", "'false'", "'fun'", "'if'", "'in'", "'int'", 
			"'let'", "'nil'", "'none'", "'not'", "'or'", "'sizeof'", "'then'", "'true'", 
			"'typ'", "'var'", "'void'", "'while'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INTCONST", "CHARCONST", "STRING", "PERIOD", "COMMA", "COLON", 
			"DEFINE", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "MODULO", "EQUALS", 
			"NOTEQUALS", "LEQUALS", "MEQUALS", "LESSER", "GREATER", "LEFTBR", "RIGHTBR", 
			"LEFTSQBR", "RIGHTSQBR", "LEFTCBR", "RIGHTCBR", "CARET", "AND", "AS", 
			"BOOL", "DO", "CHAR", "ELSE", "END", "FALSE", "FUN", "IF", "IN", "INT", 
			"LET", "NIL", "NONE", "NOT", "OR", "SIZEOF", "THEN", "TRUE", "TYP", "VAR", 
			"VOID", "WHILE", "NAME", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Prev26Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }



	    private Location loc(final Token tok) { return new Location((LexAn.LocLogToken)tok); }
	    private Location loc(final Locatable loc) { return new Location(loc); }
	    private Location loc(final Token tok1, final Token tok2) { return new Location((LexAn.LocLogToken)tok1, (LexAn.LocLogToken)tok2); }
	    private Location loc(final Token tok1, final Locatable loc2) { if (loc2 == null) return null; return new Location((LexAn.LocLogToken)tok1, loc2); }
	    private Location loc(final Locatable loc1, final Token tok2) { if (loc1 == null) return null; return new Location(loc1, (LexAn.LocLogToken)tok2); }
	    private Location loc(final Locatable loc1, final Locatable loc2) { if ((loc1 == null) || (loc2 == null)) return null; return new Location(loc1, loc2); }


	public Prev26Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SourceContext extends ParserRuleContext {
		public AST.Nodes<AST.FullDefn> ast;
		public ProgContext p;
		public TerminalNode EOF() { return getToken(Prev26Parser.EOF, 0); }
		public ProgContext prog() {
			return getRuleContext(ProgContext.class,0);
		}
		public SourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_source; }
	}

	public final SourceContext source() throws RecognitionException {
		SourceContext _localctx = new SourceContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_source);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			((SourceContext)_localctx).p = prog();
			setState(51);
			match(EOF);
			 ((SourceContext)_localctx).ast =  ((SourceContext)_localctx).p.ast; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Par_list_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.ParDefn> nodes;
		public List<AST.ParDefn> pars = new ArrayList<>(); ;;
		public Token n1;
		public TypeContext t;
		public Token n;
		public List<TerminalNode> COLON() { return getTokens(Prev26Parser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Prev26Parser.COLON, i);
		}
		public List<TerminalNode> NAME() { return getTokens(Prev26Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(Prev26Parser.NAME, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Prev26Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Prev26Parser.COMMA, i);
		}
		public Par_list_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_list_nodes; }
	}

	public final Par_list_nodesContext par_list_nodes() throws RecognitionException {
		Par_list_nodesContext _localctx = new Par_list_nodesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_par_list_nodes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			((Par_list_nodesContext)_localctx).n1 = match(NAME);
			setState(55);
			match(COLON);
			setState(56);
			((Par_list_nodesContext)_localctx).t = type();
			 AST.ParDefn pd = new AST.ParDefn((((Par_list_nodesContext)_localctx).n1!=null?((Par_list_nodesContext)_localctx).n1.getText():null), ((Par_list_nodesContext)_localctx).t.ast); _localctx.pars.add(pd); Abstr.locAttr.put(pd, loc(((Par_list_nodesContext)_localctx).n1, (((Par_list_nodesContext)_localctx).t!=null?(((Par_list_nodesContext)_localctx).t.stop):null))); 
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(58);
				match(COMMA);
				setState(59);
				((Par_list_nodesContext)_localctx).n = match(NAME);
				setState(60);
				match(COLON);
				setState(61);
				((Par_list_nodesContext)_localctx).t = type();
				 AST.ParDefn pd1 = new AST.ParDefn((((Par_list_nodesContext)_localctx).n!=null?((Par_list_nodesContext)_localctx).n.getText():null), ((Par_list_nodesContext)_localctx).t.ast); _localctx.pars.add(pd1); Abstr.locAttr.put(pd1, loc(((Par_list_nodesContext)_localctx).n, (((Par_list_nodesContext)_localctx).t!=null?(((Par_list_nodesContext)_localctx).t.stop):null))); 
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((Par_list_nodesContext)_localctx).nodes =  new AST.Nodes<>(_localctx.pars); Abstr.locAttr.put(_localctx.nodes, loc(((Par_list_nodesContext)_localctx).n1, (((Par_list_nodesContext)_localctx).t!=null?(((Par_list_nodesContext)_localctx).t.stop):null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Par_list_zero_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.ParDefn> nodes;
		public Par_list_nodesContext a;
		public Par_list_nodesContext par_list_nodes() {
			return getRuleContext(Par_list_nodesContext.class,0);
		}
		public Par_list_zero_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_par_list_zero_nodes; }
	}

	public final Par_list_zero_nodesContext par_list_zero_nodes() throws RecognitionException {
		Par_list_zero_nodesContext _localctx = new Par_list_zero_nodesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_par_list_zero_nodes);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RIGHTBR:
				enterOuterAlt(_localctx, 1);
				{
				 ((Par_list_zero_nodesContext)_localctx).nodes =  new AST.Nodes<>(); Abstr.locAttr.put(_localctx.nodes, loc(getCurrentToken())); 
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				((Par_list_zero_nodesContext)_localctx).a = par_list_nodes();
				 ((Par_list_zero_nodesContext)_localctx).nodes =  ((Par_list_zero_nodesContext)_localctx).a.nodes; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expr_listContext extends ParserRuleContext {
		public AST.Expr ast;
		public List<AST.Expr> exprs = new ArrayList<>(); Token start;;
		public ExprContext e;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Prev26Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Prev26Parser.COMMA, i);
		}
		public Expr_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list; }
	}

	public final Expr_listContext expr_list() throws RecognitionException {
		Expr_listContext _localctx = new Expr_listContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expr_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			((Expr_listContext)_localctx).e = expr();
			 _localctx.exprs.add(((Expr_listContext)_localctx).e.ast); ((Expr_listContext)_localctx).start =  (((Expr_listContext)_localctx).e!=null?(((Expr_listContext)_localctx).e.start):null); 
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(79);
				match(COMMA);
				setState(80);
				((Expr_listContext)_localctx).e = expr();
				 _localctx.exprs.add(((Expr_listContext)_localctx).e.ast); 
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 AST.Nodes<AST.Expr> nodes = new AST.Nodes<>(_localctx.exprs); ((Expr_listContext)_localctx).ast =  new AST.Exprs(nodes);
			    Abstr.locAttr.put(_localctx.ast, loc(_localctx.start, (((Expr_listContext)_localctx).e!=null?(((Expr_listContext)_localctx).e.stop):null))); Abstr.locAttr.put(nodes, loc(_localctx.start, (((Expr_listContext)_localctx).e!=null?(((Expr_listContext)_localctx).e.stop):null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expr_list_zeroContext extends ParserRuleContext {
		public AST.Expr ast;
		public Expr_listContext e;
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public Expr_list_zeroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list_zero; }
	}

	public final Expr_list_zeroContext expr_list_zero() throws RecognitionException {
		Expr_list_zeroContext _localctx = new Expr_list_zeroContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expr_list_zero);
		try {
			setState(94);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EOF:
				enterOuterAlt(_localctx, 1);
				{
				 AST.Nodes<AST.Expr> nodes = new AST.Nodes<>(); ((Expr_list_zeroContext)_localctx).ast =  new AST.Exprs(nodes);
				        Abstr.locAttr.put(nodes, loc(getCurrentToken())); Abstr.locAttr.put(_localctx.ast, loc(getCurrentToken())); 
				}
				break;
			case INTCONST:
			case CHARCONST:
			case STRING:
			case PLUS:
			case MINUS:
			case LEFTBR:
			case CARET:
			case FALSE:
			case IF:
			case LET:
			case NIL:
			case NONE:
			case NOT:
			case SIZEOF:
			case TRUE:
			case WHILE:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				((Expr_list_zeroContext)_localctx).e = expr_list();
				 ((Expr_list_zeroContext)_localctx).ast =  ((Expr_list_zeroContext)_localctx).e.ast; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expr_list_zero_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.Expr> nodes;
		public List<AST.Expr> exprs = new ArrayList<>(); Token start;;
		public ExprContext e;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Prev26Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Prev26Parser.COMMA, i);
		}
		public Expr_list_zero_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_list_zero_nodes; }
	}

	public final Expr_list_zero_nodesContext expr_list_zero_nodes() throws RecognitionException {
		Expr_list_zero_nodesContext _localctx = new Expr_list_zero_nodesContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expr_list_zero_nodes);
		int _la;
		try {
			setState(110);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RIGHTBR:
				enterOuterAlt(_localctx, 1);
				{
				 ((Expr_list_zero_nodesContext)_localctx).nodes =  new AST.Nodes<>(); Abstr.locAttr.put(_localctx.nodes, loc(getCurrentToken())); 
				}
				break;
			case INTCONST:
			case CHARCONST:
			case STRING:
			case PLUS:
			case MINUS:
			case LEFTBR:
			case CARET:
			case FALSE:
			case IF:
			case LET:
			case NIL:
			case NONE:
			case NOT:
			case SIZEOF:
			case TRUE:
			case WHILE:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				((Expr_list_zero_nodesContext)_localctx).e = expr();
				 _localctx.exprs.add(((Expr_list_zero_nodesContext)_localctx).e.ast); ((Expr_list_zero_nodesContext)_localctx).start =  (((Expr_list_zero_nodesContext)_localctx).e!=null?(((Expr_list_zero_nodesContext)_localctx).e.start):null); 
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(99);
					match(COMMA);
					setState(100);
					((Expr_list_zero_nodesContext)_localctx).e = expr();
					 _localctx.exprs.add(((Expr_list_zero_nodesContext)_localctx).e.ast); 
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				 ((Expr_list_zero_nodesContext)_localctx).nodes =  new AST.Nodes<>(_localctx.exprs); Abstr.locAttr.put(_localctx.nodes, loc(_localctx.start, (((Expr_list_zero_nodesContext)_localctx).e!=null?(((Expr_list_zero_nodesContext)_localctx).e.stop):null))); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Comp_list_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.CompDefn> nodes;
		public List<AST.CompDefn> comps = new ArrayList<>();
		public Token n1;
		public TypeContext t;
		public Token n;
		public List<TerminalNode> COLON() { return getTokens(Prev26Parser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Prev26Parser.COLON, i);
		}
		public List<TerminalNode> NAME() { return getTokens(Prev26Parser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(Prev26Parser.NAME, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Prev26Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Prev26Parser.COMMA, i);
		}
		public Comp_list_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_list_nodes; }
	}

	public final Comp_list_nodesContext comp_list_nodes() throws RecognitionException {
		Comp_list_nodesContext _localctx = new Comp_list_nodesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comp_list_nodes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			((Comp_list_nodesContext)_localctx).n1 = match(NAME);
			setState(113);
			match(COLON);
			setState(114);
			((Comp_list_nodesContext)_localctx).t = type();
			 AST.CompDefn cd = new AST.CompDefn((((Comp_list_nodesContext)_localctx).n1!=null?((Comp_list_nodesContext)_localctx).n1.getText():null), ((Comp_list_nodesContext)_localctx).t.ast); _localctx.comps.add(cd); Abstr.locAttr.put(cd, loc(((Comp_list_nodesContext)_localctx).n1, (((Comp_list_nodesContext)_localctx).t!=null?(((Comp_list_nodesContext)_localctx).t.stop):null))); 
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(116);
				match(COMMA);
				setState(117);
				((Comp_list_nodesContext)_localctx).n = match(NAME);
				setState(118);
				match(COLON);
				setState(119);
				((Comp_list_nodesContext)_localctx).t = type();
				 cd = new AST.CompDefn((((Comp_list_nodesContext)_localctx).n!=null?((Comp_list_nodesContext)_localctx).n.getText():null), ((Comp_list_nodesContext)_localctx).t.ast); _localctx.comps.add(cd); Abstr.locAttr.put(cd, loc(((Comp_list_nodesContext)_localctx).n, (((Comp_list_nodesContext)_localctx).t!=null?(((Comp_list_nodesContext)_localctx).t.stop):null))); 
				}
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((Comp_list_nodesContext)_localctx).nodes =  new AST.Nodes<>(_localctx.comps); Abstr.locAttr.put(_localctx.nodes, loc(((Comp_list_nodesContext)_localctx).n1, (((Comp_list_nodesContext)_localctx).t!=null?(((Comp_list_nodesContext)_localctx).t.stop):null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Comp_list_zero_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.CompDefn> nodes;
		public Comp_list_nodesContext c;
		public Comp_list_nodesContext comp_list_nodes() {
			return getRuleContext(Comp_list_nodesContext.class,0);
		}
		public Comp_list_zero_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comp_list_zero_nodes; }
	}

	public final Comp_list_zero_nodesContext comp_list_zero_nodes() throws RecognitionException {
		Comp_list_zero_nodesContext _localctx = new Comp_list_zero_nodesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_comp_list_zero_nodes);
		try {
			setState(133);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RIGHTCBR:
				enterOuterAlt(_localctx, 1);
				{
				 ((Comp_list_zero_nodesContext)_localctx).nodes =  new AST.Nodes<>(); Abstr.locAttr.put(_localctx.nodes, loc(getCurrentToken())); 
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(130);
				((Comp_list_zero_nodesContext)_localctx).c = comp_list_nodes();
				 ((Comp_list_zero_nodesContext)_localctx).nodes =  ((Comp_list_zero_nodesContext)_localctx).c.nodes; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_list_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.Type> nodes;
		public List<AST.Type> types = new ArrayList<>(); Token start;;
		public TypeContext t;
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Prev26Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Prev26Parser.COMMA, i);
		}
		public Type_list_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_list_nodes; }
	}

	public final Type_list_nodesContext type_list_nodes() throws RecognitionException {
		Type_list_nodesContext _localctx = new Type_list_nodesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type_list_nodes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			((Type_list_nodesContext)_localctx).t = type();
			 _localctx.types.add(((Type_list_nodesContext)_localctx).t.ast); ((Type_list_nodesContext)_localctx).start =  (((Type_list_nodesContext)_localctx).t!=null?(((Type_list_nodesContext)_localctx).t.start):null); 
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(137);
				match(COMMA);
				setState(138);
				((Type_list_nodesContext)_localctx).t = type();
				 _localctx.types.add(((Type_list_nodesContext)_localctx).t.ast); 
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((Type_list_nodesContext)_localctx).nodes =  new AST.Nodes<>(_localctx.types); Abstr.locAttr.put(_localctx.nodes, loc(_localctx.start, (((Type_list_nodesContext)_localctx).t!=null?(((Type_list_nodesContext)_localctx).t.stop):null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_list_zero_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.Type> nodes;
		public Type_list_nodesContext t;
		public Type_list_nodesContext type_list_nodes() {
			return getRuleContext(Type_list_nodesContext.class,0);
		}
		public Type_list_zero_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_list_zero_nodes; }
	}

	public final Type_list_zero_nodesContext type_list_zero_nodes() throws RecognitionException {
		Type_list_zero_nodesContext _localctx = new Type_list_zero_nodesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type_list_zero_nodes);
		try {
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case COLON:
				enterOuterAlt(_localctx, 1);
				{
				 ((Type_list_zero_nodesContext)_localctx).nodes =  new AST.Nodes<>(); Abstr.locAttr.put(_localctx.nodes, loc(getCurrentToken())); 
				}
				break;
			case LEFTBR:
			case LEFTSQBR:
			case LEFTCBR:
			case CARET:
			case BOOL:
			case CHAR:
			case INT:
			case VOID:
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				((Type_list_zero_nodesContext)_localctx).t = type_list_nodes();
				 ((Type_list_zero_nodesContext)_localctx).nodes =  ((Type_list_zero_nodesContext)_localctx).t.nodes; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Defn_list_nodesContext extends ParserRuleContext {
		public AST.Nodes<AST.FullDefn> nodes;
		public List<AST.FullDefn> defns = new ArrayList<>(); Token start;;
		public DefnContext d;
		public List<DefnContext> defn() {
			return getRuleContexts(DefnContext.class);
		}
		public DefnContext defn(int i) {
			return getRuleContext(DefnContext.class,i);
		}
		public Defn_list_nodesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defn_list_nodes; }
	}

	public final Defn_list_nodesContext defn_list_nodes() throws RecognitionException {
		Defn_list_nodesContext _localctx = new Defn_list_nodesContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_defn_list_nodes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			((Defn_list_nodesContext)_localctx).d = defn();
			 _localctx.defns.add(((Defn_list_nodesContext)_localctx).d.ast); ((Defn_list_nodesContext)_localctx).start =  (((Defn_list_nodesContext)_localctx).d!=null?(((Defn_list_nodesContext)_localctx).d.start):null); 
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 211123412402176L) != 0)) {
				{
				{
				setState(156);
				((Defn_list_nodesContext)_localctx).d = defn();
				 _localctx.defns.add(((Defn_list_nodesContext)_localctx).d.ast); 
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((Defn_list_nodesContext)_localctx).nodes =  new AST.Nodes<>(_localctx.defns); Abstr.locAttr.put(_localctx.nodes, loc(_localctx.start, (((Defn_list_nodesContext)_localctx).d!=null?(((Defn_list_nodesContext)_localctx).d.stop):null))); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public AST.Nodes<AST.FullDefn> ast;
		public List<AST.FullDefn> defs = new ArrayList<>(); Token start;;
		public DefnContext d;
		public List<DefnContext> defn() {
			return getRuleContexts(DefnContext.class);
		}
		public DefnContext defn(int i) {
			return getRuleContext(DefnContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			((ProgContext)_localctx).d = defn();
			 _localctx.defs.add(((ProgContext)_localctx).d.ast); ((ProgContext)_localctx).start =  (((ProgContext)_localctx).d!=null?(((ProgContext)_localctx).d.start):null); 
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 211123412402176L) != 0)) {
				{
				{
				setState(168);
				((ProgContext)_localctx).d = defn();
				 _localctx.defs.add(((ProgContext)_localctx).d.ast); 
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((ProgContext)_localctx).ast =  new AST.Nodes<>(_localctx.defs); Abstr.locAttr.put(_localctx.ast, loc(_localctx.start, (((ProgContext)_localctx).d!=null?(((ProgContext)_localctx).d.stop):null)));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefnContext extends ParserRuleContext {
		public AST.FullDefn ast;
		public Token TYP;
		public Token n;
		public TypeContext t;
		public Token VAR;
		public Token FUN;
		public Par_list_zero_nodesContext a;
		public Expr_listContext e;
		public TerminalNode TYP() { return getToken(Prev26Parser.TYP, 0); }
		public TerminalNode DEFINE() { return getToken(Prev26Parser.DEFINE, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode VAR() { return getToken(Prev26Parser.VAR, 0); }
		public TerminalNode COLON() { return getToken(Prev26Parser.COLON, 0); }
		public TerminalNode FUN() { return getToken(Prev26Parser.FUN, 0); }
		public TerminalNode LEFTBR() { return getToken(Prev26Parser.LEFTBR, 0); }
		public TerminalNode RIGHTBR() { return getToken(Prev26Parser.RIGHTBR, 0); }
		public Par_list_zero_nodesContext par_list_zero_nodes() {
			return getRuleContext(Par_list_zero_nodesContext.class,0);
		}
		public Expr_listContext expr_list() {
			return getRuleContext(Expr_listContext.class,0);
		}
		public DefnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defn; }
	}

	public final DefnContext defn() throws RecognitionException {
		DefnContext _localctx = new DefnContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_defn);
		try {
			setState(210);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				((DefnContext)_localctx).TYP = match(TYP);
				setState(179);
				((DefnContext)_localctx).n = match(NAME);
				setState(180);
				match(DEFINE);
				setState(181);
				((DefnContext)_localctx).t = type();
				 ((DefnContext)_localctx).ast =  new AST.TypDefn((((DefnContext)_localctx).n!=null?((DefnContext)_localctx).n.getText():null), ((DefnContext)_localctx).t.ast);
				          Abstr.locAttr.put(_localctx.ast, loc(((DefnContext)_localctx).TYP, (((DefnContext)_localctx).t!=null?(((DefnContext)_localctx).t.stop):null))); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
				((DefnContext)_localctx).VAR = match(VAR);
				setState(185);
				((DefnContext)_localctx).n = match(NAME);
				setState(186);
				match(COLON);
				setState(187);
				((DefnContext)_localctx).t = type();
				 ((DefnContext)_localctx).ast =  new AST.VarDefn((((DefnContext)_localctx).n!=null?((DefnContext)_localctx).n.getText():null), ((DefnContext)_localctx).t.ast);
				          Abstr.locAttr.put(_localctx.ast, loc(((DefnContext)_localctx).VAR, (((DefnContext)_localctx).t!=null?(((DefnContext)_localctx).t.stop):null))); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(190);
				((DefnContext)_localctx).FUN = match(FUN);
				setState(191);
				((DefnContext)_localctx).n = match(NAME);
				setState(192);
				match(LEFTBR);
				setState(193);
				((DefnContext)_localctx).a = par_list_zero_nodes();
				setState(194);
				match(RIGHTBR);
				setState(195);
				match(COLON);
				setState(196);
				((DefnContext)_localctx).t = type();
				 ((DefnContext)_localctx).ast =  new AST.ExtFunDefn((((DefnContext)_localctx).n!=null?((DefnContext)_localctx).n.getText():null), ((DefnContext)_localctx).a.nodes, ((DefnContext)_localctx).t.ast);
				          Abstr.locAttr.put(_localctx.ast, loc(((DefnContext)_localctx).FUN, (((DefnContext)_localctx).t!=null?(((DefnContext)_localctx).t.stop):null))); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
				((DefnContext)_localctx).FUN = match(FUN);
				setState(200);
				((DefnContext)_localctx).n = match(NAME);
				setState(201);
				match(LEFTBR);
				setState(202);
				((DefnContext)_localctx).a = par_list_zero_nodes();
				setState(203);
				match(RIGHTBR);
				setState(204);
				match(COLON);
				setState(205);
				((DefnContext)_localctx).t = type();
				setState(206);
				match(DEFINE);
				setState(207);
				((DefnContext)_localctx).e = expr_list();
				 ((DefnContext)_localctx).ast =  new AST.DefFunDefn((((DefnContext)_localctx).n!=null?((DefnContext)_localctx).n.getText():null), ((DefnContext)_localctx).a.nodes, ((DefnContext)_localctx).t.ast, ((DefnContext)_localctx).e.ast);
				          Abstr.locAttr.put(_localctx.ast, loc(((DefnContext)_localctx).FUN, (((DefnContext)_localctx).e!=null?(((DefnContext)_localctx).e.stop):null))); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public AST.Type ast;
		public Token tok;
		public Token n;
		public Token INTCONST;
		public TypeContext t;
		public Token CARET;
		public Token LEFTBR;
		public Comp_list_nodesContext a;
		public Token RIGHTBR;
		public Token LEFTCBR;
		public Comp_list_zero_nodesContext a1;
		public Token RIGHTCBR;
		public Type_list_zero_nodesContext tl;
		public TerminalNode INT() { return getToken(Prev26Parser.INT, 0); }
		public TerminalNode CHAR() { return getToken(Prev26Parser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(Prev26Parser.BOOL, 0); }
		public TerminalNode VOID() { return getToken(Prev26Parser.VOID, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode INTCONST() { return getToken(Prev26Parser.INTCONST, 0); }
		public TerminalNode RIGHTSQBR() { return getToken(Prev26Parser.RIGHTSQBR, 0); }
		public TerminalNode LEFTSQBR() { return getToken(Prev26Parser.LEFTSQBR, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode CARET() { return getToken(Prev26Parser.CARET, 0); }
		public TerminalNode LEFTBR() { return getToken(Prev26Parser.LEFTBR, 0); }
		public TerminalNode RIGHTBR() { return getToken(Prev26Parser.RIGHTBR, 0); }
		public Comp_list_nodesContext comp_list_nodes() {
			return getRuleContext(Comp_list_nodesContext.class,0);
		}
		public TerminalNode LEFTCBR() { return getToken(Prev26Parser.LEFTCBR, 0); }
		public TerminalNode RIGHTCBR() { return getToken(Prev26Parser.RIGHTCBR, 0); }
		public Comp_list_zero_nodesContext comp_list_zero_nodes() {
			return getRuleContext(Comp_list_zero_nodesContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(Prev26Parser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Prev26Parser.COLON, i);
		}
		public Type_list_zero_nodesContext type_list_zero_nodes() {
			return getRuleContext(Type_list_zero_nodesContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_type);
		try {
			setState(255);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(212);
				((TypeContext)_localctx).tok = match(INT);
				 ((TypeContext)_localctx).ast =  new AST.AtomType(AST.AtomType.Type.INT);  Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).tok, ((TypeContext)_localctx).tok)); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				((TypeContext)_localctx).tok = match(CHAR);
				 ((TypeContext)_localctx).ast =  new AST.AtomType(AST.AtomType.Type.CHAR); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).tok, ((TypeContext)_localctx).tok)); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				((TypeContext)_localctx).tok = match(BOOL);
				 ((TypeContext)_localctx).ast =  new AST.AtomType(AST.AtomType.Type.BOOL); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).tok, ((TypeContext)_localctx).tok)); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(218);
				((TypeContext)_localctx).tok = match(VOID);
				 ((TypeContext)_localctx).ast =  new AST.AtomType(AST.AtomType.Type.VOID); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).tok, ((TypeContext)_localctx).tok)); 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(220);
				((TypeContext)_localctx).n = match(NAME);
				 ((TypeContext)_localctx).ast =  new AST.NameType((((TypeContext)_localctx).n!=null?((TypeContext)_localctx).n.getText():null)); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).n, ((TypeContext)_localctx).n)); 
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(222);
				((TypeContext)_localctx).tok = match(LEFTSQBR);
				setState(223);
				((TypeContext)_localctx).INTCONST = match(INTCONST);
				setState(224);
				match(RIGHTSQBR);
				setState(225);
				((TypeContext)_localctx).t = type();
				 ((TypeContext)_localctx).ast =  new AST.ArrType(((TypeContext)_localctx).t.ast, (((TypeContext)_localctx).INTCONST!=null?((TypeContext)_localctx).INTCONST.getText():null)); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).tok, (((TypeContext)_localctx).t!=null?(((TypeContext)_localctx).t.stop):null))); 
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(228);
				((TypeContext)_localctx).CARET = match(CARET);
				setState(229);
				((TypeContext)_localctx).t = type();
				 ((TypeContext)_localctx).ast =  new AST.PtrType(((TypeContext)_localctx).t.ast); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).CARET, (((TypeContext)_localctx).t!=null?(((TypeContext)_localctx).t.stop):null))); 
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(232);
				((TypeContext)_localctx).LEFTBR = match(LEFTBR);
				setState(233);
				((TypeContext)_localctx).a = comp_list_nodes();
				setState(234);
				((TypeContext)_localctx).RIGHTBR = match(RIGHTBR);
				 ((TypeContext)_localctx).ast =  new AST.StrType(((TypeContext)_localctx).a.nodes); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).LEFTBR, ((TypeContext)_localctx).RIGHTBR)); 
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(237);
				((TypeContext)_localctx).LEFTCBR = match(LEFTCBR);
				setState(238);
				((TypeContext)_localctx).a1 = comp_list_zero_nodes();
				setState(239);
				((TypeContext)_localctx).RIGHTCBR = match(RIGHTCBR);
				 ((TypeContext)_localctx).ast =  new AST.UniType(((TypeContext)_localctx).a1.nodes); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).LEFTCBR, ((TypeContext)_localctx).RIGHTCBR)); 
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(242);
				((TypeContext)_localctx).LEFTBR = match(LEFTBR);
				setState(243);
				match(COLON);
				setState(244);
				((TypeContext)_localctx).tl = type_list_zero_nodes();
				setState(245);
				match(COLON);
				setState(246);
				((TypeContext)_localctx).t = type();
				setState(247);
				((TypeContext)_localctx).RIGHTBR = match(RIGHTBR);
				 ((TypeContext)_localctx).ast =  new AST.FunType(((TypeContext)_localctx).tl.nodes, ((TypeContext)_localctx).t.ast); Abstr.locAttr.put(_localctx.ast, loc(((TypeContext)_localctx).LEFTBR, ((TypeContext)_localctx).RIGHTBR)); 
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(250);
				match(LEFTBR);
				setState(251);
				((TypeContext)_localctx).t = type();
				setState(252);
				match(RIGHTBR);
				 ((TypeContext)_localctx).ast =  ((TypeContext)_localctx).t.ast; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public DefineExprContext e;
		public DefineExprContext defineExpr() {
			return getRuleContext(DefineExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			((ExprContext)_localctx).e = defineExpr();
			 ((ExprContext)_localctx).ast =  ((ExprContext)_localctx).e.ast; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefineExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public TypeExprContext e;
		public TypeExprContext e1;
		public TypeExprContext e2;
		public List<TypeExprContext> typeExpr() {
			return getRuleContexts(TypeExprContext.class);
		}
		public TypeExprContext typeExpr(int i) {
			return getRuleContext(TypeExprContext.class,i);
		}
		public TerminalNode DEFINE() { return getToken(Prev26Parser.DEFINE, 0); }
		public DefineExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defineExpr; }
	}

	public final DefineExprContext defineExpr() throws RecognitionException {
		DefineExprContext _localctx = new DefineExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_defineExpr);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(260);
				((DefineExprContext)_localctx).e = typeExpr();
				 ((DefineExprContext)_localctx).ast =  ((DefineExprContext)_localctx).e.ast; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				((DefineExprContext)_localctx).e1 = typeExpr();
				setState(264);
				match(DEFINE);
				setState(265);
				((DefineExprContext)_localctx).e2 = typeExpr();
				 ((DefineExprContext)_localctx).ast =  new AST.AsgnExpr(((DefineExprContext)_localctx).e1.ast, ((DefineExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((DefineExprContext)_localctx).e1!=null?(((DefineExprContext)_localctx).e1.start):null), (((DefineExprContext)_localctx).e2!=null?(((DefineExprContext)_localctx).e2.stop):null))); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public LogicOrExprContext e;
		public TypeContext t;
		public LogicOrExprContext logicOrExpr() {
			return getRuleContext(LogicOrExprContext.class,0);
		}
		public TerminalNode AS() { return getToken(Prev26Parser.AS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeExpr; }
	}

	public final TypeExprContext typeExpr() throws RecognitionException {
		TypeExprContext _localctx = new TypeExprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_typeExpr);
		try {
			setState(278);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				((TypeExprContext)_localctx).e = logicOrExpr();
				 ((TypeExprContext)_localctx).ast =  ((TypeExprContext)_localctx).e.ast; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(273);
				((TypeExprContext)_localctx).e = logicOrExpr();
				setState(274);
				match(AS);
				setState(275);
				((TypeExprContext)_localctx).t = type();
				 ((TypeExprContext)_localctx).ast =  new AST.CastExpr(((TypeExprContext)_localctx).t.ast, ((TypeExprContext)_localctx).e.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((TypeExprContext)_localctx).e!=null?(((TypeExprContext)_localctx).e.start):null), (((TypeExprContext)_localctx).t!=null?(((TypeExprContext)_localctx).t.stop):null))); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicOrExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public LogicAndExprContext e1;
		public LogicAndExprContext e2;
		public List<LogicAndExprContext> logicAndExpr() {
			return getRuleContexts(LogicAndExprContext.class);
		}
		public LogicAndExprContext logicAndExpr(int i) {
			return getRuleContext(LogicAndExprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(Prev26Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(Prev26Parser.OR, i);
		}
		public LogicOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicOrExpr; }
	}

	public final LogicOrExprContext logicOrExpr() throws RecognitionException {
		LogicOrExprContext _localctx = new LogicOrExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_logicOrExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			((LogicOrExprContext)_localctx).e1 = logicAndExpr();
			 ((LogicOrExprContext)_localctx).ast =  ((LogicOrExprContext)_localctx).e1.ast; 
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(282);
				match(OR);
				setState(283);
				((LogicOrExprContext)_localctx).e2 = logicAndExpr();
				 ((LogicOrExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.OR, _localctx.ast, ((LogicOrExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((LogicOrExprContext)_localctx).e1!=null?(((LogicOrExprContext)_localctx).e1.start):null), (((LogicOrExprContext)_localctx).e2!=null?(((LogicOrExprContext)_localctx).e2.stop):null))); 
				}
				}
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicAndExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public ComparisonExprContext e1;
		public ComparisonExprContext e2;
		public List<ComparisonExprContext> comparisonExpr() {
			return getRuleContexts(ComparisonExprContext.class);
		}
		public ComparisonExprContext comparisonExpr(int i) {
			return getRuleContext(ComparisonExprContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(Prev26Parser.AND); }
		public TerminalNode AND(int i) {
			return getToken(Prev26Parser.AND, i);
		}
		public LogicAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicAndExpr; }
	}

	public final LogicAndExprContext logicAndExpr() throws RecognitionException {
		LogicAndExprContext _localctx = new LogicAndExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_logicAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			((LogicAndExprContext)_localctx).e1 = comparisonExpr();
			 ((LogicAndExprContext)_localctx).ast =  ((LogicAndExprContext)_localctx).e1.ast; 
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(293);
				match(AND);
				setState(294);
				((LogicAndExprContext)_localctx).e2 = comparisonExpr();
				 ((LogicAndExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.AND, _localctx.ast, ((LogicAndExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((LogicAndExprContext)_localctx).e1!=null?(((LogicAndExprContext)_localctx).e1.start):null), (((LogicAndExprContext)_localctx).e2!=null?(((LogicAndExprContext)_localctx).e2.stop):null))); 
				}
				}
				setState(301);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public AdditiveExprContext e1;
		public AdditiveExprContext e2;
		public List<AdditiveExprContext> additiveExpr() {
			return getRuleContexts(AdditiveExprContext.class);
		}
		public AdditiveExprContext additiveExpr(int i) {
			return getRuleContext(AdditiveExprContext.class,i);
		}
		public TerminalNode EQUALS() { return getToken(Prev26Parser.EQUALS, 0); }
		public TerminalNode NOTEQUALS() { return getToken(Prev26Parser.NOTEQUALS, 0); }
		public TerminalNode LESSER() { return getToken(Prev26Parser.LESSER, 0); }
		public TerminalNode GREATER() { return getToken(Prev26Parser.GREATER, 0); }
		public TerminalNode LEQUALS() { return getToken(Prev26Parser.LEQUALS, 0); }
		public TerminalNode MEQUALS() { return getToken(Prev26Parser.MEQUALS, 0); }
		public ComparisonExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpr; }
	}

	public final ComparisonExprContext comparisonExpr() throws RecognitionException {
		ComparisonExprContext _localctx = new ComparisonExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comparisonExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(302);
			((ComparisonExprContext)_localctx).e1 = additiveExpr();
			 ((ComparisonExprContext)_localctx).ast =  ((ComparisonExprContext)_localctx).e1.ast; 
			setState(328);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
				{
				setState(304);
				match(EQUALS);
				setState(305);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.EQU, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case NOTEQUALS:
				{
				setState(308);
				match(NOTEQUALS);
				setState(309);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.NEQ, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case LESSER:
				{
				setState(312);
				match(LESSER);
				setState(313);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.LTH, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case GREATER:
				{
				setState(316);
				match(GREATER);
				setState(317);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.GTH, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case LEQUALS:
				{
				setState(320);
				match(LEQUALS);
				setState(321);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.LEQ, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case MEQUALS:
				{
				setState(324);
				match(MEQUALS);
				setState(325);
				((ComparisonExprContext)_localctx).e2 = additiveExpr();
				 ((ComparisonExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.GEQ, ((ComparisonExprContext)_localctx).e1.ast, ((ComparisonExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc((((ComparisonExprContext)_localctx).e1!=null?(((ComparisonExprContext)_localctx).e1.start):null), (((ComparisonExprContext)_localctx).e2!=null?(((ComparisonExprContext)_localctx).e2.stop):null))); 
				}
				break;
			case EOF:
			case COMMA:
			case DEFINE:
			case RIGHTBR:
			case RIGHTSQBR:
			case AND:
			case AS:
			case DO:
			case ELSE:
			case END:
			case FUN:
			case IN:
			case OR:
			case THEN:
			case TYP:
			case VAR:
				break;
			default:
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AdditiveExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public MultiplicativeExprContext e;
		public MultiplicativeExprContext e2;
		public List<MultiplicativeExprContext> multiplicativeExpr() {
			return getRuleContexts(MultiplicativeExprContext.class);
		}
		public MultiplicativeExprContext multiplicativeExpr(int i) {
			return getRuleContext(MultiplicativeExprContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(Prev26Parser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(Prev26Parser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(Prev26Parser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(Prev26Parser.MINUS, i);
		}
		public AdditiveExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpr; }
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			((AdditiveExprContext)_localctx).e = multiplicativeExpr();
			 ((AdditiveExprContext)_localctx).ast =  ((AdditiveExprContext)_localctx).e.ast; 
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				setState(340);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case PLUS:
					{
					setState(332);
					match(PLUS);
					setState(333);
					((AdditiveExprContext)_localctx).e2 = multiplicativeExpr();
					 ((AdditiveExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.ADD, _localctx.ast, ((AdditiveExprContext)_localctx).e2.ast);
					            Abstr.locAttr.put(_localctx.ast, loc((((AdditiveExprContext)_localctx).e!=null?(((AdditiveExprContext)_localctx).e.start):null), (((AdditiveExprContext)_localctx).e2!=null?(((AdditiveExprContext)_localctx).e2.stop):null))); 
					}
					break;
				case MINUS:
					{
					setState(336);
					match(MINUS);
					setState(337);
					((AdditiveExprContext)_localctx).e2 = multiplicativeExpr();
					 ((AdditiveExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.SUB, _localctx.ast, ((AdditiveExprContext)_localctx).e2.ast);
					            Abstr.locAttr.put(_localctx.ast, loc((((AdditiveExprContext)_localctx).e!=null?(((AdditiveExprContext)_localctx).e.start):null), (((AdditiveExprContext)_localctx).e2!=null?(((AdditiveExprContext)_localctx).e2.stop):null))); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(344);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicativeExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public PrefixExprContext e;
		public PrefixExprContext e2;
		public List<PrefixExprContext> prefixExpr() {
			return getRuleContexts(PrefixExprContext.class);
		}
		public PrefixExprContext prefixExpr(int i) {
			return getRuleContext(PrefixExprContext.class,i);
		}
		public List<TerminalNode> MULTIPLY() { return getTokens(Prev26Parser.MULTIPLY); }
		public TerminalNode MULTIPLY(int i) {
			return getToken(Prev26Parser.MULTIPLY, i);
		}
		public List<TerminalNode> DIVIDE() { return getTokens(Prev26Parser.DIVIDE); }
		public TerminalNode DIVIDE(int i) {
			return getToken(Prev26Parser.DIVIDE, i);
		}
		public List<TerminalNode> MODULO() { return getTokens(Prev26Parser.MODULO); }
		public TerminalNode MODULO(int i) {
			return getToken(Prev26Parser.MODULO, i);
		}
		public MultiplicativeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpr; }
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			((MultiplicativeExprContext)_localctx).e = prefixExpr();
			 ((MultiplicativeExprContext)_localctx).ast =  ((MultiplicativeExprContext)_localctx).e.ast; 
			setState(361);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7168L) != 0)) {
				{
				setState(359);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case MULTIPLY:
					{
					setState(347);
					match(MULTIPLY);
					setState(348);
					((MultiplicativeExprContext)_localctx).e2 = prefixExpr();
					 ((MultiplicativeExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.MUL, _localctx.ast, ((MultiplicativeExprContext)_localctx).e2.ast);
					            Abstr.locAttr.put(_localctx.ast, loc((((MultiplicativeExprContext)_localctx).e!=null?(((MultiplicativeExprContext)_localctx).e.start):null), (((MultiplicativeExprContext)_localctx).e2!=null?(((MultiplicativeExprContext)_localctx).e2.stop):null))); 
					}
					break;
				case DIVIDE:
					{
					setState(351);
					match(DIVIDE);
					setState(352);
					((MultiplicativeExprContext)_localctx).e2 = prefixExpr();
					 ((MultiplicativeExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.DIV, _localctx.ast, ((MultiplicativeExprContext)_localctx).e2.ast);
					            Abstr.locAttr.put(_localctx.ast, loc((((MultiplicativeExprContext)_localctx).e!=null?(((MultiplicativeExprContext)_localctx).e.start):null), (((MultiplicativeExprContext)_localctx).e2!=null?(((MultiplicativeExprContext)_localctx).e2.stop):null))); 
					}
					break;
				case MODULO:
					{
					setState(355);
					match(MODULO);
					setState(356);
					((MultiplicativeExprContext)_localctx).e2 = prefixExpr();
					 ((MultiplicativeExprContext)_localctx).ast =  new AST.BinExpr(AST.BinExpr.Oper.MOD, _localctx.ast, ((MultiplicativeExprContext)_localctx).e2.ast);
					            Abstr.locAttr.put(_localctx.ast, loc((((MultiplicativeExprContext)_localctx).e!=null?(((MultiplicativeExprContext)_localctx).e.start):null), (((MultiplicativeExprContext)_localctx).e2!=null?(((MultiplicativeExprContext)_localctx).e2.stop):null))); 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(363);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrefixExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public PostfixExprContext e1;
		public Token tok;
		public PrefixExprContext e;
		public Token i;
		public TypeContext t;
		public PostfixExprContext postfixExpr() {
			return getRuleContext(PostfixExprContext.class,0);
		}
		public TerminalNode NOT() { return getToken(Prev26Parser.NOT, 0); }
		public PrefixExprContext prefixExpr() {
			return getRuleContext(PrefixExprContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(Prev26Parser.MINUS, 0); }
		public TerminalNode INTCONST() { return getToken(Prev26Parser.INTCONST, 0); }
		public TerminalNode PLUS() { return getToken(Prev26Parser.PLUS, 0); }
		public TerminalNode CARET() { return getToken(Prev26Parser.CARET, 0); }
		public TerminalNode SIZEOF() { return getToken(Prev26Parser.SIZEOF, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public PrefixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixExpr; }
	}

	public final PrefixExprContext prefixExpr() throws RecognitionException {
		PrefixExprContext _localctx = new PrefixExprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_prefixExpr);
		try {
			setState(393);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(364);
				((PrefixExprContext)_localctx).e1 = postfixExpr(0);
				 ((PrefixExprContext)_localctx).ast =  ((PrefixExprContext)_localctx).e1.ast; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(367);
				((PrefixExprContext)_localctx).tok = match(NOT);
				setState(368);
				((PrefixExprContext)_localctx).e = prefixExpr();
				 ((PrefixExprContext)_localctx).ast =  new AST.PfxExpr(AST.PfxExpr.Oper.NOT, ((PrefixExprContext)_localctx).e.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, (((PrefixExprContext)_localctx).e!=null?(((PrefixExprContext)_localctx).e.stop):null))); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(371);
				((PrefixExprContext)_localctx).tok = match(MINUS);
				setState(372);
				((PrefixExprContext)_localctx).i = match(INTCONST);
				 ((PrefixExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.INT, "-" + (((PrefixExprContext)_localctx).i!=null?((PrefixExprContext)_localctx).i.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, ((PrefixExprContext)_localctx).tok)); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(374);
				((PrefixExprContext)_localctx).tok = match(PLUS);
				setState(375);
				((PrefixExprContext)_localctx).i = match(INTCONST);
				 ((PrefixExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.INT, "+" + (((PrefixExprContext)_localctx).i!=null?((PrefixExprContext)_localctx).i.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, ((PrefixExprContext)_localctx).tok)); 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(377);
				((PrefixExprContext)_localctx).tok = match(PLUS);
				setState(378);
				((PrefixExprContext)_localctx).e = prefixExpr();
				 ((PrefixExprContext)_localctx).ast =  new AST.PfxExpr(AST.PfxExpr.Oper.ADD, ((PrefixExprContext)_localctx).e.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, (((PrefixExprContext)_localctx).e!=null?(((PrefixExprContext)_localctx).e.stop):null))); 
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(381);
				((PrefixExprContext)_localctx).tok = match(MINUS);
				setState(382);
				((PrefixExprContext)_localctx).e = prefixExpr();
				 ((PrefixExprContext)_localctx).ast =  new AST.PfxExpr(AST.PfxExpr.Oper.SUB, ((PrefixExprContext)_localctx).e.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, (((PrefixExprContext)_localctx).e!=null?(((PrefixExprContext)_localctx).e.stop):null))); 
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(385);
				((PrefixExprContext)_localctx).tok = match(CARET);
				setState(386);
				((PrefixExprContext)_localctx).e = prefixExpr();
				 ((PrefixExprContext)_localctx).ast =  new AST.PfxExpr(AST.PfxExpr.Oper.PTR, ((PrefixExprContext)_localctx).e.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, (((PrefixExprContext)_localctx).e!=null?(((PrefixExprContext)_localctx).e.stop):null))); 
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(389);
				((PrefixExprContext)_localctx).tok = match(SIZEOF);
				setState(390);
				((PrefixExprContext)_localctx).t = type();
				 ((PrefixExprContext)_localctx).ast =  new AST.SizeExpr(((PrefixExprContext)_localctx).t.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((PrefixExprContext)_localctx).tok, (((PrefixExprContext)_localctx).t!=null?(((PrefixExprContext)_localctx).t.stop):null))); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PostfixExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public PostfixExprContext e1;
		public AtomExprContext e;
		public ExprContext e2;
		public Token RIGHTSQBR;
		public Token CARET;
		public Token NAME;
		public Expr_list_zero_nodesContext elist;
		public Token RIGHTBR;
		public AtomExprContext atomExpr() {
			return getRuleContext(AtomExprContext.class,0);
		}
		public TerminalNode LEFTSQBR() { return getToken(Prev26Parser.LEFTSQBR, 0); }
		public TerminalNode RIGHTSQBR() { return getToken(Prev26Parser.RIGHTSQBR, 0); }
		public PostfixExprContext postfixExpr() {
			return getRuleContext(PostfixExprContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CARET() { return getToken(Prev26Parser.CARET, 0); }
		public TerminalNode PERIOD() { return getToken(Prev26Parser.PERIOD, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public TerminalNode LEFTBR() { return getToken(Prev26Parser.LEFTBR, 0); }
		public TerminalNode RIGHTBR() { return getToken(Prev26Parser.RIGHTBR, 0); }
		public Expr_list_zero_nodesContext expr_list_zero_nodes() {
			return getRuleContext(Expr_list_zero_nodesContext.class,0);
		}
		public PostfixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpr; }
	}

	public final PostfixExprContext postfixExpr() throws RecognitionException {
		return postfixExpr(0);
	}

	private PostfixExprContext postfixExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PostfixExprContext _localctx = new PostfixExprContext(_ctx, _parentState);
		PostfixExprContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_postfixExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(396);
			((PostfixExprContext)_localctx).e = atomExpr();
			 ((PostfixExprContext)_localctx).ast =  ((PostfixExprContext)_localctx).e.ast; 
			}
			_ctx.stop = _input.LT(-1);
			setState(420);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(418);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new PostfixExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpr);
						setState(399);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(400);
						match(LEFTSQBR);
						setState(401);
						((PostfixExprContext)_localctx).e2 = expr();
						setState(402);
						((PostfixExprContext)_localctx).RIGHTSQBR = match(RIGHTSQBR);
						 ((PostfixExprContext)_localctx).ast =  new AST.ArrExpr(((PostfixExprContext)_localctx).e1.ast, ((PostfixExprContext)_localctx).e2.ast);
						                      Abstr.locAttr.put(_localctx.ast, loc((((PostfixExprContext)_localctx).e1!=null?(((PostfixExprContext)_localctx).e1.start):null), ((PostfixExprContext)_localctx).RIGHTSQBR)); 
						}
						break;
					case 2:
						{
						_localctx = new PostfixExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpr);
						setState(405);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(406);
						((PostfixExprContext)_localctx).CARET = match(CARET);
						 ((PostfixExprContext)_localctx).ast =  new AST.SfxExpr(AST.SfxExpr.Oper.PTR, ((PostfixExprContext)_localctx).e1.ast);
						                      Abstr.locAttr.put(_localctx.ast, loc((((PostfixExprContext)_localctx).e1!=null?(((PostfixExprContext)_localctx).e1.start):null), ((PostfixExprContext)_localctx).CARET)); 
						}
						break;
					case 3:
						{
						_localctx = new PostfixExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpr);
						setState(408);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(409);
						match(PERIOD);
						setState(410);
						((PostfixExprContext)_localctx).NAME = match(NAME);
						 ((PostfixExprContext)_localctx).ast =  new AST.CompExpr(((PostfixExprContext)_localctx).e1.ast, (((PostfixExprContext)_localctx).NAME!=null?((PostfixExprContext)_localctx).NAME.getText():null));
						                      Abstr.locAttr.put(_localctx.ast, loc((((PostfixExprContext)_localctx).e1!=null?(((PostfixExprContext)_localctx).e1.start):null), ((PostfixExprContext)_localctx).NAME)); 
						}
						break;
					case 4:
						{
						_localctx = new PostfixExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_postfixExpr);
						setState(412);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(413);
						match(LEFTBR);
						setState(414);
						((PostfixExprContext)_localctx).elist = expr_list_zero_nodes();
						setState(415);
						((PostfixExprContext)_localctx).RIGHTBR = match(RIGHTBR);
						 ((PostfixExprContext)_localctx).ast =  new AST.CallExpr(((PostfixExprContext)_localctx).e1.ast, ((PostfixExprContext)_localctx).elist.nodes);
						                      Abstr.locAttr.put(_localctx.ast, loc((((PostfixExprContext)_localctx).e1!=null?(((PostfixExprContext)_localctx).e1.start):null), ((PostfixExprContext)_localctx).RIGHTBR)); 
						}
						break;
					}
					} 
				}
				setState(422);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ParserRuleContext {
		public AST.Expr ast;
		public Token IF;
		public ExprContext e1;
		public Expr_listContext e2;
		public Token END;
		public Expr_listContext e3;
		public Token WHILE;
		public Token LET;
		public Defn_list_nodesContext d1;
		public Expr_listContext e;
		public Token tok;
		public TerminalNode IF() { return getToken(Prev26Parser.IF, 0); }
		public TerminalNode THEN() { return getToken(Prev26Parser.THEN, 0); }
		public TerminalNode END() { return getToken(Prev26Parser.END, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<Expr_listContext> expr_list() {
			return getRuleContexts(Expr_listContext.class);
		}
		public Expr_listContext expr_list(int i) {
			return getRuleContext(Expr_listContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(Prev26Parser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(Prev26Parser.WHILE, 0); }
		public TerminalNode DO() { return getToken(Prev26Parser.DO, 0); }
		public TerminalNode LET() { return getToken(Prev26Parser.LET, 0); }
		public TerminalNode IN() { return getToken(Prev26Parser.IN, 0); }
		public Defn_list_nodesContext defn_list_nodes() {
			return getRuleContext(Defn_list_nodesContext.class,0);
		}
		public TerminalNode LEFTBR() { return getToken(Prev26Parser.LEFTBR, 0); }
		public TerminalNode RIGHTBR() { return getToken(Prev26Parser.RIGHTBR, 0); }
		public TerminalNode INTCONST() { return getToken(Prev26Parser.INTCONST, 0); }
		public TerminalNode TRUE() { return getToken(Prev26Parser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(Prev26Parser.FALSE, 0); }
		public TerminalNode CHARCONST() { return getToken(Prev26Parser.CHARCONST, 0); }
		public TerminalNode STRING() { return getToken(Prev26Parser.STRING, 0); }
		public TerminalNode NONE() { return getToken(Prev26Parser.NONE, 0); }
		public TerminalNode NIL() { return getToken(Prev26Parser.NIL, 0); }
		public TerminalNode NAME() { return getToken(Prev26Parser.NAME, 0); }
		public AtomExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomExpr; }
	}

	public final AtomExprContext atomExpr() throws RecognitionException {
		AtomExprContext _localctx = new AtomExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_atomExpr);
		try {
			setState(474);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(423);
				((AtomExprContext)_localctx).IF = match(IF);
				setState(424);
				((AtomExprContext)_localctx).e1 = expr();
				setState(425);
				match(THEN);
				setState(426);
				((AtomExprContext)_localctx).e2 = expr_list();
				setState(427);
				((AtomExprContext)_localctx).END = match(END);
				 ((AtomExprContext)_localctx).ast =  new AST.IfThenExpr(((AtomExprContext)_localctx).e1.ast, ((AtomExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).IF, ((AtomExprContext)_localctx).END)); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(430);
				((AtomExprContext)_localctx).IF = match(IF);
				setState(431);
				((AtomExprContext)_localctx).e1 = expr();
				setState(432);
				match(THEN);
				setState(433);
				((AtomExprContext)_localctx).e2 = expr_list();
				setState(434);
				match(ELSE);
				setState(435);
				((AtomExprContext)_localctx).e3 = expr_list();
				setState(436);
				((AtomExprContext)_localctx).END = match(END);
				 ((AtomExprContext)_localctx).ast =  new AST.IfThenElseExpr(((AtomExprContext)_localctx).e1.ast, ((AtomExprContext)_localctx).e2.ast, ((AtomExprContext)_localctx).e3.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).IF, ((AtomExprContext)_localctx).END)); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(439);
				((AtomExprContext)_localctx).WHILE = match(WHILE);
				setState(440);
				((AtomExprContext)_localctx).e1 = expr();
				setState(441);
				match(DO);
				setState(442);
				((AtomExprContext)_localctx).e2 = expr_list();
				setState(443);
				((AtomExprContext)_localctx).END = match(END);
				 ((AtomExprContext)_localctx).ast =  new AST.WhileExpr(((AtomExprContext)_localctx).e1.ast, ((AtomExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).WHILE, ((AtomExprContext)_localctx).END)); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(446);
				((AtomExprContext)_localctx).LET = match(LET);
				setState(447);
				((AtomExprContext)_localctx).d1 = defn_list_nodes();
				setState(448);
				match(IN);
				setState(449);
				((AtomExprContext)_localctx).e2 = expr_list();
				setState(450);
				((AtomExprContext)_localctx).END = match(END);
				 ((AtomExprContext)_localctx).ast =  new AST.LetExpr(((AtomExprContext)_localctx).d1.nodes, ((AtomExprContext)_localctx).e2.ast);
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).LET, ((AtomExprContext)_localctx).END)); 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(453);
				match(LEFTBR);
				setState(454);
				((AtomExprContext)_localctx).e = expr_list();
				setState(455);
				match(RIGHTBR);
				 ((AtomExprContext)_localctx).ast =  ((AtomExprContext)_localctx).e.ast; 
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(458);
				((AtomExprContext)_localctx).tok = match(INTCONST);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.INT, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(460);
				((AtomExprContext)_localctx).tok = match(TRUE);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.BOOL, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(462);
				((AtomExprContext)_localctx).tok = match(FALSE);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.BOOL, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(464);
				((AtomExprContext)_localctx).tok = match(CHARCONST);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.CHAR, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(466);
				((AtomExprContext)_localctx).tok = match(STRING);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.STR, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(468);
				((AtomExprContext)_localctx).tok = match(NONE);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.VOID, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(470);
				((AtomExprContext)_localctx).tok = match(NIL);
				 ((AtomExprContext)_localctx).ast =  new AST.AtomExpr(AST.AtomExpr.Type.PTR, (((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(472);
				((AtomExprContext)_localctx).tok = match(NAME);
				 ((AtomExprContext)_localctx).ast =  new AST.NameExpr((((AtomExprContext)_localctx).tok!=null?((AtomExprContext)_localctx).tok.getText():null));
				            Abstr.locAttr.put(_localctx.ast, loc(((AtomExprContext)_localctx).tok, ((AtomExprContext)_localctx).tok)); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 23:
			return postfixExpr_sempred((PostfixExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean postfixExpr_sempred(PostfixExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00014\u01dd\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001A\b\u0001\n\u0001\f\u0001D\t\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002L\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003T\b\u0003\n\u0003\f\u0003W\t\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004_\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005h\b\u0005\n\u0005\f\u0005"+
		"k\t\u0005\u0001\u0005\u0001\u0005\u0003\u0005o\b\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0005\u0006{\b\u0006\n\u0006\f\u0006~\t"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0003\u0007\u0086\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b\u008e\b\b\n\b\f\b\u0091\t\b\u0001\b\u0001\b\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0003\t\u0099\b\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0005\n\u00a0\b\n\n\n\f\n\u00a3\t\n\u0001\n\u0001\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00ac\b\u000b"+
		"\n\u000b\f\u000b\u00af\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0003\f\u00d3\b\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0003\r\u0100\b\r\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u010d\b\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u0117\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u011f\b\u0011\n\u0011\f\u0011"+
		"\u0122\t\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0005\u0012\u012a\b\u0012\n\u0012\f\u0012\u012d\t\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0003\u0013\u0149\b\u0013\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0005\u0014\u0155\b\u0014\n\u0014\f\u0014\u0158\t\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0005\u0015\u0168\b\u0015\n\u0015\f\u0015\u016b"+
		"\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003"+
		"\u0016\u018a\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001"+
		"\u0017\u0005\u0017\u01a3\b\u0017\n\u0017\f\u0017\u01a6\t\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0003\u0018\u01db\b\u0018\u0001\u0018\u0000\u0001"+
		".\u0019\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u001c\u001e \"$&(*,.0\u0000\u0000\u0202\u00002\u0001\u0000\u0000"+
		"\u0000\u00026\u0001\u0000\u0000\u0000\u0004K\u0001\u0000\u0000\u0000\u0006"+
		"M\u0001\u0000\u0000\u0000\b^\u0001\u0000\u0000\u0000\nn\u0001\u0000\u0000"+
		"\u0000\fp\u0001\u0000\u0000\u0000\u000e\u0085\u0001\u0000\u0000\u0000"+
		"\u0010\u0087\u0001\u0000\u0000\u0000\u0012\u0098\u0001\u0000\u0000\u0000"+
		"\u0014\u009a\u0001\u0000\u0000\u0000\u0016\u00a6\u0001\u0000\u0000\u0000"+
		"\u0018\u00d2\u0001\u0000\u0000\u0000\u001a\u00ff\u0001\u0000\u0000\u0000"+
		"\u001c\u0101\u0001\u0000\u0000\u0000\u001e\u010c\u0001\u0000\u0000\u0000"+
		" \u0116\u0001\u0000\u0000\u0000\"\u0118\u0001\u0000\u0000\u0000$\u0123"+
		"\u0001\u0000\u0000\u0000&\u012e\u0001\u0000\u0000\u0000(\u014a\u0001\u0000"+
		"\u0000\u0000*\u0159\u0001\u0000\u0000\u0000,\u0189\u0001\u0000\u0000\u0000"+
		".\u018b\u0001\u0000\u0000\u00000\u01da\u0001\u0000\u0000\u000023\u0003"+
		"\u0016\u000b\u000034\u0005\u0000\u0000\u000145\u0006\u0000\uffff\uffff"+
		"\u00005\u0001\u0001\u0000\u0000\u000067\u00052\u0000\u000078\u0005\u0006"+
		"\u0000\u000089\u0003\u001a\r\u00009B\u0006\u0001\uffff\uffff\u0000:;\u0005"+
		"\u0005\u0000\u0000;<\u00052\u0000\u0000<=\u0005\u0006\u0000\u0000=>\u0003"+
		"\u001a\r\u0000>?\u0006\u0001\uffff\uffff\u0000?A\u0001\u0000\u0000\u0000"+
		"@:\u0001\u0000\u0000\u0000AD\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000\u0000\u0000DB\u0001\u0000"+
		"\u0000\u0000EF\u0006\u0001\uffff\uffff\u0000F\u0003\u0001\u0000\u0000"+
		"\u0000GL\u0006\u0002\uffff\uffff\u0000HI\u0003\u0002\u0001\u0000IJ\u0006"+
		"\u0002\uffff\uffff\u0000JL\u0001\u0000\u0000\u0000KG\u0001\u0000\u0000"+
		"\u0000KH\u0001\u0000\u0000\u0000L\u0005\u0001\u0000\u0000\u0000MN\u0003"+
		"\u001c\u000e\u0000NU\u0006\u0003\uffff\uffff\u0000OP\u0005\u0005\u0000"+
		"\u0000PQ\u0003\u001c\u000e\u0000QR\u0006\u0003\uffff\uffff\u0000RT\u0001"+
		"\u0000\u0000\u0000SO\u0001\u0000\u0000\u0000TW\u0001\u0000\u0000\u0000"+
		"US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VX\u0001\u0000\u0000"+
		"\u0000WU\u0001\u0000\u0000\u0000XY\u0006\u0003\uffff\uffff\u0000Y\u0007"+
		"\u0001\u0000\u0000\u0000Z_\u0006\u0004\uffff\uffff\u0000[\\\u0003\u0006"+
		"\u0003\u0000\\]\u0006\u0004\uffff\uffff\u0000]_\u0001\u0000\u0000\u0000"+
		"^Z\u0001\u0000\u0000\u0000^[\u0001\u0000\u0000\u0000_\t\u0001\u0000\u0000"+
		"\u0000`o\u0006\u0005\uffff\uffff\u0000ab\u0003\u001c\u000e\u0000bi\u0006"+
		"\u0005\uffff\uffff\u0000cd\u0005\u0005\u0000\u0000de\u0003\u001c\u000e"+
		"\u0000ef\u0006\u0005\uffff\uffff\u0000fh\u0001\u0000\u0000\u0000gc\u0001"+
		"\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000ig\u0001\u0000\u0000\u0000"+
		"ij\u0001\u0000\u0000\u0000jl\u0001\u0000\u0000\u0000ki\u0001\u0000\u0000"+
		"\u0000lm\u0006\u0005\uffff\uffff\u0000mo\u0001\u0000\u0000\u0000n`\u0001"+
		"\u0000\u0000\u0000na\u0001\u0000\u0000\u0000o\u000b\u0001\u0000\u0000"+
		"\u0000pq\u00052\u0000\u0000qr\u0005\u0006\u0000\u0000rs\u0003\u001a\r"+
		"\u0000s|\u0006\u0006\uffff\uffff\u0000tu\u0005\u0005\u0000\u0000uv\u0005"+
		"2\u0000\u0000vw\u0005\u0006\u0000\u0000wx\u0003\u001a\r\u0000xy\u0006"+
		"\u0006\uffff\uffff\u0000y{\u0001\u0000\u0000\u0000zt\u0001\u0000\u0000"+
		"\u0000{~\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000|}\u0001\u0000"+
		"\u0000\u0000}\u007f\u0001\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000"+
		"\u007f\u0080\u0006\u0006\uffff\uffff\u0000\u0080\r\u0001\u0000\u0000\u0000"+
		"\u0081\u0086\u0006\u0007\uffff\uffff\u0000\u0082\u0083\u0003\f\u0006\u0000"+
		"\u0083\u0084\u0006\u0007\uffff\uffff\u0000\u0084\u0086\u0001\u0000\u0000"+
		"\u0000\u0085\u0081\u0001\u0000\u0000\u0000\u0085\u0082\u0001\u0000\u0000"+
		"\u0000\u0086\u000f\u0001\u0000\u0000\u0000\u0087\u0088\u0003\u001a\r\u0000"+
		"\u0088\u008f\u0006\b\uffff\uffff\u0000\u0089\u008a\u0005\u0005\u0000\u0000"+
		"\u008a\u008b\u0003\u001a\r\u0000\u008b\u008c\u0006\b\uffff\uffff\u0000"+
		"\u008c\u008e\u0001\u0000\u0000\u0000\u008d\u0089\u0001\u0000\u0000\u0000"+
		"\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000"+
		"\u008f\u0090\u0001\u0000\u0000\u0000\u0090\u0092\u0001\u0000\u0000\u0000"+
		"\u0091\u008f\u0001\u0000\u0000\u0000\u0092\u0093\u0006\b\uffff\uffff\u0000"+
		"\u0093\u0011\u0001\u0000\u0000\u0000\u0094\u0099\u0006\t\uffff\uffff\u0000"+
		"\u0095\u0096\u0003\u0010\b\u0000\u0096\u0097\u0006\t\uffff\uffff\u0000"+
		"\u0097\u0099\u0001\u0000\u0000\u0000\u0098\u0094\u0001\u0000\u0000\u0000"+
		"\u0098\u0095\u0001\u0000\u0000\u0000\u0099\u0013\u0001\u0000\u0000\u0000"+
		"\u009a\u009b\u0003\u0018\f\u0000\u009b\u00a1\u0006\n\uffff\uffff\u0000"+
		"\u009c\u009d\u0003\u0018\f\u0000\u009d\u009e\u0006\n\uffff\uffff\u0000"+
		"\u009e\u00a0\u0001\u0000\u0000\u0000\u009f\u009c\u0001\u0000\u0000\u0000"+
		"\u00a0\u00a3\u0001\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a3\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a5\u0006\n\uffff\uffff\u0000"+
		"\u00a5\u0015\u0001\u0000\u0000\u0000\u00a6\u00a7\u0003\u0018\f\u0000\u00a7"+
		"\u00ad\u0006\u000b\uffff\uffff\u0000\u00a8\u00a9\u0003\u0018\f\u0000\u00a9"+
		"\u00aa\u0006\u000b\uffff\uffff\u0000\u00aa\u00ac\u0001\u0000\u0000\u0000"+
		"\u00ab\u00a8\u0001\u0000\u0000\u0000\u00ac\u00af\u0001\u0000\u0000\u0000"+
		"\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000"+
		"\u00ae\u00b0\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000"+
		"\u00b0\u00b1\u0006\u000b\uffff\uffff\u0000\u00b1\u0017\u0001\u0000\u0000"+
		"\u0000\u00b2\u00b3\u0005.\u0000\u0000\u00b3\u00b4\u00052\u0000\u0000\u00b4"+
		"\u00b5\u0005\u0007\u0000\u0000\u00b5\u00b6\u0003\u001a\r\u0000\u00b6\u00b7"+
		"\u0006\f\uffff\uffff\u0000\u00b7\u00d3\u0001\u0000\u0000\u0000\u00b8\u00b9"+
		"\u0005/\u0000\u0000\u00b9\u00ba\u00052\u0000\u0000\u00ba\u00bb\u0005\u0006"+
		"\u0000\u0000\u00bb\u00bc\u0003\u001a\r\u0000\u00bc\u00bd\u0006\f\uffff"+
		"\uffff\u0000\u00bd\u00d3\u0001\u0000\u0000\u0000\u00be\u00bf\u0005\"\u0000"+
		"\u0000\u00bf\u00c0\u00052\u0000\u0000\u00c0\u00c1\u0005\u0013\u0000\u0000"+
		"\u00c1\u00c2\u0003\u0004\u0002\u0000\u00c2\u00c3\u0005\u0014\u0000\u0000"+
		"\u00c3\u00c4\u0005\u0006\u0000\u0000\u00c4\u00c5\u0003\u001a\r\u0000\u00c5"+
		"\u00c6\u0006\f\uffff\uffff\u0000\u00c6\u00d3\u0001\u0000\u0000\u0000\u00c7"+
		"\u00c8\u0005\"\u0000\u0000\u00c8\u00c9\u00052\u0000\u0000\u00c9\u00ca"+
		"\u0005\u0013\u0000\u0000\u00ca\u00cb\u0003\u0004\u0002\u0000\u00cb\u00cc"+
		"\u0005\u0014\u0000\u0000\u00cc\u00cd\u0005\u0006\u0000\u0000\u00cd\u00ce"+
		"\u0003\u001a\r\u0000\u00ce\u00cf\u0005\u0007\u0000\u0000\u00cf\u00d0\u0003"+
		"\u0006\u0003\u0000\u00d0\u00d1\u0006\f\uffff\uffff\u0000\u00d1\u00d3\u0001"+
		"\u0000\u0000\u0000\u00d2\u00b2\u0001\u0000\u0000\u0000\u00d2\u00b8\u0001"+
		"\u0000\u0000\u0000\u00d2\u00be\u0001\u0000\u0000\u0000\u00d2\u00c7\u0001"+
		"\u0000\u0000\u0000\u00d3\u0019\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005"+
		"%\u0000\u0000\u00d5\u0100\u0006\r\uffff\uffff\u0000\u00d6\u00d7\u0005"+
		"\u001e\u0000\u0000\u00d7\u0100\u0006\r\uffff\uffff\u0000\u00d8\u00d9\u0005"+
		"\u001c\u0000\u0000\u00d9\u0100\u0006\r\uffff\uffff\u0000\u00da\u00db\u0005"+
		"0\u0000\u0000\u00db\u0100\u0006\r\uffff\uffff\u0000\u00dc\u00dd\u0005"+
		"2\u0000\u0000\u00dd\u0100\u0006\r\uffff\uffff\u0000\u00de\u00df\u0005"+
		"\u0015\u0000\u0000\u00df\u00e0\u0005\u0001\u0000\u0000\u00e0\u00e1\u0005"+
		"\u0016\u0000\u0000\u00e1\u00e2\u0003\u001a\r\u0000\u00e2\u00e3\u0006\r"+
		"\uffff\uffff\u0000\u00e3\u0100\u0001\u0000\u0000\u0000\u00e4\u00e5\u0005"+
		"\u0019\u0000\u0000\u00e5\u00e6\u0003\u001a\r\u0000\u00e6\u00e7\u0006\r"+
		"\uffff\uffff\u0000\u00e7\u0100\u0001\u0000\u0000\u0000\u00e8\u00e9\u0005"+
		"\u0013\u0000\u0000\u00e9\u00ea\u0003\f\u0006\u0000\u00ea\u00eb\u0005\u0014"+
		"\u0000\u0000\u00eb\u00ec\u0006\r\uffff\uffff\u0000\u00ec\u0100\u0001\u0000"+
		"\u0000\u0000\u00ed\u00ee\u0005\u0017\u0000\u0000\u00ee\u00ef\u0003\u000e"+
		"\u0007\u0000\u00ef\u00f0\u0005\u0018\u0000\u0000\u00f0\u00f1\u0006\r\uffff"+
		"\uffff\u0000\u00f1\u0100\u0001\u0000\u0000\u0000\u00f2\u00f3\u0005\u0013"+
		"\u0000\u0000\u00f3\u00f4\u0005\u0006\u0000\u0000\u00f4\u00f5\u0003\u0012"+
		"\t\u0000\u00f5\u00f6\u0005\u0006\u0000\u0000\u00f6\u00f7\u0003\u001a\r"+
		"\u0000\u00f7\u00f8\u0005\u0014\u0000\u0000\u00f8\u00f9\u0006\r\uffff\uffff"+
		"\u0000\u00f9\u0100\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\u0013\u0000"+
		"\u0000\u00fb\u00fc\u0003\u001a\r\u0000\u00fc\u00fd\u0005\u0014\u0000\u0000"+
		"\u00fd\u00fe\u0006\r\uffff\uffff\u0000\u00fe\u0100\u0001\u0000\u0000\u0000"+
		"\u00ff\u00d4\u0001\u0000\u0000\u0000\u00ff\u00d6\u0001\u0000\u0000\u0000"+
		"\u00ff\u00d8\u0001\u0000\u0000\u0000\u00ff\u00da\u0001\u0000\u0000\u0000"+
		"\u00ff\u00dc\u0001\u0000\u0000\u0000\u00ff\u00de\u0001\u0000\u0000\u0000"+
		"\u00ff\u00e4\u0001\u0000\u0000\u0000\u00ff\u00e8\u0001\u0000\u0000\u0000"+
		"\u00ff\u00ed\u0001\u0000\u0000\u0000\u00ff\u00f2\u0001\u0000\u0000\u0000"+
		"\u00ff\u00fa\u0001\u0000\u0000\u0000\u0100\u001b\u0001\u0000\u0000\u0000"+
		"\u0101\u0102\u0003\u001e\u000f\u0000\u0102\u0103\u0006\u000e\uffff\uffff"+
		"\u0000\u0103\u001d\u0001\u0000\u0000\u0000\u0104\u0105\u0003 \u0010\u0000"+
		"\u0105\u0106\u0006\u000f\uffff\uffff\u0000\u0106\u010d\u0001\u0000\u0000"+
		"\u0000\u0107\u0108\u0003 \u0010\u0000\u0108\u0109\u0005\u0007\u0000\u0000"+
		"\u0109\u010a\u0003 \u0010\u0000\u010a\u010b\u0006\u000f\uffff\uffff\u0000"+
		"\u010b\u010d\u0001\u0000\u0000\u0000\u010c\u0104\u0001\u0000\u0000\u0000"+
		"\u010c\u0107\u0001\u0000\u0000\u0000\u010d\u001f\u0001\u0000\u0000\u0000"+
		"\u010e\u010f\u0003\"\u0011\u0000\u010f\u0110\u0006\u0010\uffff\uffff\u0000"+
		"\u0110\u0117\u0001\u0000\u0000\u0000\u0111\u0112\u0003\"\u0011\u0000\u0112"+
		"\u0113\u0005\u001b\u0000\u0000\u0113\u0114\u0003\u001a\r\u0000\u0114\u0115"+
		"\u0006\u0010\uffff\uffff\u0000\u0115\u0117\u0001\u0000\u0000\u0000\u0116"+
		"\u010e\u0001\u0000\u0000\u0000\u0116\u0111\u0001\u0000\u0000\u0000\u0117"+
		"!\u0001\u0000\u0000\u0000\u0118\u0119\u0003$\u0012\u0000\u0119\u0120\u0006"+
		"\u0011\uffff\uffff\u0000\u011a\u011b\u0005*\u0000\u0000\u011b\u011c\u0003"+
		"$\u0012\u0000\u011c\u011d\u0006\u0011\uffff\uffff\u0000\u011d\u011f\u0001"+
		"\u0000\u0000\u0000\u011e\u011a\u0001\u0000\u0000\u0000\u011f\u0122\u0001"+
		"\u0000\u0000\u0000\u0120\u011e\u0001\u0000\u0000\u0000\u0120\u0121\u0001"+
		"\u0000\u0000\u0000\u0121#\u0001\u0000\u0000\u0000\u0122\u0120\u0001\u0000"+
		"\u0000\u0000\u0123\u0124\u0003&\u0013\u0000\u0124\u012b\u0006\u0012\uffff"+
		"\uffff\u0000\u0125\u0126\u0005\u001a\u0000\u0000\u0126\u0127\u0003&\u0013"+
		"\u0000\u0127\u0128\u0006\u0012\uffff\uffff\u0000\u0128\u012a\u0001\u0000"+
		"\u0000\u0000\u0129\u0125\u0001\u0000\u0000\u0000\u012a\u012d\u0001\u0000"+
		"\u0000\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012b\u012c\u0001\u0000"+
		"\u0000\u0000\u012c%\u0001\u0000\u0000\u0000\u012d\u012b\u0001\u0000\u0000"+
		"\u0000\u012e\u012f\u0003(\u0014\u0000\u012f\u0148\u0006\u0013\uffff\uffff"+
		"\u0000\u0130\u0131\u0005\r\u0000\u0000\u0131\u0132\u0003(\u0014\u0000"+
		"\u0132\u0133\u0006\u0013\uffff\uffff\u0000\u0133\u0149\u0001\u0000\u0000"+
		"\u0000\u0134\u0135\u0005\u000e\u0000\u0000\u0135\u0136\u0003(\u0014\u0000"+
		"\u0136\u0137\u0006\u0013\uffff\uffff\u0000\u0137\u0149\u0001\u0000\u0000"+
		"\u0000\u0138\u0139\u0005\u0011\u0000\u0000\u0139\u013a\u0003(\u0014\u0000"+
		"\u013a\u013b\u0006\u0013\uffff\uffff\u0000\u013b\u0149\u0001\u0000\u0000"+
		"\u0000\u013c\u013d\u0005\u0012\u0000\u0000\u013d\u013e\u0003(\u0014\u0000"+
		"\u013e\u013f\u0006\u0013\uffff\uffff\u0000\u013f\u0149\u0001\u0000\u0000"+
		"\u0000\u0140\u0141\u0005\u000f\u0000\u0000\u0141\u0142\u0003(\u0014\u0000"+
		"\u0142\u0143\u0006\u0013\uffff\uffff\u0000\u0143\u0149\u0001\u0000\u0000"+
		"\u0000\u0144\u0145\u0005\u0010\u0000\u0000\u0145\u0146\u0003(\u0014\u0000"+
		"\u0146\u0147\u0006\u0013\uffff\uffff\u0000\u0147\u0149\u0001\u0000\u0000"+
		"\u0000\u0148\u0130\u0001\u0000\u0000\u0000\u0148\u0134\u0001\u0000\u0000"+
		"\u0000\u0148\u0138\u0001\u0000\u0000\u0000\u0148\u013c\u0001\u0000\u0000"+
		"\u0000\u0148\u0140\u0001\u0000\u0000\u0000\u0148\u0144\u0001\u0000\u0000"+
		"\u0000\u0148\u0149\u0001\u0000\u0000\u0000\u0149\'\u0001\u0000\u0000\u0000"+
		"\u014a\u014b\u0003*\u0015\u0000\u014b\u0156\u0006\u0014\uffff\uffff\u0000"+
		"\u014c\u014d\u0005\b\u0000\u0000\u014d\u014e\u0003*\u0015\u0000\u014e"+
		"\u014f\u0006\u0014\uffff\uffff\u0000\u014f\u0155\u0001\u0000\u0000\u0000"+
		"\u0150\u0151\u0005\t\u0000\u0000\u0151\u0152\u0003*\u0015\u0000\u0152"+
		"\u0153\u0006\u0014\uffff\uffff\u0000\u0153\u0155\u0001\u0000\u0000\u0000"+
		"\u0154\u014c\u0001\u0000\u0000\u0000\u0154\u0150\u0001\u0000\u0000\u0000"+
		"\u0155\u0158\u0001\u0000\u0000\u0000\u0156\u0154\u0001\u0000\u0000\u0000"+
		"\u0156\u0157\u0001\u0000\u0000\u0000\u0157)\u0001\u0000\u0000\u0000\u0158"+
		"\u0156\u0001\u0000\u0000\u0000\u0159\u015a\u0003,\u0016\u0000\u015a\u0169"+
		"\u0006\u0015\uffff\uffff\u0000\u015b\u015c\u0005\n\u0000\u0000\u015c\u015d"+
		"\u0003,\u0016\u0000\u015d\u015e\u0006\u0015\uffff\uffff\u0000\u015e\u0168"+
		"\u0001\u0000\u0000\u0000\u015f\u0160\u0005\u000b\u0000\u0000\u0160\u0161"+
		"\u0003,\u0016\u0000\u0161\u0162\u0006\u0015\uffff\uffff\u0000\u0162\u0168"+
		"\u0001\u0000\u0000\u0000\u0163\u0164\u0005\f\u0000\u0000\u0164\u0165\u0003"+
		",\u0016\u0000\u0165\u0166\u0006\u0015\uffff\uffff\u0000\u0166\u0168\u0001"+
		"\u0000\u0000\u0000\u0167\u015b\u0001\u0000\u0000\u0000\u0167\u015f\u0001"+
		"\u0000\u0000\u0000\u0167\u0163\u0001\u0000\u0000\u0000\u0168\u016b\u0001"+
		"\u0000\u0000\u0000\u0169\u0167\u0001\u0000\u0000\u0000\u0169\u016a\u0001"+
		"\u0000\u0000\u0000\u016a+\u0001\u0000\u0000\u0000\u016b\u0169\u0001\u0000"+
		"\u0000\u0000\u016c\u016d\u0003.\u0017\u0000\u016d\u016e\u0006\u0016\uffff"+
		"\uffff\u0000\u016e\u018a\u0001\u0000\u0000\u0000\u016f\u0170\u0005)\u0000"+
		"\u0000\u0170\u0171\u0003,\u0016\u0000\u0171\u0172\u0006\u0016\uffff\uffff"+
		"\u0000\u0172\u018a\u0001\u0000\u0000\u0000\u0173\u0174\u0005\t\u0000\u0000"+
		"\u0174\u0175\u0005\u0001\u0000\u0000\u0175\u018a\u0006\u0016\uffff\uffff"+
		"\u0000\u0176\u0177\u0005\b\u0000\u0000\u0177\u0178\u0005\u0001\u0000\u0000"+
		"\u0178\u018a\u0006\u0016\uffff\uffff\u0000\u0179\u017a\u0005\b\u0000\u0000"+
		"\u017a\u017b\u0003,\u0016\u0000\u017b\u017c\u0006\u0016\uffff\uffff\u0000"+
		"\u017c\u018a\u0001\u0000\u0000\u0000\u017d\u017e\u0005\t\u0000\u0000\u017e"+
		"\u017f\u0003,\u0016\u0000\u017f\u0180\u0006\u0016\uffff\uffff\u0000\u0180"+
		"\u018a\u0001\u0000\u0000\u0000\u0181\u0182\u0005\u0019\u0000\u0000\u0182"+
		"\u0183\u0003,\u0016\u0000\u0183\u0184\u0006\u0016\uffff\uffff\u0000\u0184"+
		"\u018a\u0001\u0000\u0000\u0000\u0185\u0186\u0005+\u0000\u0000\u0186\u0187"+
		"\u0003\u001a\r\u0000\u0187\u0188\u0006\u0016\uffff\uffff\u0000\u0188\u018a"+
		"\u0001\u0000\u0000\u0000\u0189\u016c\u0001\u0000\u0000\u0000\u0189\u016f"+
		"\u0001\u0000\u0000\u0000\u0189\u0173\u0001\u0000\u0000\u0000\u0189\u0176"+
		"\u0001\u0000\u0000\u0000\u0189\u0179\u0001\u0000\u0000\u0000\u0189\u017d"+
		"\u0001\u0000\u0000\u0000\u0189\u0181\u0001\u0000\u0000\u0000\u0189\u0185"+
		"\u0001\u0000\u0000\u0000\u018a-\u0001\u0000\u0000\u0000\u018b\u018c\u0006"+
		"\u0017\uffff\uffff\u0000\u018c\u018d\u00030\u0018\u0000\u018d\u018e\u0006"+
		"\u0017\uffff\uffff\u0000\u018e\u01a4\u0001\u0000\u0000\u0000\u018f\u0190"+
		"\n\u0004\u0000\u0000\u0190\u0191\u0005\u0015\u0000\u0000\u0191\u0192\u0003"+
		"\u001c\u000e\u0000\u0192\u0193\u0005\u0016\u0000\u0000\u0193\u0194\u0006"+
		"\u0017\uffff\uffff\u0000\u0194\u01a3\u0001\u0000\u0000\u0000\u0195\u0196"+
		"\n\u0003\u0000\u0000\u0196\u0197\u0005\u0019\u0000\u0000\u0197\u01a3\u0006"+
		"\u0017\uffff\uffff\u0000\u0198\u0199\n\u0002\u0000\u0000\u0199\u019a\u0005"+
		"\u0004\u0000\u0000\u019a\u019b\u00052\u0000\u0000\u019b\u01a3\u0006\u0017"+
		"\uffff\uffff\u0000\u019c\u019d\n\u0001\u0000\u0000\u019d\u019e\u0005\u0013"+
		"\u0000\u0000\u019e\u019f\u0003\n\u0005\u0000\u019f\u01a0\u0005\u0014\u0000"+
		"\u0000\u01a0\u01a1\u0006\u0017\uffff\uffff\u0000\u01a1\u01a3\u0001\u0000"+
		"\u0000\u0000\u01a2\u018f\u0001\u0000\u0000\u0000\u01a2\u0195\u0001\u0000"+
		"\u0000\u0000\u01a2\u0198\u0001\u0000\u0000\u0000\u01a2\u019c\u0001\u0000"+
		"\u0000\u0000\u01a3\u01a6\u0001\u0000\u0000\u0000\u01a4\u01a2\u0001\u0000"+
		"\u0000\u0000\u01a4\u01a5\u0001\u0000\u0000\u0000\u01a5/\u0001\u0000\u0000"+
		"\u0000\u01a6\u01a4\u0001\u0000\u0000\u0000\u01a7\u01a8\u0005#\u0000\u0000"+
		"\u01a8\u01a9\u0003\u001c\u000e\u0000\u01a9\u01aa\u0005,\u0000\u0000\u01aa"+
		"\u01ab\u0003\u0006\u0003\u0000\u01ab\u01ac\u0005 \u0000\u0000\u01ac\u01ad"+
		"\u0006\u0018\uffff\uffff\u0000\u01ad\u01db\u0001\u0000\u0000\u0000\u01ae"+
		"\u01af\u0005#\u0000\u0000\u01af\u01b0\u0003\u001c\u000e\u0000\u01b0\u01b1"+
		"\u0005,\u0000\u0000\u01b1\u01b2\u0003\u0006\u0003\u0000\u01b2\u01b3\u0005"+
		"\u001f\u0000\u0000\u01b3\u01b4\u0003\u0006\u0003\u0000\u01b4\u01b5\u0005"+
		" \u0000\u0000\u01b5\u01b6\u0006\u0018\uffff\uffff\u0000\u01b6\u01db\u0001"+
		"\u0000\u0000\u0000\u01b7\u01b8\u00051\u0000\u0000\u01b8\u01b9\u0003\u001c"+
		"\u000e\u0000\u01b9\u01ba\u0005\u001d\u0000\u0000\u01ba\u01bb\u0003\u0006"+
		"\u0003\u0000\u01bb\u01bc\u0005 \u0000\u0000\u01bc\u01bd\u0006\u0018\uffff"+
		"\uffff\u0000\u01bd\u01db\u0001\u0000\u0000\u0000\u01be\u01bf\u0005&\u0000"+
		"\u0000\u01bf\u01c0\u0003\u0014\n\u0000\u01c0\u01c1\u0005$\u0000\u0000"+
		"\u01c1\u01c2\u0003\u0006\u0003\u0000\u01c2\u01c3\u0005 \u0000\u0000\u01c3"+
		"\u01c4\u0006\u0018\uffff\uffff\u0000\u01c4\u01db\u0001\u0000\u0000\u0000"+
		"\u01c5\u01c6\u0005\u0013\u0000\u0000\u01c6\u01c7\u0003\u0006\u0003\u0000"+
		"\u01c7\u01c8\u0005\u0014\u0000\u0000\u01c8\u01c9\u0006\u0018\uffff\uffff"+
		"\u0000\u01c9\u01db\u0001\u0000\u0000\u0000\u01ca\u01cb\u0005\u0001\u0000"+
		"\u0000\u01cb\u01db\u0006\u0018\uffff\uffff\u0000\u01cc\u01cd\u0005-\u0000"+
		"\u0000\u01cd\u01db\u0006\u0018\uffff\uffff\u0000\u01ce\u01cf\u0005!\u0000"+
		"\u0000\u01cf\u01db\u0006\u0018\uffff\uffff\u0000\u01d0\u01d1\u0005\u0002"+
		"\u0000\u0000\u01d1\u01db\u0006\u0018\uffff\uffff\u0000\u01d2\u01d3\u0005"+
		"\u0003\u0000\u0000\u01d3\u01db\u0006\u0018\uffff\uffff\u0000\u01d4\u01d5"+
		"\u0005(\u0000\u0000\u01d5\u01db\u0006\u0018\uffff\uffff\u0000\u01d6\u01d7"+
		"\u0005\'\u0000\u0000\u01d7\u01db\u0006\u0018\uffff\uffff\u0000\u01d8\u01d9"+
		"\u00052\u0000\u0000\u01d9\u01db\u0006\u0018\uffff\uffff\u0000\u01da\u01a7"+
		"\u0001\u0000\u0000\u0000\u01da\u01ae\u0001\u0000\u0000\u0000\u01da\u01b7"+
		"\u0001\u0000\u0000\u0000\u01da\u01be\u0001\u0000\u0000\u0000\u01da\u01c5"+
		"\u0001\u0000\u0000\u0000\u01da\u01ca\u0001\u0000\u0000\u0000\u01da\u01cc"+
		"\u0001\u0000\u0000\u0000\u01da\u01ce\u0001\u0000\u0000\u0000\u01da\u01d0"+
		"\u0001\u0000\u0000\u0000\u01da\u01d2\u0001\u0000\u0000\u0000\u01da\u01d4"+
		"\u0001\u0000\u0000\u0000\u01da\u01d6\u0001\u0000\u0000\u0000\u01da\u01d8"+
		"\u0001\u0000\u0000\u0000\u01db1\u0001\u0000\u0000\u0000\u001bBKU^in|\u0085"+
		"\u008f\u0098\u00a1\u00ad\u00d2\u00ff\u010c\u0116\u0120\u012b\u0148\u0154"+
		"\u0156\u0167\u0169\u0189\u01a2\u01a4\u01da";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}