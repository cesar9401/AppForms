package com.cesar31.formsweb.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author cesar31
 */
public class Request {

    public interface NoView {
    };

    @JsonView(NoView.class)
    private int number;

    @JsonView(NoView.class)
    private Operation op;

    @JsonView(NoView.class)
    private int line;

    @JsonView(NoView.class)
    private int column;

    @JsonView(NoView.class)
    private String nameRequest;

    public Request() {
    }

    public Request(int number, Operation op) {
        this.number = number;
        this.op = op;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
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

    public String getNameRequest() {
        return nameRequest;
    }

    public void setNameRequest(String nameRequest) {
        this.nameRequest = nameRequest;
    }

    @Override
    public String toString() {
        return "Request{" + "number=" + number + ", line=" + line + ", column=" + column + '}';
    }
}
