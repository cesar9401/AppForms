package com.cesar31.formsweb.parser.main;

import com.cesar31.formsweb.control.HandleDB;
import com.cesar31.formsweb.control.HandlerFormParser;
import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.User;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class TestParserMain {

    public static void main(String[] args) {

        HandleDB db = new HandleDB();
        HandlerFormParser handler = new HandlerFormParser();

        String data = db.readData("sqf.indigo");
        //System.out.println(data);
//        Message m = handler.parserInput(new Message(null, data));
//
//        System.out.println(m.getMesssage());
        
        //debug(data);
        
        FormsLex lex = new FormsLex(new StringReader(data));
        FormsParser parser = new FormsParser(lex);
        try {
            parser.parse();
            if(parser.isParsed()) {
                List<Request> r = parser.getContainer().getRequests();
                r.forEach(req -> {
                    System.out.println(req.toString());
                });
            } else {
                parser.getContainer().getErrors().forEach(e -> {
                    System.out.println(e.toString());
                });
            }
            //System.out.println("ParserMain");
        } catch (Exception ex) {
            System.out.println("no parserMAIN");
            ex.printStackTrace(System.out);
        }
    }

    public static void operation(List<User> u, List<Form> f, List<Component> c) {
        c.forEach(comp -> {
            for (Form form : f) {
                if (comp.getForm_id().equals(form.getId_form())) {
                    comp.setIndex(form.getComponents().size() + 1);
                    form.getComponents().add(comp);
                }
            }
        });
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
