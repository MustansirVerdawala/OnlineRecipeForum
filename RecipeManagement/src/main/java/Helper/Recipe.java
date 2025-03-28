package Helper;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Persistence.*;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mustansir
 */
@XmlRootElement(name = "recipe")
@XmlAccessorType(XmlAccessType.FIELD)  // Use fields directly for marshalling/unmarshalling
public class Recipe {
    
    @XmlElement
    private String title;
    
    @XmlElement
    private String description;
    
    @XmlElement
    private String instructions;
    
    @XmlElement(name="nutrient")
    private ArrayList<String> nutrients;
    
    @XmlElement
    private String URL;
    
    private ArrayList<Recipe> recipes;
    
    @XmlElement
    private int ID;
    
    public Recipe(){
    }
    
    public Recipe(String title, String instructions, ArrayList<String> nutrients, String description, String URL){
        this.title=title;
        this.instructions=instructions;
        this.nutrients=nutrients;
        this.description=description;
        this.URL=URL;
    }
    
        public Recipe(String title, String instructions, ArrayList<String> nutrients, String description, String URL, int ID){
        this.title=title;
        this.instructions=instructions;
        this.nutrients=nutrients;
        this.description=description;
        this.URL=URL;
        this.ID=ID;
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
    
    public void setID(int ID){
        this.ID=ID;
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
    
    public int getID(){
        return this.ID;
    }
    
    public static ArrayList<Recipe> getAllRecipes(){
        return Recipe_CRUD.getAllRecipes();
    }
    
    //public static void addRecipe(Recipe recipe){
    //    Recipe_CRUD.write(recipe);
    //}
    
}
