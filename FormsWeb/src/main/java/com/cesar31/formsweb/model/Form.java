package com.cesar31.formsweb.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDate;

/**
 *
 * @author cesar31
 */
public class Form {

    public interface NoView {
    };

    private String id;
    private String title;
    private String name;
    private String theme;
    private String user;
    private String cDate;

    @JsonView(NoView.class)
    private LocalDate creationDate;

    public Form() {
    }

    public Form(String id, String title, String name) {
        this.id = id;
        this.title = title;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Form{" + "id=" + id + ", title=" + title + ", name=" + name + ", theme=" + theme + ", user=" + user + ", creationDate=" + creationDate + '}';
    }
}
