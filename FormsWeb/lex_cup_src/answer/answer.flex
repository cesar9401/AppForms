package com.cesar31.formsweb.parser.answer;

import static com.cesar31.formsweb.parser.answer.AnswerParserSym.*;
import java_cup.runtime.*;

%%

%class AnswerLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
// %cupdebug

%{
	StringBuffer string = new StringBuffer();

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}

	private Symbol setType(int type, Object value) {
		String s = value.toString();

		switch(s) {
			case "idForm":
				return symbol(ID, s);
			case "nameForm":
				return symbol(NAME, s);
			case "data":
				return symbol(DATA, s);
			default:
			return symbol(STR, s);
		}
	}
%}

%eofval{
	return new java_cup.runtime.Symbol(AnswerParserSym.EOF);
%eofval}
%eofclose

/* Integer */
Integer = 0 | [1-9][0-9]*

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

%state STRING

%%

<YYINITIAL> {

	"DATOS_RECOPILADOS"
	{ return symbol(COLLECTED); }

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
	{ string.append(yytext()); }

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