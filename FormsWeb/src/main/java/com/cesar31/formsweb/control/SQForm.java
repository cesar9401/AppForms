package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Token;
import com.cesar31.formsweb.parser.sqf.SQFLex;
import com.cesar31.formsweb.parser.sqf.SQFParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class SQForm {

    private HashMap<String, Token> params;
    private UserContainer container;

    private List<Error> sqErrors;

    public SQForm(UserContainer container) {
        this.container = container;
        this.params = new HashMap<>();
        this.sqErrors = new ArrayList<>();
    }

    public void createSQF(Token t) {
        params.forEach((s, k) -> {
            String value = k.getValue();
            value = value.replace("\"", "");
            System.out.println(value);
            
            SQFLex lex = new SQFLex(new StringReader(value));
            SQFParser parser = new SQFParser(lex);
            try {
                parser.parse();
                System.out.println("parseado");
                //System.out.println(s + " -> " + k.getValue() + "linea: " + k.getX() + "columna: " + k.getY());
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        });
    }

    public void setParam(String key, Token t) {
        if (!params.containsKey(key)) {
            params.put(key, t);

        } else {
            Error e = new Error(key, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha usado: " + key + " para la hacer una consulta.";
            e.setDescription(description);

            System.out.println(e.toString());
            sqErrors.add(e);
        }
    }
}
