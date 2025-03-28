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
public class Recipe {
    
    private String title;
    private String description;
    private ArrayList<String> ingredients;
    private String nutritionalReq="Unknown";
    
    public Recipe(String title, String description, ArrayList<String> ingredients){
        this.title=title;
        this.description=description;
        this.ingredients=ingredients;
    }
    
    public Recipe(String title, String description, ArrayList<String> ingredients, String nutritionalReq){
        this.title=title;
        this.description=description;
        this.ingredients=ingredients;
        this.nutritionalReq=nutritionalReq;
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public void setIngredients(ArrayList<String> ingredients){
        this.ingredients=ingredients;
    }
    
    public void setNutritionalReq(String nutritionalReq){
        this.nutritionalReq=nutritionalReq;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public ArrayList<String> getIngredients(){
        return this.ingredients;
    }
    
    public String getNutritionalReq(){
        return this.nutritionalReq;
    }
    
}
