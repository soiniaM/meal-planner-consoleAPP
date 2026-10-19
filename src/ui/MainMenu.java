package ui;

import java.util.Scanner;

public class MainMenu {

    public static void getMainMenu(){
        Dispatcher dispatcher = new Dispatcher();
        System.out.println("=================================");
        System.out.println("       MEAL PLANNER APP");
        System.out.println("=================================");

        System.out.println();
        System.out.println("Welcome to the Meal Planner!");
        System.out.println();
        System.out.println();
        System.out.println("1. Manage Recipes");
        System.out.println("2. Manage Weekly Meal Plans");
        System.out.println("3. View Shopping List");
        System.out.println("4. Track Grocery Expenses");
        System.out.println("5. AI Meal Planner");
        System.out.println("6. View Weekly Summary");
        System.out.println("0. Exit ");
        System.out.println("Choose an option:");
        Scanner scanner = new Scanner(System.in);
        int inputChoice = scanner.nextInt();
        dispatcher.dispatch(inputChoice);
    }

}
