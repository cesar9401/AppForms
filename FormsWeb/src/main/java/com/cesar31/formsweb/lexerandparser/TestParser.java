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

        String input1 = "<!INI_solicitud : \"\n\tCREAR_USUARIO\t\n\"> \n "
                + "{ \"    CREDENCIALES_USUARIO\" : [{ \n "
                + "\"USUARIO\" : \"\\t32123_jose21\" , \n"
                + "\"PASSWORD\" : \"123.321\"\n  ,"
                + "\"FECHA_CREACION\"  : \"\t2020-02-12\"    \n"
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud!>"
                + "\n";

        String input2 = "<!ini_solicitud:\"MODIFICAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USUARIO_ANTIGUO\": \"juanito619\",\n"
                + "            \"USUARIO_NUEVO\": \"juanito619lopez\",\n"
                + "            \"NUEVO_PASSWORD\": \"12345678910\"\n"
                + "           ,  \"FECHA_MODIFICACION\" : \"2002-12-21\"  }    \n"
                + "          ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input3 = "< ! ini_solicitud : \"ELIMINAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USUARIO\": \"juanito619lopez\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud !>";

        String input4 = "<!ini_solicitud:\"LOGIN_USUARIO\">\n"
                + "     { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USUARIO\": \"juanito619\",\n"
                + "            \"PASSWORD\": \"12345678\"\n"
                + "           }         \n"
                + "         ]\n"
                + "      }      \n"
                + "<fin_solicitud!>";

        //debugCup(input2);
        //debug(input2);
        FormsLex lexer = new FormsLex(new StringReader(input4));
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
