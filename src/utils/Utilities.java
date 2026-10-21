package utils;

public class Utilities {

 public static String removeMakdownfences(String text){

     // Remove Markdown fences if the model includes them.
     text = text.trim();

     if (text.startsWith("```")) {
         text = text.replaceFirst(
                 "^```(?:json)?\\s*", "");
         text = text.replaceFirst(
                 "\\s*```$", "");
     }
     return text;
 }

    //Role - Task instructions- Constraints - Output formatting -Output example (schema)
    public static final String PROMT_MESSAGE= """
              You are a meal planning assistant.
            
                        Create a 7-day meal plan using only the recipes provided.
                        Include breakfast, lunch, and dinner for each day.
                        Do not invent recipes or modify recipe names.
            
                        Return ONLY valid JSON in this format:
                        {
                          "mealPlan": [
                            {
                              "day": 1,
                              "breakfast": "Recipe Name",
                              "lunch": "Recipe Name",
                              "dinner": "Recipe Name"
                            }
                          ]
                        }
            
                        Include exactly 7 days.
                        Use the exact recipe names provided.
                        Do not include Markdown code fences or explanations.
                        
             Available recipes:
            
            """;
}
