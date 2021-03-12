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
        String input = "<!ini_solicitud : \"CREAR_USUARIO\">\n"
                + "      { \"CREDENCIALES_USUARIO\":[{\n"
                + "            \"USUARIO\": \"juanito619\"    ,\n"
                + "            \"PASSWORD\": \"12345678\"\n"
                + "             }       \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        String input2 = "<!INI_solicitud : \"\\n\tCREAR_USUARIO\t\n\"> \n "
                + "{ \"CREDENCIALES_USUARIO\" : [{ \n "
                + "\"USUARIO\" : \"cesar<_@#$%^&*))_{}[]!>31\" , \n"
                + "\"PASSWORD\" : \"123.321\"\n"
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud!>";
        
        debugCup(input);
       
        FormsLex lexer = new FormsLex(new StringReader(input2));
        parser parser = new parser(lexer);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void debugCup(String input) {
        FormsLex lex = new FormsLex(new StringReader(input));

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
        System.out.println("----------------FIN DEBUG CUP--------------------------------------------------------");
    }

}
