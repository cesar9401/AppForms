package com.cesar31.formsweb.parser.db;

import com.cesar31.formsweb.control.HandlerDB;
import com.cesar31.formsweb.model.User;
import java.io.StringReader;
import java.util.List;

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
            if(parser.isParsed()) {
                List<User> users = parser.getDaoDB().getUsers();
                users.forEach(u -> {
                    System.out.println(u);
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
