package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.FormData;
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

    public final String DB_URL = "/home/cesar31/Java/AppForms/FormsWeb/DB/forms.db";
    public final String DB_DATA_URL = "/home/cesar31/Java/AppForms/FormsWeb/DB/forms_data.db";

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
    public String readData(String path) {
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
        String data = readData(DB_URL);
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
     * Obtener usuario para login
     *
     * @param u
     * @param p
     * @return
     */
    public User getUser(String u, String p) {
        readDataBase();
        User login = null;
        for (User user : users) {
            if (user.getUser().equals(u) && user.getPassword().equals(p)) {
                login = user;
                break;
            }
        }

        return login;
    }

    /**
     * Obtener formulario segun id
     *
     * @param id
     * @return
     */
    public Form getForm(String id) {
        readDataBase();
        Form fm = null;
        for (Form form : forms) {
            if (form.getId_form().equals(id)) {
                fm = form;
                break;
            }
        }

        return fm;
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
     * @param path
     * @param json
     */
    public void writeData(String path, String json) {
        File file = new File(path);
        try {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(json);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void writeFormData(FormData fd) {
        List<FormData> list = new ArrayList<>();
        list.add(fd);
        ObjectMapper mapper = new ObjectMapper();
        String json = "DATOS_RECOPILADOS\n";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            json += "\n";
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }
        System.out.println("\n");
        System.out.println(json);
        
        writeData(DB_DATA_URL, json);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Form> getForms() {
        return forms;
    }
}
