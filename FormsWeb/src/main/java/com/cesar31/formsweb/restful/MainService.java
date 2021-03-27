package com.cesar31.formsweb.restful;

import com.cesar31.formsweb.control.HandlerFormParser;
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRequest(String input) {
        //Write your code here
        try {
            System.out.println(input);
            HandlerFormParser formInput = new HandlerFormParser();
            formInput.parserInput(input);
            return Response.ok().entity(input).build();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
