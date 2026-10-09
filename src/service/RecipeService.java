package service;

import model.Ingredient;
import model.Recipe;
import model.RecipeCategory;
import model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeService implements IRecipeService {
    public static ArrayList<Recipe> recipes = new ArrayList<>();

    public List<Recipe> addRecipe(String name, RecipeCategory category, String description, int service, List<RecipeIngredient> recipeIngredients) {
        recipeIngredients.forEach(recipeIngredient ->
                IngredienService.ingredientList.add(recipeIngredient.getIngredient())); //save ingredient ins the shrare list
        RecipeIngredientService.recipeIngredientList.addAll(recipeIngredients);
        recipes.add(new Recipe(name, category, service, description));
        displayRecipe(new Recipe(name, category, service, description));
        return recipes;
    }

    private void displayRecipe(Recipe recipe) {
        System.out.println();
        System.out.println("Recipe added successfully!");
        recipe.toString();
    }
}
