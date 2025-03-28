<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Helper.*" %>

<%
    // Get the username from the session
    User user = User.getCurrentUser();
    String username = (user != null) ? user.getUsername() : "Guest";
    
    ArrayList<Recipe> recipes = Recipe.getAllRecipes();
    
    // Get search query from request (if any)
    String searchQuery = request.getParameter("searchQuery");
    ArrayList<Recipe> filteredRecipes = new ArrayList<>();

    // If there is a search query, filter the recipes
    if (searchQuery != null && !searchQuery.isEmpty()) {
        for (Recipe recipe : recipes) {
            String queryLower = searchQuery.toLowerCase();
            boolean matches = recipe.getTitle().toLowerCase().contains(queryLower) ||
                              recipe.getNutrients().toString().toLowerCase().contains(queryLower);
            
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
        for (Recipe recipe : recipes) {
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
                <em><%= recipe.getNutrients() %></em><br>
                <p><%= recipe.getDescription() %></p>
            </div>
        <% } %>
    </div>
    
    <a href="index.html"><button>Go Back</button></a>
    
</body>
</html>