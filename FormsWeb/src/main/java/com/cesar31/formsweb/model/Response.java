package com.cesar31.formsweb.model;

/**
 *
 * @author cesar31
 */
public class Response {

    private int request;
    private int file;
    private int column;
    private String type;
    private int message;

    public Response() {
    }

    public Response(int request, int file, int column) {
        this.request = request;
        this.file = file;
        this.column = column;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" + "request=" + request + ", file=" + file + ", column=" + column + ", type=" + type + ", message=" + message + '}';
    }
}
