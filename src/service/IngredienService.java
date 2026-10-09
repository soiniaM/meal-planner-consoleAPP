package service;

import model.Ingredient;

import java.util.*;

public class IngredienService implements IIngredienService {

    public  static Set<Ingredient> ingredientList = new HashSet<>();

    public void  addIngredient( Ingredient ingredient) {
        ingredientList.add(ingredient);
    }
}
