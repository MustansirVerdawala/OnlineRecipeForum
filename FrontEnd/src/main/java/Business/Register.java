package Business;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
import Helper.*;

@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws JAXBException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JAXBException {
        
        String username = request.getParameter("username");
        String password1= request.getParameter("password1");
        String password2= request.getParameter("password2");
        String email = request.getParameter("email");
        
        // Validate inputs
        if (username == null || username.isEmpty() || password1 == null || password1.isEmpty() || 
            password2 == null || password2.isEmpty() || email == null || email.isEmpty()) {
            forwardToPage(request, response, "failedRegistration1.jsp");
            return;
        }

        // Check if passwords match
        if (!password1.equals(password2)) {
            forwardToPage(request, response, "failedRegistration3.jsp");
            return;
        }
        
        Client client = ClientBuilder.newClient(); 
        WebTarget webTarget = client.target("http://localhost:8080/UserSession/webresources/Register")
                .queryParam("username", username)
                .queryParam("password", password1)
                .queryParam("email", email);

        javax.ws.rs.core.Response apiResponse = webTarget.request(MediaType.APPLICATION_XML).get();

        int statusCode=apiResponse.getStatus();

        if (apiResponse.getStatus() == javax.ws.rs.core.Response.Status.OK.getStatusCode()){ //check if customer exists in the database
            String responseXml = apiResponse.readEntity(String.class);

            // Extract flag value from the response XML
            int flag = Integer.parseInt(responseXml.replaceAll("<[^>]*>", "")); // Extracts integer from XML
            
            if (flag==1){
                User user = new User(username, password1);
                request.setAttribute("user", user);
                request.getSession().setAttribute("username",user.getUsername());
                
                // Set the attributes before forwarding
                request.setAttribute("search", "");
                RequestDispatcher rd = request.getRequestDispatcher("FetchRecipes");
                rd.forward(request,response);
            }else{
                RequestDispatcher rd = request.getRequestDispatcher("failedRegistration2.jsp");
                rd.forward(request,response);
            }
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("failedRegistration2.jsp");
            rd.forward(request,response);
        }

    }
    
    // Utility method to forward the request to a specific JSP page
    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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