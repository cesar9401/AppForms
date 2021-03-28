package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.FormData;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.main.FormsLex;
import com.cesar31.formsweb.parser.main.FormsParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class HandlerFormParser {

    private HandleDB db;
    private HandleResponse hres;

    // Elementos para el parseo
    private List<Request> reqs;
    private List<Error> errors;

    // Elementos de la base de datos
    private List<User> users;
    private List<Form> forms;

    // Datos recopilados con formularios
    private List<FormData> formData;

    public HandlerFormParser() {
        this.db = new HandleDB();
        this.hres = new HandleResponse();

        this.reqs = new ArrayList<>();
        this.errors = new ArrayList<>();

        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();

        this.formData = new ArrayList<>();
    }

    /**
     * Parsear la entrada y convertirla a requests
     *
     * @param input
     * @return 
     */
    public String parserInput(String input) {
        reqs.clear();
        errors.clear();
        String response = "";

        FormsLex lex = new FormsLex(new StringReader(input));
        FormsParser parser = new FormsParser(lex);
        try {
            parser.parse();
            if (parser.isParsed()) {
                reqs = parser.getContainer().getRequests();
                executeRequests();
                response = hres.createResponseStr();
            } else {
                response = hres.createErrorResponseStr(parser.getContainer().getErrors());
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        
        return response;
    }

    /**
     * Ejecutar las peticiones
     */
    private void executeRequests() {
        reqs.forEach(r -> {
            //System.out.println(r.toString());
            if (r instanceof User) {
                switch (r.getOp()) {
                    case LOGIN:
                        login((User) r);
                        break;
                    case ADD:
                        addUser((User) r);
                        break;
                    case EDIT:
                        editUser((User) r);
                        break;
                    case DEL:
                        delUser((User) r);
                        break;
                }
            }

            if (r instanceof Form) {
                switch (r.getOp()) {
                    case ADD:
                        addForm((Form) r);
                        break;

                    case EDIT:
                        editForm((Form) r);
                        break;

                    case DEL:
                        delForm((Form) r);
                        break;
                }
            }

            if (r instanceof Component) {
                switch (r.getOp()) {
                    case ADD:
                        addComp((Component) r);
                        break;
                    case EDIT:
                        editComp((Component) r);
                        break;
                    case DEL:
                        delComp((Component) r);
                        break;
                }
            }
        });
    }

    private User login(User u) {
        readDB();
        User login = null;
        User user = getUser(u.getUser());

        if (user != null) {
            if (user.getPassword().equals(u.getPassword())) {
                login = user;
            }
        }

        if (login == null) {
            System.out.println("Credenciales incorrectas");
        }

        return login;
    }

    private void addUser(User u) {
        readDB();

        User user = getUser(u.getUser());
        if (user == null) {
            users.add(u);

            // Actualizar
            executeUpdate();

            // Respuesta cliente
            hres.createSuccessResponse(u);
        } else {
            String message = "El usuario que desea utilizar(" + u.getUser() + "), no esta disponible.";
            hres.createResponse(u, message);
        }
    }

    /**
     * Se actualiza si el usuario ya existe en la DB
     *
     * @param nuevo
     * @return
     */
    private void editUser(User edit) {
        readDB();
        User u = getUser(edit.getUser());

        if (u != null) {
            User tmp = getUser(edit.getNewUser());
            if (tmp == null) {
                u.setUser(edit.getNewUser());
                u.setPassword(edit.getPassword());
                u.setEditDate(edit.getEditDate());
                u.seteDate(edit.geteDate());

                // Actualizar
                executeUpdate();

                // Respuesta cliente
                hres.createSuccessResponse(edit);
            } else {
                String message = "El username que desea utilizar(" + edit.getNewUser() + "), no esta disponible, intente con otro.";
                hres.createResponse(edit, message);
            }
        } else {
            String message = "El usuario que desea editar(" + edit.getUser() + "), no existe.";
            hres.createResponse(edit, message);
        }
    }

    /**
     * Eliminar usuario
     *
     * @param u
     */
    private void delUser(User u) {
        readDB();
        User user = getUser(u.getUser());
        if (user != null) {
            users.remove(user);

            // Eliminar formularios tambien :v
            delFormsUser(user);

            // Actualizar
            executeUpdate();

            // Respuesta cliente
            hres.createSuccessResponse(u);
        } else {
            String message = "El usuario que desea eliminar(" + u.getUser() + "), no existe.";
            hres.createResponse(u, message);
        }
    }

    /**
     * Eliminar formularios del usuario
     *
     * @param u
     */
    private void delFormsUser(User u) {
        for (Form f : forms) {
            if (f.getUser_creation().equals(u.getUser())) {
                forms.remove(f);
                // Eliminar datos recopilados
                //delDataFormsUser(f.getId_form());
            }
        }
    }

    /**
     * Eliminar datos recopilados segun id de formulario
     *
     * @param id
     */
    private void delDataFormsUser(String id) {
        readFormsData();
        FormData fd = getFormData(id);
        if (fd != null) {
            this.formData.remove(fd);
        }

        // Actualizar datos recopilados
        db.writeFormData(this.formData);
    }

    /**
     * Agregar formulario
     *
     * @param f
     */
    private void addForm(Form f) {
        readDB();
        Form fm = getForm(f.getId_form());

        if (fm == null) {
            forms.add(f);

            // Actualizar
            executeUpdate();

            // Respuesta cliente
            hres.createSuccessResponse(f);
        } else {
            String message = "El id que desea utilizar para el formulario(" + f.getId_form() + ") no esta disponible, intente con otro.";
            hres.createResponse(f, message);
        }
    }

    /**
     * Editar formulario
     *
     * @param f
     */
    private void editForm(Form f) {
        readDB();
        Form fm = getForm(f.getId_form());
        if (fm != null) {

            if (f.getTitle() != null) {
                fm.setTitle(f.getTitle());
            }

            if (f.getName() != null) {
                fm.setName(f.getName());
            }

            if (f.getTheme() != null) {
                fm.setTheme(f.getTheme());
            }

            // Actualizar
            executeUpdate();

            // Respuesta cliente
            hres.createSuccessResponse(f);

            // Actualizar nombre de formulario en datos recopilados
            if (f.getName() != null) {
                updateNameFormData(f.getId_form(), fm.getName());
            }
        } else {
            String message = "El formulario que desea editar(" + f.getId_form() + "), no existe.";
            hres.createResponse(f, message);
        }
    }

    /**
     * Actualzar nombre en DB de datos recopilados con formularios
     *
     * @param id
     * @param name
     */
    private void updateNameFormData(String id, String name) {
        readFormsData();
        FormData fd = getFormData(id);
        if (fd != null) {
            fd.setNameForm(name);
        }
        // Actualidar DB
        db.writeFormData(this.formData);
    }

    /**
     * Eliminar formulario
     *
     * @param f
     */
    private void delForm(Form f) {
        readDB();
        Form fm = getForm(f.getId_form());
        if (fm != null) {
            forms.remove(fm);

            // Actualizar
            executeUpdate();

            // Respuesta cliente
            hres.createSuccessResponse(f);
        } else {
            String message = "El formulario que desea eliminar(" + f.getId_form() + "), no existe.";
            hres.createResponse(f, message);
        }
    }

    /**
     * Agregar un componente
     *
     * @param c
     */
    private void addComp(Component c) {
        readDB();
        Form fm = getForm(c.getForm_id());
        if (fm != null) {
            Component cm = getComponent(fm, c.getId_component());
            if (cm == null) {
                c.setIndex(fm.getComponents().size() + 1);
                fm.getComponents().add(c);

                // Actualizar
                executeUpdate();

                // Respuesta cliente
                hres.createSuccessResponse(c);
            } else {
                String message = "El id del componente que desea agregar(" + c.getId_component() + ") no esta disponible, intente con otro.";
                hres.createResponse(c, message);
            }
        } else {
            String message = "El formulario(" + c.getForm_id() + ") al cual se intenta agregar el componente(" + c.getId_component() + "), no existe.";
            hres.createResponse(c, message);
        }
    }

    /**
     * Editar un componente
     *
     * @param c
     */
    private void editComp(Component c) {
        readDB();
        Form fm = getForm(c.getForm_id());

        if (fm != null) {
            Component cm = getComponent(fm, c.getId_component());
            if (cm != null) {
                boolean index = true;
                boolean satisfies = true;

                // INDICE
                if (c.getIndex() != null) {
                    cm.setIndex(c.getIndex());
                    index = orderByIndex(fm.getComponents(), cm);

                    if (!index) {
                        String message = "El indice indicado para el componente(" + c.getId_component() + "), no es valido.";
                        hres.createResponse(c, message);
                    }
                }

                // NOMBRE_CAMPO
                if (c.getFieldName() != null) {
                    cm.setFieldName(c.getFieldName());
                }
                // TEXTO_VISIBLE
                if (c.getText() != null) {
                    cm.setText(c.getText());
                }
                // ALINEACION
                if (c.getAling() != null) {
                    cm.setAling(c.getAling());
                }
                // REQUERIDO
                if (c.getRequired() != null) {
                    cm.setRequired(c.getRequired());
                }

                // Opciones
                if (!c.getOptions().isEmpty()) {
                    cm.setOptions(c.getOptions());
                }
                // URL
                if (c.getUrl() != null) {
                    cm.setUrl(c.getUrl());
                }

                // COLUMNAS
                if (c.getColumns() != null) {
                    cm.setColumns(c.getColumns());
                }

                // FILAS
                if (c.getRows() != null) {
                    cm.setRows(c.getRows());
                }

                // CLASE
                if (c.getKind() != null) {
                    cm.setKind(c.getKind());
                }

                // Agregar elemenos de request para mensaje de respuesta
                cm.setOp(c.getOp());
                cm.setNameRequest(c.getNameRequest());
                cm.setNumber(c.getNumber());
                cm.setLine(c.getLine());
                cm.setColumn(c.getColumn());

                satisfies = checkComponent(cm);

                if (satisfies && index) {
                    // Actualizar
                    executeUpdate();

                    // Respuesta cliente
                    hres.createSuccessResponse(c);
                }

            } else {
                String message = "El componente(" + c.getId_component() + ") que desea editar, no existe.";
                hres.createResponse(c, message);
            }
        } else {
            String message = "El formulario(" + c.getForm_id() + ") indicado para editar el componente(" + c.getId_component() + "), no existe.";
            hres.createResponse(c, message);
        }
    }

    /**
     * Eliminar un componente
     *
     * @param c
     */
    private void delComp(Component c) {
        readDB();
        Form f = getForm(c.getForm_id());

        if (f != null) {
            Component del = getComponent(f, c.getId_component());
            if (del != null) {
                f.getComponents().remove(del);

                // Actualizar indice de los demas componentes
                updateIndex(f.getComponents());

                // Actualizar
                executeUpdate();

                // Respuesta cliente
                hres.createSuccessResponse(c);
            } else {
                String message = "El componente(" + c.getId_component() + ") que intenta eliminar, no existe.";
                hres.createResponse(c, message);
            }
        } else {
            String message = "El formulario(" + c.getForm_id() + ") indicado para eliminar el componente(" + c.getId_component() + "), no existe.";
            hres.createResponse(c, message);
        }
    }

    /**
     * Verificar si el componente cumple con los requisitos establecidos
     *
     * @param c
     * @return
     */
    private boolean checkComponent(Component c) {
        boolean satisfies = true;
        String s = c.getKind();
        String message = "El componente(" + c.getId_component() + ") de tipo " + s + ", ";
        switch (s) {
            case "CHECKBOX":
            case "RADIO":
            case "COMBO":
                if (c.getOptions().isEmpty()) {
                    String m = message + "debe tener el parametro OPCIONES.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }

                if (c.getUrl() != null || c.getColumns() != null || c.getRows() != null) {
                    String m = message + "no debe tener los parametros URL, FILAS Y COLUMNAS.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }
                break;

            case "IMAGEN":
                if (c.getUrl() == null) {
                    String m = message + "debe tener el parametro URL.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }

                if (!c.getOptions().isEmpty() || c.getColumns() != null || c.getRows() != null) {
                    String m = message + "no debe tener los parametros OPCIONES, FILAS Y COLUMNAS.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }
                break;

            case "AREA_TEXTO":
                if (c.getRows() == null || c.getColumns() == null) {
                    String m = message + "debe tener los parametros FILAS y COLUMNAS.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }

                if (!c.getOptions().isEmpty() || c.getUrl() != null) {
                    String m = "no debe tener los parametros OPCIONES y URL.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }

                break;

            default:
                if (!c.getOptions().isEmpty() || c.getUrl() != null || c.getColumns() != null || c.getRows() != null) {
                    String m = message + "no debe tener los parametros OPCIONES, URL, FILAS y COLUMNAS.";
                    hres.createResponse(c, m);
                    satisfies = false;
                }
        }

        return satisfies;
    }

    /**
     * Ordernar componentes por indices
     *
     * @param comps
     * @param c
     */
    private boolean orderByIndex(List<Component> comps, Component c) {
        if (c.getIndex() <= comps.size()) {
            comps.remove(c);
            comps.add(c.getIndex() - 1, c);
            for (int i = 0; i < comps.size(); i++) {
                comps.get(i).setIndex(i + 1);
            }
            return true;
        }
        return false;
    }

    /**
     * Actualizar indice de componentes luego de eliminar uno
     *
     * @param comps
     */
    private void updateIndex(List<Component> comps) {
        for (int i = 0; i < comps.size(); i++) {
            comps.get(i).setIndex(i + 1);
        }
    }

    /**
     * Obtener usuario por username
     *
     * @param str
     * @return
     */
    private User getUser(String str) {
        User user = null;
        for (User u : users) {
            if (u.getUser().equals(str)) {
                user = u;
                break;
            }
        }
        return user;
    }

    /**
     * Obtener formulario por id
     *
     * @param id
     * @return
     */
    private Form getForm(String id) {
        Form f = null;
        for (Form fm : forms) {
            if (fm.getId_form().equals(id)) {
                f = fm;
                break;
            }
        }
        return f;
    }

    /**
     * Obtener componente por id
     *
     * @param f
     * @param id
     * @return
     */
    private Component getComponent(Form f, String id) {
        Component c = null;
        for (Component cm : f.getComponents()) {
            if (cm.getId_component().equals(id)) {
                c = cm;
                break;
            }
        }
        return c;
    }

    /**
     * Escribir en DB
     */
    private void executeUpdate() {
        String json = db.createJson(users, forms);
        //System.out.println(json);
        db.writeData(db.DB_URL, json);
    }

    /**
     * Leer DB
     */
    private void readDB() {
        this.db.readDataBase();
        this.users = this.db.getUsers();
        this.forms = this.db.getForms();
    }

    /**
     * Leer datos recopilados formularios
     */
    private void readFormsData() {
        db.readDataForms();
        this.formData = db.getFmData();
    }

    /**
     * Obtener FormData por id
     *
     * @param id
     * @return
     */
    private FormData getFormData(String id) {
        FormData fd = null;
        for (FormData fm : this.formData) {
            if (fm.getIdForm().equals(id)) {
                fd = fm;
            }
        }
        return fd;
    }

    /**
     * Mensajes de respuesta para cliente
     *
     * @return
     */
    public HandleResponse getHres() {
        return hres;
    }
}
