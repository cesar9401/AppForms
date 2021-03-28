package com.cesar31.formsweb.control;

import com.cesar31.formsweb.model.Component;
import com.cesar31.formsweb.model.Form;
import com.cesar31.formsweb.model.Request;
import com.cesar31.formsweb.model.Response;
import com.cesar31.formsweb.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class HandleResponse {

    private List<Response> responses;

    private final String HEADER_M = "<!ini_respuestas>\n\n";
    private final String FOOTER_M = "<!fin_respuestas>\n";
    private final String FOOTER = "\n<!fin_respuesta>\n\n";

    public HandleResponse() {
        this.responses = new ArrayList<>();
    }

    /**
     * Respuesta en string para solicitudes procesadas
     *
     * @return
     */
    public String createResponseStr() {
        String response = "";
        ObjectMapper mapper = new ObjectMapper();

        if (responses.size() == 1) {
            response = getHeader(responses.get(0).getType());
            try {
                response += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responses.get(0));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace(System.out);
            }
            response += FOOTER;
        }

        if (responses.size() > 1) {
            response = HEADER_M;
            for (Response r : responses) {
                response += getHeader(r.getType());
                try {
                    response += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace(System.out);
                }
                response += FOOTER;
            }
            response += FOOTER_M;
        }

        return response;
    }

    /**
     * Respuesta en string para errores de parseo
     *
     * @param errors
     * @return
     */
    public String createErrorResponseStr(List<Error> errors) {
        String response = "";
        ObjectMapper mapper = new ObjectMapper();

        if (errors.size() == 1) {
            response = getHeader("ERROR");
            try {
                response += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(errors.get(0));
            } catch (JsonProcessingException ex) {
                ex.printStackTrace(System.out);
            }
            response += FOOTER;
        }

        if (errors.size() > 1) {
            response = HEADER_M;
            for (Error e : errors) {
                response += getHeader("ERROR");
                try {
                    response += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(e);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace(System.out);
                }
                response += FOOTER;
            }
            response += FOOTER_M;
        }

        return response;
    }

    private String getHeader(String name) {
        return "<!ini_respuesta:\"" + name + "\">\n";
    }

    /**
     * Crear respuesta para peticiones que fueron rechazadas
     *
     * @param r
     * @param message
     */
    public void createResponse(Request r, String message) {
        Response res = new Response(r.getNumber(), r.getLine(), r.getColumn() - 1);
        res.setType(r.getNameRequest());
        res.setMessage(message);
        responses.add(res);
    }

    /**
     * Respuestas para peticiones aprobadas
     *
     * @param r
     */
    public void createSuccessResponse(Request r) {
        Response res = new Response(r.getNumber(), r.getLine(), r.getColumn() - 1);
        res.setType(r.getNameRequest());

        // Crear mensaje
        res.setMessage(getMessage(r));
        responses.add(res);
    }

    /**
     * Mensaje para peticiones aprobadas
     *
     * @param r
     * @return
     */
    private String getMessage(Request r) {
        String message = "";
        if (r instanceof User) {
            switch (r.getOp()) {
                case LOGIN:
                    return "Login usuario: " + ((User) r).getUser();
                case ADD:
                    return "Se ha creado correctamente al usuario: " + ((User) r).getUser();
                case DEL:
                    return "Se ha eliminado correctamente al usuario: " + ((User) r).getUser();
                case EDIT:
                    return "Se ha editado correctamente la informacion del usuario: " + ((User) r).getUser();
            }
        }

        if (r instanceof com.cesar31.formsweb.model.Form) {
            switch (r.getOp()) {
                case ADD:
                    return "Se ha agregado correctamente el formulario: " + ((Form) r).getId_form();
                case EDIT:
                    return "Se ha editado correctamente la informacion del formulario: " + ((Form) r).getId_form();
                case DEL:
                    return "Se ha eliminado correctamente el formulario: " + ((Form) r).getId_form();
            }
        }

        if (r instanceof Component) {
            switch (r.getOp()) {
                case ADD:
                    return "Se ha agregado correctamene el componente: " + ((Component) r).getId_component() + ", al formulario: " + ((Component) r).getForm_id();
                case EDIT:
                    return "Se ha editado correctamene el componente: " + ((Component) r).getId_component() + ", del formulario: " + ((Component) r).getForm_id();
                case DEL:
                    return "Se ha eliminado correctamene el componente: " + ((Component) r).getId_component() + ", del formulario: " + ((Component) r).getForm_id();
            }
        }
        return message;
    }

    public List<Response> getResponses() {
        return responses;
    }
}
