package service;

import model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientService implements   IRecipeIngredientService{
    public static List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    @Override
    public List<RecipeIngredient> getAllRecipeIngredient() {
        return recipeIngredientList;
    }

    @Override
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredientList.add(recipeIngredient);
    }
}
