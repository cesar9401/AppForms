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

		switch(s) {
			case "user":
				return symbol(USER, s);
			case "password":
				return symbol(PASS, s);
			case "cDate_user":
				return symbol(DATE_USER, s);
			case "eDate":
				return symbol(E_DATE, s);
			case "id_form":
				return symbol(ID_FORM, s);
			case "title":
				return symbol(TITLE, s);
			case "name":
				return symbol(NAME, s);
			case "theme":
				return symbol(THEME, s);
			case "user_creation":
				return symbol(USER_C, s);
			case "cDate_form":
				return symbol(DATE_FORM, s);
			case "components":
				return symbol(COMP, s);
			case "id_component":
				return symbol(ID_COMP, s);
			case "fieldName":
				return symbol(FIELD_N, s);
			case "form_id":
				return symbol(FORM_ID, s);
			case "kind":
				return symbol(TIPO, s);
			case "index":
				return symbol(INDEX, s);
			case "text":
				return symbol(TEXT, s);
			case "aling":
				return symbol(ALING, s);
			case "required":
				return symbol(REQUIRED, s);
			case "url":
				return symbol(URL, s);
			case "options":
				return symbol(OPT, s);
			case "rows":
				return symbol(ROWS, s);
			case "columns":
				return symbol(COLUMNS, s);

			default:
				return symbol(STR, s);
		}
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

/* Integer */
Integer = 0 | [1-9][0-9]*

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

%state STRING

%%

<YYINITIAL> {

	"USERS"
	{ return symbol(USERS); }

	"FORMS"
	{ return symbol(FORMS); }

	"null"
	{ return symbol(NULL, String.valueOf(yytext())); }

	"true"
	{ return symbol(TRUE, Boolean.valueOf(yytext())); }

	"false"
	{ return symbol(FALSE, Boolean.valueOf(yytext())); }

	{Integer}
	{ return symbol(INTEGER, Integer.valueOf(yytext())); }

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