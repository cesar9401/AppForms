package com.cesar31.formsclient.parser;

import static com.cesar31.formsclient.parser.ResponseParserSym.*;
import java_cup.runtime.*;

%%

%class ResponseLex
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
			case "request":
				return symbol(REQ, s);
			case "line":
				return symbol(LINE, s);
			case "column":
				return symbol(COLUMN, s);
			case "type":
				return symbol(TYPE, s);
			case "message":
				return symbol(MESSAGE, s);
			case "lexema":
				return symbol(LEXEMA, s);
			case "description":
				return symbol(DESC, s);

			default:
				return symbol(type, s);
		}
	}

%}

%eofval{
	return new java_cup.runtime.Symbol(ResponseParserSym.EOF);
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

	"ini_respuesta"
	{ return symbol(INI_R); }

	"fin_respuesta"
	{ return symbol(FIN_R); }

	"ini_respuestas"
	{ return symbol(INI_RS); }

	"fin_respuestas"
	{ return symbol(FIN_RS); }

	"null"
	{ return symbol(NULL, String.valueOf(yytext())); }

	{Integer}
	{ return symbol(INTEGER, Integer.valueOf(yytext())); }

	"<"
	{ return symbol(SMALLER); }

	"!"
	{ return symbol(EXCL); }

	">"
	{ return symbol(GREATER); }

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