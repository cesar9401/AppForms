package com.cesar31.formsweb.restful;

import com.cesar31.formsweb.control.HandlerDB;
import com.cesar31.formsweb.control.HandlerFormParser;
import com.cesar31.formsweb.model.User;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        HandlerFormParser formInput = new HandlerFormParser();
        List<User> users = formInput.readInput(input).getAddUser();

        HandlerDB db = new HandlerDB();
        db.addUser(users);

        return null;
    }

    public static void main(String[] args) {
        String input = "<!INI_solicitud : \"\n\tCREAR_USUARIO\t\n\"> \n "
                + "{ \"    CREDENCIALES_USUARIO\" : [{ \n "
                + "\"USUARIO\" : \"mario_ballotelli\" , \n"
                + "\"PASSWORD\" : \"__ballotelli_12\"\n  ,"
                + "\"FECHA_CREACION\"  : \"\t2020-03-12\"    \n"
                + " }\n "
                + "  ] \n"
                + "} \n "
                + "<fiN_solicitud!>"
                + "\n";
        
        HandlerFormParser clientInput = new HandlerFormParser();
        List<User> users = clientInput.readInput(input).getAddUser();

        HandlerDB db = new HandlerDB();
        db.addUser(users);
    }
}
