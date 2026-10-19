package service;

import model.Ingredient;
import model.MealPlan;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingListService implements IShoppingListService{
    @Override
    public void generateShoppingList(List<MealPlan> meals) {

        Map<String, Double> quantities = meals.stream()
                // Get the recipes from the planned meals
                .map(MealPlan::getRecipe)

                // Flatten all recipe ingredients into one stream
                .flatMap(recipe -> recipe.getIngredients().stream())

                // Group by ingredient name and unit
                .collect(Collectors.groupingBy(
                        ri -> ri.getName().trim().toLowerCase()
                                + "|" + ri.getUnit().toLowerCase(),

                        // Sum quantities for matching ingredients and units
                        Collectors.summingDouble(
                                Ingredient::getQuantity)
                ));

        displayShoppingList(quantities);
    }

    private static void displayShoppingList(Map<String, Double> quantities) {

        System.out.println("\n========== SHOPPING LIST ==========");

        quantities.forEach((key, quantity) -> {
            String[] parts = key.split("\\|");

            System.out.printf(
                    "%-25s %-15.2f %-15s%n",
                    parts[0], quantity, parts[1]
            );
        });
    }


}
