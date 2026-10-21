package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.MealPlanResponse;
import model.DailyMealPlan;

public class JsonParserExample {

    public static void main(String[] args) throws Exception {

        String jsonResponse = """
            {
              "mealPlan": [
                {
                  "day": 1,
                  "breakfast": "Oatmeal",
                  "lunch": "Grilled Chicken",
                  "dinner": "Vegetable Soup"
                }
              ]
            }
            """;

        ObjectMapper mapper = new ObjectMapper();

        MealPlanResponse response =
                mapper.readValue(
                        jsonResponse,
                        MealPlanResponse.class
                );

        DailyMealPlan day1 = response.mealPlan().get(0);

        System.out.println(day1.breakfast());
        System.out.println(day1.lunch());
        System.out.println(day1.dinner());
    }
}