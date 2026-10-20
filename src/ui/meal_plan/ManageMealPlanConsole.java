package ui.meal_plan;

import service.AIMealPlanService;
import service.IAIMealPlanService;
import service.IMealPlanService;
import service.MealPlanService;
import ui.MainMenu;

import java.net.http.HttpClient;
import java.util.Scanner;

public class ManageMealPlanConsole {

     private final IMealPlanService mealPlanService = new MealPlanService();

    public void manageWeeklyMealPlans() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Manage Weekly Meal Plans ===");
            System.out.println("1. Create a Weekly Meal Plan");
            System.out.println("2. View Weekly Meal Plans");
            System.out.println("3. Edit a Weekly Meal Plan");
            System.out.println("4. Delete a Weekly Meal Plan");
            System.out.println("0. Back to Main Menu");
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
                    mealPlanService.createWeeklyMealPlan();
                    break;

                case 2:
                   mealPlanService.viewWeeklyMealPlans();
                    break;

                case 3:
                    //editWeeklyMealPlan(scanner);
                    break;

                case 4:
                   // deleteWeeklyMealPlan(scanner);
                    break;

                case 0:
                    MainMenu.getMainMenu();

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
