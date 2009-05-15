/* Generated By:JavaCC: Do not edit this line. TermParser.java */
/* GNU Prolog for Java
 * Copyright (C) 1997-1999  Constantine Plotnikov
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA. The text ol license can be also found 
 * at http://www.gnu.org/copyleft/lgpl.html
 */
package gnu.prolog.io.parser.gen;

import gnu.prolog.io.Operator;
import gnu.prolog.io.ReadOptions;
import gnu.prolog.io.parser.NameToken;
import gnu.prolog.io.parser.ReaderCharStream;
import gnu.prolog.io.parser.TermParserUtils;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.CompoundTermTag;
import gnu.prolog.term.FloatTerm;
import gnu.prolog.term.IntegerTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.TermConstants;

import java.util.ArrayList;

public final class TermParser implements TermParserConstants
{
	CharStream stream;

	public int getCurrentLine()
	{
		return stream.getEndLine();
	}

	public int getCurrentColumn()
	{
		return stream.getEndColumn();
	}

	public boolean isFunctor()
	{
		return getToken(1).kind == TermParserConstants.NAME_TOKEN && getToken(2).kind == TermParserConstants.OPEN_TOKEN
				&& getToken(2).specialToken == null;
	}

	boolean testOp(ReadOptions options, int i)
	{
		Token tk = getToken(i);
		return tk instanceof NameToken && ((NameToken) tk).isOperator(options.operatorSet);
	}

	boolean testNoOp(ReadOptions options)
	{
		Token tk = getToken(1);
		return tk instanceof NameToken && ((NameToken) tk).isNonOperator(options.operatorSet);
	}

	boolean testFX(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return !isFunctor() && tk instanceof NameToken && ((NameToken) tk).isFxOperator(options.operatorSet, priority);
	}

	boolean testFY(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return !isFunctor() && tk instanceof NameToken && ((NameToken) tk).isFyOperator(options.operatorSet, priority);
	}

	boolean testXFX(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return tk instanceof NameToken && ((NameToken) tk).isXfxOperator(options.operatorSet, priority);
	}

	boolean testXFY(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return priority >= options.operatorSet.getCommaLevel() && tk.kind == COMMA_TOKEN || tk instanceof NameToken
				&& ((NameToken) tk).isXfyOperator(options.operatorSet, priority);
	}

	boolean testYFX(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return tk instanceof NameToken && ((NameToken) tk).isYfxOperator(options.operatorSet, priority);
	}

	boolean testXF(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return tk instanceof NameToken && ((NameToken) tk).isXfOperator(options.operatorSet, priority);
	}

	boolean testYF(ReadOptions options, int priority)
	{
		Token tk = getToken(1);
		return tk instanceof NameToken && ((NameToken) tk).isYfOperator(options.operatorSet, priority);
	}

	boolean isExpSeparator(int i)
	{
		Token tk = getToken(i);
		switch (tk.kind)
		{
			case COMMA_TOKEN:
			case CLOSE_TOKEN:
			case CLOSE_CURLY_TOKEN:
			case CLOSE_LIST_TOKEN:
			case END_TOKEN:
			case TermParserUtils.EOF_TOKEN:
			case HEAD_TAIL_SEPARATOR_TOKEN:
				return true;
			default:
				return false;
		}
	}

	boolean is1201Separator(int i)
	{
		Token tk = getToken(i);
		switch (tk.kind)
		{
			case CLOSE_TOKEN:
			case CLOSE_CURLY_TOKEN:
			case END_TOKEN:
			case TermParserUtils.EOF_TOKEN:
				return true;
			default:
				return false;
		}
	}

	Term createTerm(CompoundTermTag op, Term t)
	{
		if (op.arity != 1)
		{
			throw new IllegalArgumentException("Arity of term tag must be 1");
		}
		return new CompoundTerm(op, new Term[] { t });
	}

	Term createTerm(CompoundTermTag op, Term t1, Term t2)
	{
		if (op.arity != 2)
		{
			throw new IllegalArgumentException("Arity of term tag must be 2");
		}
		return new CompoundTerm(op, new Term[] { t1, t2 });
	}

	public TermParser(java.io.Reader r, int line, int col)
	{
		this(new ReaderCharStream(r, line, col), null);
	}

	public TermParser(ReaderCharStream str, Object obj)
	{
		this(str);
		stream = str;
	}

	final public Term readTerm(ReadOptions options) throws ParseException
	{
		Term t;
		try
		{
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case 0:
					jj_consume_token(0);
					{
						if (true) return null;
					}
					break;
				case END_TOKEN:
					jj_consume_token(END_TOKEN);
					{
						if (true) return null;
					}
					break;
				default:
					jj_la1[0] = jj_gen;
					t = term(options, options.operatorSet.getMaxLevel());
					end();
					{
						if (true) return t;
					}
			}
		}
		catch (ParseException ex)
		{
			skipToDot();
			{
				if (true) throw ex;
			}
		}
		throw new Error("Missing return statement in function");
	}

	void skipToDot() throws ParseException
	{
		Token tok = getToken(0);
		while (tok.kind != END_TOKEN && tok.kind != EOF)
		{
			tok = getNextToken();
		}
	}

	final public Term readTermEof(ReadOptions options) throws ParseException
	{
		Term t;
		try
		{
			t = term(options, options.operatorSet.getMaxLevel());
			jj_consume_token(0);
			{
				if (true) return t;
			}
		}
		catch (ParseException ex)
		{
			skipToEof();
			{
				if (true) throw ex;
			}
		}
		throw new Error("Missing return statement in function");
	}

	void skipToEof() throws ParseException
	{
		Token tok = getToken(0);
		while (tok.kind != TermParserUtils.EOF_TOKEN)
		{
			tok = getNextToken();
		}
	}

	final public Term term(ReadOptions options, int priority) throws ParseException
	{
		int p = options.operatorSet.getNextLevel(priority);
		Term t;
		if (p == 0)
		{
			t = simpleTerm(options);
		}
		else if (jj_2_1(2147483647) && (priority == 1201 && is1201Separator(2)))
		{
			t = name();
		}
		else
		{
			t = operatorTerm(options, p);
		}
		{
			if (true) return t;
		}
		throw new Error("Missing return statement in function");
	}

	Term exp(ReadOptions options) throws ParseException
	{
		Term t;
		if (testOp(options, 1) && isExpSeparator(2))
		{
			t = name();
		}
		else
		{
			t = term(options, options.operatorSet.getCommaLevel() - 1);
		}
		return t;
	}

	/*
	 * JAVACODE Term exp(ReadOptions options): { Term t; } { (
	 * //LOOKAHEAD(<NAME_TOKEN>,{getToken(1).kind == NAME_TOKEN &&
	 * !isTermStart(2)}) LOOKAHEAD({testOp(options, 1) && isExpSeparator(2)})
	 * //LOOKAHEAD
	 * (<NAME_TOKEN>,(<CLOSE_TOKEN>|<CLOSE_CURLY_TOKEN>|<CLOSE_LIST_TOKEN
	 * >|<END_TOKEN>|<EOF_TOKEN>|<HEAD_TAIL_SEPARATOR_TOKEN>|<COMMA_TOKEN>)) t =
	 * name() | t = term(options, options.operatorSet.getCommaLevel()-1) ) {return
	 * t;} }
	 */
	// prefix operator
	final public CompoundTermTag op(ReadOptions options, int priority) throws ParseException
	{
		CompoundTermTag f;
		Term t;
		jj_consume_token(NAME_TOKEN);
		NameToken tk = (NameToken) token;
		{
			if (true) return tk.fxOp.tag;
		}
		throw new Error("Missing return statement in function");
	}

	// infix or postfix
	final public CompoundTermTag op2(ReadOptions options, int priority) throws ParseException
	{
		CompoundTermTag f;
		Term t;
		if (jj_2_2(2147483647) && (priority >= 1000))
		{
			comma();
			f = CompoundTermTag.comma;
			{
				if (true) return f;
			}
		}
		else
		{
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk)
			{
				case NAME_TOKEN:
					jj_consume_token(NAME_TOKEN);
					NameToken tk = (NameToken) token;
					{
						if (true) return tk.xfOp.tag;
					}
					break;
				default:
					jj_la1[1] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
			}
		}
		throw new Error("Missing return statement in function");
	}

	Term operatorTerm(ReadOptions options, int priority) throws ParseException
	{
		int p = priority;
		Term t1, t2;
		CompoundTermTag f;
		if (testFY(options, p)) // fy term
		{
			f = op(options, p);
			t1 = term(options, p);
			t1 = createTerm(f, t1);
		}
		else if (testFX(options, p)) // fx term
		{
			f = op(options, p);
			t1 = term(options, p - 1);
			t1 = createTerm(f, t1);
		}
		else
		// infix or postfix term
		{
			t1 = term(options, p - 1);
			if (testXFX(options, p))
			{
				f = op2(options, p);
				t2 = term(options, p - 1);
				t1 = createTerm(f, t1, t2);
			}
			else if (testXF(options, p))
			{
				f = op2(options, p);
				t1 = createTerm(f, t1);
			}
			else if (testXFY(options, p))
			{
				f = op2(options, p);
				t2 = term(options, p);
				t1 = createTerm(f, t1, t2);
			}
			else
			{
				while (true)
				{
					Token tk = getToken(1);
					if ((tk.kind == INTEGER_TOKEN || tk.kind == FLOAT_NUMBER_TOKEN) && tk.image.charAt(0) == '-')
					{
						if (options.operatorSet.lookupXf("-") == Operator.nonOperator)
						{
							break;
						}
						if (tk.kind == INTEGER_TOKEN)
						{
							jj_consume_token(INTEGER_TOKEN);
							t1 = createTerm(CompoundTermTag.minus2, t1, IntegerTerm.get(-(IntegerTerm.get(tk.image).value)));
						}
						else
						{
							jj_consume_token(FLOAT_NUMBER_TOKEN);
							t1 = createTerm(CompoundTermTag.minus2, t1, new FloatTerm(-(new FloatTerm(tk.image).value)));
						}
					}
					else if (testYFX(options, p))
					{
						f = op2(options, p);
						t2 = term(options, p - 1);
						t1 = createTerm(f, t1, t2);
					}
					else if (testYF(options, p))
					{
						f = op2(options, p);
						t1 = createTerm(f, t1);
					}
					else
					{
						break;
					}
				}
			}
		}
		return t1;
	}

	// 6.3.1.3 Atoms
	final public Term simpleTerm(ReadOptions options) throws ParseException
	{
		Term t;
		if (jj_2_3(2147483647) && (getToken(2).kind == TermParserConstants.OPEN_TOKEN && getToken(2).specialToken == null))
		{
			t = compound(options);
		}
		else if (testNoOp(options))
		{
			t = name();
		}
		else if (jj_2_4(2147483647))
		{
			t = variable(options);
		}
		else if (jj_2_5(2147483647))
		{
			t = integer();
		}
		else if (jj_2_6(2147483647))
		{
			t = float_number();
		}
		else if (jj_2_7(2147483647))
		{
			t = list_term(options);
		}
		else if (jj_2_8(2147483647))
		{
			t = curly_term(options);
		}
		else if (jj_2_9(2147483647))
		{
			t = char_code_list();
		}
		else if (jj_2_10(2147483647))
		{
			open();
			t = term(options, 1201);
			close();
		}
		else
		{
			jj_consume_token(-1);
			throw new ParseException();
		}
		{
			if (true) return t;
		}
		throw new Error("Missing return statement in function");
	}

	final public Term list_term(ReadOptions options) throws ParseException
	{
		Term rc = null;
		Term t1 = null;
		Term t2;
		open_list();
		if (jj_2_13(2147483647))
		{
			rc = null;
		}
		else
		{
			t1 = exp(options);
			t1 = createTerm(TermConstants.listTag, t1, null);
			rc = t1;
			label_1: while (true)
			{
				if (jj_2_11(2147483647))
				{
					;
				}
				else
				{
					break label_1;
				}
				comma();
				t2 = exp(options);
				t2 = createTerm(TermConstants.listTag, t2, null);
				((CompoundTerm) t1).args[1] = t2;
				t1 = t2;
			}
			if (jj_2_12(2147483647))
			{
				ht_sep();
				t2 = exp(options);
				((CompoundTerm) t1).args[1] = t2;
			}
			else
			{
				;
			}
		}
		close_list();
		if (rc == null)
		{
			{
				if (true) return TermConstants.emptyListAtom;
			}
		}
		if (((CompoundTerm) t1).args[1] == null)
		{
			((CompoundTerm) t1).args[1] = TermConstants.emptyListAtom;
		}
		{
			if (true) return rc;
		}
		throw new Error("Missing return statement in function");
	}

	final public Term curly_term(ReadOptions options) throws ParseException
	{
		Term t = null;
		open_curly();
		t = term(options, 1201);
		close_curly();
		if (t == null)
		{
			{
				if (true) return TermConstants.emptyCurlyAtom;
			}
		}
		else
		{
			{
				if (true) return createTerm(CompoundTermTag.curly1, t);
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public CompoundTerm compound(ReadOptions options) throws ParseException
	{
		AtomTerm functor;
		ArrayList args = new ArrayList();
		Term el;
		CompoundTerm rc;
		functor = name();
		open_ct();
		el = exp(options);
		args.add(el);
		label_2: while (true)
		{
			if (jj_2_14(2147483647))
			{
				;
			}
			else
			{
				break label_2;
			}
			comma();
			el = exp(options);
			args.add(el);
		}
		close();
		int n = args.size();
		rc = new CompoundTerm(functor, n);
		for (int i = 0; i < n; i++)
		{
			rc.args[i] = (Term) args.get(i);
		}
		{
			if (true) return rc;
		}
		throw new Error("Missing return statement in function");
	}

	// 6.4 Tokens
	// ~~~~~~~~~~
	final public AtomTerm name() throws ParseException
	{
		jj_consume_token(NAME_TOKEN);
		{
			if (true) return AtomTerm.get(((NameToken) token).getValue());
		}
		throw new Error("Missing return statement in function");
	}

	final public VariableTerm variable(ReadOptions options) throws ParseException
	{
		jj_consume_token(VARIABLE_TOKEN);
		VariableTerm var = (VariableTerm) options.variableNames.get(token.image);
		if (var == null)
		{
			var = new VariableTerm();
			if (!"_".equals(token.image))
			{
				options.variableNames.put(token.image, var);
				options.singletons.put(token.image, var);
			}
			options.variables.add(var);
		}
		else
		{
			options.singletons.remove(token.image);
		}
		{
			if (true) return var;
		}
		throw new Error("Missing return statement in function");
	}

	final public IntegerTerm integer() throws ParseException
	{
		jj_consume_token(INTEGER_TOKEN);
		{
			if (true) return IntegerTerm.get(token.image);
		}
		throw new Error("Missing return statement in function");
	}

	final public FloatTerm float_number() throws ParseException
	{
		jj_consume_token(FLOAT_NUMBER_TOKEN);
		{
			if (true) return new FloatTerm(token.image);
		}
		throw new Error("Missing return statement in function");
	}

	final public Term char_code_list() throws ParseException
	{
		jj_consume_token(CHAR_CODE_LIST_TOKEN);
		String val = TermParserUtils.convertQuotedString(token.image, '\"');
		int i, n = val.length();
		Term rc = TermConstants.emptyListAtom;
		for (i = n - 1; i >= 0; i--)
		{
			rc = CompoundTerm.getList(IntegerTerm.get(val.charAt(i)), rc);
		}
		{
			if (true) return rc;
		}
		throw new Error("Missing return statement in function");
	}

	final public void open() throws ParseException
	{
		jj_consume_token(OPEN_TOKEN);
	}

	final public void open_ct() throws ParseException
	{
		if (getToken(1).specialToken == null)
		{

		}
		else
		{
			jj_consume_token(-1);
			throw new ParseException();
		}
		jj_consume_token(OPEN_TOKEN);
	}

	final public void close() throws ParseException
	{
		jj_consume_token(CLOSE_TOKEN);
	}

	final public void open_list() throws ParseException
	{
		jj_consume_token(OPEN_LIST_TOKEN);
	}

	final public void close_list() throws ParseException
	{
		jj_consume_token(CLOSE_LIST_TOKEN);
	}

	final public void open_curly() throws ParseException
	{
		jj_consume_token(OPEN_CURLY_TOKEN);
	}

	final public void close_curly() throws ParseException
	{
		jj_consume_token(CLOSE_CURLY_TOKEN);
	}

	final public void ht_sep() throws ParseException
	{
		jj_consume_token(HEAD_TAIL_SEPARATOR_TOKEN);
	}

	final public void comma() throws ParseException
	{
		jj_consume_token(COMMA_TOKEN);
	}

	final public void end() throws ParseException
	{
		jj_consume_token(END_TOKEN);
	}

	final private boolean jj_2_1(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_1();
		jj_save(0, xla);
		return retval;
	}

	final private boolean jj_2_2(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_2();
		jj_save(1, xla);
		return retval;
	}

	final private boolean jj_2_3(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_3();
		jj_save(2, xla);
		return retval;
	}

	final private boolean jj_2_4(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_4();
		jj_save(3, xla);
		return retval;
	}

	final private boolean jj_2_5(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_5();
		jj_save(4, xla);
		return retval;
	}

	final private boolean jj_2_6(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_6();
		jj_save(5, xla);
		return retval;
	}

	final private boolean jj_2_7(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_7();
		jj_save(6, xla);
		return retval;
	}

	final private boolean jj_2_8(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_8();
		jj_save(7, xla);
		return retval;
	}

	final private boolean jj_2_9(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_9();
		jj_save(8, xla);
		return retval;
	}

	final private boolean jj_2_10(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_10();
		jj_save(9, xla);
		return retval;
	}

	final private boolean jj_2_11(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_11();
		jj_save(10, xla);
		return retval;
	}

	final private boolean jj_2_12(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_12();
		jj_save(11, xla);
		return retval;
	}

	final private boolean jj_2_13(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_13();
		jj_save(12, xla);
		return retval;
	}

	final private boolean jj_2_14(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean retval = !jj_3_14();
		jj_save(13, xla);
		return retval;
	}

	final private boolean jj_3_12()
	{
		if (jj_scan_token(HEAD_TAIL_SEPARATOR_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_11()
	{
		if (jj_scan_token(COMMA_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_13()
	{
		if (jj_scan_token(CLOSE_LIST_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_1()
	{
		if (jj_scan_token(NAME_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_10()
	{
		if (jj_scan_token(OPEN_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_9()
	{
		if (jj_scan_token(CHAR_CODE_LIST_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_8()
	{
		if (jj_scan_token(OPEN_CURLY_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_14()
	{
		if (jj_scan_token(COMMA_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_7()
	{
		if (jj_scan_token(OPEN_LIST_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_6()
	{
		if (jj_scan_token(FLOAT_NUMBER_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_5()
	{
		if (jj_scan_token(INTEGER_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_4()
	{
		if (jj_scan_token(VARIABLE_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_3()
	{
		if (jj_scan_token(NAME_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	final private boolean jj_3_2()
	{
		if (jj_scan_token(COMMA_TOKEN)) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
		return false;
	}

	public TermParserTokenManager token_source;
	public Token token, jj_nt;
	private int jj_ntk;
	private Token jj_scanpos, jj_lastpos;
	private int jj_la;
	public boolean lookingAhead = false;
	private boolean jj_semLA;
	private int jj_gen;
	final private int[] jj_la1 = new int[2];
	final private int[] jj_la1_0 = { 0x1, 0x40, };
	final private int[] jj_la1_1 = { 0x2000000, 0x0, };
	final private int[] jj_la1_2 = { 0x0, 0x0, };
	final private JJTermParserCalls[] jj_2_rtns = new JJTermParserCalls[14];
	private boolean jj_rescan = false;
	private int jj_gc = 0;

	public TermParser(CharStream stream)
	{
		token_source = new TermParserTokenManager(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 2; i++)
			jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJTermParserCalls();
	}

	public void ReInit(CharStream stream)
	{
		token_source.ReInit(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 2; i++)
			jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJTermParserCalls();
	}

	public TermParser(TermParserTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 2; i++)
			jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJTermParserCalls();
	}

	public void ReInit(TermParserTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 2; i++)
			jj_la1[i] = -1;
		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJTermParserCalls();
	}

	final private Token jj_consume_token(int kind) throws ParseException
	{
		Token oldToken;
		if ((oldToken = token).next != null) token = token.next;
		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind)
		{
			jj_gen++;
			if (++jj_gc > 100)
			{
				jj_gc = 0;
				for (int i = 0; i < jj_2_rtns.length; i++)
				{
					JJTermParserCalls c = jj_2_rtns[i];
					while (c != null)
					{
						if (c.gen < jj_gen) c.first = null;
						c = c.next;
					}
				}
			}
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	final private boolean jj_scan_token(int kind)
	{
		if (jj_scanpos == jj_lastpos)
		{
			jj_la--;
			if (jj_scanpos.next == null)
			{
				jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
			}
			else
			{
				jj_lastpos = jj_scanpos = jj_scanpos.next;
			}
		}
		else
		{
			jj_scanpos = jj_scanpos.next;
		}
		if (jj_rescan)
		{
			int i = 0;
			Token tok = token;
			while (tok != null && tok != jj_scanpos)
			{
				i++;
				tok = tok.next;
			}
			if (tok != null) jj_add_error_token(kind, i);
		}
		return (jj_scanpos.kind != kind);
	}

	final public Token getNextToken()
	{
		if (token.next != null) token = token.next;
		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	final public Token getToken(int index)
	{
		Token t = lookingAhead ? jj_scanpos : token;
		for (int i = 0; i < index; i++)
		{
			if (t.next != null) t = t.next;
			else t = t.next = token_source.getNextToken();
		}
		return t;
	}

	final private int jj_ntk()
	{
		if ((jj_nt = token.next) == null) return (jj_ntk = (token.next = token_source.getNextToken()).kind);
		else return (jj_ntk = jj_nt.kind);
	}

	private java.util.Vector jj_expentries = new java.util.Vector();
	private int[] jj_expentry;
	private int jj_kind = -1;
	private int[] jj_lasttokens = new int[100];
	private int jj_endpos;

	private void jj_add_error_token(int kind, int pos)
	{
		if (pos >= 100) return;
		if (pos == jj_endpos + 1)
		{
			jj_lasttokens[jj_endpos++] = kind;
		}
		else if (jj_endpos != 0)
		{
			jj_expentry = new int[jj_endpos];
			for (int i = 0; i < jj_endpos; i++)
			{
				jj_expentry[i] = jj_lasttokens[i];
			}
			boolean exists = false;
			for (java.util.Enumeration enum_ = jj_expentries.elements(); enum_.hasMoreElements();)
			{
				int[] oldentry = (int[]) (enum_.nextElement());
				if (oldentry.length == jj_expentry.length)
				{
					exists = true;
					for (int i = 0; i < jj_expentry.length; i++)
					{
						if (oldentry[i] != jj_expentry[i])
						{
							exists = false;
							break;
						}
					}
					if (exists) break;
				}
			}
			if (!exists) jj_expentries.addElement(jj_expentry);
			if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
		}
	}

	final public ParseException generateParseException()
	{
		jj_expentries.removeAllElements();
		boolean[] la1tokens = new boolean[92];
		for (int i = 0; i < 92; i++)
		{
			la1tokens[i] = false;
		}
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 2; i++)
		{
			if (jj_la1[i] == jj_gen)
			{
				for (int j = 0; j < 32; j++)
				{
					if ((jj_la1_0[i] & (1 << j)) != 0)
					{
						la1tokens[j] = true;
					}
					if ((jj_la1_1[i] & (1 << j)) != 0)
					{
						la1tokens[32 + j] = true;
					}
					if ((jj_la1_2[i] & (1 << j)) != 0)
					{
						la1tokens[64 + j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 92; i++)
		{
			if (la1tokens[i])
			{
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.addElement(jj_expentry);
			}
		}
		jj_endpos = 0;
		jj_rescan_token();
		jj_add_error_token(0, 0);
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++)
		{
			exptokseq[i] = (int[]) jj_expentries.elementAt(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	final public void enable_tracing()
	{}

	final public void disable_tracing()
	{}

	final private void jj_rescan_token()
	{
		jj_rescan = true;
		for (int i = 0; i < 14; i++)
		{
			JJTermParserCalls p = jj_2_rtns[i];
			do
			{
				if (p.gen > jj_gen)
				{
					jj_la = p.arg;
					jj_lastpos = jj_scanpos = p.first;
					switch (i)
					{
						case 0:
							jj_3_1();
							break;
						case 1:
							jj_3_2();
							break;
						case 2:
							jj_3_3();
							break;
						case 3:
							jj_3_4();
							break;
						case 4:
							jj_3_5();
							break;
						case 5:
							jj_3_6();
							break;
						case 6:
							jj_3_7();
							break;
						case 7:
							jj_3_8();
							break;
						case 8:
							jj_3_9();
							break;
						case 9:
							jj_3_10();
							break;
						case 10:
							jj_3_11();
							break;
						case 11:
							jj_3_12();
							break;
						case 12:
							jj_3_13();
							break;
						case 13:
							jj_3_14();
							break;
					}
				}
				p = p.next;
			} while (p != null);
		}
		jj_rescan = false;
	}

	final private void jj_save(int index, int xla)
	{
		JJTermParserCalls p = jj_2_rtns[index];
		while (p.gen > jj_gen)
		{
			if (p.next == null)
			{
				p = p.next = new JJTermParserCalls();
				break;
			}
			p = p.next;
		}
		p.gen = jj_gen + xla - jj_la;
		p.first = token;
		p.arg = xla;
	}

}

final class JJTermParserCalls
{
	int gen;
	Token first;
	int arg;
	JJTermParserCalls next;
}
