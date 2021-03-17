package com.cesar31.formsweb.parser.main;

/**
 *
 * @author cesar31
 */
public class Token {

    private String value;
    private int x;
    private int y;

    public Token(String value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Token(int x, int y) {
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

    @Override
    public String toString() {
        return "Token{" + "value=" + value + ", fila=" + x + ", columna=" + y + '}';
    }
}
