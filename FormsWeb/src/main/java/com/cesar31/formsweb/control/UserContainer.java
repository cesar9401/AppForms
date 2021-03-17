package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.User;
import com.cesar31.formsweb.parser.main.Token;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class UserContainer {

    private List<User> addUser;
    private List<Error> errors;
    private HashMap<String, String> params;

    public UserContainer() {
        this.addUser = new ArrayList<>();
        this.errors = new ArrayList<>();
        params = new HashMap<>();
    }

    public void setResult(Token t, String result) {
        switch (result) {
            case "CREAR":
                addUser(t);
                break;
        }
    }

    /**
     * Usuarios que seran creados
     *
     * @param t
     */
    public void addUser(Token t) {
        boolean created = true;
        User u = new User();

        if (!params.containsKey("USUARIO")) {
            Error e = new Error("", "SINTACTITO", t.getX(), t.getY());
            e.setDescription("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir USUARIO");
            errors.add(e);

            //System.out.println("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir USUARIO");
            created = false;
        } else {
            u.setUser(params.get("USUARIO"));
        }

        if (!params.containsKey("PASSWORD")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir PASSWORD");
            errors.add(e);
            
            //System.out.println("En la peticion para CREAR_USUARIO, fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir PASSWORD");
            created = false;
        } else {
            u.setPassword(params.get("PASSWORD"));
        }

        if (!params.containsKey("FECHA_CREACION")) {
            u.setCreationDate(LocalDate.now());
            u.setcDate(LocalDate.now().toString());
        } else {
            u.setCreationDate(fLD(params.get("FECHA_CREACION")));
            u.setcDate(params.get("FECHA_CREACION"));
        }

        if (created) {
            System.out.println(u.toString());
            addUser.add(u);
        }

        // Limpiar HashMap
        params.clear();
    }

    /**
     * Agregar username
     *
     * @param t
     * @param user
     */
    public void setUser(Token t, String user) {
        if (!params.containsKey("USUARIO")) {
            params.put("USUARIO", fS(user));
            //System.out.println("Se agrego user");
        } else {
            Error e = new Error("USUARIO", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("Ya se ha indicado username para el usuario que se desea crear");
            errors.add(e);
            
            System.out.println("El usuario ya tiene user -> " + t.toString());
        }
    }

    /**
     * Agregar password
     *
     * @param t
     * @param pass
     */
    public void setPassword(Token t, String pass) {
        if (!params.containsKey("PASSWORD")) {
            params.put("PASSWORD", fS(pass));
            //System.out.println("Se agrego password");
        } else {
            Error e = new Error("PASSWORD", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("Ya se ha indicado un password para el usuario que se desea crear");
            
            System.out.println("El usuario ya posee password -> " + t.toString());
        }
    }

    /**
     * Agregar fecha
     *
     * @param t
     * @param date
     */
    public void setDate(Token t, String date) {
        if (!params.containsKey("FECHA_CREACION")) {
            params.put("FECHA_CREACION", fS_(date));
            //System.out.println("Se agrego fecha de creacion");
        } else {
            Error e = new Error("FECHA_CREACION", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("Ya se ha indicado una fecha de cracion para el usuario que se desea crear");
            errors.add(e);
            
            System.out.println("EL usuario ya tiene fecha de creacion -> " + t.toString());
        }
    }

    /**
     * Eliminar comillas
     *
     * @param string
     * @return
     */
    public String fS(String string) {
        return string.replace("\"", "");
    }

    /**
     * Eliminar comillas y espacios al inicio y final
     *
     * @param string
     * @return
     */
    public String fS_(String string) {
        return string.replace("\"", "").trim();
    }

    /**
     * String a LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate fLD(String date) {
        return LocalDate.parse(date);
    }

    public List<User> getAddUser() {
        return addUser;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
