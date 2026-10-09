package service;

import model.RecipeIngredient;

import java.util.List;

public interface IRecipeIngredientService {

    List<RecipeIngredient>getAllRecipeIngredient();
    void addRecipeIngredient(RecipeIngredient recipeIngredient);

}
