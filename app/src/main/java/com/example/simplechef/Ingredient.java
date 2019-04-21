package com.example.simplechef;

public class Ingredient {

    private String name, quantity, image;

    public Ingredient() {

    }


    public Ingredient(String name, String quantity, String image) {
        this.name = name;
        this.quantity = quantity;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
