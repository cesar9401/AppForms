package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DaoDB {

    private List<User> users;

    public DaoDB() {
        this.users = new ArrayList<>();
    }

    /**
     * Crear usuario desde DB
     *
     * @param user
     * @param pass
     * @param cDate
     * @param eDate
     * @return
     */
    public static User createUser(String user, String pass, String cDate, String eDate) {
        User u = new User(user, pass);
        u.setCreationDate(LocalDate.parse(cDate));
        u.setcDate(cDate);

        if (!eDate.equals("null")) {
            u.setEditDate(LocalDate.parse(eDate));
            u.seteDate(eDate);
        } else {
            u.setEditDate(null);
        }

        return u;
    }

    /**
     * Agregar usuario a lista de usuarios
     *
     * @param u
     */
    public void setUser(User u) {
        users.add(u);
    }

    public List<User> getUsers() {
        return this.users;
    }
}
