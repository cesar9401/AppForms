package com.cesar31.formsweb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class FormData extends Request{
    
    public interface NoView {
    };
    
    private String idForm;
    private String nameForm;
    private List<HashMap<String, String>> data;
    
    public FormData() {
        this.data = new ArrayList<>();
    }
    
    public FormData(String idForm, String nameForm) {
        this();
        this.idForm = idForm;
        this.nameForm = nameForm;
    }

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public String getNameForm() {
        return nameForm;
    }

    public void setNameForm(String nameForm) {
        this.nameForm = nameForm;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FormData{" + "idForm=" + idForm + ", nameForm=" + nameForm + '}';
    }
}
