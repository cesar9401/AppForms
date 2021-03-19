package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Form;
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
            f.setId(container.getParams().remove("ID"));
            container.getTokens().remove("ID");
        }

        if (!isPresent("TITULO")) {
            setError(t, "TITULO", r);
            created = false;
        } else {
            f.setTitle(container.getParams().remove("TITULO"));
            container.getTokens().remove("TITULO");
        }

        if (!isPresent("NOMBRE")) {
            setError(t, "NOMBRE", r);
            created = false;
        } else {
            f.setName(container.getParams().remove("NOMBRE"));
            container.getTokens().remove("NOMBRE");
        }

        if (!isPresent("TEMA")) {
            setError(t, "TEMA", r);
            created = false;
        } else {
            f.setTheme(container.getParams().remove("TEMA"));
            container.getTokens().remove("TEMA");
        }

        if (!isPresent("USUARIO_CREACION")) {
            // Write your code here to add user

        } else {
            f.setUser(container.getParams().remove("USUARIO_CREACION"));
            container.getTokens().remove("USUARIO_CREACION");
        }

        if (!isPresent("FECHA_CREACION")) {
            f.setCreationDate(LocalDate.now());
            f.setcDate(LocalDate.now().toString());
        } else {
            f.setCreationDate(container.fLD(container.getParams().get("FECHA_CREACION")));
            f.setcDate(f.getCreationDate().toString());

            container.getParams().remove("FECHA_CREACION");
            container.getTokens().remove("FECHA_CREACION");
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
            System.out.println("Nuevo formulario: " + f.toString());
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
            f.setId(container.getParams().remove("ID"));
            container.getTokens().remove("ID");
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
            System.out.println("Eliminar formulario: " + f.toString());
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
            f.setId(container.getParams().remove("ID"));
            container.getTokens().remove("ID");
        }

        if (!isPresent("TITULO")) {
            setError(t, "TITULO", r);
            created = false;
        } else {
            f.setTitle(container.getParams().remove("TITULO"));
            container.getTokens().remove("TITULO");
        }

        if (!isPresent("NOMBRE")) {
            setError(t, "NOMBRE", r);
            created = false;
        } else {
            f.setName(container.getParams().remove("NOMBRE"));
            container.getTokens().remove("NOMBRE");
        }

        if (!isPresent("TEMA")) {
            setError(t, "TEMA", r);
            created = false;
        } else {
            f.setTheme(container.getParams().remove("TEMA"));
            container.getTokens().remove("TEMA");
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
            System.out.println("Editar formulario: " + f.toString());
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
}
