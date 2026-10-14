package model;

import java.time.LocalDate;

public class MealPlan {

    private LocalDate date;
    private Recipe recipe;
    private MealType mealType;

    public MealPlan(LocalDate date, Recipe recipe,
                    MealType mealType) {
        this.date = date;
        this.recipe = recipe;
        this.mealType = mealType;
    }

    public LocalDate getDate() {
        return date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public MealType getMealType() {
        return mealType;
    }
}
