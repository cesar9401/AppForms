package com.cesar31.formsweb.control;

import com.cesar31.formsweb.parser.main.FormsLex;
import com.cesar31.formsweb.parser.main.FormsParser;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class HandlerFormParser {

    public HandlerFormParser() {
    }

    /**
     * Parsear input
     *
     * @param input
     * @return
     */
    public UserContainer readInput(String input) {
        UserContainer container = null;

        FormsLex lex = new FormsLex(new StringReader(input));
        FormsParser parser = new FormsParser(lex);
        try {
            parser.parse();
            if (parser.isParsed()) {
                // Obtener elementos aqui
                container = parser.getContainer();
            } else {
                // Write error message here
                System.out.println("Error al parsear");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        return container;
    }

}
