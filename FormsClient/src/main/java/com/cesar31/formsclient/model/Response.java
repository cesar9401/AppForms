package com.cesar31.formsclient.model;

/**
 *
 * @author cesar31
 */
public class Response {
    
    private int numberRequest;
    private int line;
    private int column;
    private String typeRequest;
    private String message;

    public Response() {
    }

    public Response(int numberRequest, int line, int column, String typeRequest, String message) {
        this.numberRequest = numberRequest;
        this.line = line;
        this.column = column;
        this.typeRequest = typeRequest;
        this.message = message;
    }

    public int getNumberRequest() {
        return numberRequest;
    }

    public void setNumberRequest(int numberRequest) {
        this.numberRequest = numberRequest;
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

    public String getTypeRequest() {
        return typeRequest;
    }

    public void setTypeRequest(String typeRequest) {
        this.typeRequest = typeRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" + "numberRequest=" + numberRequest + ", line=" + line + ", column=" + column + ", typeRequest=" + typeRequest + ", message=" + message + '}';
    }
}
