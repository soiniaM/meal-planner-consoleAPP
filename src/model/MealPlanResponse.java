package model;

import java.util.List;

public record MealPlanResponse(
        List<DailyMealPlan> mealPlan
) {}