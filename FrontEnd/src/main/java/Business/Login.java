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

@WebServlet(name = "Login", urlPatterns = {"/Login"})

public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String customerApiUrl = "http://localhost:8080/UserSession/webresources/User";
    private static final String authenticationCookieName = "Cookie"; // Define the token name
    Authenticate auth = new Authenticate();
    
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
        String password = request.getParameter("password");
        
        if (username==null || password==null){
            RequestDispatcher rd = request.getRequestDispatcher("failedLogin1.jsp");
            rd.forward(request,response);
        }else{
        
            Client client = ClientBuilder.newClient(); 
            WebTarget webTarget = client.target(customerApiUrl)
                    .queryParam("username", username)
                    .queryParam("password", password);

            javax.ws.rs.core.Response apiResponse = webTarget.request(MediaType.APPLICATION_XML).get();
            boolean proceed = false;

            int statusCode=apiResponse.getStatus();

            if (apiResponse.getStatus() == javax.ws.rs.core.Response.Status.OK.getStatusCode()){ //check if customer exists in the database
                proceed = true;
                String token = auth.createJWT("User", username, 100000);
                Cookie newCookie = new Cookie(authenticationCookieName, token);
                response.addCookie(newCookie);
                System.out.println("I got into cookies");
            }

            if(proceed){
                InputStream inputStream = webTarget.request(MediaType.APPLICATION_XML).get(InputStream.class);
                String xml = IOUtils.toString(inputStream, "UTF-8");

                JAXBContext context = JAXBContext.newInstance(User.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                User user = (User) unmarshaller.unmarshal(new StringReader(xml));

                request.getSession().setAttribute("username", username);

                // Set the attributes before forwarding
                request.setAttribute("search", "");
                RequestDispatcher rd = request.getRequestDispatcher("FetchRecipes");
                rd.forward(request,response);
            }else{
                RequestDispatcher rd = request.getRequestDispatcher("failedLogin2.jsp");
                rd.forward(request,response);
            }
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

