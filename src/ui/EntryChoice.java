package ui;

import com.sun.tools.javac.Main;
import ui.meal_plan.ManageMealPlanConsole;
import ui.recipe.ManageRecipeConsole;

public class EntryChoice {

    public static void menuChoice(int choiceNumber){
        switch (choiceNumber) {

            case 1:
                ManageRecipeConsole.manageRecipes();
               // System.out.println("Recipe management selected.");
                break;

            case 2:
                ManageMealPlanConsole.manageWeeklyMealPlans();
                //System.out.println("Meal planning selected.");
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
                MainMenu.getMainMenu();
               // System.out.println("Thank you for using Meal Planner!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
