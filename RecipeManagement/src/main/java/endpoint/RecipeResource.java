/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
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
import java.util.ArrayList;
import javax.xml.bind.PropertyException;

/**
 * REST Web Service
 *
 * @author mustansir
 */
@Path("Recipe")
@RequestScoped
public class RecipeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RecipeResource
     */
    public RecipeResource() {
    }
    
    /**
     * Retrieves representation of an instance of UserSession.endpoint.CustomerResource
     * @param search
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response fetchRecipes(@QueryParam("search") String search) throws PropertyException, JAXBException {
        
        try {

            ArrayList<Recipe> recipes;
            
            if (search==""){
                // Marshal the User object into XML
                recipes = Recipe.getAllRecipes();

            }else{
                // Tokenizing the string by spaces
                String[] tokens = search.split(" ");

                recipes=new ArrayList<Recipe>();
                ArrayList IDs=new ArrayList<>();
                
                for (String token : tokens) {
                    ArrayList<Recipe> fetchedRecipes = Recipe_CRUD.readTitle(token);
            
                    // Iterate through the fetched recipes
                    for (Recipe recipe : fetchedRecipes) {
                        int recipeID = recipe.getID(); // Assuming the getID() method returns the recipe ID

                        // Check if the recipe ID doesn't already exist in the IDs set
                        if (!IDs.contains(recipeID)) {
                            // If not, add the recipe to the recipes list and the ID to the IDs set
                            recipes.add(recipe);
                            IDs.add(recipeID);
                        }
                    }
                    
                    fetchedRecipes = Recipe_CRUD.readNutrient(token);
                    
                    // Iterate through the fetched recipes
                    for (Recipe recipe : fetchedRecipes) {
                        int recipeID = recipe.getID(); // Assuming the getID() method returns the recipe ID

                        // Check if the recipe ID doesn't already exist in the IDs set
                        if (!IDs.contains(recipeID)) {
                            // If not, add the recipe to the recipes list and the ID to the IDs set
                            recipes.add(recipe);
                            IDs.add(recipeID);
                        }
                    }
                }
            }

            // Wrap the ArrayList in a RecipeListWrapper
            RecipeList recipeListWrapper = new RecipeList();
            recipeListWrapper.setRecipes(recipes);
            
            // Create JAXBContext for the wrapper class
            JAXBContext context = JAXBContext.newInstance(RecipeList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal the object to a string
            StringWriter writer = new StringWriter();
            marshaller.marshal(recipeListWrapper, writer);
            // Return the valid XML string as the response
            return Response.ok(writer.toString(), MediaType.APPLICATION_XML).build();

        } catch (JAXBException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            // Return a valid XML error message
            String errorXML = "<error>Internal server error</error>";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(errorXML)
                           .type(MediaType.APPLICATION_XML)
                           .build();
        }
        
        /*StringBuilder responseXML = new StringBuilder();
        responseXML.append("<recipes>");
        
        if (type==1){
            ArrayList<Recipe> recipes = Recipe.getAllRecipes();
            for (Recipe recipe : recipes) {
                responseXML.append("<recipe>");
                responseXML.append("<title>").append(recipe.getTitle()).append("</title>");
                responseXML.append("<description>").append(recipe.getDescription()).append("</description>");
                responseXML.append("<nutrients>");
                for (String nutrient : recipe.getNutrients()){
                    responseXML.append("<nutrient>").append(nutrient).append("</nutrient>");
                }
                responseXML.append("</nutrients>");
                responseXML.append("<imageURL>").append(recipe.getURL()).append("</imageURL>");
                responseXML.append("<instructions>").append(recipe.getInstructions()).append("</instructions>");
                responseXML.append("</recipe>");
            }
        }
        
        responseXML.append("</response>");
        
        return Response.ok(responseXML, MediaType.APPLICATION_XML).build();*/

    }
    
    /*
    /**
     * Retrieves representation of an instance of endpoint.RecipeResource
     * @return an instance of java.lang.String
     
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    */

    /**
     * PUT method for updating or creating an instance of RecipeResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
