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
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Part;

/**
 *
 * @author cesar31
 */
public class HandleDB {

    /* CAMBIAR */
    public final String PATH = "/home/cesar31/Java/AppForms/";

    /* Base de Datos */
    public final String DB_URL = PATH + "FormsWeb/src/main/webapp/resources/DB/forms.db";
    public final String DB_DATA_URL = PATH + "FormsWeb/src/main/webapp/resources/DB/forms_data.db";
    public final String FILES = PATH + "FormsWeb/src/main/webapp/resources/DB/Files/";

    private List<User> users;
    private List<Form> forms;

    //Datos Recopilados
    private List<FormData> fmData;

    public HandleDB() {
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
                    data += line + "\n";
                    line = br.readLine();
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
     * Obtener usuario segun username
     *
     * @param u
     * @return
     */
    public User getUser(String u) {
        readDataBase();
        User us = null;
        for (User user : users) {
            if (user.getUser().equals(u)) {
                us = user;
                break;
            }
        }

        return us;
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
     * Obtener formulario por busqueda
     *
     * @param input
     * @return
     */
    public Form getFormByRegex(String input) {
        
        input = input.trim();
        Form fm = null;

        if (!input.isEmpty()) {
            String match = null;
            Pattern pat = Pattern.compile("^[$-_]([$-_]|[^\\n\\s\\f\\t\\r])+$");
            Pattern pat2 = Pattern.compile("^http:\\/\\/localhost:8080\\/FormsWeb\\/Form\\?id=([$-_]([S-_]|[^\\n\\s\\f\\t\\r])+)$");

            Matcher matcher = pat.matcher(input);
            Matcher matcher2 = pat2.matcher(input);

            if (matcher.find()) {
                match = matcher.group();
            } else if (matcher2.find()) {
                match = matcher2.group(1);
            }

            if (match != null) {
                fm = getForm(match);
            }
        }

        return fm;
    }

    /**
     * Obtener formularios por usuario
     *
     * @param user
     * @param url
     * @return
     */
    public List<Form> getForms(String user) {
        readDataBase();
        List<Form> fms = new ArrayList<>();
        for (Form f : forms) {
            if (f.getUser_creation() != null) {
                if (f.getUser_creation().equals(user)) {
                    fms.add(f);
                }
            }
        }

        return fms;
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

    /**
     * Obtener FormData por id o nombre
     *
     * @param id
     * @param param
     * @return
     */
    public FormData getFormData(boolean id, String param) {
        readDataForms();
        FormData fmd = null;
        for (FormData fd : fmData) {
            if (id) {
                if (fd.getIdForm().equals(param)) {
                    fmd = fd;
                    break;
                }
            } else {
                if (fd.getNameForm().equals(param)) {
                    fmd = fd;
                    break;
                }
            }
        }

        return fmd;
    }

    public void writeFormData(List<FormData> fd) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "DATOS_RECOPILADOS\n";
        try {
            json += mapper.writerWithDefaultPrettyPrinter().withView(FormData.class).writeValueAsString(fd);
            json += "\n";
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }
        System.out.println(json);
        writeData(DB_DATA_URL, json);
    }

    public String saveFile(Part filePart) {
        String path = FILES + filePart.getSubmittedFileName();
        System.out.println(path);
        File file = new File(path);
        try {
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        return path;
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
