package com.cesar31.formsclient.control;

import com.cesar31.formsclient.parser.ResponseLex;
import com.cesar31.formsclient.parser.ResponseParser;
import java.io.StringReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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

    public Request() {
        client = ClientBuilder.newClient();
        webTarget = client.target(URL_BASE).path("/application");
    }

    /**
     * Enviar peticion
     *
     * @param input
     */
    public void sendRequest(String input) {
        invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        response = invocationBuilder.post(Entity.entity(input, MediaType.TEXT_PLAIN));
        //System.out.println(response.getStatus());
        String res = response.readEntity(String.class);
        System.out.println(res);

        parseResponse(res);
    }

    /**
     * Parsear respuesta
     *
     * @param response
     */
    private void parseResponse(String response) {
        ResponseLex lex = new ResponseLex(new StringReader(response));
        ResponseParser parser = new ResponseParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
