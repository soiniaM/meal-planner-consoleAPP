package service;

import model.MealPlan;
import model.MealType;
import model.Recipe;

import ui.meal_plan.ManageMealPlan;
import ui.recipe.ManageRecipe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MealPlanService  implements IMealPlanService{
    private  final Scanner scanner = new Scanner(System.in);
    private final RecipeService recipeService = new RecipeService();
    private final ShoppingListService shoppingListService = new ShoppingListService();

    public static List<MealPlan> mealPlans = new ArrayList<>();

    @Override
    public void createWeeklyMealPlan() {
        MealType mealType;
        System.out.println("\n=== Create Weekly Meal Plan ===");

        if (RecipeService.recipeList.isEmpty()) {
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

    public List<MealPlan>  getMealPlanByDates(LocalDate startDate,LocalDate endDate){
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
        return  matchingMeals;
       // return getMatchingMeals(startDate, endDate);

    }

    @Override
    public void viewWeeklyMealPlans() {

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

        List<MealPlan> matchingMeals = getMealPlanByDates(startDate, endDate);

       displayMealPlanTable(startDate, endDate, matchingMeals);
        // Ask whether the user wants a shopping list
        System.out.print(
                "\nWould you like to generate a shopping list? (yes/no): ");
        String answer = scanner.nextLine().trim();

        if (answer.equalsIgnoreCase("yes")
                || answer.equalsIgnoreCase("y")) {

            shoppingListService.generateShoppingList(matchingMeals);
        }
    }
    private  void displayMealPlanTable(
            LocalDate startDate,
            LocalDate endDate,
            List<MealPlan> meals) {

        System.out.println();
        System.out.printf("%-40s", "WEEKLY MEAL PLAN");
        System.out.println(startDate + " to " + endDate);
        System.out.println(" ");
        System.out.printf(
                "%-15s %-25s %-25s %-25s%n",
                "DATE", "BREAKFAST", "LUNCH", "DINNER"
        );

        System.out.println("-".repeat(90));

        for (LocalDate date = startDate;
             !date.isAfter(endDate);
             date = date.plusDays(1)) {

            String breakfast = "-";
            String lunch = "-";
            String dinner = "-";

            for (MealPlan meal : meals) {

                if (!meal.getDate().equals(date)) {
                    continue;
                }

                String recipeName = meal.getRecipe().getName();

                switch (meal.getMealType()) {
                    case BREAKFAST:
                        breakfast = recipeName;
                        break;
                    case LUNCH:
                        lunch = recipeName;
                        break;
                    case DINNER:
                        dinner = recipeName;
                        break;
                }
            }

            System.out.printf(
                    "%-15s %-25s %-25s %-25s%n",
                    date.getDayOfWeek(),
                    breakfast,
                    lunch,
                    dinner
            );
        }
    }

    private MealType getMealType(){
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

    private Recipe selectOrCreateRecipe() {

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
                    recipeService.createRecipe();

                    // After adding, let the user choose a recipe.
                    break;

                case 0:

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


}
