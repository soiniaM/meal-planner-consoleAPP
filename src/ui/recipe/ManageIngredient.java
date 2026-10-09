package ui.recipe;

import model.Ingredient;
import model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManageIngredient {
    static  List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    public static int addIngredientMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Enter Ingredient Name:");
        String  name = scanner.nextLine();

        System.out.println(" Enter Ingredient quantity:");
        double  quantity = scanner.nextDouble();
        scanner.nextLine(); // Consume the leftover newline

        System.out.println(" Enter Ingredient unit:");
        String  unit = scanner.nextLine();

       RecipeIngredient recipeIngredient = new RecipeIngredient(new Ingredient(name),quantity,unit);
        recipeIngredients.add(recipeIngredient );

        System.out.println(recipeIngredient);
        System.out.println("""
                    press:  1 to Enter Another Ingredient
                            0 to quick
                                          \s""");
        int inputValue = scanner.nextInt();
        return inputValue;
    }

}
