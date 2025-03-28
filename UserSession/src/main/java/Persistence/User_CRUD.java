/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

/**
 *
 * @author mustansir
 */

import Helper.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class User_CRUD {

    private static Connection getCon () {
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Users?autoReconnect=true&useSSL=false", "root", "Yasmeen_123");
            System.out.println("Connection established.");
        }catch(Exception e){
            System.out.println(e);
        }
        
        return con;
        
    }
    
    public static User getUser(String username, String password) {
        User userInfo=null;
        
        try{
            Connection con=getCon() ;
            String q = "SELECT * FROM Users WHERE Username ='" + username + "';";
            PreparedStatement ps=con.prepareStatement(q);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                //been= new UserInfo() ;
                String username_=rs.getString("username");
                String pass=rs.getString ("password") ;
                if (username_.equals(username) && pass.equals(password)) {
                    userInfo = new User(username, password) ;
                }
            }
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        return userInfo;
    }
    
    public static int write(String username, String password, String email) {
        
        try{
            Connection con=getCon() ;
            String q = "INSERT INTO Users (Username, Password, Email) VALUES ('" + username + "', '" + password + "', '" + email + "');";
            PreparedStatement ps=con.prepareStatement(q);
            ps.executeUpdate();
            con.close();
            return 1;
        }catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        
    }
    
    public static void update(String username, String password, String email) {
        User userInfo = User_CRUD.getUser(username, password);
        
        if (userInfo!=null){
            try{
                Connection con=getCon() ;
                String q = "UPDATE Users SET Username=" + username + ", Password=" + password + ", Email=" + email + ";";
                PreparedStatement ps=con.prepareStatement(q);
                ps.executeUpdate();
                con.close();
            }catch (Exception e) {
                System.out.println(e);
            }
        }
        
    }
    
    public static void delete(String username){
        try{
            Connection con=getCon() ;
            String q = "DELETE from Users WHERE Username=" + username + ";";
            PreparedStatement ps=con.prepareStatement(q);
            ps.executeUpdate();
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static ArrayList<User> getAllUsers(){
        ArrayList <User> users = new ArrayList<User>();
        
        try{
            Connection con=getCon() ;
            String q = "SELECT * FROM Users;";
            PreparedStatement ps=con.prepareStatement(q);
            ResultSet rs=ps. executeQuery();
            while (rs.next()){
                //been= new UserInfo() ;
                String username_=rs.getString("Username");
                String pass=rs.getString ("Password") ;
                users.add(new User(username_, pass));
            }
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        return users;
    }
    
}
