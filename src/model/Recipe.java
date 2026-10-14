package model;

import java.util.*;

public class Recipe {

    private String name;
    // private RecipeCategory category;
    private String description;
    private int service; //number of service
   private Set<Ingredient> ingredients = new HashSet<>();

    public Recipe(String name, int service,String desciption) {
        this.name = name;
        this.service=service;
        this.description=desciption;
    }

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setService(int service) {
        this.service = service;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Recipe other)) {
            return false;
        }

        return name.trim().equalsIgnoreCase(other.name.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.trim().toLowerCase(Locale.ROOT));
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean addIngredient(Ingredient ingredient) {
            if ( !ingredients.add(ingredient)) {
                return false;
            }
        ingredients.add(ingredient);
        return true;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", service=" + service +
                ", ingredients=" + ingredients +
                '}';
    }
}
