/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Business;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.NotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;
import Helper.*;

/**
 *
 * @author mustansir
 */
@WebServlet(name = "FetchRecipes", urlPatterns = {"/FetchRecipes"})
public class FetchRecipes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JAXBException {
        
        String search1= (String) request.getAttribute("search");
        
        String search2= request.getParameter("searchQuery");
        
        String search="";
        
        if (search2==null){
            if (search1 !=null){
                search=search1;
            }
        }else{
            search=search2;
        }
        
        /*String username1 = (String) request.getAttribute("username");
        String username2 = request.getParameter("username");
        
        String username="";
        
        if (username2==null){
            if (username1 !=null){
                username=username1;
            }
        }else{
            username=username2;
        }*/
        
        Client client = ClientBuilder.newClient(); 
        WebTarget webTarget = client.target("http://localhost:8080/RecipeManagement/webresources/Recipe")
                .queryParam("search", search);
        javax.ws.rs.core.Response apiResponse = webTarget.request(MediaType.APPLICATION_XML).get();

        int statusCode=apiResponse.getStatus();

        if (apiResponse.getStatus() == javax.ws.rs.core.Response.Status.OK.getStatusCode()){ //check if customer exists in the database
            RecipeList recipes = null;

            InputStream inputStream = webTarget.request(MediaType.APPLICATION_XML).get(InputStream.class);
            String xml = IOUtils.toString(inputStream, "UTF-8");

            JAXBContext context = JAXBContext.newInstance(RecipeList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            try{
                recipes = (RecipeList) unmarshaller.unmarshal(new StringReader(xml));
            }catch (Exception e){
                System.out.println(e);
            }
            
            //request.getSession().setAttribute("username",username);
            request.setAttribute("recipes", recipes);
            
            RequestDispatcher rd = request.getRequestDispatcher("UserRecipe.jsp");
            rd.forward(request,response);
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JAXBException ex) {
            Logger.getLogger(FetchRecipes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JAXBException ex) {
            Logger.getLogger(FetchRecipes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
