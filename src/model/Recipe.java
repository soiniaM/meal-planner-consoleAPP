package model;

import java.util.List;
import java.util.UUID;

public class Recipe {
    private UUID recipeId;
    private String name;
    private RecipeCategory category;
    private String description;
    private int service; //number of service
   private List<Ingredient> ingredients;

    public Recipe(String name, RecipeCategory category, int service,String desciption) {
        this.recipeId = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.service=service;
        this.description=desciption;
    }

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString(){
        return  "Recipe [Name:" + name + " Category; "+ category +"]";

    }
}
