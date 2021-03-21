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

    private String id;
    private String title;
    private String name;
    private String theme;
    private String user;
    private String cDate;
    
    private List<Component> components;

    @JsonView(NoView.class)
    private LocalDate creationDate;

    public Form() {
        components = new ArrayList<>();
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

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
    
    @Override
    public String toString() {
        return super.toString() + " Form{" + "id=" + id + ", title=" + title + ", name=" + name + ", theme=" + theme + ", user=" + user + ", creationDate=" + creationDate + '}';
    }
}
