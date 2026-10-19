package service;

import model.MealPlan;

import java.util.List;

public interface IShoppingListService {
    void generateShoppingList(List<MealPlan> meals);
}
