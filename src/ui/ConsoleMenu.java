package ui;

import ui.recipe.ManageRecipe;

public class ConsoleMenu {

    public static void menuChoice(int choiceNumber){
        switch (choiceNumber) {

            case 1:
                ManageRecipe.manageRecipes();
               // System.out.println("Recipe management selected.");
                break;

            case 2:
                System.out.println("Meal planning selected.");
                break;

            case 3:
                System.out.println("Shopping list selected.");
                break;

            case 4:
                System.out.println("Expense tracking selected.");
                break;

            case 5:
                System.out.println("Weekly summary selected.");
                break;

            case 0:
                System.out.println("Thank you for using Meal Planner!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
