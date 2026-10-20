package ui;

import service.AIMealPlanService;
import service.IAIMealPlanService;
import ui.meal_plan.ManageMealPlanConsole;
import ui.recipe.ManageRecipeConsole;
import ui.shopping_list.ManageShoppingListConsole;

import java.net.http.HttpClient;

public class Dispatcher {
    private final ManageRecipeConsole manageRecipeConsole = new ManageRecipeConsole();
    private final ManageMealPlanConsole manageMealPlanConsole = new ManageMealPlanConsole();
    private final ManageShoppingListConsole manageShoppingListConsole = new ManageShoppingListConsole();
    private  final IAIMealPlanService iaiMealPlanService = new AIMealPlanService(HttpClient.newHttpClient());

    public  void dispatch(int choiceNumber){
        switch (choiceNumber) {

            case 1:
                manageRecipeConsole.manageRecipes();
               // System.out.println("Recipe management selected.");
                break;

            case 2:
                manageMealPlanConsole.manageWeeklyMealPlans();
                //System.out.println("Meal planning selected.");
                break;

            case 3:
                manageShoppingListConsole.viewShoppingList();
                //System.out.println("Shopping list selected.");
                break;

            case 4:

                System.out.println("Expense tracking selected.");
                break;

            case 5:
                iaiMealPlanService.generateMealsPlan();
              //  System.out.println("Weekly summary selected.");
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
