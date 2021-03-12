package com.cesar31.formsclient.control;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
    }
}
