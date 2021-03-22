package com.cesar31.formsweb.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Form extends Request{

    public interface NoView {
    };

    private String id_form;
    private String title;
    private String name;
    private String theme;
    private String user_creation;
    private String cDate_form;
    
    private List<Component> components;

    @JsonView(NoView.class)
    private LocalDate creationDate;

    public Form() {
        components = new ArrayList<>();
    }

    public Form(String id_form, String title, String name) {
        this();
        this.id_form = id_form;
        this.title = title;
        this.name = name;
    }

    public String getId_form() {
        return id_form;
    }

    public void setId_form(String id_form) {
        this.id_form = id_form;
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

    public String getUser_creation() {
        return user_creation;
    }

    public void setUser_creation(String user_creation) {
        this.user_creation = user_creation;
    }

    public String getcDate_form() {
        return cDate_form;
    }

    public void setcDate_form(String cDate_form) {
        this.cDate_form = cDate_form;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    @Override
    public String toString() {
        return super.toString() + " Form{" + "id_form=" + id_form + ", title=" + title + ", name=" + name + ", theme=" + theme + ", user_creation=" + user_creation + ", creationDate=" + creationDate + '}';
    }
}
