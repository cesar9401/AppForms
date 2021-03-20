package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.parser.main.Token;

/**
 *
 * @author cesar31
 */
public class ComponentContainer {

    private UserContainer container;

    public ComponentContainer(UserContainer container) {
        this.container = container;
    }

    public void addComponent(Token t) {
        boolean created = true;
        Component c = new Component();
        String r = "AGREGAR_COMPONENTE";

        if (!isPresent("CLASE")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion " + r + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir la CLASE para poder crear el componente.");
            container.getErrors().add(e);
            created = false;

            c = new Component();
        } else {
            String kind = container.fS_(container.getParams().get("CLASE"));
            c.setKind(getParam("CLASE"));
            switch (kind) {
                case "CHECKBOX":
                    System.out.println("CHECKBOX");
                case "RADIO":
                    System.out.println("RADIO");
                case "COMBO":
                    created = isPresent("OPCIONES");
                    System.out.println("COMBO");
                    getOptions(t, r, kind, c);
                    break;

                case "AREA_TEXTO":
                    System.out.println("AREA_TEXTO");
                    created = isPresent("FILAS") && isPresent("COLUMNAS");
                    getRowsAndColumns(t, r, kind, c);
                    break;

                case "IMAGEN":
                    System.out.println("IMAGEN");
                    created = isPresent("URL");
                    getUrl(t, r, kind, c);
                    break;
                default:
            }

        }

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            c.setId(getParam("ID"));
        }

        if (!isPresent("NOMBRE_CAMPO")) {
            setError(t, "NOMBRE_CAMPO", r);
            created = false;
        } else {
            c.setFieldName(getParam("NOMBRE_CAMPO"));
        }

        if (!isPresent("FORMULARIO")) {
            setError(t, "FORMULARIO", r);
            created = false;
        } else {
            c.setForm(getParam("FORMULARIO"));
        }

        if (isPresent("INDICE")) {
            created = false;
            //Error se agrega al liberar tokens
        }

        if (!isPresent("TEXTO_VISIBLE")) {
            setError(t, "TEXTO_VISIBLE", r);
            created = false;
        } else {
            c.setText(getParam("TEXTO_VISIBLE"));
        }

        if (!isPresent("ALINEACION")) {
            setError(t, "ALINEACION", r);
            created = false;
        } else {
            c.setAling(getParam("ALINEACION"));
        }

        boolean required = getParam("REQUERIDO") != null;
        c.setRequired(required);

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID") || s.equals("CLASE") || s.equals("NOMBRE_CAMPO") || s.equals("FORMULARIO") || s.equals("TEXTO_VISIBLE") || s.equals("ALINEACION") || s.equals("REQUERIDO")) {
                    setCurrentErrors(e, s, r);
                }
            });
            container.getCurrentErrors().clear();
            created = false;
        }

        if (!container.getTokens().isEmpty()) {
            container.freeTokens(r);
            created = false;
        }

        if (created) {
            System.out.println(c.toString());
            System.out.println("Lineas -> " + c.getRows());
            System.out.println("Columnas -> " + c.getColumns());
            System.out.println("URL -> " + c.getUrl());
            System.out.print("Opciones -> ");
            c.getOptions().forEach(p -> {
                System.out.print(p + "   ");
            });
        }
        
        clearHash();
    }
    
    public void delComponent(Token t) {
        boolean created = true;
        Component c = new Component();
        String r = "ELIMINAR_COMPONENTE";
        
        if(!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            c.setId(getParam("ID"));
        }
        
        if(!isPresent("FORMULARIO")) {
            setError(t, "FORMULARIO", r);
            created = false;
        } else {
            c.setForm(getParam("FORMULARIO"));
        }
        
        if(!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if(s.equals("ID") || s.equals("FORMULARIO")) {
                    setCurrentErrors(e, s, r);
                }
            });
            container.getCurrentErrors().clear();
            created = false;
        }
        
        if(!container.getTokens().isEmpty()) {
            container.freeTokens(r);
            created = false;
        }
        
        if(created) {
            System.out.println("Eliminar componente -> " + c.toString());
        }
        
        //Limpiar HashMaps
        clearHash();
    }

    /**
     * Opciones para imagen
     *
     * @param t
     * @param r
     * @param kind
     * @param c
     */
    private void getUrl(Token t, String r, String kind, Component c) {
        if (!isPresent("URL")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion " + r + ", de tipo: " + kind + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir el parametro URL.");
            container.getErrors().add(e);
        } else {
            c.setUrl(getParam("URL"));
            if (!container.getCurrentErrors().isEmpty()) {
                container.getCurrentErrors().forEach(e -> {
                    String s = e.getLexema();
                    if (s.equals("URL")) {
                        setCurrentErrors(e, s, r);
                    }
                });
            }
        }
    }

    /**
     * Opciones para text_area
     *
     * @param t
     * @param r
     * @param kind
     * @param c
     */
    private void getRowsAndColumns(Token t, String r, String kind, Component c) {
        if (isPresent("FILAS") && isPresent("COLUMNAS")) {
            c.setRows(Integer.valueOf(getParam("FILAS")));
            c.setColumns(Integer.valueOf(getParam("COLUMNAS")));

            if (!container.getCurrentErrors().isEmpty()) {
                container.getCurrentErrors().forEach(e -> {
                    String s = e.getLexema();
                    if (s.equals("FILAS") || s.equals("COLUMNAS")) {
                        setCurrentErrors(e, s, r);
                    }
                });
            }

        } else {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion " + r + ", de tipo: " + kind + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir los parametros para FILAS Y COLUMNAS.");
            container.getErrors().add(e);
        }
    }

    /**
     * Opciones para checkbox, radio y combo
     *
     * @param t
     * @param r
     * @param kind
     * @param c
     */
    private void getOptions(Token t, String r, String kind, Component c) {
        if (!isPresent("OPCIONES")) {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion " + r + ", de tipo: " + kind + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir el parametro OPCIONES.");
            container.getErrors().add(e);
        } else {
            String[] opt = getParam("OPCIONES").split("\\|");
            for (String opt1 : opt) {
                c.getOptions().add(opt1.trim());
            }

            if (!container.getCurrentErrors().isEmpty()) {
                container.getCurrentErrors().forEach(e -> {
                    String s = e.getLexema();
                    if (s.equals("OPCIONES")) {
                        setCurrentErrors(e, s, r);
                    }
                });
            }

        }
    }

    private void setCurrentErrors(Error e, String param, String request) {
        String description = "Ya se ha indicado " + param + " para la peticion " + request + ".";
        e.setDescription(description);
        container.getErrors().add(e);
    }

    private void clearHash() {
        container.getParams().clear();
        container.getTokens().clear();
    }

    private void setError(Token t, String param, String request) {
        container.getErrors().add(container.getRequestError(t, param, request));
    }

    private boolean isPresent(String param) {
        return container.getParams().containsKey(param);
    }

    private String getParam(String param) {
        container.getTokens().remove(param);
        return container.getParams().remove(param);
    }
}
