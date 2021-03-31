package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Error;
import com.cesar31.formsweb.model.FormData;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.Response;
import com.cesar31.formsweb.model.Token;
import com.cesar31.formsweb.parser.sqf.SQFLex;
import com.cesar31.formsweb.parser.sqf.SQFParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class SQForm {

    private HandleDB db;

    private HashMap<String, Token> params;
    private UserContainer container;

    private List<Error> sqErrors;
    private List<Response> res;

    private String param;

    private String current;
    private Token token;
    private Token tkn;

    private HashMap<String, Token> fields;

    public SQForm(UserContainer container) {
        this.container = container;
        this.db = new HandleDB();
        this.params = new HashMap<>();
        this.sqErrors = new ArrayList<>();

        this.param = null;
        this.fields = new HashMap<>();
    }

    public void createSQF(Token t) {
        this.token = t;

        params.forEach((s, k) -> {
            this.current = s;
            this.tkn = k;

            String value = k.getValue().replace("\"", "");

            SQFLex lex = new SQFLex(new StringReader(value));
            SQFParser parser = new SQFParser(lex, this);
            try {
                parser.parse();
                //System.out.println(s + " -> " + k.getValue() + "linea: " + k.getX() + "columna: " + k.getY());
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        });
    }

    public void createQuery(Boolean id, Boolean whereCnd) {
        FormData fd = db.getFormDate(id, param);
        if (fd != null) {
            if (!fd.getData().isEmpty()) {
                if (this.fields.isEmpty()) {
                    if (!whereCnd) {
                        List<HashMap<String, String>> list = fd.getData();
                        list.forEach(map -> {
                            map.forEach((k, v) -> {
                                System.out.printf("%s -> %s\n", k, v);
                            });
                        });
                    }
                } else {
                    List<String> match = new ArrayList<>();
                    List<HashMap<String, String>> data = new ArrayList<>();

                    fd.getData().forEach(map -> {
                        HashMap<String, String> tmp = new HashMap<>();
                        map.forEach((k, s) -> {
                            if (fields.containsKey(k)) {
                                tmp.put(k, s);
                                if (!match.contains(k)) {
                                    match.add(k);
                                }
                            }
                        });
                        data.add(tmp);
                    });

                    /* Agregar informacion recopilada */
                    fd.setData(data);

                    /* Agregar a listado de consultas */
                    addRequest(token, current, fd);

//                    System.out.println("Datos Recopilados:");
//                    data.forEach(map -> {
//                        map.forEach((k, v) -> {
//                            System.out.printf("%s -> %s\n", k, v);
//                        });
//                    });
//                    System.out.println("Match");
//                    match.forEach(s -> {
//                        System.out.println(s);
//                    });
                    match.forEach(s -> {
                        fields.remove(s);
                    });
                    //fields.removeAll(match);

                    if (!fields.isEmpty()) {
                        //System.out.println("Error, no se encontraron los campos: ");
                        fields.forEach((s, t) -> {
//                            int y = tkn.getY() + t.getY() - 1;
//                            int x = tkn.getX() + t.getX() - 1;
//                            if(y != tkn.getY()) {
//                                x = t.getX();
//                            }       

                            Error e = new Error(s, "SEMANTICO", tkn.getX(), tkn.getY());
                            String description = "En la consulta: " + current + ", no se encontro el campo: " + s;
                            e.setDescription(description);

                            addError(e);
                        });
                    }
                }

            } else {
                Response r = new Response(0, tkn.getX(), tkn.getY());
                r.setType(current);
                r.setMessage("No existen respuestas del formulario " + param);
                res.add(r);
            }

        } else {
            Response r = new Response(0, tkn.getX(), tkn.getY());
            r.setType(current);
            r.setMessage("No existen datos del formulario " + param);
            res.add(r);
        }
    }

    /**
     * Error
     *
     * @param symbol
     * @param type
     * @param expectedTokens
     */
    public void setError(Symbol symbol, String type, List<String> expectedTokens) {
        Token t = (Token) symbol.value;
        String typeE = (type.equals("ERROR")) ? "LEXICO" : "SINTACTICO";
        String value = (type.equals("EOF")) ? "Fin de entrada" : t.getValue();

        Error e = new Error(value, typeE, tkn.getX() + t.getX() - 1, tkn.getY() + t.getY() - 1);
        String description = (typeE.equals("LEXICO")) ? "La cadena no se reconoce en el lenguaje. " : "";
        description += "Se encontro: " + value + ". ";
        description += "Se esperaba: ";

        for (int i = 0; i < expectedTokens.size(); i++) {
            if (!expectedTokens.get(i).equals("error")) {
                description = description.concat("'" + getHandle().getValue(expectedTokens.get(i)) + "'");
                if (i == expectedTokens.size() - 1) {
                    description = description.concat(".");
                } else {
                    description = description.concat(", ");
                }
            }
        }
        e.setDescription(description);

        // Agregar error
        addError(e);
    }

    /**
     * Agregar los campos para consultar
     *
     * @param field
     */
    public void setField(Token field) {
//        System.out.println(field.getValue());
//        System.out.println("y " + field.getY());
//        System.out.println("x " +field.getX());
        if (!this.fields.containsKey(field.getValue())) {
            this.fields.put(field.getValue(), field);
            //System.out.println(field.getValue());
        } else {
            Error e = new Error(field.getValue(), "SINTACTICO", field.getX(), field.getY());
            String description = "El campo de consulta " + field.getValue() + ", ya ha sido agregado en la consulta";
            e.setDescription(description);

            sqErrors.add(e);
        }
    }

    /**
     * Id or Name
     *
     * @param param
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * Obtener nombre de consulta y consulta
     *
     * @param key
     * @param t
     */
    public void setParam(String key, Token t) {
        if (!params.containsKey(key)) {
            params.put(key, t);

        } else {
            Error e = new Error(key, "SINTACTICO", t.getX(), t.getY());
            String description = "Ya se ha usado el nombre: " + key + " para hacer una consulta.";
            e.setDescription(description);
            sqErrors.add(e);
        }
    }

    private void addRequest(Token t, String name, Request r) {
        this.container.addRequest(t, name, r);
    }

    public List<Error> getSqErrors() {
        return sqErrors;
    }

    public HandleError getHandle() {
        return this.container.getHandle();
    }

    public void addError(Error e) {
        this.container.getErrors().add(e);
    }
}
