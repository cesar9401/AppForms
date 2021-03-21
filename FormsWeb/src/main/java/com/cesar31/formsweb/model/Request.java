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

    @Override
    public String toString() {
        return "Request{" + "number=" + number + '}';
    }
}
