package model;

import java.util.UUID;

public class RecipeIngredient {
    private Ingredient ingredient;
    private double quantity;
    private String unit;

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public RecipeIngredient(Ingredient ingredient, double quantity, String unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public RecipeIngredient() {
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "ingredient=" + ingredient +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
