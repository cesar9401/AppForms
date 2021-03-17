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
    private HashMap<String, String> params;

    public UserContainer() {
        this.addUser = new ArrayList<>();
        params = new HashMap<>();
    }

    /**
     * Usuarios que seran creados
     *
     * @param u
     */
    public void addUser(User u) {
        addUser.add(u);
        params = new HashMap<>();
    }

    /**
     * Agregar username
     *
     * @param user
     */
    public void setUser(Token t, String user) {
        if (!params.containsKey("USUARIO")) {
            params.put("USUARIO", fS(user));
            System.out.println("Se agrego user");
        } else {
            System.out.println("El usuario ya tiene user");
            System.out.println(t.toString());
        }
    }

    /**
     * Agregar password
     *
     * @param pass
     */
    public void setPassword(Token t, String pass) {
        if (!params.containsKey("PASSWORD")) {
            params.put("PASSWORD", fS(pass));
            System.out.println("Se agrego password");
        } else {
            System.out.println("El usuario ya posee password");
            System.out.println(t.toString());
        }
    }

    /**
     * Agregar fecha
     *
     * @param date
     */
    public void setDate(Token t, String date) {
        if (!params.containsKey("FECHA_CREACION")) {
            params.put("FECHA_CREACION", fS_(date));
            System.out.println("Se agrego fecha de creacion");
        } else {
            System.out.println("EL usuario ya tiene fecha de creacion");
            System.out.println(t.toString());
        }

//        if (u == null) {
//            u = new User();
//        }
//
//        if (!date.equals("")) {
//            u.setCreationDate(fLD(date));
//            u.setcDate(fS_(date));
//        } else {
//            u.setCreationDate(LocalDate.now());
//        }
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
        String local = date.replace("\"", "").trim();
        return LocalDate.parse(local);
    }

    /**
     * Parametros para crear usuario
     *
     * @return
     */
    public HashMap<String, Boolean> paramCreateUser() {
        HashMap<String, Boolean> elements = new HashMap<>();
        elements.put("USUARIO", true);
        elements.put("PASSWORD", true);
        elements.put("FECHA_CREACION", false);

        return elements;
    }

    public List<User> getAddUser() {
        return addUser;
    }

}
