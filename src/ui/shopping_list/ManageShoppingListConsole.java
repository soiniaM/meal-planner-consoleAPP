package ui.shopping_list;

import model.Ingredient;
import model.MealPlan;
import service.MealPlanService;
import service.ShoppingListService;
import ui.meal_plan.ManageMealPlan;

import java.time.LocalDate;
import java.util.List;

public class ManageShoppingListConsole {

    public static void viewShoppingList() {
        MealPlanService mealPlanService = new MealPlanService();
        ShoppingListService shoppingListService = new ShoppingListService();

        LocalDate startDate = ManageMealPlan.readDate("Enter start date (yyyy-MM-dd): ");
        if (startDate == null) {
            return;
        }

        LocalDate endDate = ManageMealPlan.readDate("Enter end date (yyyy-MM-dd): ");
        if (endDate == null) {
            return;
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
            return;
        }
        List<MealPlan> matchingMeals = mealPlanService.getMealPlanByDates(startDate, endDate);
        shoppingListService.generateShoppingList(matchingMeals);

    }

}
