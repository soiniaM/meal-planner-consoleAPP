package model;

public record DailyMealPlan (
        int day,
        String breakfast,
        String lunch,
        String dinner
){}