package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.main.Token;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class UserContainer {

    private List<User> addUsers;
    private List<User> editUsers;
    private List<Error> errors;
    private List<Error> currents;
    private HashMap<String, String> create;
    private HashMap<String, String> edit;

    public UserContainer() {
        this.addUsers = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.create = new HashMap<>();
        this.edit = new HashMap<>();
        this.editUsers = new ArrayList<>();
        this.currents = new ArrayList<>();
    }

    public void setResult(Token t, String result) {
        if (result != null && t != null) {
            switch (result) {
                case "CREAR":
                    addUser(t);
                    break;
                case "EDIT":
                    editUser(t);
                    break;

                case "LOGIN":
                    System.out.println("Login");
                    break;
            }
        } else {
            System.out.println("Result/token = null");
        }

    }

    public void setError(Symbol symbol, String type, List<String> expectedTokens) {
        Token t = (Token) symbol.value;
        String typeError = (type.equals("SYMB") || type.equals("ERROR")) ? "LEXICO" : "SINTACTICO";

        Error e = new Error(t.getValue(), typeError, t.getX(), t.getY());

        String description = (typeError.equals("LEXICO")) ? "La cadena no se reconoce en el lenguaje. " : "";
        description += "Se esperaba: ";
        for (int i = 0; i < expectedTokens.size(); i++) {
            if (!expectedTokens.get(i).equals("error")) {
                description = description.concat("'" + expectedTokens.get(i) + "'");
                if (i == expectedTokens.size() - 1) {
                    description = description.concat(".");
                } else {
                    description = description.concat(", ");
                }
            }
        }
        e.setDescription(description);

        errors.add(e);
    }

    /**
     * Usuarios que seran creados
     *
     * @param t
     */
    public void addUser(Token t) {
        boolean created = true;
        User u = new User();

        if (!create.containsKey("USUARIO")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir USUARIO.");
            errors.add(e);

            created = false;
        } else {
            u.setUser(create.get("USUARIO"));
        }

        if (!create.containsKey("PASSWORD")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir PASSWORD.");
            errors.add(e);

            created = false;
        } else {
            u.setPassword(create.get("PASSWORD"));
        }

        if (!create.containsKey("FECHA_CREACION")) {
            u.setCreationDate(LocalDate.now());
            u.setcDate(LocalDate.now().toString());
        } else {
            u.setCreationDate(fLD(create.get("FECHA_CREACION")));
            u.setcDate(create.get("FECHA_CREACION"));
        }

        if (created) {
            addUsers.add(u);
        }

        // Limpiar HashMap
        create.clear();
    }

    public void setNewParam(Token t, String u, String param) {
        if (!create.containsKey(param)) {
            if (param.equals("FECHA_CREACION")) {
                create.put(param, fS_(u));
            } else {
                create.put(param, fS(u));
            }
            //System.out.println(param + ": " + edit.get(param));
        } else {
            Error e = new Error(param, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha indicado " + param + " para el usuario que desea crear.";
            e.setDescription(description);
            System.out.println(e.toString());
            
            //Revisar currents
            currents.add(e);
            errors.add(e);
        }
    }

    /**
     * Usuarios que seran editados
     *
     * @param t
     */
    public void editUser(Token t) {
        boolean created = true;
        User u = new User();

        if (!edit.containsKey("USUARIO_ANTIGUO")) {
            errors.add(getEditError(t, "USUARIO_ANTIGUO"));
            created = false;
        } else {
            u.setUser(edit.get("USUARIO_ANTIGUO"));
        }

        if (!edit.containsKey("USUARIO_NUEVO")) {
            errors.add(getEditError(t, "USUARIO_NUEVO"));
            created = false;
        } else {
            u.setNewUser(edit.get("USUARIO_NUEVO"));
        }

        if (!edit.containsKey("NUEVO_PASSWORD")) {
            errors.add(getEditError(t, "NUEVO_PASSWORD"));
            created = false;
        } else {
            u.setPassword(edit.get("NUEVO_PASSWORD"));
        }

        if (!edit.containsKey("FECHA_MODIFICACION")) {
            u.setEditDate(LocalDate.now());
            u.seteDate(LocalDate.now().toString());
        } else {
            u.setEditDate(fLD(edit.get("FECHA_MODIFICACION")));
            u.seteDate(edit.get("FECHA_MODIFICACION"));
        }

        if (created) {
            editUsers.add(u);
        }

        // Limpiar HashMap
        edit.clear();
    }

    private Error getEditError(Token t, String param) {
        Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
        e.setDescription("En la peticion para MODIFICAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir " + param + ".");
        return e;
    }

    /**
     * Obtener los parametros para los usuarios que seran editados
     *
     * @param t
     * @param u
     * @param param
     */
    public void setEditParam(Token t, String u, String param) {
        if (!edit.containsKey(param)) {
            if (param.equals("FECHA_MODIFICACION")) {
                edit.put(param, fS_(u));
            } else {
                edit.put(param, fS(u));
            }
            //System.out.println(param + ": " + edit.get(param));
        } else {
            Error e = new Error(param, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha indicado " + param + " para el usuario que desea modificar.";
            e.setDescription(description);
            //System.out.println(e.toString());
            errors.add(e);
        }
    }

    public String fS(String string) {
        return string.replace("\"", "");
    }

    public String fS_(String string) {
        return string.replace("\"", "").trim();
    }

    public static LocalDate fLD(String date) {
        return LocalDate.parse(date);
    }

    public List<User> getAddUsers() {
        return addUsers;
    }

    public List<User> getEditUsers() {
        return editUsers;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
