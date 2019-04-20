package com.example.simplechef;

public class Ingredients {

    private String name, quantity;

    public Ingredients()
    {

    }
    public Ingredients(String name, String quantity){
        this.name = name;
        this.quantity = quantity;

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

}
