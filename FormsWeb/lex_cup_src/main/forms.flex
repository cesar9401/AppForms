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
Symbol = ([\\\*\.\(\)\?\^&@¡#%;¿] | {Param})+
Integer =  0|[1-9][0-9]*
Date = \d{4,4}\-\d{2,2}\-\d{2,2}
Input = [^\n\r\"\\\|]+
InputClean = [^\n\r\"\\\t\s\f\|]+
Id = \" [\_\-\$] ([\_\-\$] | \w )+ \"

str = {WhiteSpace}* {InputClean} ( [\\] | {Input} )+ {WhiteSpace}*

str_space = ({WhiteSpace} | [\\] | {Input})+

// str_c = {WhiteSpace}* \w {str}*

StringNoClean = \" {str} \"
StringSpace = \" {str_space} \"

//StringClean =  \" ([\\] | {InputClean})+ \"

Options	= \" ({str} \| )* {str} \"
Options_space	= \" ({str_space} \| )* {str_space} \"


Quote = \"
Ql = {Quote} {WhiteSpace}*
Qr = {WhiteSpace}* {Quote}

/* Tags solicitudes */
Ini = [Ii][Nn][Ii]
Sol = [Ss][Oo][Ll][Ii][Cc][Ii][Tt][Uu][Dd]
Fin = [Ff][Ii][Nn]

Ini_sol = {Ini} "_" {Sol}
Fin_sol = {Fin} "_" {Sol}
Ini_m_sol = {Ini} "_" {Sol}[Ee][Ss]
Fin_m_sol = {Fin} "_" {Sol}[Ee][Ss]

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

	{Ql} {Integer} {Qr}
	{ return symbol(STR_NUMBER, yytext()); }

	/* Palabras reservadas con comillas */
	/* Usuarios */
	{Ql} "CREAR_USUARIO" {Qr}
	{ return symbol(ADD_USER, yytext()); }

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

	/* Formularios */
	{Ql} "NUEVO_FORMULARIO" {Qr}
	{ return symbol(NEW_FORM, yytext()); }

	{Ql} "PARAMETROS_FORMULARIO" {Qr}
	{ return symbol(PARAM_F, yytext()); }

	{Ql} "ID" {Qr}
	{ return symbol(ID, yytext()); }

	{Id}
	{ return symbol(ID_, yytext()); }

	{Ql} "TITULO" {Qr}
	{ return symbol(TITLE, yytext()); }

	{Ql} "NOMBRE" {Qr}
	{ return symbol(NAME, yytext()); }

	{Ql} "TEMA" {Qr}
	{ return symbol(THEME, yytext()); }

	{Ql} "USUARIO_CREACION" {Qr}
	{ return symbol(USER_C, yytext()); }

	{Ql} "ELIMINAR_FORMULARIO" {Qr}
	{ return symbol(DEL_FORM, yytext()); }

	{Ql} "MODIFICAR_FORMULARIO" {Qr}
	{ return symbol(EDIT_FORM, yytext()); }

	{Ql} "AGREGAR_COMPONENTE" {Qr}
	{ return symbol(ADD_COMP, yytext()); }

	{Ql} "PARAMETROS_COMPONENTE" {Qr}
	{ return symbol(PARAM_C, yytext()); }

	{Ql} "NOMBRE_CAMPO" {Qr}
	{ return symbol(FIELD_N, yytext()); }

	{Ql} "FORMULARIO" {Qr}
	{ return symbol(FORM, yytext()); }

	{Ql} "CLASE" {Qr}
	{ return symbol(CLASS, yytext()); }

	/* CLASE COMPONENTE */
	{Ql} "CAMPO_TEXTO" {Qr}
	{ return symbol(TEXT_FIELD, yytext()); }

	{Ql} "AREA_TEXTO" {Qr}
	{ return symbol(TEXT_AREA, yytext()); }

	{Ql} "CHECKBOX" {Qr}
	{ return symbol(CHECKBOX, yytext()); }

	{Ql} "RADIO" {Qr}
	{ return symbol(RADIO, yytext()); }

	{Ql} "FICHERO" {Qr}
	{ return symbol(FILE, yytext()); }

	{Ql} "IMAGEN" {Qr}
	{ return symbol(IMG, yytext()); }

	{Ql} "COMBO" {Qr}
	{ return symbol(COMBO, yytext()); }

	{Ql} "BOTON" {Qr}
	{ return symbol(BTN, yytext()); }
	/* CLASE COMPONENTE */

	{Ql} "INDICE" {Qr}
	{ return symbol(INDEX, yytext()); }

	{Ql} "TEXTO_VISIBLE" {Qr}
	{ return symbol(TEXT, yytext()); }

	{Ql} "ALINEACION" {Qr}
	{ return symbol(ALIGN, yytext()); }

	/* Opciones alineacion */
	{Ql} "CENTRO" {Qr}
	{ return symbol(CENTER, yytext()); }

	{Ql} "IZQUIERDA" {Qr}
	{ return symbol(LEFT, yytext()); }

	{Ql} "DERECHA" {Qr}
	{ return symbol(RIGHT, yytext()); }

	{Ql} "JUSTIFICAR" {Qr}
	{ return symbol(JUSTIFY, yytext()); }
	/* Opciones alineacion */

	{Ql} "REQUERIDO" {Qr}
	{ return symbol(REQUIRED, yytext()); }

	{Ql} "SI" {Qr}
	{ return symbol(YES, yytext()); }

	{Ql} "NO" {Qr}
	{ return symbol(NO, yytext()); }

	{Ql} "OPCIONES" {Qr}
	{ return symbol(OPTION, yytext()); }

	{Ql} "FILAS" {Qr}
	{ return symbol(ROWS, yytext()); }

	{Ql} "COLUMNAS" {Qr}
	{ return symbol(COLUMNS, yytext()); }

	{Ql} "URL" {Qr}
	{ return symbol(URL, yytext()); }

	{Ql} "ELIMINAR_COMPONENTE" {Qr}
	{ return symbol(DEL_COMP, yytext()); }

	{Ql} "MODIFICAR_COMPONENTE" {Qr}
	{ return symbol(EDIT_COMP, yytext()); }

	/* Input con comillas */
	{StringNoClean}
	{ return symbol(STR, yytext()); }

	/* opciones para radio y combo */
	{Options}
	{ return symbol(OPTION_V, yytext()); }

	// {StringNoClean}
	// { return symbol(STR_N, yytext()); }

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
	{ return symbol(INTEGER, yytext()); }

	{Param}
	{ return symbol(PARAM, yytext()); }

	{Symbol}
	{ return symbol(SYMB, yytext()); }

	/* Comillas */
	{Quote}
	{ return symbol(QUOTE, yytext()); }

	{Qr} {WhiteSpace}* {Ql}
	{ return symbol(EMPTY, yytext()); }

	{StringSpace}
	{ return symbol(STR_SPACE, yytext()); }

	{Options_space}
	{ return symbol(OPTION_SPACE, yytext()); }

	{WhiteSpace}
	{ /* Ignore */ }
}

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
