package com.cesar31.formsweb.parser.sqf;

import static com.cesar31.formsweb.parser.sqf.SQFParserSym.*;
import com.cesar31.formsweb.model.Token;

import java_cup.runtime.*;

%%

%class SQFLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
// %cupdebug

%{

	private Symbol symbol(int type) {
		return new Symbol(type, new Token(type, yyline + 1, yycolumn + 1));
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, new Token(type, (String) value, yyline + 1, yycolumn + 1));
	}

%}

%eofval{
	return symbol(EOF);
%eofval}
%eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

/* Entrada sin espacios, saltos de linea, tabulaciones, comillas y \ */
Input = [^\n\r\"\\]+

/* Nombre y se usa en la expresion regular para id */
//InputClean = [\w\-]+
InputClean = [^\n\r\"\\\t\s\f><\{\}\[\]=\*\+\,\|\?\'\’]+

Id = [\_\-\$] ([\_\-\$] | {InputClean} )+

Value_str = \’ {Input} \’

Integer =  0|[1-9][0-9]*
Decimal = {Integer} \. [0-9]+

%%

<YYINITIAL> {

	"SELECT"
	{ return symbol(SEL, yytext()); }

	"TO"
	{ return symbol(TO, yytext()); }

	"FORM"
	{ return symbol(FORM, yytext()); }

	"->"
	{ return symbol(ARROW, yytext()); }

	"WHERE"
	{ return symbol(WHERE, yytext()); }

	"["
	{ return symbol(LBRACKET, yytext()); }

	"]"
	{ return symbol(RBRACKET, yytext()); }

	"AND"
	{ return symbol(AND, yytext()); }

	"OR"
	{ return symbol(OR, yytext()); }

	"NOT"
	{ return symbol(NOT, yytext()); }

	">"
	{ return symbol(GREATER, yytext()); }

	"<"
	{ return symbol(SMALLER, yytext()); }

	">="
	{ return symbol(GR_EQ, yytext()); }

	"<="
	{ return symbol(SM_EQ, yytext()); }

	"="
	{ return symbol(EQ, yytext()); }

	"<>"
	{ return symbol(NEQ, yytext()); }

	","
	{ return symbol(COMMA, yytext()); }

	"+"
	{ return symbol(PLUS, yytext()); }

	"-"
	{ return symbol(MINUS, yytext()); }

	"*"
	{ return symbol(TIMES, yytext()); }

	"/"
	{ return symbol(DIVIDE, yytext()); }

	// \’
	// { return symbol(APOS, yytext()); }

	{Integer}
	{ return symbol(INTEGER, yytext()); }

	{Decimal}
	{ return symbol(DECIMAL, yytext()); }

	{Id}
	{ return symbol(ID, yytext()); }

	{Value_str}
	{ return symbol(VALUE_STR, yytext()); }

	/* Se usa para nombre del formulario*/
	{InputClean}
	{ return symbol(STR, yytext()); }

	{WhiteSpace}
	{ /* Ignore */ }

}

[^]
{
	System.out.println("Error: < " + yytext() + " >");
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}