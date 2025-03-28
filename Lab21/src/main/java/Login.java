

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import mustansir2.lab21.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mustansir
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username =(String) request.getParameter("username");
        String password =(String) request.getParameter("password");
                
        int flag=0;
        
        if ((username == "") || (password=="")){
            RequestDispatcher rd= request.getRequestDispatcher("failedLogin1.jsp");
            rd.forward(request, response);
        }
        
        if (Users.getUsers() != null){
            for (User user: Users.getUsers()){
                if (user.getUsername().equals(username)){
                    if (user.getPassword().equals(password)){
                        User.setCurrentUser(user);
                        flag=1;
                        break;
                    }
                }
            }
        }
        
        if (flag==1){
            RequestDispatcher rd= request.getRequestDispatcher("UserRecipe.jsp");
            rd.forward(request, response);
        }else{
            RequestDispatcher rd= request.getRequestDispatcher("failedLogin2.jsp");
            rd.forward(request, response);
        }        
        
    }

}
