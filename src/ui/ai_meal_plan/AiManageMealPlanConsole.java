package ui.ai_meal_plan;

import model.MealPlan;
import service.AIMealPlanService;

import java.time.LocalDate;
import java.util.List;

public class AiManageMealPlanConsole {

    public void displayWeeklyMealPlan(List<MealPlan> proposedPlan) {
        System.out.println("\n========== AI WEEKLY MEAL PLAN ==========");

        LocalDate currentDate = null;

        for (MealPlan meal : proposedPlan) {

            if (!meal.getDate().equals(currentDate)) {
                currentDate = meal.getDate();

                System.out.println("\n" + currentDate);
                System.out.println("------------------------------");
            }

            System.out.printf("%-10s : %s%n",
                    meal.getMealType(),
                    meal.getRecipe().getName());
        }

        System.out.println("\n=========================================");
    }
}
