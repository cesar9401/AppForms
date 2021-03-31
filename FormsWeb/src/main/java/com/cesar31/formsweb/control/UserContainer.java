package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Error;
import com.cesar31.formsweb.model.Operation;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.model.Token;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class UserContainer {

    private String user;

    private List<Error> errors;
    private List<Error> currentErrors;
    private HashMap<String, Token> params;
    private HashMap<String, Token> tokens;

    private HandleError handle;

    // Request
    private List<Request> requests;

    // Formularios
    private FormContainer form;

    // Componentes
    private ComponentContainer component;

    // Consultas
    private SQForm sqf;

    public UserContainer() {
        this.errors = new ArrayList<>();
        this.params = new HashMap<>();
        this.tokens = new HashMap<>();
        this.currentErrors = new ArrayList<>();

        this.handle = new HandleError();

        this.requests = new ArrayList<>();
        this.form = new FormContainer(this);
        this.component = new ComponentContainer(this);
        this.sqf = new SQForm(this);
    }

    /**
     * Tipo de peticion
     *
     * @param t
     * @param result
     */
    public void setResult(Token t, String result) {
        if (result != null && t != null) {
            //System.out.println(t.toString());
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

                case "MODIFICAR_COMPONENTE":
                    component.editComponent(t);
                    break;

                case "SQForm":
                    sqf.createSQF(t);
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
    public void setNewParam(Token t, Token u, String param) {
        if (!params.containsKey(param)) {
            //params.put(param, fS(u));
            params.put(param, u);
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
            if (!haveSpace("USUARIO")) {
                u.setUser(getParam("USUARIO"));
            } else {
                setErrorSpace(r, "USUARIO");
                created = false;
            }
        }

        if (!params.containsKey("PASSWORD")) {
            errors.add(getRequestError(t, "PASSWORD", r));
            created = false;
        } else {
            if (!haveSpace("PASSWORD")) {
                u.setPassword(getParam("PASSWORD"));
            } else {
                setErrorSpace(r, "PASSWORD");
                created = false;
            }
        }

        if (!params.containsKey("FECHA_CREACION")) {
            u.setCreationDate(LocalDate.now());
            u.setcDate_user(LocalDate.now().toString());
        } else {
            //String string = params.get("FECHA_CREACION");
            LocalDate date = getDate("FECHA_CREACION", r);
            if (date != null) {
                u.setCreationDate(date);
                u.setcDate_user(u.getCreationDate().toString());
            } else {
                created = false;
            }
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
            u.setOp(Operation.ADD);
            addRequest(t, r, u);
            //System.out.println(u.toString());
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
            if (!haveSpace("USUARIO")) {
                u.setUser(getParam("USUARIO"));
            } else {
                setErrorSpace(r, "USUARIO");
                created = false;
            }
        }

        if (!params.containsKey("PASSWORD")) {
            errors.add(getRequestError(t, "PASSWORD", r));
            created = false;
        } else {
            if (!haveSpace("PASSWORD")) {
                u.setPassword(getParam("PASSWORD"));
            } else {
                setErrorSpace(r, "PASSWORD");
                created = false;
            }
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
            //System.out.println("login: " + u.toString());
            u.setOp(Operation.LOGIN);
            addRequest(t, r, u);
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
            if (!haveSpace("USUARIO")) {
                u.setUser(getParam("USUARIO"));
            } else {
                setErrorSpace(r, "USUARIO");
                created = false;
            }
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
            u.setOp(Operation.DEL);
            addRequest(t, r, u);
            //System.out.println("Eliminado: " + u.toString());
        }

        // Limpiar HashMap
        params.clear();
        tokens.clear();
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

        if (!params.containsKey("USUARIO_ANTIGUO")) {
            errors.add(getRequestError(t, "USUARIO_ANTIGUO", r));
            created = false;
        } else {
            if (!haveSpace("USUARIO_ANTIGUO")) {
                u.setUser(getParam("USUARIO_ANTIGUO"));
            } else {
                setErrorSpace(r, "USUARIO_ANTIGUO");
                created = false;
            }
        }

        if (!params.containsKey("USUARIO_NUEVO")) {
            errors.add(getRequestError(t, "USUARIO_NUEVO", r));
            created = false;
        } else {
            if (!haveSpace("USUARIO_NUEVO")) {
                u.setNewUser(getParam("USUARIO_NUEVO"));
            } else {
                setErrorSpace(r, "USUARIO_NUEVO");
                created = false;
            }
        }

        if (!params.containsKey("NUEVO_PASSWORD")) {
            errors.add(getRequestError(t, "NUEVO_PASSWORD", r));
            created = false;
        } else {
            if (!haveSpace("NUEVO_PASSWORD")) {
                u.setPassword(getParam("NUEVO_PASSWORD"));
            } else {
                setErrorSpace(r, "NUEVO_PASSWORD");
                created = false;
            }
        }

        if (!params.containsKey("FECHA_MODIFICACION")) {
            u.setEditDate(LocalDate.now());
            u.seteDate(LocalDate.now().toString());
        } else {
            //String string = params.get("FECHA_MODIFICACION");
            LocalDate date = getDate("FECHA_MODIFICACION", r);
            if (date != null) {
                u.setEditDate(date);
                u.seteDate(u.getEditDate().toString());
            } else {
                created = false;
            }
        }

        if (!currentErrors.isEmpty()) {
            currentErrors.forEach(e -> {
                String s = e.getLexema();
                if (s.equals("USUARIO_ANTIGUO") || s.equals("USUARIO_NUEVO") || s.equals("NUEVO_PASSWORD") || s.equals("FECHA_MODIFICACION")) {
                    String description = "Ya se ha indicado " + s + " para la peticion " + r + ".";
                    e.setDescription(description);
                    errors.add(e);
                }
            });
            currentErrors.clear();
            created = false;
        }

        if (!tokens.isEmpty()) {
            freeTokens(r);
            created = false;
        }

        if (created) {
            u.setOp(Operation.EDIT);
            addRequest(t, r, u);
            //System.out.println("Editar: " + u.toString());
        }

        // Limpiar HashMap
        params.clear();
        tokens.clear();
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

        String value = (type.equals("EOF")) ? "Fin de entrada" : t.getValue();

        Error e = new Error(value, typeError, t.getX(), t.getY());

        String description = (typeError.equals("LEXICO")) ? "La cadena no se reconoce en el lenguaje. " : "";
        description += "Se encontro: " + value + ". ";
        description += "Se esperaba: ";
        for (int i = 0; i < expectedTokens.size(); i++) {
            if (!expectedTokens.get(i).equals("error")) {
                description = description.concat("'" + handle.getValue(expectedTokens.get(i)) + "'");
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

    public String getParam(String param) {
        this.tokens.remove(param);
        //return this.params.remove(param).trim();
        return fS(this.params.remove(param).getValue()).trim();

    }

    /**
     * Verificar si no tiene espacios
     *
     * @param param
     * @return
     */
    public boolean haveSpace(String param) {
        //return params.get(param).contains(" "); //Solo espacios
        //return params.get(param).contains(" ") || params.get(param).contains("\t") || params.get(param).contains("\n");
        String s = fS(params.get(param).getValue());
        return s.contains(" ") || s.contains("\t") || s.contains("\n");
    }

    /**
     * Errores por espacios
     *
     * @param r
     * @param param
     */
    public void setErrorSpace(String r, String param) {
        Token u = params.get(param);
        Error e = new Error(u.getValue(), "SINTACTICO", u.getX(), u.getY());
        String description = "En la peticion " + r + ", el valor para el parametro " + param + ", no debe de incluir espacios, tabulaciones o saltos de linea.";
        e.setDescription(description);
        errors.add(e);

        // Eliminar de tokens y params
        //tokens.remove(param);
        //params.remove(param);
        getParam(param);
    }

    /**
     * Convertir param a LocalDate o lanzar exception
     *
     * @param param
     * @param r
     * @return
     */
    public LocalDate getDate(String param, String r) {
        Token u = params.get(param);
        LocalDate date = null;
        String string = getParam(param);
        try {
            date = LocalDate.parse(string);
        } catch (DateTimeParseException ex) {
            Error e = new Error(u.getValue(), "SEMANTICO", u.getX(), u.getY());
            String description = "En la peticion " + r + ", el valor para el parametro " + param + "(" + string + "), no es valido";
            e.setDescription(description);
            errors.add(e);
        }
        return date;
    }

    /**
     * Agregar peticion
     *
     * @param t
     * @param name
     * @param r
     */
    public void addRequest(Token t, String name, Request r) {
        r.setNameRequest(name);
        r.setNumber(requests.size() + 1);
        r.setLine(t.getX());
        r.setColumn(t.getY());
        requests.add(r);
    }
    
    public void setParamSQF(String key, Token t) {
        this.sqf.setParam(key, t);
    }

    /**
     * Remover comillas
     *
     * @param string
     * @return
     */
    public String fS(String string) {
        return string.replace("\"", "");
    }

    public String fS_(String string) {
        return string.replace("\"", "").trim();
    }

    public LocalDate fLD(String date) {
        return LocalDate.parse(date.replace("\"", "").trim());
    }

    public List<Error> getErrors() {
        return errors;
    }

    public List<Error> getCurrentErrors() {
        return currentErrors;
    }

    public HashMap<String, Token> getParams() {
        return params;
    }

    public HashMap<String, Token> getTokens() {
        return tokens;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public HandleError getHandle() {
        return handle;
    }
}
