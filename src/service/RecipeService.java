package service;

import model.Ingredient;
import model.Recipe;
import ui.recipe.ManageRecipe;

import java.util.*;

public class RecipeService implements IRecipeService {
    public static Set<Recipe> recipeList = new HashSet<>();
    @Override
    public void createRecipe() {
        System.out.println();
        System.out.println("\n=== Add a Recipe ===");

        Recipe recipe =  ManageRecipe.buildRecipe();
        ManageRecipe.addIngredients(recipe);

        if (recipeList.add(recipe)) {
            System.out.println("Recipe added successfully: " );
        } else {
            System.out.println("Recipe already exists. No changes made.");
        }
    }

    public Set<Recipe> getAllRecipes(){
        return recipeList;
}
    @Override
    public void deleteRecipe() {
        ManageRecipe.deleteRecipe();
    }
    @Override
    public void editRecipe() {
        ManageRecipe.editRecipe();
    }
}


