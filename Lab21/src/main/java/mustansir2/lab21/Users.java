package mustansir2.lab21;





/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;

/**
 *
 * @author mustansir
 */
public class Users {
    
    public static ArrayList<User> users;
    
    public Users(){
        users=new ArrayList<User>();
    }
    
    public static void addUser(User user){
        users.add(user);
    }
    
    public static ArrayList<User> getUsers(){
        return users;
    }
    
}
