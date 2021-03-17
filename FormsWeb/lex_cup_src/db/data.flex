package com.cesar31.formsweb.parser.db;

import static com.cesar31.formsweb.parser.db.DataParserSym.*;
import java_cup.runtime.*;

%%

%class DataLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
// %cupdebug

%{
	StringBuffer string = new StringBuffer();

	private Symbol setType(int type, Object value) {
		String s = value.toString();

		if(s.equals("user"))
			return symbol(USER, s);
		if(s.equals("password"))
			return symbol(PASS, s);
		if(s.equals("cDate"))
			return symbol(C_DATE, s);
		if(s.equals("eDate"))
			return symbol(E_DATE, s);

		return symbol(STR, value.toString());
	}

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}

%}

%eofval{
	return new java_cup.runtime.Symbol(DataParserSym.EOF);
%eofval}
%eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]
Null = "null"

%state STRING

%%

<YYINITIAL> {

	"["
	{ return symbol(LBRACKET); }

	"]"
	{ return symbol(RBRACKET); }

	"{"
	{ return symbol(LBRACE); }

	"}"
	{ return symbol(RBRACE); }

	":"
	{ return symbol(COLON); }

	","
	{ return symbol(COMMA); }

	{Null}
	{ return symbol(NULL, new String(yytext())); }

	\"
	{
		string.setLength(0);
		yybegin(STRING);
	}

	{WhiteSpace}
	{ /* Ignore */ }
}

<STRING> {

    \"
	{
		yybegin(YYINITIAL);
		return setType(STR, string.toString());
	}

    [^\n\r\"\\]+
	{ string.append( yytext() ); }

	\\t
	{ string.append('\t'); }

	\\n
	{ string.append('\n'); }

	\\r
	{ string.append('\r'); }

	\\\"
	{ string.append('\"'); }

	\\
	{ string.append('\\'); }

}

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}