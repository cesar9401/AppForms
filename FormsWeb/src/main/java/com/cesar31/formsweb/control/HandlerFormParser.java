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
                        System.out.println("Login " + ((User) r).getUser());
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
                        break;

                    case DEL:
                        delComp((Component) r);
                        break;
                }
            }
        });
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

            // Actualizar
            executeUpdate();

        } else {
            System.out.println("El usuario que desea eliminar no existe.");
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

    private void readDB() {
        this.db.readDataBase();
        this.users = this.db.getUsers();
        this.forms = this.db.getForms();
    }
}
