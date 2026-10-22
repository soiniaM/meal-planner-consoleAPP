package ui.recipe;

import model.Ingredient;
import model.Recipe;
import service.RecipeService;
import ui.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ManageRecipe {

   private static Scanner scanner = new Scanner(System.in);
   private final RecipeService recipeService= new RecipeService();

  /*  public void createRecipe() {
        System.out.println();
        System.out.println("\n=== Add a Recipe ===");

        Recipe recipe =  buildRecipe();
        addIngredients(recipe);

        if (RecipeService.recipes.add(recipe)) {
            System.out.println("Recipe added successfully: " );
        } else {
            System.out.println("Recipe already exists. No changes made.");
        }

    }

    public static void viewAllRecipes() {

        System.out.println("\n=== All Recipes ===");

        if (RecipeService.recipes.isEmpty()) {
            System.out.println("No recipes available. Add a recipe first.");
            return;
        }
        int index = 1;

        for (Recipe recipe : RecipeService.recipes) {
            System.out.println("\n" + index + ". " + recipe.getName());
            System.out.println("Ingredients:");
            for (Ingredient ingredient : recipe.getIngredients()) {
                System.out.printf("   - %-15s %s %s%n",
                        ingredient.getName(),
                        ingredient.getQuantity(),
                        ingredient.getUnit());
            }
            index++;
        }

        System.out.println("\nTotal recipes: " + RecipeService.recipes.size());
        }
        */

    public static Recipe chooseExistingRecipe() {

        if (RecipeService.recipeList.isEmpty()) {
            System.out.println("No recipes available. Please add a recipe first.");
            return null;
        }

        List<Recipe> recipeList = new ArrayList<>(RecipeService.recipeList);

        System.out.println("\n=== Available Recipes ===");

        for (int i = 0; i < recipeList.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + recipeList.get(i).getName()
            );
        }

        System.out.println("0. Cancel");
        System.out.print("Select a recipe: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice == 0) {
                return null;
            }

            if (choice < 1 || choice > recipeList.size()) {
                System.out.println("Invalid recipe selection.");
                return null;
            }

            return recipeList.get(choice - 1);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }
    }

    public static void deleteRecipe() {
      Scanner scanner = new Scanner(System.in);

      System.out.println("\n=== Delete a Recipe ===");

      if (RecipeService.recipeList.isEmpty()) {
          System.out.println("No recipes available to delete.");
          return;
      }

      System.out.print("Enter recipe name to delete (0 to cancel): ");
      String name = scanner.nextLine().trim();

      if (name.equals("0")) {
          return;
      }
      Recipe recipeToDelete = searchRecipeByName(name);


      System.out.print("Are you sure you want to delete this recipe? (yes/no): ");
      String confirmation = scanner.nextLine().trim();

      if (confirmation.equalsIgnoreCase("yes")) {
          RecipeService.recipeList.remove(recipeToDelete);
          System.out.println("Recipe deleted successfully.");
      } else {
          System.out.println("Deletion cancelled.");
      }
  }

    public static void editRecipe() {

        System.out.println("\n=== Edit a Recipe ===");

        if (RecipeService.recipeList.isEmpty()) {
            System.out.println("No recipes available to edit.");
            return;
        }

        System.out.print("Enter recipe name (0 to cancel): ");
        String name = scanner.nextLine().trim();

        if (name.equals("0")) {
            return;
        }

        Recipe selectedRecipe = searchRecipeByName(name);

        int choice =0 ;

        do {
            System.out.println("\n=== Edit Recipe Menu ===");
            System.out.println("1. Rename Recipe");
            System.out.println("2. Add Ingredient");
            System.out.println("3. Remove Ingredient");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Rename the recipe
                    renameRecipe(selectedRecipe);
                    break;
                case 2:
                    // Add an ingredient
                    addIngredients(selectedRecipe);
                    break;
                case 3:
                    // Remove an ingredient
                    removeIngredient(selectedRecipe);
                    break;
                case 0:
                    System.out.println("Returning to recipe menu.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    public static void addIngredients(Recipe recipe) {

        System.out.println("\n=== Add Ingredients ===");
        System.out.println("Enter 0 at any time to finish.");

        while (true) {
            System.out.print("Enter Ingredient name ( 0 to finish): ");
            String name = scanner.nextLine().trim();
            if (name.equals("0")) {
                return;
            }
            if (name.isEmpty()) {
                System.out.println("Ingredient name cannot be empty.");
                continue;
            }

            System.out.print(" Enter Quantity: ");

            double quantity;

            try {
                quantity = Double.parseDouble(scanner.nextLine().trim());

                if (!Double.isFinite(quantity) || quantity <= 0) {
                    System.out.println("Quantity must be a positive number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            System.out.print("Unit (g, ml, pieces, etc.): ");
            String unit = scanner.nextLine().trim();

            if (unit.isEmpty()) {
                System.out.println("Unit cannot be empty.");
                continue;
            }

            Ingredient ingredient = new Ingredient(name, quantity, unit);

            if (recipe.addIngredient(ingredient)) {
                System.out.println("Ingredient added: " + name);
            } else {
                System.out.println(
                        "Ingredient already exists."
                );
            }
        }
    }

    public  static Recipe buildRecipe(){
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter recipe name (0 to return to main): ");
            name = scanner.nextLine().trim();

            if (name.equals("0")) {
                MainMenu.getMainMenu();
                break;
            }

            if (name.isEmpty()) {
                System.out.println("Recipe name cannot be empty.");
            }
        }
        System.out.println("Enter number of service:");
        int service = scanner.nextInt();

        System.out.println("Enter description :");
        String description = scanner.nextLine();
    return new Recipe(name, service, description);
    }

    public void viewAllRecipes() {
        Set<Recipe> recipeList = recipeService.getAllRecipes();
        System.out.println("\n=== All Recipes ===");

        if (recipeList.isEmpty()) {
            System.out.println("No recipes available. Add a recipe first.");
            return;
        }
        int index = 1;

        for (Recipe recipe : recipeList) {
            System.out.println("\n" + index + ". " + recipe.getName());
            System.out.println("Ingredients:");
            for (Ingredient ingredient : recipe.getIngredients()) {
                System.out.printf("   - %-15s %s %s%n",
                        ingredient.getName(),
                        ingredient.getQuantity(),
                        ingredient.getUnit());
            }
            index++;
        }

        System.out.println("\nTotal recipes: " + recipeList.size());
    }

    private static void renameRecipe(Recipe recipe) {

        System.out.print("Enter new recipe name (0 to cancel): ");
        String newName = scanner.nextLine().trim();

        if (newName.equals("0")) {
            return;
        }

        if (newName.isEmpty()) {
            System.out.println("Recipe name cannot be empty.");
            return;
        }

        Recipe existingRecipe = buildRecipe();

        if (RecipeService.recipeList.contains(existingRecipe)) {
            System.out.println("A recipe with this name already exists.");
            return;
        }

        RecipeService.recipeList.remove(recipe);
        recipe.setName(newName);
        RecipeService.recipeList.add(recipe);

        System.out.println("Recipe renamed successfully.");
    }

    private static Recipe searchRecipeByName(String name){
    Recipe recipeFound = null;

    for (Recipe recipe : RecipeService.recipeList) {
        if (recipe.getName().equalsIgnoreCase(name)) {
            recipeFound = recipe;
            break;
        }
    }

    if (recipeFound == null) {
        System.out.println("Recipe not found: " + name);
        return recipeFound;
    }

    System.out.println("\nRecipe details:");
    System.out.println(recipeFound);
    return recipeFound;
}

    private static void removeIngredient(Recipe recipe) {

        if (recipe.getIngredients().isEmpty()) {
            System.out.println("This recipe has no ingredients.");
            return;
        }

        System.out.println("\n=== Ingredients ===");

        for (Ingredient ingredient : recipe.getIngredients()) {
            System.out.println("- " + ingredient.getName());
        }

        System.out.print("Enter ingredient name to remove (0 to cancel): ");
        String name = scanner.nextLine().trim();

        if (name.equals("0")) {
            return;
        }

        boolean removed = recipe.getIngredients().removeIf(
                ingredient -> ingredient.getName().equalsIgnoreCase(name)
        );

        if (removed) {
            System.out.println("Ingredient removed successfully.");
        } else {
            System.out.println("Ingredient not found.");
        }
    }
}