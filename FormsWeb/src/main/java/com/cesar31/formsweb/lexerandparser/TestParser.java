package com.cesar31.formsweb.lexerandparser;

import java.io.StringReader;

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
                + "           h         \n"
                + "         ]\n"
                + "      }\n"
                + "<fin_solicitud!>";

        FormsLex lex = new FormsLex(new StringReader(input));
        FormsParser parser = new FormsParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

}
