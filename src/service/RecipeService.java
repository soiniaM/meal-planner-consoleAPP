package service;

import model.Recipe;

import java.util.HashSet;
import java.util.Set;

public class RecipeService implements IRecipeService {
    public static Set<Recipe> recipes = new HashSet<>();

}
