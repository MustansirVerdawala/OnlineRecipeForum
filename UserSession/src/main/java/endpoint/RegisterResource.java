package endpoint;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import Helper.*;
import Persistence.*;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author mustansir
 */
@Path("Register")
@RequestScoped
public class RegisterResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public RegisterResource() {
    }

    /**
     * Retrieves representation of an instance of UserSession.endpoint.CustomerResource
     * @param username
     * @param password
     * @param email
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response register(@QueryParam("username") String username, 
                             @QueryParam("password") String password, 
                             @QueryParam("email") String email) {
        int flag = User.addUser(username, password, email);

        // Construct an XML response containing the flag value
        String responseXML = "<response><flag>" + flag + "</flag></response>";

        return Response.ok(responseXML, MediaType.APPLICATION_XML).build();
    }

    // You can remove the getXml() method if it's not needed, or if you need it,
    // give it a unique path, e.g., @Path("/getUser").
    
    /*
    @GET
    @Path("/getUser")  // Example: unique path
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        // Return a default or specific representation of the resource.
        throw new UnsupportedOperationException();
    }
    */
    
    /**
     * PUT method for updating or creating an instance of UserResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
