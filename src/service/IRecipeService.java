package service;

import model.Ingredient;
import model.Recipe;
import model.RecipeCategory;
import model.RecipeIngredient;

import java.util.List;

public interface IRecipeService {
    public List<Recipe> addRecipe(String name, RecipeCategory category, String description, int service, List<RecipeIngredient>recipeIngredients);
}
