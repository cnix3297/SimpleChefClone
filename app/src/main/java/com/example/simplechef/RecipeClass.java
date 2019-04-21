package com.example.simplechef;

import java.util.ArrayList;

public class RecipeClass {

    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private String name, description, time, steps;
    private String image;
    private Double cost = 0.0;


    //Constructor
    public RecipeClass() {

    }
    //Constructor
    public RecipeClass(String name, String description, String steps) {
        this.name = name;
        this.description = description;
        this.steps = steps;
    }
    public void AddIngredient(String name, String quantity){
        Ingredient obj = new Ingredient(name, quantity);
        ingredientList.add(obj);
    }



    //GETTERS && SETTERS
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
    public Ingredient getIngredientAtIndex(int index){
        return ingredientList.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
