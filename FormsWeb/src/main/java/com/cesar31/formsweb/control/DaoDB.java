package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.FormData;
import com.cesar31.formsweb.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DaoDB {

    private List<User> users;
    private List<Form> forms;
    private List<Component> currents;
    private HashMap<String, String> params;
    private HashMap<String, String> paramC;
    private List<String> options;

    // Datos recolectados con formularios
    private List<FormData> fmData;
    private HashMap<String, String> data;
    private HashMap<String, String> fm;
    private List<HashMap<String, String>> currentData;

    public DaoDB() {
        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
        this.currents = new ArrayList<>();
        this.params = new HashMap<>();
        this.paramC = new HashMap<>();
        this.options = new ArrayList<>();

        // Datos recolectados con formularios
        this.fmData = new ArrayList<>();
        this.data = new HashMap<>();
        this.fm = new HashMap<>();
        this.currentData = new ArrayList<>();
    }

    /**
     * Crear usuarios
     */
    public void createUser() {
        String user = params.get("user");
        String pass = params.get("pass");
        String cDate = params.get("cDate");
        String eDate = params.get("eDate");

        User u = new User(user, pass);
        u.setCreationDate(LocalDate.parse(cDate));
        u.setcDate_user(cDate);
        if (eDate.equals("null")) {
            u.setEditDate(null);
            u.seteDate(null);
        } else {
            u.setEditDate(LocalDate.parse(eDate));
            u.seteDate(eDate);
        }

        users.add(u);

        params.clear();
    }

    /**
     * Crear formularios
     */
    public void createForms() {
        String idForm = params.get("id_form");
        String title = params.get("title");
        String name = params.get("name");
        String theme = params.get("theme");
        String user = params.get("user_c");
        String date = params.get("date_form");

        Form f = new Form(idForm, title, name);
        f.setTheme(theme);
        f.setUser_creation(user);
        f.setCreationDate(LocalDate.parse(date));
        f.setcDate_form(date);

        f.getComponents().addAll(currents);
        forms.add(f);

        params.clear();
        currents.clear();
    }

    /**
     * Crear componentes
     */
    public void createComponents() {
        String id = paramC.get("id_comp");
        String field = paramC.get("field");
        String form = paramC.get("form_id");
        String kind = paramC.get("kind");
        String index = paramC.get("index");
        String text = paramC.get("text");
        String aling = paramC.get("aling");
        String req = paramC.get("req");
        String url = paramC.get("url");
        String rows = paramC.get("rows");
        String cols = paramC.get("cols");

        Component c = new Component(id, field, form);
        c.setKind(kind);
        c.setIndex(Integer.valueOf(index));
        c.setText(text);
        c.setAling(aling);
        c.setRequired(Boolean.valueOf(req));

        if (!url.equals("null")) {
            c.setUrl(url);
        }

        if (!rows.equals("null")) {
            c.setRows(Integer.valueOf(rows));
        }

        if (!cols.equals("null")) {
            c.setColumns(Integer.valueOf(cols));
        }

        if (!options.isEmpty()) {
            c.getOptions().addAll(options);
        }

        currents.add(c);

        paramC.clear();
        options.clear();
    }

    /**
     * Crear objeto FormData y agregarlo a litado fmData
     */
    public void createFormWithData() {
        String id = fm.get("ID");
        String name = fm.get("NAME");

        // crear objecto FormData
        FormData fD = new FormData(id, name);
        fD.getData().addAll(currentData);

        //Agregar a listado de FormData
        this.fmData.add(fD);

        //Limpiar listado
        this.currentData.clear();
        this.fm.clear();
    }

    /**
     * Crear listado de respuestas recolectadas con formularios
     */
    public void createDataForm() {
        HashMap<String, String> dataFm = new HashMap<>();
        dataFm.putAll(data);

        // Agregar a listado
        this.currentData.add(dataFm);

        // Limpiar HashMap
        data.clear();
    }

    /**
     * Datos del formulario del cual se recolectaron datos
     *
     * @param key
     * @param param
     */
    public void setDataFm(String key, String param) {
        this.fm.put(key, param);
    }

    /**
     * Parametros de datos recolectados con formularios
     *
     * @param key
     * @param param
     */
    public void setDataParam(String key, String param) {
        this.data.put(key, param);
    }

    /**
     * Parametros para crear objetos
     *
     * @param key
     * @param param
     */
    public void setParam(String key, String param) {
        params.put(key, param);
    }

    public void setParamC(String key, String param) {
        paramC.put(key, param);
    }

    /**
     * Agregar opciones
     *
     * @param option
     */
    public void setOption(String option) {
        options.add(option);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public List<Form> getForms() {
        return forms;
    }

    public List<FormData> getFmData() {
        return fmData;
    }
}
