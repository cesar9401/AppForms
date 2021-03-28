package com.cesar31.formsweb.model;

/**
 *
 * @author cesar31
 */
public class Response {

    private int request;
    private int line;
    private int column;
    private String type;
    private String message;

    public Response() {
    }

    public Response(int request, int line, int column) {
        this.request = request;
        this.line = line;
        this.column = column;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" + "request=" + request + ", line=" + line + ", column=" + column + ", type=" + type + ", message=" + message + '}';
    }
}
