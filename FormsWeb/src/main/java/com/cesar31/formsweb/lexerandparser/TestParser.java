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
        String input = "<ini_solicitud : \"CEAR_USUARIO\">\n"
                + "      { \"CREDENCILES_USUARIO\":[{\n"
                + "            \"USUARiO\" :\"2020-13-12\"   , \n"
                + "            \"PASsWORD\": \"2020-12-12\"    ,\n"
                + "\"FECA_CREACION\"  : \" 2020-02-12 \"    "
                + "             }      \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_Solicitud>";

        String input2 = "<!INI_solicitud : \n\tCREAR_USUARIO\t\n\"> \n "
                + "{ \"    CREDENCIALES_USUARIO\" : [{ \n "
                + "\"USUARIO\" : \"\n\tcesar<_]>31\" , \n"
                + "\"PASSWORD\" : \"123.321\"\n  ,"
                + "\"FECHA_CREACION\"  : \" 2020-02-12 \"    "
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud>"
                + "\n";

        String input3 = "\"fdsafadsfd\" ini_solicitud \"fdasfads 12.32";

//        String string = "\"  fdsafadsfads \"";
//        string = string.replaceAll("\"", "");
//        string = string.trim();
//        System.out.println(string);
//        string = "\"".concat(string).concat("\"");
//        System.out.println(string);

        debugCup(input2);
        debug(input2);

//        FormsLex lexer = new FormsLex(new StringReader(input2));
//        parser parser = new parser(lexer);
//        try {
//            parser.parse();
//        } catch (Exception ex) {
//            ex.printStackTrace(System.out);
//        }
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

    public static void debug(String str) {
        FormsLex lex = new FormsLex(new StringReader(str));

        while (true) {
            Symbol s;
            try {
                s = lex.next_token();
                if (s.sym == 0) {
                    break;
                } else {
                    System.out.printf("Valor -> %s, fila -> %d, columna -> %d\n", s.value.toString(), s.left, s.right);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }

        }
    }

}
