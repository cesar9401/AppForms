package com.cesar31.formsweb.control;

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
            }
        });
    }

    private void addUser(User u) {
        readDB();
        if (userNameIsAvailable(u.getUser())) {
            users.add(u);
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
    
    private void delUser(User u) {
        readDB();
        
        if(!userNameIsAvailable(u.getUser())) {
            for(User us : users) {
                if(us.getUser().equals(u.getUser())) {
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
     * Verificar si el username esta disponible para editar
     * @param nuevo
     * @return 
     */
    private boolean userNameIsAvailable(String string) {
        for(User u : users) {
            if(u.getUser().equals(string)) {
                return false;
            }
        }
        return true;
        //return users.stream().noneMatch(us -> (us.getUser().equals(string)));
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
