package com.cesar31.formsweb.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDate;

/**
 *
 * @author cesar31
 */
public class User extends Request {

    private String user;
    private String password;
    private String cDate_user;
    private String eDate;

    @JsonView(NoView.class)
    private String newUser;

    @JsonView(NoView.class)
    private LocalDate editDate;

    @JsonView(NoView.class)
    private LocalDate creationDate;

    public User() {
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public User(String user, String password, LocalDate creationDate) {
        this.user = user;
        this.password = password;
        this.creationDate = creationDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public LocalDate getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDate editDate) {
        this.editDate = editDate;
    }

    public String getcDate_user() {
        return cDate_user;
    }

    public void setcDate_user(String cDate_user) {
        this.cDate_user = cDate_user;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    @Override
    public String toString() {
        return super.toString() + " User{" + "user=" + user + ", password=" + password + ", cDate_user=" + cDate_user + ", eDate=" + eDate + ", newUser=" + newUser + ", editDate=" + editDate + ", creationDate=" + creationDate + '}';
    }
}
