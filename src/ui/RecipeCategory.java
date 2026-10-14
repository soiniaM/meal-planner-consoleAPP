package ui;

import model.MealType;

import java.util.Scanner;

public class RecipeCategory {

    public static MealType getRecipeCategory() {
        Scanner scanner = new Scanner(System.in);
        MealType category;

        do {
            System.out.println(""" 
                    Select a Meal :
                      1. BREAKFAST,
                      2. LUNCH,
                      3. DINNER,
                      4. SNACK,
                      5. DESSERT.
                    """);
            int choice = scanner.nextInt();
            category = switch (choice) {
                case 1 -> MealType.BREAKFAST;
                case 2 -> MealType.LUNCH;
                case 3 -> MealType.DINNER;
                case 4 -> MealType.SNACK;
                case 5 -> MealType.DESSERT;
                default -> {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    yield null;
                }
            };
        } while (category == null);
return  category;
    }
}
