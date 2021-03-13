package com.cesar31.formsweb.lexerandparser;

import java.io.IOException;
import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class TestParser {

    public static void main(String[] args) {
        String input = "<!ini_solicitud : \"CEAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USURIO\" :\"2020-13-12\"   , \n"
                + "            \"PASSWORD\": \"2020-12-12\"    ,\n"
                + "\"FECHA_CREACION\"  : \" 2020-02-12 \"    "
                + "             }      \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_Solicitud>";

        String input2 = "<!INI_solicitud : \"\\n\tCREAR_USUARIO\t\n\"> \n "
                + "{ \"CREDENCIALES_USUARIO\" : [{ \n "
                + "\"USUARIO\" : \"cesar<_@#$%^&*))_{}[]!>31\" , \n"
                + "\"PASSWORD\" : \"123.321\"\n,   "
                + "\"FECHA_CREACION\"  : \" 2020-02-12 \"    "
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud!>"
                + "\n"
                + "\" \n\t 2020-01-12 \n\t    \n\t\"";

        //debugCup(input2);
        FormsLex lexer = new FormsLex(new StringReader(input));
        parser parser = new parser(lexer);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void debugCup(String str) {
        FormsLex lex = new FormsLex(new StringReader(str));

        while (true) {
            try {
                Symbol s = lex.debug_next_token();
                if (s.sym == 0) {
                    break;
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        System.out.println("\n\n");
        System.out.println("----------------FIN DEBUG CUP-------");
    }

}
