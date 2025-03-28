/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import Helper.Recipe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author mustansir
 */
public class Recipe_CRUD {
    private static Connection getCon () {
        Connection con=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RecipeRealm?autoReconnect=true&useSSL=false", "root", "Yasmeen_123");
            System.out.println("Connection established.");
        }catch(Exception e){
            System.out.println(e);
        }
        
        return con;
        
    }
    
    public static ArrayList<Recipe> readTitle(String title) {
        ArrayList <Recipe> recipes=new ArrayList<Recipe>();
        
        try{
            Connection con=getCon() ;
            String q = "SELECT * FROM Recipes WHERE Title LIKE " + title + ";";
            PreparedStatement ps=con.prepareStatement(q);
            ResultSet rs=ps. executeQuery();
            while (rs.next()){
                //been= new UserInfo() ;
                String title_=rs.getString("Title");
                String description=rs.getString ("Description") ;
                String URL=rs.getString("ImageURL");
                String instructions=rs.getString("Instructions");
                
                int recipeID=Integer.parseInt(rs.getString("RecipeID"));
                q = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE Nutrients.NutrientID=Contain.NutrientID AND Contain.RecipeID= " + recipeID + ";";
                ps=con.prepareStatement(q);
                ResultSet rs2=ps.executeQuery();
                String nutrientsStr = rs2.getString("Nutrient_Name"); // Get as a String
                ArrayList<String> nutrients = new ArrayList<>(Arrays.asList(nutrientsStr.split(",")));
                
                recipes.add(new Recipe(title_, instructions, nutrients, description, URL));
            }
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        return recipes;
    }
    
    public static ArrayList<Recipe> readNutrient(String Nutrient) {
        ArrayList <Recipe> recipes=new ArrayList<Recipe>();
        
        try{
            Connection con=getCon();
            String q = "SELECT Title, Description, ImageURL, Instructions FROM Contain, Nutrients, Recipes "
                    + "WHERE Nutrients.NutrientID=Contain.NutrientID AND"
                    + "Contain.RecipeID=Recipe.RecipeID AND "
                    + "Nutrient.Nutrient_Name LIKE '" + Nutrient + "';";
            PreparedStatement ps=con.prepareStatement(q);
            ResultSet rs=ps. executeQuery();
            while (rs.next()){
                //been= new UserInfo() ;
                String title_=rs.getString("Title");
                String description=rs.getString ("Description") ;
                String URL=rs.getString("ImageURL");
                String instructions=rs.getString("Instructions");
                
                int recipeID=Integer.parseInt(rs.getString("RecipeID"));
                q = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE Nutrients.NutrientID=Contain.NutrientID AND Contain.RecipeID= " + recipeID + ";";
                ps=con.prepareStatement(q);
                rs=ps. executeQuery();
                String nutrientsStr = rs.getString("Nutrient_Name"); // Get as a String
                ArrayList<String> nutrients = new ArrayList<>(Arrays.asList(nutrientsStr.split(",")));
                
                recipes.add(new Recipe(title_, instructions, nutrients, description, URL));
            }
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        return recipes;
    }
    
    /*
    
    public static void write(Recipe recipe) {
        
        try{
            Connection con=getCon() ;
            String q = "INSERT INTO Recipes (Title, Description, ImageURL, Instructions) VALUES ('" + recipe.getTitle() + "', '" + recipe.getDescription() + "', '" + recipe.getURL() + "', '" + recipe.getInstructions() + "');";
            PreparedStatement ps=con.prepareStatement(q);
            ps.executeUpdate();
            
            q = "SELECT RecipeID FROM Recipes WHERE Title='" + recipe.getTitle() + "');";
            ps=con.prepareStatement(q);
            ps.executeUpdate();
            ResultSet rs=ps. executeQuery();
            
            if (rs.next()){
                int recipeID = rs.getInt("RecipeID");
            
                for (String name: recipe.getNutrients()){
                    q = "SELECT NutrientID FROM Nutrients WHERE Nutrient_Name='" + name + "');";
                    ps=con.prepareStatement(q);
                    ps.executeUpdate();
                    rs=ps.executeQuery();

                    if (rs.next()){
                        int nid=rs.getInt("NutrientID");
                        q = "INSERT INTO Contain (RecipeID, NutrientID) VALUES (" + recipeID + ", " + nid + ";";
                        ps=con.prepareStatement(q);
                        ps.executeUpdate();
                    }else{
                        q = "INSERT INTO Nutrients (RecipeID) VALUES (" + recipeID + ");";
                        ps=con.prepareStatement(q);
                        ps.executeUpdate();
                        
                        q = "SELECT NutrientID From Nutrients WHERE RecipeID=" + recipeID + ";";
                        ps=con.prepareStatement(q);
                        rs=ps.executeQuery();
                        
                        int nid = rs.getInt("NutrientID");
                        
                        q = "INSERT INTO Contain (RecipeID, NutrientID) VALUES (" + recipeID + ", " + nid + ";";
                        ps=con.prepareStatement(q);
                        ps.executeUpdate();
                    }
                }
            }
            
            con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    */
    
    public static ArrayList<Recipe> getAllRecipes(){
        ArrayList <Recipe> recipes = new ArrayList<Recipe>();
        
        try{
            Connection con=getCon() ;
            String q = "SELECT Title, Description, ImageURL, Instructions, RecipeID FROM Recipes;";
            PreparedStatement ps=con.prepareStatement(q);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()){
            
                String title=rs.getString("Title");
                String description=rs.getString ("Description") ;
                String URL=rs.getString("ImageURL");
                String instructions=rs.getString("Instructions");
                int RID=rs.getInt("RecipeID");
                
                q = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE "
                        + "Contain.NutrientID=Nutrients.NutrientID AND "
                        + "Contain.RecipeID=" + RID + ";";
                ps=con.prepareStatement(q);
                ResultSet rs1=ps.executeQuery();
                
                while (rs1.next()){
                    
                    String nutrientsStr = rs1.getString("Nutrient_Name"); // Get as a String
                    ArrayList<String> nutrients = new ArrayList<>(Arrays.asList(nutrientsStr.split(",")));

                    recipes.add(new Recipe(title, instructions, nutrients, description, URL));
                }
            }
                con.close();
        }catch (Exception e) {
            System.out.println(e);
        }
        
        return recipes;
    }
}
