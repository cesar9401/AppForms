package com.cesar31.formsweb.lexerandparser;

import static com.cesar31.formsweb.lexerandparser.FormsParserSym.*;
import java_cup.runtime.*;

%%

%class FormsLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column

%{

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}

%}

%eofval{
	return new java_cup.runtime.Symbol(FormsParserSym.EOF);
%eofval}
%eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

Integer =  0|[1-9][0-9]*
DateFormat = \d{4,4}\-\d{2,2}\-\d{2,2}
AlfaN = [0-9a-zA-Z]
Param = \w*{AlfaN}+\w*
Id = [_\-\$]\w*{AlfaN}+\w*

/* Tags solicitudes */
Ini = [Ii][Nn][Ii]
Sol = [Ss][Oo][Ll][Ii][Cc][Ii][Tt][Uu][Dd]
Fin = [Ff][Ii][Nn]
Res = [Rr][Ee][Ss][Pp][Uu][Ee][Ss][Tt][Aa]

Ini_sol = "<!" {Ini} "_" {Sol}
Fin_sol = "<" {Fin} "_" {Sol} "!>"
Ini_m_sol = "<!" {Ini} "_" {Sol}[Ed][Ss] ">"
Fin_m_sol = "<!" {Fin} "_" {Sol}[Ed][Ss] ">"

/* Para respuestas del servidor */
Ini_res = "<!" {Ini} "_" {Res}
Fin_res = "<!" {Fin} "_" {Res}
Ini_m_res = "<!" {Ini} "_" {Res}[Ss] ">"
Fin_m_res = "<!" {Fin} "_" {Res}[Ss] ">"

%%

<YYINITIAL> {

	{Ini_sol}
	{ return symbol(INIT_SOL, yytext()); }

	{Fin_sol}
	{ return symbol(FIN_SOL, yytext()); }

	{Ini_m_sol}
	{ return symbol(INIT_MANY_SOL, yytext()); }

	{Fin_m_sol}
	{ return symbol(FIN_MANY_SOL, yytext()); }

	\"
	{ return symbol(QUOTE, yytext()); }

	/* Solicitudes predefinidas */
	"CREAR_USUARIO"
	{ return symbol(NEW_USER, yytext()); }

	"CREDENCIALES_USUARIO"
	{ return symbol(CRED, yytext()); }

	"USUARIO"
	{ return symbol(USER, yytext()); }

	"PASSWORD"
	{ return symbol(PASS, yytext()); }

	"FECHA_CREACION"
	{ return symbol(DATE, yytext()); }

	"MODIFICAR_USUARIO"
	{ return symbol(EDIT_USER, yytext()); }

	"ELIMINAR_USUARIO"
	{ return symbol(DEL_USER, yytext()); }

	"LOGIN_USUARIO"
	{ return symbol(LOGIN, yytext()); }

	{Param}
	{ return symbol(PARAMQ, yytext()); }

	{Id}
	{ return symbol(ID, yytext()); }

	":"
	{ return symbol(COLON, yytext()); }

	">"
	{ return symbol(GREATER, yytext()); }

	"{"
	{ return symbol(LBRACE, yytext()); }

	"}"
	{ return symbol(RBRACE, yytext()); }

	"["
	{ return symbol(LBRACKET, yytext()); }

	"]"
	{ return symbol(RBRACKET, yytext()); }

	","
	{ return symbol(COMMA, yytext()); }

	{WhiteSpace}
	{ /* Ignore */ }

}

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
