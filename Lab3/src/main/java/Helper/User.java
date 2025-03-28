package Helper;

import Persistence.*;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mustansir
 */
public class User {
    
    private String username;
    private String password;
    private static User currentUser;
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }
    
    public static User getCurrentUser(){
        return User.currentUser;
    }
    
    public static void setCurrentUser(User user){
        User.currentUser=user;
    }
    
    public static ArrayList<User> getAllUsers(){
        return User_CRUD.getAllUsers();
    }
    
    public static void addUser(String username, String password, String email){
        User_CRUD.write(username, password, email);
    }
    
}
