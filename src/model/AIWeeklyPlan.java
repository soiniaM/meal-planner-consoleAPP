package model;

import java.util.List;

public class AIWeeklyPlan {

    private List<DailyMealPlan> mealPlan;

    public AIWeeklyPlan() {
    }

    public List<DailyMealPlan> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(List<DailyMealPlan> mealPlan) {
        this.mealPlan = mealPlan;
    }
}