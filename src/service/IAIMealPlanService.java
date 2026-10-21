package service;

import model.AIWeeklyPlan;
import model.MealPlan;
import model.Recipe;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IAIMealPlanService {
    AIWeeklyPlan generateWeeklyPlan(Set<Recipe> recipeList) ;

   void generateAIWeeklyMealPlan();
    void validateAndSaveWeeklyMealPlan(
            List<MealPlan> proposedPlan,
            Set<Recipe> recipes,
            LocalDate startDate);
}
