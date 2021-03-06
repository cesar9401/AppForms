package com.cesar31.formsclient.control;

import com.cesar31.formsclient.model.Message;
import com.cesar31.formsclient.parser.ResponseLex;
import com.cesar31.formsclient.parser.ResponseParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author cesar31
 */
public class Request {

    private final String URL_BASE = "http://localhost:8080/FormsWeb/webapi";
    private Client client;
    private WebTarget webTarget;
    private Invocation.Builder invocationBuilder;
    private Response response;
    private List list;
    private boolean errors;
    private String serverResponse;

    private String user;

    public Request() {
        client = ClientBuilder.newClient();
        webTarget = client.target(URL_BASE).path("/application");
        list = new ArrayList<>();
    }

    /**
     * Enviar peticion
     *
     * @param input
     */
    public void sendRequest(String input) {
        System.out.println(input);

        Message message = new Message();
        message.setMsj(input);
        if (this.user != null) {
            message.setUser(user);
            System.out.println("User request = " + this.user);
        } else {
            message.setUser(null);
        }

        invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.post(Entity.entity(message, MediaType.APPLICATION_XML));
        System.out.println(response.getStatus());
        System.out.println("Here");

        // Obtener respuesta
        Message res = response.readEntity(Message.class);

        if (res.getUser() != null) {
            this.user = res.getUser();
            System.out.println("User response = " + this.user);
        } else {
            // Cerrar sesion
            this.user = null;
        }

        System.out.println(res.getMsj());

        // Parsear respuesta
        parseResponse(res.getMsj());
    }

    /**
     * Parsear respuesta
     *
     * @param response
     */
    private void parseResponse(String response) {
        this.serverResponse = response;
        ResponseLex lex = new ResponseLex(new StringReader(response));
        ResponseParser parser = new ResponseParser(lex);
        try {
            parser.parse();
            errors = parser.getHandle().hasErrors();
            list = parser.getHandle().getList();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public List getList() {
        return list;
    }

    public boolean isErrors() {
        return errors;
    }
}
