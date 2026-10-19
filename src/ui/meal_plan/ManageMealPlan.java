package ui.meal_plan;

import model.Ingredient;
import model.MealPlan;
import service.MealPlanService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ManageMealPlan {
    private static final Scanner scanner = new Scanner(System.in);

    public static LocalDate readDate(String prompt) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid date. Use yyyy-MM-dd or enter 0 to go back."
                );
            }
        }
    }



    /*
    public static void createWeeklyMealPlan() {
        MealType mealType;
        System.out.println("\n=== Create Weekly Meal Plan ===");

        if (RecipeService.recipes.isEmpty()) {
            System.out.println("No recipes available. Please add a recipe first.");
            return;
        }
        LocalDate date;
        while (true) {
            System.out.print("Enter meal date (YYYY-MM-DD, or 0 to cancel): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return;
            }

            try {
                date = LocalDate.parse(input);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }

        }
        mealType = getMealType();
        // Check whether this meal has already been planned
        for (MealPlan entry : MealPlanService.mealPlans) {
            if (entry.getDate().equals(date)
                    && entry.getMealType() == mealType) {
                System.out.println("A recipe is already planned for this meal.");
                return;
            }
        }
        Recipe selectedRecipe = selectOrCreateRecipe();

        if (selectedRecipe == null) {
            System.out.println("Meal planning cancelled.");
            return;
        }

        MealPlan entry = new MealPlan(date, selectedRecipe,mealType);
        MealPlanService.mealPlans.add(entry);

        System.out.println("\nMeal planned successfully!"   );
        System.out.println("Date: " + date);
        System.out.println("Meal: " + mealType);
        System.out.println("Recipe: " + selectedRecipe.getName());

    }
*/

    /*
    public static void viewWeeklyMealPlans() {

        LocalDate startDate = readDate("Enter start date (yyyy-MM-dd): ");
        if (startDate == null) {
            return;
        }

        LocalDate endDate = readDate("Enter end date (yyyy-MM-dd): ");
        if (endDate == null) {
            return;
        }

        if (endDate.isBefore(startDate)) {
            System.out.println("End date cannot be before start date.");
            return;
        }

        List<MealPlan> matchingMeals = mealPlanService.getMealPlanByDates(startDate, endDate);

        displayMealPlanTable(startDate, endDate, matchingMeals);
        // Ask whether the user wants a shopping list
        System.out.print(
                "\nWould you like to generate a shopping list? (yes/no): ");
        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("yes")
                || answer.equalsIgnoreCase("y")) {

            generateShoppingList(matchingMeals);
        }
    }

 */

/*
    private static List<MealPlan> getMatchingMeals(LocalDate startDate, LocalDate endDate) {

        List<MealPlan> matchingMeals = MealPlanService.mealPlans.stream()
                .filter(meal -> !meal.getDate().isBefore(startDate))
                .filter(meal -> !meal.getDate().isAfter(endDate))
                .sorted(Comparator.comparing(MealPlan::getDate)
                        .thenComparing(MealPlan::getMealType))
                .toList();

        if (matchingMeals.isEmpty()) {
            System.out.println("No meals planned for this date interval.");
            return List.of();
        }
        return matchingMeals;
    }

 */
/*
    private static MealType getMealType(){
        while (true) {
        System.out.println("\nSelect meal type:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Choose an option: ");

        //int mealChoice=0;

        try {
          int  mealChoice = Integer.parseInt(scanner.nextLine().trim());
         switch (mealChoice) {
            case 1:
                return MealType.BREAKFAST;
            case 2:
                return MealType.LUNCH;
            case 3:
                return MealType.DINNER;
            case 0:
                return null;
            default:
                System.out.println("Invalid selection. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
        }

    private static Recipe selectOrCreateRecipe() {

        while (true) {
            System.out.println("\n=== Recipe Selection ===");
            System.out.println("1. Select an Existing Recipe");
            System.out.println("2. Add a New Recipe");
            System.out.println("0. Cancel");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
              return ManageRecipe.chooseExistingRecipe();

                case 2:
                    ManageRecipe.createRecipe();

                    // After adding, let the user choose a recipe.
                    break;

                case 0:

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

 */

}