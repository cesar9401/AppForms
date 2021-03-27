package com.cesar31.formsclient.control;

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
    
    public void sendRequest(String input) {
        invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        response = invocationBuilder.post(Entity.entity(input, MediaType.TEXT_PLAIN));
        System.out.println("");
        System.out.println(response.getStatus());
        String inputRecovery = response.readEntity(String.class);
        System.out.println("inputRecovery = " + inputRecovery);
    }
}
