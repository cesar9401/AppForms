package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.FormData;
import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.answer.AnswerLex;
import com.cesar31.formsweb.parser.answer.AnswerParser;
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

    //Datos Recopilados
    private List<FormData> fmData;

    public HandlerDB() {
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();

        this.fmData = new ArrayList<>();
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

    /**
     * Leer base de datos de formularios y usuarios
     */
    public void readDataBase() {
        String data = readData(DB_URL);
        DataLex lex = new DataLex(new StringReader(data));
        DataParser parser = new DataParser(lex);
        try {
            parser.parse();
            this.users = parser.getDaoDB().getUsers();
            this.forms = parser.getDaoDB().getForms();
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
     * Escribir en la base de datos, recibe path y string con la informacion en
     * formato json
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

    /**
     * Leer base de datos recopilados con formularios
     */
    public void readDataForms() {
        String data = readData(DB_DATA_URL);
        AnswerLex lex = new AnswerLex(new StringReader(data));
        AnswerParser parser = new AnswerParser(lex);
        try {
            parser.parse();
            this.fmData = parser.getDaoDB().getFmData();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void addData(FormData formData) {
        if (!formData.getData().isEmpty()) {
            //Agregar a db
            readDataForms();
            FormData fd = getFormData(formData.getIdForm());

            if (fd != null) {
                fd.getData().add(formData.getData().get(0));
            } else {
                this.fmData.add(formData);
            }

            //Actualizar
            writeFormData(this.fmData);
        }
    }

    /**
     * Obtener FormData segun id
     *
     * @param id
     * @return
     */
    private FormData getFormData(String id) {
        FormData fd = null;
        for (FormData form : fmData) {
            if (form.getIdForm().equals(id)) {
                fd = form;
                break;
            }
        }
        return fd;
    }

    public void writeFormData(List<FormData> fd) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "DATOS_RECOPILADOS\n";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fd);
            json += "\n";
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }
        //System.out.println(json);

        writeData(DB_DATA_URL, json);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<FormData> getFmData() {
        return fmData;
    }
}
