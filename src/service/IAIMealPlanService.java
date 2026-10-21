package service;

import model.AIWeeklyPlan;
import model.MealPlan;
import model.Recipe;

import java.util.List;
import java.util.Set;

public interface IAIMealPlanService {
    AIWeeklyPlan generateWeeklyPlan(Set<Recipe> recipeList) ;

    List<MealPlan> generateAIWeeklyMealPlan();
}
