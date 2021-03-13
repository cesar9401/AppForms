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
		String s = value.toString().trim();
		//System.out.println("string -> " + value.toString());
		//System.out.println("string -> " + s);

		if(date)
			return symbol(DATE, s);
		if(s.equals("CREAR_USUARIO"))
			return symbol(ADD_USER, s);
		if(s.equals("CREDENCIALES_USUARIO"))
			return symbol(CRED, s);
		if(s.equals("USUARIO"))
			return symbol(USER, s);
		if(s.equals("PASSWORD"))
			return symbol(PASS, s);
		if(s.equals("FECHA_CREACION"))
			return symbol(DATE_ADD, s);
		if(s.equals("MODIFICAR_USUARIO"))
			return symbol(EDIT_USER, s);
		if(s.equals("ELIMINAR_USUARIO"))
			return symbol(DEL_USER, s);
		if(s.equals("LOGIN_USUARIO"))
			return symbol(LOGIN, s);
		if(s.equals("USUARIO_ANTIGUO"))
			return symbol(OLD_USER, s);
		if(s.equals("USUARIO_NUEVO"))
			return symbol(NEW_USER, s);
		if(s.equals("NUEVO_PASSWORD"))
			return symbol(NEW_PASS, s);
		if(s.equals("FECHA_MODIFICACION"))
			return symbol(DATE_MOD, s);

		return symbol(type, value.toString());
	}

%}

// %eofval{
// 	return new java_cup.runtime.Symbol(FormsParserSym.EOF);
// %eofval}
// %eofclose

/* Espacios */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[\s\t\f ]

//Param = [\w\-\$\^\*\+\.\/\?\(\)@#%&~`¿,:;¡|]+
Param = \w+
Integer =  0|[1-9][0-9]*
Date = \d{4,4}\-\d{2,2}\-\d{2,2}

/* Tags solicitudes */
Ini = [Ii][Nn][Ii]
Sol = [Ss][Oo][Ll][Ii][Cc][Ii][Tt][Uu][Dd]
Fin = [Ff][Ii][Nn]
//Res = [Rr][Ee][Ss][Pp][Uu][Ee][Ss][Tt][Aa]

Ini_sol = {Ini} "_" {Sol}
Fin_sol = {Fin} "_" {Sol}
Ini_m_sol = {Ini} "_" {Sol}[Ed][Ss]
Fin_m_sol = {Fin} "_" {Sol}[Ed][Ss]

/* Para respuestas del servidor */
// Ini_res = {Ini} "_" {Res}
// Fin_res =  {Fin} "_" {Res}
// Ini_m_res = {Ini} "_" {Res}[Ss]
// Fin_m_res = {Fin} "_" {Res}[Ss]

%state STRING

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

	\"
	{
		string.setLength(0);
		yybegin(STRING);
	}

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

	{WhiteSpace}
	{ /* Ignore */ }

}

<STRING> {
	\"
	{
		yybegin(YYINITIAL);
		return getSymbol(STR, string.toString());
	}

	{WhiteSpace}*{Date}{WhiteSpace}*
	{
		//System.out.println("Date: " + yytext());
		date = true;
		string.append(yytext());
	}

	[^\n\r\"\\]+
	{
		date = false;
		string.append(yytext());
	}

	\\t
	{
		string.append('\t');
	}

    \\n
	{
		string.append('\n');
	}

	\\
	{
		string.append('\\');
	}

	{WhiteSpace}
	{ /* Ignore */ }
}

[^]
{
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
