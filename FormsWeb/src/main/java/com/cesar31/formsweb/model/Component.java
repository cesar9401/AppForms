package com.cesar31.formsweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Component extends Request {

    private String id_component;
    private String fieldName;
    private String form_id;
    private String kind;
    private Integer index;
    private String text;
    private String aling;
    private Boolean required;

    // imagen
    private String url;

    // checkbox, radio y combo
    private List<String> options;

    // text area
    private Integer rows;
    private Integer columns;

    public Component() {
        this.options = new ArrayList<>();
    }

    public Component(String id_component, String fieldName, String form_id) {
        this();
        this.id_component = id_component;
        this.fieldName = fieldName;
        this.form_id = form_id;
    }

    public String getId_component() {
        return id_component;
    }

    public void setId_component(String id_component) {
        this.id_component = id_component;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAling() {
        return aling;
    }

    public void setAling(String aling) {
        this.aling = aling;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return super.toString() + " Component{" + "id_component=" + id_component + ", fieldName=" + fieldName + ", form_id=" + form_id + ", kind=" + kind + ", index=" + index + ", text=" + text + ", aling=" + aling + ", required=" + required + '}';
    }
}
