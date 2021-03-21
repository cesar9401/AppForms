package com.cesar31.formsweb.parser.main;

import com.cesar31.formsweb.control.Error;
import com.cesar31.formsweb.control.HandlerDB;
import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class TestParserMain {

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Form> forms = new ArrayList<>();
        List<Component> components = new ArrayList<>();

        HandlerDB db = new HandlerDB();
        String data = db.readDate("request.indigo");

        FormsLex lexer = new FormsLex(new StringReader(data));
        FormsParser parser = new FormsParser(lexer);

        try {
            parser.parse();
            if (parser.isParsed()) {
                List<Request> reqs = parser.getContainer().getRequests();
                reqs.forEach(r -> {
                    if (r instanceof User) {
                        users.add((User) r);
                    }

                    if (r instanceof Form) {
                        forms.add((Form) r);
                    }

                    if (r instanceof Component) {
                        components.add((Component) r);
                    }

                });

                operation(users, forms, components);

            } else {
                List<Error> errors = parser.getContainer().getErrors();
                System.out.println("\n\nErrores: ");
                errors.forEach(e -> {
                    System.out.println(e.toString());
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void operation(List<User> u, List<Form> f, List<Component> c) {
        c.forEach(comp -> {
            for (Form form : f) {
                if (comp.getForm().equals(form.getId())) {
                    comp.setIndex(form.getComponents().size() + 1);
                    form.getComponents().add(comp);
                }
            }
        });

//        f.forEach(form -> {
//            System.out.println(form.toString());
//            form.getComponents().forEach(comp -> {
//                System.out.println(comp.toString());
//            });
//        });
        ObjectMapper mapper = new ObjectMapper();

        try {
            String json = "USERS\n";
            json += mapper.writerWithDefaultPrettyPrinter().withView(User.class).writeValueAsString(u);
            json += "\n\n";
            
            json += "FORMS\n";
            json += mapper.writerWithDefaultPrettyPrinter().withView(Form.class).writeValueAsString(f);
            json += "\n";
            
            System.out.println(json);
            
            HandlerDB.writeDate(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }
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
