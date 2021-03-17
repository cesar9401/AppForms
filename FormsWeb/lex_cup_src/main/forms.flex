package com.cesar31.formsweb.parser.main;

import static com.cesar31.formsweb.parser.main.FormsParserSym.*;
import java_cup.runtime.*;

%%

%class FormsLex
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
	return new java_cup.runtime.Symbol(FormsParserSym.EOF);
%eofval}
%eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

//Param = [\w\-\$\^\*\+\.\/\?\(\)@#%&~`¿,:;¡|]+
Param = \w+
Symbol = ([\\] | {Param})+
Integer =  0|[1-9][0-9]*
Date = \d{4,4}\-\d{2,2}\-\d{2,2}
Input = [^\n\r\"\\]+
InputClean = [^\n\r\"\\\t\s\f]+

StringNoClean =  \" ({WhiteSpace} | [\\] | {Input})+ \"
StringClean =  \" ([\\] | {InputClean})+ \"

Quote = \"
Ql = {Quote} {WhiteSpace}*
Qr = {WhiteSpace}* {Quote}

/* Tags solicitudes */
Ini = [Ii][Nn][Ii]
Sol = [Ss][Oo][Ll][Ii][Cc][Ii][Tt][Uu][Dd]
Fin = [Ff][Ii][Nn]
//Res = [Rr][Ee][Ss][Pp][Uu][Ee][Ss][Tt][Aa]

Ini_sol = {Ini} "_" {Sol}
Fin_sol = {Fin} "_" {Sol}
Ini_m_sol = {Ini} "_" {Sol}[Ee][Ss]
Fin_m_sol = {Fin} "_" {Sol}[Ee][Ss]

/* Para respuestas del servidor */
// Ini_res = {Ini} "_" {Res}
// Fin_res =  {Fin} "_" {Res}
// Ini_m_res = {Ini} "_" {Res}[Ss]
// Fin_m_res = {Fin} "_" {Res}[Ss]

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

	/* Fecha */
	{Ql} {Date} {Qr}
	{ return symbol(DATE, yytext()); }

	/* Palabras reservadas con comillas */
	{Ql} "CREAR_USUARIO" {Qr}
	{ return symbol(ADD_USER, yytext()); }

	{Ql} "NUEVO_FORMULARIO" {Qr}
	{ return symbol(NEW_FORM, yytext()); }

	{Ql} "CREDENCIALES_USUARIO" {Qr}
	{ return symbol(CRED, yytext()); }

	{Ql} "USUARIO" {Qr}
	{ return symbol(USER, yytext()); }

	{Ql} "PASSWORD" {Qr}
	{ return symbol(PASS, yytext()); }

	{Ql} "FECHA_CREACION" {Qr}
	{ return symbol(DATE_ADD, yytext()); }

	{Ql} "MODIFICAR_USUARIO" {Qr}
	{ return symbol(EDIT_USER, yytext()); }

	{Ql} "ELIMINAR_USUARIO" {Qr}
	{ return symbol(DEL_USER, yytext()); }

	{Ql} "LOGIN_USUARIO" {Qr}
	{ return symbol(LOGIN, yytext()); }

	{Ql} "USUARIO_ANTIGUO" {Qr}
	{ return symbol(OLD_USER, yytext()); }

	{Ql} "USUARIO_NUEVO" {Qr}
	{ return symbol(NEW_USER, yytext()); }

	{Ql} "NUEVO_PASSWORD" {Qr}
	{ return symbol(NEW_PASS, yytext()); }

	{Ql} "FECHA_MODIFICACION" {Qr}
	{ return symbol(DATE_MOD, yytext()); }

	{Ql} "PARAMETROS_FORMULARIO" {Qr}
	{ return symbol(PARAM_F, yytext()); }

	/* Input con comillas */
	{StringClean}
	{ return symbol(STR, yytext()); }

	{StringNoClean}
	{ return symbol(STR_N, yytext()); }

	":"
	{ return symbol(COLON, yytext()); }

	"<"
	{ return symbol(SMALLER, yytext()); }

	"!"
	{ return symbol(EXCL, yytext()); }

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

	"+"
	{ return symbol(PLUS, yytext()); }

	"-"
	{ return symbol(MINUS, yytext()); }

	"*"
	{ return symbol(TIMES, yytext()); }

	"/"
	{ return symbol(DIVIDE, yytext()); }

	{Integer}
	{
		return symbol(INTEGER, yytext());
	}

	{Param}
	{
		return symbol(PARAM, yytext());
	}

	{Symbol}
	{
		return symbol(SYMB, yytext());
	}

	{WhiteSpace}
	{ /* Ignore */ }

	/* Comillas */
	{Quote}
	{ return symbol(QUOTE, yytext()); }
}

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
