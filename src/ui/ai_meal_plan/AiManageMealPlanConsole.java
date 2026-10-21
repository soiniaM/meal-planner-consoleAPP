package ui.ai_meal_plan;

import model.MealPlan;
import model.MealType;
import model.Recipe;
import service.AIMealPlanService;
import service.IAIMealPlanService;
import service.MealPlanService;
import service.RecipeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AiManageMealPlanConsole {
    private final IAIMealPlanService iaiMealPlanService = new AIMealPlanService();
    private final  Scanner scanner= new Scanner(System.in);

    public void generateAIWeeklyMealPlan(){
        iaiMealPlanService.generateAIWeeklyMealPlan();
    }

    public void modifyWeeklyMealPlan(
            List<MealPlan> proposedPlan,
            Set<Recipe> recipes) {
        MealPlanService mealPlanService = new MealPlanService();

        while (true) {
            System.out.println("\n1. Modify a meal");
            System.out.println("0. Finish editing");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                return;
            }

            if (!choice.equals("1")) {
                System.out.println("Invalid option.");
                continue;
            }

            displayWeeklyMealPlan(proposedPlan);

            LocalDate date;

            try {
                System.out.print("Enter the date to modify (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date.");
                continue;
            }

            if (proposedPlan.stream()
                    .noneMatch(meal -> meal.getDate().equals(date))) {
                System.out.println("Date not found in the proposed plan.");
                continue;
            }
            MealType mealType = mealPlanService.getMealType();
            int index = -1;

            for (int i = 0; i < proposedPlan.size(); i++) {
                MealPlan meal = proposedPlan.get(i);

                if (meal.getDate().equals(date)
                        && meal.getMealType() == mealType) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("Meal not found.");
                continue;
            }

            List<Recipe> recipeOptions = new ArrayList<>(recipes);

            System.out.println("\nAvailable recipes:");

            for (int i = 0; i < recipeOptions.size(); i++) {
                System.out.printf("%d. %s%n",
                        i + 1, recipeOptions.get(i).getName());
            }

            System.out.print("Select a recipe number (0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                continue;
            }

            try {
                int selected = Integer.parseInt(input);

                if (selected < 1 || selected > recipeOptions.size()) {
                    System.out.println("Invalid recipe number.");
                    continue;
                }

                Recipe selectedRecipe = recipeOptions.get(selected - 1);

                proposedPlan.set(index, new MealPlan(
                        date, selectedRecipe, mealType));

                System.out.println("Meal updated in the proposed plan.");

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    public boolean confirmWeeklyMealPlan() {

        System.out.print(
                "\nSave this weekly meal plan? (Y/N): ");

        String answer = scanner.nextLine().trim();

        return answer.equalsIgnoreCase("Y")
                || answer.equalsIgnoreCase("YES");
    }

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
