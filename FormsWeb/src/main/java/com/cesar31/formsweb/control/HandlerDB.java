package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.db.DataLex;
import com.cesar31.formsweb.parser.db.DataParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class HandlerDB {

    private final String DB_URL = "forms.db";
    
    private List<User> users;
    private List<Form> forms;

    public HandlerDB() {
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
    }

    /**
     * Leer DB
     *
     * @param path
     * @return
     */
    public String readDate(String path) {
        String data = "";
        File file = new File(path);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    data += line;
                    line = br.readLine();
                    if (line != null) {
                        line += "\n";
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        return data;
    }
    
    public void readDataBase() {
        String data = readDate(DB_URL);
        DataLex lex = new DataLex(new StringReader(data));
        DataParser parser = new DataParser(lex);
        try {
            parser.parse();
            parser.getDaoDB();
            users = parser.getDaoDB().getUsers();
            forms = parser.getDaoDB().getForms();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Convertir lista de usuarios y formularios a string con la informacion en
     * formato json
     *
     * @param users
     * @param forms
     * @return
     */
    public String createJson(List<User> users, List<Form> forms) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json += "USERS\n";
            json += mapper.writerWithDefaultPrettyPrinter().withView(User.class).writeValueAsString(users);
            json += "\n\n";

            json += "FORMS\n";
            json += mapper.writerWithDefaultPrettyPrinter().withView(Form.class).writeValueAsString(forms);
            json += "\n";

        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }

        return json;
    }

    /**
     * Escribir en la base de datos, recibe string con la informacion en formato
     * json
     *
     * @param json
     */
    public void writeDate(String json) {
        File file = new File(DB_URL);
        try {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(json);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Form> getForms() {
        return forms;
    }
}
