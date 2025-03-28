package Helper;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Persistence.*;
import java.util.ArrayList;

/**
 *
 * @author mustansir
 */
public class Recipe {
    
    private String title;
    private String description;
    private String instructions;
    private ArrayList<String> nutrients;
    private String URL;
    
    private ArrayList<Recipe> recipes;
    
    public Recipe(String title, String instructions, ArrayList<String> nutrients, String description, String URL){
        this.title=title;
        this.instructions=instructions;
        this.nutrients=nutrients;
        this.description=description;
        this.URL=URL;
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public void setNutrients(ArrayList<String> nutrients){
        this.nutrients=nutrients;
    }
    
    public void setInstructions(String instructions){
        this.instructions=instructions;
    }
    
    public void setURL(String URL){
        this.URL=URL;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public ArrayList<String> getNutrients(){
        return this.nutrients;
    }
    
    public String getInstructions(){
        return this.instructions;
    }
    
    public String getURL(){
        return this.URL;
    }
    
    public static ArrayList<Recipe> getAllRecipes(){
        return Recipe_CRUD.getAllRecipes();
    }
    
    //public static void addRecipe(Recipe recipe){
    //    Recipe_CRUD.write(recipe);
    //}
    
}
