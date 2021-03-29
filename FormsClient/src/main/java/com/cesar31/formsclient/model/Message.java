package com.cesar31.formsclient.model;

/**
 *
 * @author cesar31
 */
public class Message {

    private String user;
    private String messsage;

    public Message() {
    }

    public Message(String user, String messsage) {
        this.user = user;
        this.messsage = messsage;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    @Override
    public String toString() {
        return "Message{" + "user=" + user + ", messsage=" + messsage + '}';
    }
}
