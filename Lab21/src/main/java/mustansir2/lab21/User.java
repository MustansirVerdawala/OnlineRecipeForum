package mustansir2.lab21;






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
    private static User instance=null;
    
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
    
    public static void setCurrentUser(User user){
        instance=user;
    }
    
    public static User getCurrentUser(){
        return instance;
    }
    
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }
    
}
