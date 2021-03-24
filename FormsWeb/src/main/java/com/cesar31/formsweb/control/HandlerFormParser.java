package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
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

    private HandlerDB db;

    // Elementos para el parseo
    private List<Request> reqs;
    private List<Error> errors;

    // Elementos de la base de datos
    private List<User> users;
    private List<Form> forms;

    public HandlerFormParser() {
        this.db = new HandlerDB();
        this.reqs = new ArrayList<>();
        this.errors = new ArrayList<>();

        this.users = new ArrayList<>();
        this.forms = new ArrayList<>();
    }

    /**
     * Parsear la entrada y convertirla a requests
     *
     * @param input
     */
    public void parserInput(String input) {
        reqs.clear();
        errors.clear();

        FormsLex lex = new FormsLex(new StringReader(input));
        FormsParser parser = new FormsParser(lex);
        try {
            parser.parse();
            if (parser.isParsed()) {
                reqs = parser.getContainer().getRequests();
                executeRequests();
            } else {
                errors = parser.getContainer().getErrors();
                errors.forEach(e -> {
                    System.out.println(e.toString());
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Ejecutar las peticiones
     */
    private void executeRequests() {
        reqs.forEach(r -> {
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
        
        if(user != null) {
            if(user.getPassword().equals(u.getPassword())) {
                login = user;
            }
        }
        
        if(login == null) {
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
        } else {
            System.out.println("El username que desea utilizar no esta disponible");
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
            } else {
                System.out.println("El userename que desea agregar no esta disponible, intente con otro");
            }
        } else {
            System.out.println("El usuario que desea editar no existe en la DB.");
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
            // delFormsUser(user);
            
            // Actualizar
            executeUpdate();

        } else {
            System.out.println("El usuario que desea eliminar no existe.");
        }
    }
    
    /**
     * Eliminar formularios del usuario
     * @param u 
     */
    private void delFormsUser(User u) {
        for(Form f : forms) {
            if(f.getUser_creation().equals(u.getUser())) {
                forms.remove(f);
            }
        }
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
        } else {
            System.out.println("El id que desea utilizar para el formulario no esta disponible");
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
        } else {
            System.out.println("El formulario que desea editar no existe.");
        }
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
        } else {
            System.out.println("El formulario que desea eliminar no existe");
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
            } else {
                System.out.println("El id del componente que intenta agregar no esta disponible.");
            }
        } else {
            System.out.println("El formulario al cual se intenta agregar el componente, no existe");
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

                satisfies = checkComponent(cm);

                if (satisfies && index) {
                    // Actualizar
                    executeUpdate();
                } else {
                    System.out.println("La solicitud de edicion no puede ser procesada.");
                }

            } else {
                System.out.println("El componente que intenta editar, no existe.");
            }

        } else {
            System.out.println("El formulario al cual le intenta editar un componente, no existe.");
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
            } else {
                System.out.println("El componente que intenta eliminar, no existe");
            }
        } else {
            System.out.println("El formulario al cual le intenta eliminar un componente, no existe");
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
        switch (s) {
            case "CHECKBOX":
            case "RADIO":
            case "COMBO":
                if (c.getOptions().isEmpty()) {
                    System.out.println("El componente de tipo " + s + ", debe tener opciones");
                    satisfies = false;
                }

                if (c.getUrl() != null || c.getColumns() != null || c.getRows() != null) {
                    System.out.println("El componente de tipo " + s + ", no debe tener url, filas y columnas");
                    satisfies = false;
                }
                break;

            case "IMAGEN":
                if (c.getUrl() == null) {
                    System.out.println("El componente de tipo " + s + ", debe de tener url");
                    satisfies = false;
                }

                if (!c.getOptions().isEmpty() || c.getColumns() != null || c.getRows() != null) {
                    System.out.println("El componente de tipo " + s + ", no debe tener opciones, filas y columnas");
                    satisfies = false;
                }
                break;

            case "AREA_TEXTO":
                if (c.getRows() == null || c.getColumns() == null) {
                    System.out.println("El componente de tipo " + s + ", debe tener filas y columnas");
                    satisfies = false;
                }

                if (!c.getOptions().isEmpty() || c.getUrl() != null) {
                    System.out.println("El componente de tipo " + s + ", no debe tener opciones o url");
                    satisfies = false;
                }

                break;

            default:
                if(!c.getOptions().isEmpty() || c.getUrl() != null || c.getColumns() != null || c.getRows() != null) {
                    System.out.println("El componente de tipo " + s + ", no debe tener opciones, url, filas o columnas");
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
        System.out.println(json);
        db.writeDate(json);
    }

    /**
     * Leer DB
     */
    private void readDB() {
        this.db.readDataBase();
        this.users = this.db.getUsers();
        this.forms = this.db.getForms();
    }
}
