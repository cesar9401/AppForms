package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class UserContainer {

    private List<User> addUser;

    public UserContainer() {
        this.addUser = new ArrayList<>();
    }

    /**
     * Usuarios que seran creados
     *
     * @param u
     */
    public void addUser(User u) {
        addUser.add(u);
    }

    /**
     * Agregar username
     *
     * @param u
     * @param user
     * @return
     */
    public User setUser(User u, String user) {
        u.setUser(fS(user));
        return u;
    }

    /**
     * Agregar password
     *
     * @param u
     * @param pass
     * @return
     */
    public User setPassword(User u, String pass) {
        u.setPassword(fS(pass));
        return u;
    }

    /**
     * Agregar fecha
     *
     * @param u
     * @param date
     * @return
     */
    public User setDate(User u, String date) {
        if (!date.equals("")) {
            u.setCreationDate(fLD(date));
            u.setcDate(fS_(date));
        } else {
            u.setCreationDate(LocalDate.now());
        }

        return u;
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

    public List<User> getAddUser() {
        return addUser;
    }
}
