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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Recipes?autoReconnect=true&useSSL=false", "root", "Yasmeen_123");
            System.out.println("Connection established.");
        }catch(Exception e){
            System.out.println(e);
        }
        
        return con;
        
    }
    
    public static ArrayList<Recipe> readTitle(String title) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            Connection con = getCon();

            // Use LOWER() to make the search case-insensitive and '%' for partial match
            String query = "SELECT * FROM Recipes WHERE LOWER(Title) LIKE LOWER(\'%"+title+"%\');";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                String title_ = rs.getString("Title");
                String description = rs.getString("Description");
                String URL = rs.getString("ImageURL");
                String instructions = rs.getString("Instructions");
                int ID = rs.getInt("RecipeID");

                // Get nutrients for the current recipe
                ArrayList<String> nutrients = new ArrayList<>();
                String nutrientQuery = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE "
                        + "Contain.NutrientID = Nutrients.NutrientID AND "
                        + "Contain.RecipeID = ?";
                PreparedStatement nutrientPs = con.prepareStatement(nutrientQuery);
                nutrientPs.setInt(1, ID);
                ResultSet rs2 = nutrientPs.executeQuery();

                while (rs2.next()) {
                    nutrients.add(rs2.getString("Nutrient_Name"));
                }

                recipes.add(new Recipe(title_, instructions, nutrients, description, URL, ID));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return recipes;
    }
    
    public static ArrayList<Recipe> readNutrient(String nutrient) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            Connection con = getCon();

            // Use LOWER() for case-insensitivity and '%' for partial match
            String q = "SELECT Title, Description, ImageURL, Instructions, Recipes.RecipeID FROM Nutrients, Contain, Recipes "
                    + "WHERE Nutrients.NutrientID = Contain.NutrientID AND "
                    + "Contain.RecipeID = Recipes.RecipeID AND "
                    + "LOWER(Nutrient_Name) LIKE LOWER(\'%"+nutrient+"%\');";

            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String title_ = rs.getString("Title");
                String description = rs.getString("Description");
                String URL = rs.getString("ImageURL");
                String instructions = rs.getString("Instructions");
                int recipeID = rs.getInt("RecipeID");

                // Get the nutrients for the current recipe
                ArrayList<String> nutrients = new ArrayList<>();
                String nutrientQuery = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE "
                        + "Nutrients.NutrientID = Contain.NutrientID AND "
                        + "Contain.RecipeID = ?";
                PreparedStatement nutrientPs = con.prepareStatement(nutrientQuery);
                nutrientPs.setInt(1, recipeID);
                ResultSet rs2 = nutrientPs.executeQuery();

                while (rs2.next()) {
                    nutrients.add(rs2.getString("Nutrient_Name"));
                }

                recipes.add(new Recipe(title_, instructions, nutrients, description, URL, recipeID));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return recipes;
    }
    
    public static ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            Connection con = getCon();
            String q = "SELECT Title, Description, ImageURL, Instructions, RecipeID FROM Recipes;";
            PreparedStatement ps = con.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String URL = rs.getString("ImageURL");
                String instructions = rs.getString("Instructions");
                int RID = rs.getInt("RecipeID");

                // Fetch all nutrients for the current recipe
                ArrayList<String> nutrients = new ArrayList<>();
                String nutrientQuery = "SELECT Nutrient_Name FROM Nutrients, Contain WHERE "
                        + "Contain.NutrientID = Nutrients.NutrientID AND "
                        + "Contain.RecipeID = ?;";

                PreparedStatement nutrientPs = con.prepareStatement(nutrientQuery);
                nutrientPs.setInt(1, RID);
                ResultSet rs1 = nutrientPs.executeQuery();

                // Collect all nutrients for the recipe in a list
                while (rs1.next()) {
                    nutrients.add(rs1.getString("Nutrient_Name"));
                }

                // Add the recipe only once after collecting all nutrients
                recipes.add(new Recipe(title, instructions, nutrients, description, URL));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return recipes;
    }
}
