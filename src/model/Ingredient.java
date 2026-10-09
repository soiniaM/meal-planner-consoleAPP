package model;

import java.util.UUID;

public class Ingredient {
    private UUID id;
    private String name;

    public Ingredient(String name) {
        this.id = UUID.randomUUID();
        this.name = name;;
    }

    public Ingredient() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

