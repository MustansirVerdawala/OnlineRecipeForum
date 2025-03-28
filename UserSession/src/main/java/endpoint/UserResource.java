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
@Path("User")
@RequestScoped
public class UserResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    /**
     * Retrieves representation of an instance of UserSession.endpoint.CustomerResource
     * @param username
     * @param password
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        User user = User_CRUD.getUser(username, password);

        try {
            if (user == null) {
                // Return a valid XML error message in case of no user found
                String errorXML = "<error>User not found</error>";
                return Response.status(Response.Status.NOT_FOUND)
                               .entity(errorXML)
                               .type(MediaType.APPLICATION_XML)
                               .build();
            }

            // Marshal the User object into XML
            JAXBContext context = JAXBContext.newInstance(User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter writer = new StringWriter();
            marshaller.marshal(user, writer);

            // Return the valid XML string as the response
            return Response.ok(writer.toString(), MediaType.APPLICATION_XML).build();

        } catch (JAXBException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            // Return a valid XML error message
            String errorXML = "<error>Internal server error</error>";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(errorXML)
                           .type(MediaType.APPLICATION_XML)
                           .build();
        }
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
