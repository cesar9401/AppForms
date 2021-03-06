package com.cesar31.formsweb.restful;

import com.cesar31.formsweb.control.HandlerFormParser;
import com.cesar31.formsweb.model.Message;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author cesar31
 */
@Path("application")
public class MainService {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getRequest(Message message) {
        //Write your code here
        try {
            //System.out.println("Servidor");
            System.out.println(message.getMsj());
            HandlerFormParser handle = new HandlerFormParser();
            Message response = handle.parserInput(message);
            System.out.println(response.getMsj());
            
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
