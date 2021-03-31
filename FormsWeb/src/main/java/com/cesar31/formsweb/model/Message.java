package com.cesar31.formsweb.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar31
 */
@XmlRootElement
public class Message implements Serializable {

    private String user;
    private String msj;

    public Message() {
    }

    public Message(String user, String msj) {
        this.user = user;
        this.msj = msj;
    }

    @XmlElement(name="usuario")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @XmlElement(name="mensaje")
    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    @Override
    public String toString() {
        return "Message{" + "user=" + user + ", msj=" + msj + '}';
    }
}
