package com.cesar31.formsweb.control;

/**
 *
 * @author cesar31
 */
public class Error {

    private String lexema;
    private String type;
    private int line;
    private int column;
    private String description;

    public Error() {
    }

    public Error(String lexema, int line, int column) {
        this.lexema = lexema;
        this.line = line;
        this.column = column;
    }

    public Error(String lexema, String type, int line, int column) {
        this.lexema = lexema;
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public Error(String lexema, String type, int line, int column, String description) {
        this.lexema = lexema;
        this.type = type;
        this.line = line;
        this.column = column;
        this.description = description;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
