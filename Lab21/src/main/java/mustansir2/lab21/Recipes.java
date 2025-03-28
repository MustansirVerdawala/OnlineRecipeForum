package mustansir2.lab21;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mustansir
 */

import java.util.ArrayList;

public class Recipes {
    
    private static ArrayList<Recipe> recipes = new ArrayList<Recipe> ();
    
    public Recipes(){
    }
    
    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }
    
    public ArrayList<Recipe> getRecipes(){
        return this.recipes;
    }
    
}
