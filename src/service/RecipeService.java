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

    @Override
    public void viewAllRecipes() {
        System.out.println("\n=== All Recipes ===");

        if (recipeList.isEmpty()) {
            System.out.println("No recipes available. Add a recipe first.");
            return;
        }
        int index = 1;

        for (Recipe recipe : recipeList) {
            System.out.println("\n" + index + ". " + recipe.getName());
            System.out.println("Ingredients:");
            for (Ingredient ingredient : recipe.getIngredients()) {
                System.out.printf("   - %-15s %s %s%n",
                        ingredient.getName(),
                        ingredient.getQuantity(),
                        ingredient.getUnit());
            }
            index++;
        }

        System.out.println("\nTotal recipes: " + recipeList.size());
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


