package com.cesar31.formsclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author cesar31
 */
public class TestForms {

    private static final String URL_BASE = "http://localhost:8080/FormsWeb/webapi";
    private static Client client;
    private static WebTarget webTarget;
    private static Invocation.Builder invocationBuilder;
    private static Response response;
    
    public static void main(String[] args) {
        // Write your code here
        client = ClientBuilder.newClient();
        
        // Leer (metodo get)
        webTarget = client.target(URL_BASE).path("/myresource");
        
        String resultado = webTarget.request(MediaType.TEXT_PLAIN).get(Response.class).readEntity(new GenericType<String>(){});
        System.out.println("El resutlado es: " + resultado);
    }
}
