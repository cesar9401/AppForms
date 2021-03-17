package com.cesar31.formsweb.parser.db;

import com.cesar31.formsweb.control.HandlerDB;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class TestParserDB {

    public static void main(String[] args) {
        HandlerDB db = new HandlerDB();
        String data = db.readDate("forms.db");
        //System.out.println(data);

        DataLex lex = new DataLex(new StringReader(data));
        DataParser parser = new DataParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
