<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="mustansir2.lab21.Recipes" %>
<%@ page import="mustansir2.lab21.User" %>
<%@ page import="mustansir2.lab21.Users" %>
<%@ page import="mustansir2.lab21.Recipe" %>

<%
    // Get the username from the session
    User user = User.getCurrentUser();
    String username = (user != null) ? user.getUsername() : "Guest";
    
    Recipes recipes = new Recipes();
    
    // Add sample recipes (you can add more or fetch from a DB)
    ArrayList<String> ingredients1 = new ArrayList<>();
    ingredients1.add("Mozzarella Cheese");
    ingredients1.add("Tomatoes");
    ingredients1.add("Basil");
    ingredients1.add("Olive Oil");
    recipes.addRecipe(new Recipe("Caprese Salad", "A fresh Italian salad with mozzarella, tomatoes, and basil.", ingredients1, "Vegetarian, Gluten-Free"));

    ArrayList<String> ingredients2 = new ArrayList<>();
    ingredients2.add("Chicken Breast");
    ingredients2.add("Quinoa");
    ingredients2.add("Spinach");
    ingredients2.add("Lemon");
    recipes.addRecipe(new Recipe("Grilled Chicken Breast with Quinoa", "A protein-rich meal with juicy grilled chicken and nutrient-dense quinoa.", ingredients2, "High Protein, Gluten-Free"));

    ArrayList<String> ingredients3 = new ArrayList<>();
    ingredients3.add("Avocado");
    ingredients3.add("Eggs");
    ingredients3.add("Bacon");
    ingredients3.add("Cheddar Cheese");
    recipes.addRecipe(new Recipe("Keto Avocado Bacon Egg Bowl", "A keto-friendly dish packed with healthy fats and protein.", ingredients3, "High Fat, Low Carb"));

    // Get search query from request (if any)
    String searchQuery = request.getParameter("searchQuery");
    ArrayList<Recipe> filteredRecipes = new ArrayList<>();

    // If there is a search query, filter the recipes
    if (searchQuery != null && !searchQuery.isEmpty()) {
        for (Recipe recipe : recipes.getRecipes()) {
            String queryLower = searchQuery.toLowerCase();
            boolean matches = recipe.getTitle().toLowerCase().contains(queryLower) ||
                              recipe.getNutritionalReq().toLowerCase().contains(queryLower) ||
                              recipe.getIngredients().toString().toLowerCase().contains(queryLower);
            
            if (matches) {
                // Check if the recipe title is already in the filtered list
                boolean alreadyExists = false;
                for (Recipe filteredRecipe : filteredRecipes) {
                    if (filteredRecipe.getTitle().equalsIgnoreCase(recipe.getTitle())) {
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    filteredRecipes.add(recipe);
                }
            }
        }
    } else {
        // No search query, show all recipes but ensure no duplicates
        for (Recipe recipe : recipes.getRecipes()) {
            boolean alreadyExists = false;
            for (Recipe filteredRecipe : filteredRecipes) {
                if (filteredRecipe.getTitle().equalsIgnoreCase(recipe.getTitle())) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                filteredRecipes.add(recipe);
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Recipes</title>
</head>
<body>
    <h1>Welcome, <%= username %>!</h1>
    
    <div class="search-container">
        <form action="UserRecipe.jsp" method="get">
            <input type="text" id="searchBar" name="searchQuery" placeholder="Search for recipes..." value="<%= searchQuery != null ? searchQuery : "" %>">
            <button type="submit">Search</button>
        </form>
    </div>
    
    <div class="recipes-container" id="recipesList">
        <% for (Recipe recipe : filteredRecipes) { %>
           <div class="recipe">
                <strong><%= recipe.getTitle() %></strong><br>
                <em><%= recipe.getNutritionalReq() %></em><br>
                <p><%= recipe.getDescription() %></p>
            </div>
        <% } %>
    </div>
    
    <a href="index.html"><button>Go Back</button></a>
    
</body>
</html>