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
                }
            }
        });
    }

    private void addUser(User u) {
        readDB();
        if (userNameIsAvailable(u.getUser())) {
            users.add(u);

            // Actualizar
            executeUpdate();
        } else {
            System.out.println("El username que desea utilizar no esta disponible.");
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
        User u = null;
        for (User us : users) {
            if (us.getUser().equals(edit.getUser())) {
                u = us;
            }
        }

        if (u != null) {
            if (userNameIsAvailable(edit.getNewUser())) {
                u.setUser(edit.getNewUser());
                u.setPassword(edit.getPassword());
                u.setEditDate(edit.getEditDate());
                u.seteDate(edit.geteDate());

                // Actualizar
                executeUpdate();
            } else {
                System.out.println("El username que desea agregar no esta disponible, intente con otro.");
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

        if (!userNameIsAvailable(u.getUser())) {
            for (User us : users) {
                if (us.getUser().equals(u.getUser())) {
                    users.remove(us);

                    //Actualizar
                    executeUpdate();
                    break;
                }
            }
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
        if (idFormIsAvailable(f.getId_form())) {
            forms.add(f);

            executeUpdate();
        } else {
            System.out.println("El id que desea usar para el formulario no esta disponible");
        }
    }

    /**
     * Editar formulario
     *
     * @param f
     */
    private void editForm(Form f) {
        readDB();

        System.out.println(f.toString());

        if (!idFormIsAvailable(f.getId_form())) {
            for (Form form : forms) {
                if (form.getId_form().equals(f.getId_form())) {
                    System.out.println(form.toString());

                    if (f.getTitle() != null) {
                        form.setTitle(f.getTitle());
                    }

                    if (f.getName() != null) {
                        form.setName(f.getName());
                    }

                    if (f.getTheme() != null) {
                        form.setTheme(f.getTheme());
                    }

                    System.out.println(form.toString());

                    // Actualizar
                    executeUpdate();
                    break;
                }
            }
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
        if (!idFormIsAvailable(f.getId_form())) {
            for (Form form : forms) {
                if (form.getId_form().equals(f.getId_form())) {
                    forms.remove(form);

                    // Actualizar
                    executeUpdate();
                    break;
                }
            }
        } else {
            System.out.println("El formulario que desea eliminar no existe");
        }
    }

    private void addComp(Component c) {
        readDB();
        if (!idFormIsAvailable(c.getForm_id())) {
            for(Form f : forms) {
                if(f.getId_form().equals(c.getForm_id())) {
                    if(idComponentIsAvailable(f.getComponents(), c.getId_component())) {
                        c.setIndex(f.getComponents().size() + 1);
                        f.getComponents().add(c);
                        
                        // Actualizar
                        executeUpdate();
                    } else {
                        System.out.println("El id del componente que intenta agregar no esta disponible.");
                    }
                    
                    break;
                }
            }
        } else {
            System.out.println("El formulario al cual se intenta agregar el componente, no existe");
        }
    }

    /**
     * Verificar si el username esta disponible
     *
     * @param nuevo
     * @return
     */
    private boolean userNameIsAvailable(String string) {
        for (User u : users) {
            if (u.getUser().equals(string)) {
                return false;
            }
        }
        return true;
        //return users.stream().noneMatch(us -> (us.getUser().equals(string)));
    }

    /**
     * Verificar que el id del formulario este disponible
     *
     * @param id
     * @return
     */
    private boolean idFormIsAvailable(String id) {
        for (Form f : forms) {
            if (f.getId_form().equals(id)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean idComponentIsAvailable(List<Component> comp, String id) {
        for(Component c : comp) {
            if(c.getId_component().equals(id)) {
                return false;
            }
        }
        return true;
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
