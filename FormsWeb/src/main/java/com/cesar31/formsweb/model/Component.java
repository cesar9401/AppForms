package com.cesar31.formsweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Component extends Request {

    private String id;
    private String fieldName;
    private String form;
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

    public Component(String id, String fieldName, String form) {
        this.id = id;
        this.fieldName = fieldName;
        this.form = form;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
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
        return super.toString() + " Component{" + "id=" + id + ", fieldName=" + fieldName + ", form=" + form + ", kind=" + kind + ", index=" + index + ", text=" + text + ", aling=" + aling + ", required=" + required + '}';
    }
}
