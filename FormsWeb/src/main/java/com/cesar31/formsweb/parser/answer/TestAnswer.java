package com.cesar31.formsweb.parser.answer;

import com.cesar31.formsweb.control.HandlerDB;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class TestAnswer {
    public static void main(String[] args) {
        // Write your code here
        
        HandlerDB db = new HandlerDB();
        String data = db.readData(db.DB_DATA_URL);
        
        AnswerLex lex = new AnswerLex(new StringReader(data));
        AnswerParser parser = new AnswerParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }
}
