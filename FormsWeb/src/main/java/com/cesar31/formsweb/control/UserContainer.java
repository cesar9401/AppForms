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
    
    public void addUser(User u) {
        addUser.add(u);
    }
    
    public User setUser(User u, String user) {
        u.setUser(fS(user));
        return u;
    }
    
    public User setPassword(User u, String pass) {
        u.setPassword(fS(pass));
        return u;
    }
    
    public User setDate(String date) {
        User u = new User();
        if(!date.equals("")) {
            u.setCreationDate(fLD(date));
        } else {
            u.setCreationDate(LocalDate.now());
        }
        
        return u;
    }
        
    public String fS(String string) {
        return string.replace("\"", "");
    }
    
    public String fS_(String string) {
        return string.replace("\"", "").trim();
    }

    public static LocalDate fLD(String date) {
        String local = date.replace("\"", "").trim();
        return LocalDate.parse(local);
    }

    public List<User> getAddUser() {
        return addUser;
    }
}