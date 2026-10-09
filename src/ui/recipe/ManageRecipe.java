package ui.recipe;

import model.Recipe;
import model.RecipeCategory;
import model.RecipeIngredient;
import service.IRecipeService;
import service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageRecipe {
    public static void manageRecipes() {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("--- Manage Recipes ---");
            System.out.println("1. Add Recipe");
            System.out.println("2. View Recipes");
            System.out.println("3. Edit Recipe");
            System.out.println("4. Delete Recipe");
            System.out.println("0. Back to Main Menu");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the Enter key
            switch (choice) {

                case 1:
                    addRecipe();
                    break;

                case 2:
                    System.out.println("View Recipes selected.");
                    break;

                case 3:
                    System.out.println("Edit Recipe selected.");
                    break;

                case 4:
                    System.out.println("Delete Recipe selected.");
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }

    private static void addRecipe() {
        RecipeCategory category;
        IRecipeService recipeService = new RecipeService();

        Scanner scanner = new Scanner(System.in);
        Recipe recipe = new Recipe();
       // RecipeIngredient recipeIngredient = new RecipeIngredient();

        System.out.println();
        System.out.println("--- Add Recipe ---");

        System.out.println("Enter recipe name:");
        String name = scanner.nextLine();
        System.out.println("Enter number of service:");
        int service = scanner.nextInt();

        System.out.println("Enter description :");
        String description = scanner.nextLine();
        do {
            System.out.println(""" 
                    Choose recipe category :
                      1 BREAKFAST,
                      2 LUNCH,
                      3 DINNER,
                      4 SNACK,
                      5 DESSERT.
                    """);
            int choice = scanner.nextInt();
            category = switch (choice) {
                case 1 -> RecipeCategory.BREAKFAST;
                case 2 -> RecipeCategory.LUNCH;
                case 3 -> RecipeCategory.DINNER;
                case 4 -> RecipeCategory.SNACK;
                case 5 -> RecipeCategory.DESSERT;
                default -> {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    yield null;
                }
            };
        } while (category == null);

        int inputValue = 0;
        System.out.println("""
                press:  1 to enter Ingredient for this recipe
                        0 to quick
                                      \s""");
        inputValue = scanner.nextInt();

        while (inputValue != 0) {
           inputValue = ManageIngredient.addIngredientMenu();
        }
        recipeService.addRecipe(name,category,description,service,ManageIngredient.recipeIngredients);
        ManageIngredient.recipeIngredients.clear();

    }
}
