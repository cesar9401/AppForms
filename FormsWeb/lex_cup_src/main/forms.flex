package com.cesar31.formsweb.lexerandparser;

import static com.cesar31.formsweb.lexerandparser.sym.*;
import java_cup.runtime.*;

%%

%class FormsLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
%cupdebug

%{

	private StringBuffer string = new StringBuffer();
	private boolean date = false;

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline + 1, yycolumn + 1, value);
	}

	private Symbol getSymbol(int type, Object value) {
		String s = value.toString().replaceAll("\"", "");
		s = s.trim();
		String a = "\"".concat(s).concat("\"");
		System.out.println("value -> " + value.toString());
		System.out.println("string -> " + s);

		if(date)
			return symbol(DATE, a);
		if(s.equals("CREAR_USUARIO"))
			return symbol(ADD_USER, a);
		if(s.equals("CREDENCIALES_USUARIO"))
			return symbol(CRED, a);
		if(s.equals("USUARIO"))
			return symbol(USER, a);
		if(s.equals("PASSWORD"))
			return symbol(PASS, a);
		if(s.equals("FECHA_CREACION"))
			return symbol(DATE_ADD, a);
		if(s.equals("MODIFICAR_USUARIO"))
			return symbol(EDIT_USER, a);
		if(s.equals("ELIMINAR_USUARIO"))
			return symbol(DEL_USER, a);
		if(s.equals("LOGIN_USUARIO"))
			return symbol(LOGIN, a);
		if(s.equals("USUARIO_ANTIGUO"))
			return symbol(OLD_USER, a);
		if(s.equals("USUARIO_NUEVO"))
			return symbol(NEW_USER, a);
		if(s.equals("NUEVO_PASSWORD"))
			return symbol(NEW_PASS, a);
		if(s.equals("FECHA_MODIFICACION"))
			return symbol(DATE_MOD, a);

		return symbol(type, a);
	}

%}

// %eofval{
// 	return new java_cup.runtime.Symbol(FormsParserSym.EOF);
// %eofval}
// %eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f]

//Param = [\w\-\$\^\*\+\.\/\?\(\)@#%&~`¿,:;¡|]+
Param = \w+
Symbol = ([\\] | {Param})+
Integer =  0|[1-9][0-9]*
Date = \d{4,4}\-\d{2,2}\-\d{2,2}
StringDate = \" {WhiteSpace}*{Date}{WhiteSpace}* \"
Input = [^\n\r\"\\]+
StringInput =  \" ({WhiteSpace}* | [\\] | [^\n\r\"\\]+)+ \"

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

//%state STRING

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

	{StringDate}
	{
		//System.out.println("Date: " + yytext());
		date = true;
		return getSymbol(STR, yytext());
	}

	{StringInput}
	{
		date = false;
		return getSymbol(STR, yytext());
	}

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

}

// <STRING> {
// 	\"
// 	{
// 		yybegin(YYINITIAL);
// 		return getSymbol(STR, string.toString());
// 	}

// 	\\t
// 	{
// 		string.append('\t');
// 	}

//     \\n
// 	{
// 		string.append('\n');
// 	}

// 	\\
// 	{
// 		string.append('\\');
// 	}

// 	{WhiteSpace}
// 	{ /* Ignore */ }
// }

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
