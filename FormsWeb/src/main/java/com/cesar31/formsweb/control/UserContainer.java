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
    private List<Error> currentErrors;
    private HashMap<String, String> params;
    private HashMap<String, Token> tokens;
    private HashMap<String, String> editP;

    private FormContainer form;
    private ComponentContainer component;

    public UserContainer() {
        this.addUsers = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.params = new HashMap<>();
        this.tokens = new HashMap<>();
        this.editP = new HashMap<>();
        this.editUsers = new ArrayList<>();
        this.currentErrors = new ArrayList<>();

        this.form = new FormContainer(this);
        this.component = new ComponentContainer(this);
    }

    /**
     * Tipo de peticion
     *
     * @param t
     * @param result
     */
    public void setResult(Token t, String result) {
        if (result != null && t != null) {
            switch (result) {
                case "CREAR":
                    addUser(t);
                    break;
                    
                case "EDIT":
                    editUser(t);
                    break;

                case "DELETE":
                    deleteUser(t);
                    break;

                case "LOGIN":
                    loginUser(t);
                    break;

                case "NUEVO_FORMULARIO":
                    form.addForm(t);
                    break;

                case "ELIMINAR_FORMULARIO":
                    form.delForm(t);
                    break;

                case "MODIFICAR_FORMULARIO":
                    form.editForm(t);
                    break;

                case "AGREGAR_COMPONENTE":
                    component.addComponent(t);
                    break;
                    
                case "ELIMINAR_COMPONENTE":
                    component.delComponent(t);
                    break;
            }
        } else {
            System.out.println("Result/token = null");
        }
    }

    /**
     * Procesar atributos de las peticiones
     *
     * @param t
     * @param u
     * @param param
     */
    public void setNewParam(Token t, String u, String param) {
        if (!params.containsKey(param)) {
            if (param.equals("FECHA_CREACION")) {
                params.put(param, fS_(u));
            } else {
                params.put(param, fS(u));
            }
            tokens.put(param, t);
            //System.out.println(param + ": " + edit.get(param));
        } else {
            Error e = new Error(param, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha indicado " + param + " para la peticion.";
            e.setDescription(description);

            //Revisar currentErrors
            currentErrors.add(e);
            //errors.add(e);
        }
    }

    /**
     * Procesar peticiones de usuarios que seran creados
     *
     * @param t
     */
    private void addUser(Token t) {
        boolean created = true;
        User u = new User();
        String r = "CREAR_USUARIO";

        if (!params.containsKey("USUARIO")) {
            errors.add(getRequestError(t, "USUARIO", r));
            created = false;
        } else {
            u.setUser(params.remove("USUARIO"));
            tokens.remove("USUARIO");
        }

        if (!params.containsKey("PASSWORD")) {
            errors.add(getRequestError(t, "PASSWORD", r));
            created = false;
        } else {
            u.setPassword(params.remove("PASSWORD"));
            tokens.remove("PASSWORD");
        }

        if (!params.containsKey("FECHA_CREACION")) {
            u.setCreationDate(LocalDate.now());
            u.setcDate(LocalDate.now().toString());
        } else {
            u.setCreationDate(fLD(params.get("FECHA_CREACION")));
            u.setcDate(params.get("FECHA_CREACION"));

            params.remove("FECHA_CREACION");
            tokens.remove("FECHA_CREACION");
        }

        // Revisar Errores en currentErrors
        if (!currentErrors.isEmpty()) {
            currentErrors.forEach(c -> {
                if (c.getLexema().equals("USUARIO") || c.getLexema().equals("PASSWORD") || c.getLexema().equals("FECHA_CREACION")) {
                    String description = "Ya se ha indicado " + c.getLexema() + " para la peticion " + r + ".";
                    c.setDescription(description);
                    errors.add(c);
                }
            });
            currentErrors.clear();
            created = false;
        }

        // Agregar errores de parametros que no se deben incluir
        if (!tokens.isEmpty()) {
            freeTokens(r);
            created = false;
        }

        if (created) {
            System.out.println("Agregado: " + u.toString());
            addUsers.add(u);
        }

        // Limpiar HashMap
        params.clear();
        tokens.clear();
    }

    /**
     * Procesar peticiones login
     *
     * @param t
     */
    private void loginUser(Token t) {
        boolean created = true;
        User u = new User();
        String r = "LOGIN_USUARIO";

        if (!params.containsKey("USUARIO")) {
            errors.add(getRequestError(t, "USUARIO", r));
            created = false;

        } else {
            u.setUser(params.remove("USUARIO"));
            tokens.remove("USUARIO");
        }

        if (!params.containsKey("PASSWORD")) {
            errors.add(getRequestError(t, "PASSWORD", r));
            created = false;
        } else {
            u.setPassword(params.remove("PASSWORD"));
            tokens.remove("PASSWORD");
        }

        //Verificar errores
        if (!currentErrors.isEmpty()) {
            currentErrors.forEach(c -> {
                if (c.getLexema().equals("USUARIO") || c.getLexema().equals("PASSWORD")) {
                    String description = "Ya se ha indicado " + c.getLexema() + " para la peticion " + r + ".";
                    c.setDescription(description);
                    errors.add(c);
                }
            });
            currentErrors.clear();
            created = false;
        }

        //Verificar que no esten los otros parametros
        if (!tokens.isEmpty()) {
            freeTokens(r);
            created = false;
        }

        if (created) {
            System.out.println("login: " + u.toString());
        }

        // Limpiar HashMap
        params.clear();
        tokens.clear();
    }

    /**
     * Procesar peticiones para eliminar usuarios
     *
     * @param t
     */
    private void deleteUser(Token t) {
        boolean created = true;
        User u = new User();
        String r = "ELIMINAR_USUARIO";

        if (!params.containsKey("USUARIO")) {
            errors.add(getRequestError(t, "USUARIO", r));
            created = false;
        } else {
            u.setUser(params.remove("USUARIO"));
            tokens.remove("USUARIO");
        }

        //Verificar errores
        if (!currentErrors.isEmpty()) {
            currentErrors.forEach(c -> {
                if (c.getLexema().equals("USUARIO")) {
                    String description = "Ya se ha indicado " + c.getLexema() + " para la peticion " + r + ".";
                    c.setDescription(description);
                    errors.add(c);
                }
            });
            currentErrors.clear();
            created = false;
        }

        //Verificar que no esten los otros parametros
        if (!tokens.isEmpty()) {
            freeTokens(r);
            created = false;
        }

        if (created) {
            System.out.println("Eliminado: " + u.toString());
        }

        // Limpiar HashMap
        params.clear();
        tokens.clear();
    }

    /**
     * Obtener los parametros para los usuarios que seran editados
     *
     * @param t
     * @param u
     * @param param
     */
    public void setEditParam(Token t, String u, String param) {
        if (!editP.containsKey(param)) {
            if (param.equals("FECHA_MODIFICACION")) {
                editP.put(param, fS_(u));
            } else {
                editP.put(param, fS(u));
            }
            //System.out.println(param + ": " + edit.get(param));
        } else {
            Error e = new Error(param, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha indicado " + param + " para la peticion.";
            e.setDescription(description);
            //System.out.println(e.toString());
            errors.add(e);
        }
    }

    /**
     * Procesar peticiones para editar usuarios
     *
     * @param t
     */
    private void editUser(Token t) {
        boolean created = true;
        User u = new User();
        String r = "MODIFICAR_USUARIO";

        if (!editP.containsKey("USUARIO_ANTIGUO")) {
            errors.add(getRequestError(t, "USUARIO_ANTIGUO", r));
            created = false;
        } else {
            u.setUser(editP.get("USUARIO_ANTIGUO"));
        }

        if (!editP.containsKey("USUARIO_NUEVO")) {
            errors.add(getRequestError(t, "USUARIO_NUEVO", r));
            created = false;
        } else {
            u.setNewUser(editP.get("USUARIO_NUEVO"));
        }

        if (!editP.containsKey("NUEVO_PASSWORD")) {
            errors.add(getRequestError(t, "NUEVO_PASSWORD", r));
            created = false;
        } else {
            u.setPassword(editP.get("NUEVO_PASSWORD"));
        }

        if (!editP.containsKey("FECHA_MODIFICACION")) {
            u.setEditDate(LocalDate.now());
            u.seteDate(LocalDate.now().toString());
        } else {
            u.setEditDate(fLD(editP.get("FECHA_MODIFICACION")));
            u.seteDate(editP.get("FECHA_MODIFICACION"));
        }

        if (created) {
            System.out.println("Editar: " + u.toString());
            editUsers.add(u);
        }

        // Limpiar HashMap
        editP.clear();
    }

    /**
     * Errores por falta de atributos en las peticiones
     *
     * @param t
     * @param param
     * @param request
     * @return
     */
    public Error getRequestError(Token t, String param, String request) {
        Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
        e.setDescription("En la peticion " + request + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir " + param + ".");
        return e;
    }

    /**
     * Errores por atributos que no se deben incluir en peticiones
     *
     * @param r
     */
    public void freeTokens(String r) {
        tokens.forEach((s, token) -> {
            Error e = new Error(s, "SINTACTICO", token.getX(), token.getY());
            e.setDescription("En la peticion " + r + ", no se debe incluir " + s + ".");
            errors.add(e);
        });
    }

    /**
     * Errores
     *
     * @param symbol
     * @param type
     * @param expectedTokens
     */
    public void setError(Symbol symbol, String type, List<String> expectedTokens) {
        Token t = (Token) symbol.value;
        String typeError = (type.equals("SYMB") || type.equals("ERROR")) ? "LEXICO" : "SINTACTICO";

        Error e = new Error(t.getValue(), typeError, t.getX(), t.getY());

        String description = (typeError.equals("LEXICO")) ? "La cadena no se reconoce en el lenguaje. " : "";
        description += "Se encontro: " + type + ". ";
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

    public String fS(String string) {
        return string.replace("\"", "");
    }

    public String fS_(String string) {
        return string.replace("\"", "").trim();
    }

    public LocalDate fLD(String date) {
        return LocalDate.parse(date.replace("\"", "").trim());
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

    public List<Error> getCurrentErrors() {
        return currentErrors;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public HashMap<String, Token> getTokens() {
        return tokens;
    }
}
