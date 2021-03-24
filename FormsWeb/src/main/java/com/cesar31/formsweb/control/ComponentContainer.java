package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Operation;
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

    /**
     * Procesar solicitudes para agregar componentes
     *
     * @param t
     */
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
                case "RADIO":
                case "COMBO":
                    created = isPresent("OPCIONES");
                    getOptions(t, r, kind, c);
                    break;

                case "AREA_TEXTO":
                    created = isPresent("FILAS") && isPresent("COLUMNAS");
                    getRowsAndColumns(t, r, kind, c);
                    break;

                case "IMAGEN":
                    created = isPresent("URL");
                    if (isPresent("URL")) {
                        created = !container.haveSpace("URL");
                    }
                    getUrl(t, r, kind, c);
                    break;
            }
        }

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            c.setId_component(getParam("ID"));
        }

        if (!isPresent("NOMBRE_CAMPO")) {
            setError(t, "NOMBRE_CAMPO", r);
            created = false;
        } else {
            if (!container.haveSpace("NOMBRE_CAMPO")) {
                c.setFieldName(getParam("NOMBRE_CAMPO"));
            } else {
                container.setErrorSpace(t, r, "NOMBRE_CAMPO");
                created = false;
            }
        }

        if (!isPresent("FORMULARIO")) {
            setError(t, "FORMULARIO", r);
            created = false;
        } else {
            c.setForm_id(getParam("FORMULARIO"));
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

        if (isPresent("REQUERIDO")) {
            String answer = getParam("REQUERIDO");
            if (answer.equals("SI") || answer.equals("NO")) {
                boolean required = answer.equals("SI");
                c.setRequired(required);
            } else {
                created = false;
            }
        } else {
            c.setRequired(false);
        }

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
            //System.out.println("Crear Componente -> " + c.toString());
            c.setOp(Operation.ADD);
            container.addRequest(c);
        }

        clearHash();
    }

    /**
     * Porcesar solicitudes para eliminar componente
     *
     * @param t
     */
    public void delComponent(Token t) {
        boolean created = true;
        Component c = new Component();
        String r = "ELIMINAR_COMPONENTE";

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            c.setId_component(getParam("ID"));
        }

        if (!isPresent("FORMULARIO")) {
            setError(t, "FORMULARIO", r);
            created = false;
        } else {
            c.setForm_id(getParam("FORMULARIO"));
        }

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID") || s.equals("FORMULARIO")) {
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
            //System.out.println("Eliminar componente -> " + c.toString());
            c.setOp(Operation.DEL);
            container.addRequest(c);
        }

        //Limpiar HashMaps
        clearHash();
    }

    /**
     * Procesar solicitud para editar componentes
     *
     * @param t
     */
    public void editComponent(Token t) {
        boolean created = true;
        Component c = new Component();
        String r = "MODIFICAR_COMPONENTE";

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            c.setId_component(getParam("ID"));
        }

        if (!isPresent("FORMULARIO")) {
            setError(t, "FORMULARIO", r);
            created = false;
        } else {
            c.setForm_id(getParam("FORMULARIO"));
        }

        if (!container.getParams().isEmpty()) {
            if (isPresent("NOMBRE_CAMPO")) {
                if (!container.haveSpace("NOMBRE_CAMPO")) {
                    c.setFieldName(getParam("NOMBRE_CAMPO"));
                } else {
                    container.setErrorSpace(t, r, "NOMBRE_CAMPO");
                    created = false;
                }
            }

            if (isPresent("CLASE")) {
                c.setKind(getParam("CLASE"));
            }

            if (isPresent("INDICE")) {
                c.setIndex(Integer.valueOf(getParam("INDICE")));
            }

            if (isPresent("TEXTO_VISIBLE")) {
                c.setText(getParam("TEXTO_VISIBLE"));
            }

            if (isPresent("ALINEACION")) {
                c.setAling(getParam("ALINEACION"));
            }

            if (isPresent("REQUERIDO")) {
                String answer = getParam("REQUERIDO");
                if (answer.equals("SI") || answer.equals("NO")) {
                    boolean required = answer.equals("SI");
                    c.setRequired(required);
                } else {
                    created = false;
                }
            }

            if (isPresent("OPCIONES")) {
                getOptions(t, r, "", c);
            }

            if (isPresent("URL")) {
                created = !container.haveSpace("URL");
                getUrl(t, r, "", c);
            }

            if (isPresent("FILAS")) {
                c.setRows(Integer.valueOf(getParam("FILAS")));
            }

            if (isPresent("COLUMNAS")) {
                c.setRows(Integer.valueOf(getParam("COLUMNAS")));
            }
        } else {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            String description = "En la peticion " + r + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se deben incluir lo parametros que se quieran modificar.";
            description += "(e.g., INDICE, CLASE, OPCIONES, ALINEACION)";
            e.setDescription(description);
            container.getErrors().add(e);
            created = false;
        }

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID") || s.equals("CLASE") || s.equals("NOMBRE_CAMPO") || s.equals("FORMULARIO") || s.equals("TEXTO_VISIBLE") || s.equals("ALINEACION") || s.equals("REQUERIDO") || s.equals("FILAS") || s.equals("COLUMNAS") || s.equals("INDICE")) {
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
            //System.out.println("Editar componente: " + c.toString());
            c.setOp(Operation.EDIT);
            container.addRequest(c);
        }

        // Limpiar HashMaps
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
            if (!container.haveSpace("URL")) {
                c.setUrl(getParam("URL"));
            } else {
                container.setErrorSpace(t, r, "URL");
            }

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

    /**
     * Errores de atributos repetidos
     *
     * @param e
     * @param param
     * @param request
     */
    private void setCurrentErrors(Error e, String param, String request) {
        String description = "Ya se ha indicado " + param + " para la peticion " + request + ".";
        e.setDescription(description);
        container.getErrors().add(e);
    }

    /**
     * Limpiar HashMaps de atributos y tokens
     */
    private void clearHash() {
        container.getParams().clear();
        container.getTokens().clear();
    }

    /**
     * Agregar errores de atributos que deberian estar en alguna solicitud
     *
     * @param t
     * @param param
     * @param request
     */
    private void setError(Token t, String param, String request) {
        container.getErrors().add(container.getRequestError(t, param, request));
    }

    /**
     * Verificar si algun parametro de alguna solicitud esta presente
     *
     * @param param
     * @return
     */
    private boolean isPresent(String param) {
        return container.getParams().containsKey(param);
    }

    /**
     * Obtener el parametro de alguna solicitud y removerlo de HashMaps
     *
     * @param param
     * @return
     */
    private String getParam(String param) {
        container.getTokens().remove(param);
        return container.getParams().remove(param).trim();
    }
}
