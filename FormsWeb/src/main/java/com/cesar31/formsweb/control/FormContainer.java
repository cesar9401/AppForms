package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.Operation;
import com.cesar31.formsweb.parser.main.Token;
import java.time.LocalDate;

/**
 *
 * @author cesar31
 */
public class FormContainer {

    private UserContainer container;

    public FormContainer(UserContainer container) {
        this.container = container;
    }

    /**
     * Procesar peticiones para crear formularios
     *
     * @param t
     */
    public void addForm(Token t) {
        boolean created = true;
        Form f = new Form();
        String r = "NUEVO_FORMULARIO";

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            f.setId_form(getParam("ID"));
        }

        if (!isPresent("TITULO")) {
            setError(t, "TITULO", r);
            created = false;
        } else {
            f.setTitle(getParam("TITULO"));
        }

        if (!isPresent("NOMBRE")) {
            setError(t, "NOMBRE", r);
            created = false;
        } else {
            if (!container.haveSpace("NOMBRE")) {
                f.setName(getParam("NOMBRE"));
            } else {
                container.setErrorSpace(r, "NOMBRE");
                created = false;
            }
        }

        if (!isPresent("TEMA")) {
            setError(t, "TEMA", r);
            created = false;
        } else {
            f.setTheme(getParam("TEMA"));
        }

        if (!isPresent("USUARIO_CREACION")) {
            // Write your code here to add user
            if(container.getUser() != null) {
                f.setUser_creation(container.getUser());
            }
        } else {
            if (!container.haveSpace("USUARIO_CREACION")) {
                f.setUser_creation(getParam("USUARIO_CREACION"));
            } else {
                container.setErrorSpace(r, "USUARIO_CREACION");
            }
        }

        if (!isPresent("FECHA_CREACION")) {
            f.setCreationDate(LocalDate.now());
            f.setcDate_form(LocalDate.now().toString());
        } else {
            //String string = container.getParams().get("FECHA_CREACION");
            LocalDate date = container.getDate("FECHA_CREACION", r);
            if (date != null) {
                f.setCreationDate(date);
                f.setcDate_form(f.getCreationDate().toString());
            } else {
                created = false;
            }
        }

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID") || s.equals("TITULO") || s.equals("NOMBRE") || s.equals("TEMA") || s.equals("USUARIO_CREACION") || s.equals("FECHA_CREACION")) {
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
            f.setOp(Operation.ADD);
            container.addRequest(t, r, f);
            //System.out.println("Nuevo formulario: " + f.toString());
        }

        // Limpiar HashMaps
        clearHash();
    }

    /**
     * Procesar solicitudes para eliminar formularios
     *
     * @param t
     */
    public void delForm(Token t) {
        boolean created = true;
        Form f = new Form();
        String r = "ELIMINAR_FORMULARIO";

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            f.setId_form(getParam("ID"));
        }

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID")) {
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
            f.setOp(Operation.DEL);
            container.addRequest(t, r, f);
            //System.out.println("Eliminar formulario: " + f.toString());
        }

        // Limpiar HashMaps
        clearHash();
    }

    /**
     * Procesar solicitud para editar formulario
     *
     * @param t
     */
    public void editForm(Token t) {
        boolean created = true;
        Form f = new Form();
        String r = "MODIFICAR_FORMULARIO";

        if (!isPresent("ID")) {
            setError(t, "ID", r);
            created = false;
        } else {
            f.setId_form(getParam("ID"));
        }

        if (isPresent("TITULO") || isPresent("NOMBRE") || isPresent("TEMA")) {
            if (isPresent("TITULO")) {
                f.setTitle(getParam("TITULO"));
                
                //f.setTitle(container.getParams().remove("TITULO"));
                //container.getTokens().remove("TITULO");
            }

            if (isPresent("NOMBRE")) {
                if (!container.haveSpace("NOMBRE")) {
                    f.setName(getParam("NOMBRE"));
                } else {
                    container.setErrorSpace(r, "NOMBRE");
                    created = false;
                }
            }

            if (isPresent("TEMA")) {
                f.setTheme(getParam("TEMA"));
                //f.setTheme(container.getParams().remove("TEMA"));
                //container.getTokens().remove("TEMA");
            }
        } else {
            Error e = new Error("", "SINTACTICO", t.getX(), t.getY());
            e.setDescription("En la peticion " + r + ", fila = " + t.getX() + ", columna = " + t.getY() + ", se debe incluir aparte del ID, TITULO, NOMBRE y/o TEMA.");
            container.getErrors().add(e);
            created = false;
        }

        if (!container.getCurrentErrors().isEmpty()) {
            container.getCurrentErrors().forEach(e -> {
                String s = e.getLexema();
                if (s.equals("ID") || s.equals("TITULO") || s.equals("NOMBRE") || s.equals("TEMA")) {
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
            f.setOp(Operation.EDIT);
            container.addRequest(t, r, f);
            //System.out.println("Editar formulario: " + f.toString());
        }

        // Limpiar HashMaps
        clearHash();
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
     * Limpiar HashMaps de atributos y tokensS
     */
    private void clearHash() {
        container.getParams().clear();
        container.getTokens().clear();
    }

    /**
     * Agregar errores de atributos que deberian estar en una peticion
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
        //container.getTokens().remove(param);
        //return container.getParams().remove(param).trim();
        return container.getParam(param);
    }
}
