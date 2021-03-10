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
//Id = "\"" [_\-\$]\w*{AlfaN}+\w* "\""
//ParamQ = "\"" {Param} "\""

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
	{ return symbol(INIT_SOL); }

	{Fin_sol}
	{ return symbol(FIN_SOL); }

	{Ini_m_sol}
	{ return symbol(INIT_MANY_SOL); }

	{Fin_m_sol}
	{ return symbol(FIN_MANY_SOL); }

	"\""
	{ return symbol(QUOTE); }

  /* Solicitudes predefinidas */
	"CREAR_USUARIO"
	{ return symbol(NEW_USER); }

	"CREDENCIALES_USUARIO"
	{ return symbol(CRED); }

	"USUARIO"
	{ return symbol(USER); }

	"PASSWORD"
	{ return symbol(PASS); }

	"FECHA_CREACION"
	{ return symbol(DATE); }

	"MODIFICAR_USUARIO"
	{ return symbol(EDIT_USER); }

	"ELIMINAR_USUARIO"
	{ return symbol(DEL_USER); }

	"LOGIN_USUARIO"
	{ return symbol(LOGIN); }

	{Param}
	{ return symbol(PARAMQ, yytext()); }

	":"
	{ return symbol(COLON); }

	">"
	{ return symbol(GREATER); }

	"{"
	{ return symbol(LBRACE); }

	"}"
	{ return symbol(RBRACE); }

	"["
	{ return symbol(LBRACKET); }

	"]"
	{ return symbol(RBRACKET); }

	","
	{ return symbol(COMMA); }

	{WhiteSpace}
	{ /* Ignore */ }

}

[^]     { /* Ignore */ }
