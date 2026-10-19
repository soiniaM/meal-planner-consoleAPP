package ui.recipe;

import service.IRecipeService;
import service.RecipeService;
import ui.MainMenu;

import java.util.Scanner;

public class ManageRecipeConsole {

    public void manageRecipes() {
         IRecipeService recipeService = new RecipeService();
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
                    recipeService.createRecipe();
                    break;

                case 2:
                    recipeService.viewAllRecipes();
             //      System.out.println("View Recipes selected.");
                    break;

                case 3:
                    recipeService.editRecipe();
                    //System.out.println("Edit Recipe selected.");
                    break;

                case 4:
                    recipeService.deleteRecipe();
                    //System.out.println("Delete Recipe selected.");
                    break;

                case 0:
                    running = false;
                    MainMenu.getMainMenu();
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }

}