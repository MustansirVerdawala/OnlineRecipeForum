<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Helper.*" %>

<%
    String username=null;
    try{
        username = (String)session.getAttribute("username");
    }catch (Exception e){
        
    }
    
    RecipeList recipes = null;
    
    try{
        recipes = (RecipeList)request.getAttribute("recipes");
    }catch (Exception e){
        
    }
    
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Recipes</title>
</head>
<body>
    <h1>Welcome<% if (username != null) { %>, <%= username %><% } %>!</h1>
    
    <div class="search-container">
        <form action="FetchRecipes" method="post">
            <input type="text" id="searchBar" name="searchQuery" placeholder="Search for recipes...">
            <input type="hidden" name="username" value="${username}">
            <button type="submit">Search</button>
        </form>
    </div>
    
    <div class="recipes-container" id="recipesList">
        <% 
            if (recipes != null && recipes.getRecipes() != null) {
                for (Recipe recipe : recipes.getRecipes()) { 
        %>
                <div class="recipe">
                    <strong><%= recipe.getTitle() %></strong><br>
                    <em><%= recipe.getNutrients() %></em><br>
                    <p><%= recipe.getDescription() %></p>
                </div>
        <% 
                } 
            } else { 
        %>
                <p>No recipes found.</p>
        <% 
            } 
        %>
    </div>
    
    <a href="index.html"><button>Go Back</button></a>
    
</body>
</html>