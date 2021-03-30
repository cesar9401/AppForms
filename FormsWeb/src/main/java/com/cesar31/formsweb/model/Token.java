package com.cesar31.formsweb.model;

/**
 *
 * @author cesar31
 */
public class Token {

    private int sym;
    private String value;
    private int x;
    private int y;

    public Token(int sym, String value, int x, int y) {
        this.sym = sym;
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Token(int sym, int x, int y) {
        this.sym = sym;
        this.x = x;
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSym() {
        return sym;
    }

    @Override
    public String toString() {
        return "Token{" + "sym=" + sym + ", value=" + value + ", x=" + x + ", y=" + y + '}';
    }
}
