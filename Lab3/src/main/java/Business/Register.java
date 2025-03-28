package Business;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import Helper.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.util.ArrayList;


/**
 *
 * @author mustansir
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username =(String) request.getParameter("username");
        String password1=(String) request.getParameter("password1");
        String password2=(String) request.getParameter("password2");
        String email=(String) request.getParameter("email");
        
        ArrayList<User> users= User.getAllUsers();
        
        int flag=0;
        
        if ((username == "") || (password1=="") || (password2=="" || (email==""))){
            RequestDispatcher rd= request.getRequestDispatcher("failedRegistration1.jsp");
            rd.forward(request, response);
        }
        
        if (!password1.equals(password2)){
            RequestDispatcher rd= request.getRequestDispatcher("failedRegistration3.jsp");
            rd.forward(request, response);
        }
        
        if (users != null){
            for (User user: users){
                if (user.getUsername().equals(username)){
                    if (user.getPassword().equals(password1)){
                        flag=1;
                        break;
                    }
                }
            }
        }
        
        if (flag==1){
            RequestDispatcher rd= request.getRequestDispatcher("failedRegistration2.jsp");
            rd.forward(request, response);
        }else{
            if (password1.equals(password2)){
                User user=new User(username, password1);
                User.setCurrentUser(user);
                User.addUser(username, password1, email);
                RequestDispatcher rd= request.getRequestDispatcher("UserRecipe.jsp");
                rd.forward(request, response);
            }
        }        
        
    }

}
