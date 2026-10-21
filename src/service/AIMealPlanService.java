package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import ui.ai_meal_plan.AiManageMealPlanConsole;
import utils.Utilities;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AIMealPlanService implements  IAIMealPlanService{

    private final ObjectMapper objectMapper= new ObjectMapper();
    private final OllamaService ollamaService = new OllamaService();
    Set<Recipe> recipes = RecipeService.recipeList;
    private AiManageMealPlanConsole aiManageMealPlanConsole= new AiManageMealPlanConsole();

    @Override
    public void generateAIWeeklyMealPlan() {
        List<MealPlan> proposedPlan = new ArrayList<>();

        if (recipes.isEmpty()) {
            System.out.println(
                    "No recipes available. Please add a recipe first.");
        }
        LocalDate startDate = getStartDate();

        if (startDate == null) {
            System.out.println("AI meal planning cancelled.");
        }
        try {
            System.out.println("\nGenerating your weekly meal plan...");

            AIWeeklyPlan weeklyPlan = generateWeeklyPlan(recipes);
            proposedPlan = convertToMealPlans(weeklyPlan, recipes, startDate);
            aiManageMealPlanConsole.displayWeeklyMealPlan(proposedPlan);

            aiManageMealPlanConsole.modifyWeeklyMealPlan(proposedPlan, recipes);

            if (!aiManageMealPlanConsole.confirmWeeklyMealPlan()) {
                System.out.println("Plan discarded. Nothing was saved.");
                return;
            }

            validateAndSaveWeeklyMealPlan(proposedPlan, recipes, startDate);

        } catch (Exception e) {
            System.out.println("Could not generate the meal plan: "
                    + e.getMessage());
        }
    }
    @Override
    public void validateAndSaveWeeklyMealPlan(
            List<MealPlan> proposedPlan,
            Set<Recipe> recipes,
            LocalDate startDate) {

        if (proposedPlan == null || proposedPlan.size() != 21) {
            throw new IllegalArgumentException(
                    "The weekly plan must contain exactly 21 meals.");
        }

        Set<MealType> requiredTypes = EnumSet.of(
                MealType.BREAKFAST,
                MealType.LUNCH,
                MealType.DINNER);

        Map<LocalDate, Set<MealType>> mealsByDate = new HashMap<>();

        // Validate every proposed meal before saving anything.
        for (MealPlan meal : proposedPlan) {

            LocalDate date = meal.getDate();
            MealType type = meal.getMealType();

            if (date == null
                    || date.isBefore(startDate)
                    || date.isAfter(startDate.plusDays(6))) {
                throw new IllegalArgumentException(
                        "Meal date is outside the selected week.");
            }

            if (type == null || !requiredTypes.contains(type)) {
                throw new IllegalArgumentException(
                        "Invalid meal type.");
            }

            if (meal.getRecipe() == null) {
                throw new IllegalArgumentException(
                        "A meal has no recipe.");
            }

            // Ensure the recipe exists in the user's recipe collection.
            findRecipeByName(meal.getRecipe().getName(), recipes);

            Set<MealType> typesForDate = mealsByDate.computeIfAbsent(
                    date, ignored -> EnumSet.noneOf(MealType.class));

            if (!typesForDate.add(type)) {
                throw new IllegalArgumentException(
                        "Duplicate meal for " + date + ": " + type);
            }
        }

        // Ensure every day contains breakfast, lunch, and dinner.
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);

            if (!requiredTypes.equals(mealsByDate.get(date))) {
                throw new IllegalArgumentException(
                        "Missing meals for " + date);
            }
        }

        // Reject the entire plan if any saved meal conflicts.
        for (MealPlan proposed : proposedPlan) {
            boolean conflict = MealPlanService.mealPlanList.stream()
                    .anyMatch(existing ->
                            existing.getDate().equals(proposed.getDate())
                                    && existing.getMealType()
                                    == proposed.getMealType());

            if (conflict) {
                throw new IllegalArgumentException(
                        "A meal is already planned for "
                                + proposed.getDate() + " ("
                                + proposed.getMealType() + "). "
                                + "No meals were saved.");
            }
        }

        // Save only after every validation succeeds.
        MealPlanService.mealPlanList.addAll(proposedPlan);

        System.out.println(
                "\nAI weekly meal plan saved successfully!");
    }

    @Override
    public AIWeeklyPlan generateWeeklyPlan(Set<Recipe> recipeList) {
        if (recipeList.isEmpty()) {
            return null;
        }

        String prompt = buildPrompt(recipeList);
        try {

            String answer = ollamaService.ask(prompt);

            answer = Utilities.removeMakdownfences(answer);

            AIWeeklyPlan weeklyPlan = objectMapper.readValue(answer, AIWeeklyPlan.class);
            System.out.println("AI meal plan generated successfully.");
            System.out.println("Days generated: "
                    + weeklyPlan.getMealPlan().size());
            //System.out.println(answer);
            return weeklyPlan;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // avoid the interruption status to be clear so that it can be report
            throw new RuntimeException("Meal plan generation was interrupted.", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    private List<MealPlan> convertToMealPlans(
            AIWeeklyPlan weeklyPlan,
            Set<Recipe> recipes,
            LocalDate startDate) {

        if (weeklyPlan == null
                || weeklyPlan.getMealPlan() == null
                || weeklyPlan.getMealPlan().size() != 7) {
            throw new IllegalArgumentException(
                    "The AI must return exactly 7 days.");
        }

        Set<Integer> days = new HashSet<>();
        List<MealPlan> mealPlans = new ArrayList<>();

        for (DailyMealPlan dayPlan : weeklyPlan.getMealPlan()) {

            int day = dayPlan.day();

            if (day < 1 || day > 7 || !days.add(day)) {
                throw new IllegalArgumentException(
                        "Invalid or duplicate day: " + day);
            }

            LocalDate date = startDate.plusDays(day - 1);

            Recipe breakfast = findRecipeByName(
                    dayPlan.breakfast(), recipes);

            Recipe lunch = findRecipeByName(
                    dayPlan.lunch(), recipes);

            Recipe dinner = findRecipeByName(
                    dayPlan.dinner(), recipes);

            mealPlans.add(new MealPlan(
                    date, breakfast, MealType.BREAKFAST));

            mealPlans.add(new MealPlan(
                    date, lunch, MealType.LUNCH));

            mealPlans.add(new MealPlan(
                    date, dinner, MealType.DINNER));
        }

        mealPlans.sort(
                Comparator.comparing(MealPlan::getDate)
                        .thenComparing(
                                MealPlan::getMealType));

        return mealPlans;
    }
    private String buildPrompt(Set<Recipe> recipes) {

        StringBuilder prompt = new StringBuilder();
        String message = Utilities.PROMT_MESSAGE;
        prompt.append(message);
        for (Recipe recipe : recipes) {
              prompt.append("- ")
                    .append(recipe.getName())
                    .append("\n");
        }
        return prompt.toString();
    }

    private Recipe findRecipeByName(
            String recipeName,
            Set<Recipe> recipes) {

        return recipes.stream()
                .filter(recipe ->
                        recipe.getName().trim()
                                .equalsIgnoreCase(recipeName.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "AI returned an unknown recipe: " + recipeName));
    }

    private LocalDate getStartDate() {
     LocalDate startDate = LocalDate.now();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(
                    "Enter the first day (YYYY-MM-DD, or 0 to cancel): ");

            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }
            startDate=  LocalDate.parse(input);

            try {
                return startDate;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }
}
