package com.example.simplechef.ui;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.simplechef.RecipeAPI;

import java.util.ArrayList;

public class Recipe {
        private String description;
        private Bitmap picture;
        private String date;
        private int completionTime;
        private double cost;
        private double rank;
        private ArrayList<Ingredient> ingredients = new ArrayList<>();
        private ArrayList<Direction> directions = new ArrayList<>();

        public Recipe(){

        }

    public void setIngredients(ArrayList<Ingredient> ingredients){
            this.ingredients = ingredients;
    }
    public void setDirections(ArrayList<Direction> directions){
        this.directions = directions;
    }

    public Ingredient getIngredients(int position) {
        return ingredients.get(position);
    }
    public String getIngredientToString(int position){
      return ingredients.get(position).measurementAmount + "\t \t" +
              ingredients.get(position).measurementType  + "\t \t      " +
              ingredients.get(position).name             + "\t \t      " +
              ingredients.get(position).price;
    }
    public String getDirectionToString(int position){
            return directions.get(position).direction;
    }
    public void deleteLastIngredient(){
            ingredients.remove(ingredients.size() -1);
    }
    public void deleteLastDirection(){
        directions.remove(directions.size() -1);
    }
    public void setIngredients(String name, double price, String measurementType, double measurementAmount){
        ingredients.add(new Ingredient(name,price,measurementType,measurementAmount));
    }
    public ArrayList<Ingredient> getIngredients(){
            return ingredients;
    }
    public ArrayList<Direction> getDirections(){
            return directions;
    }
    public void setDirections(String direction , int count){
            directions.add(new Direction(direction,count));
    }



    public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;

        }

        public Bitmap getPicture() {
            return picture;
        }

        public void setPicture(Bitmap picture) {
            this.picture = picture;

        }

        public String getDate() {
            return date;
        }



        public int getCompletionTime() {
            return completionTime;
        }

        public void setCompletionTime(int completionTime) {
            this.completionTime = completionTime;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getRank() {
            return rank;
        }

        public void setRank(double rank) {
            this.rank = rank;
        }


private class Ingredient{
    private String name;
    private double price;
    private String measurementType;
    private double measurementAmount;

    public Ingredient(String name, double price, String measurementType, double measurementAmount) {
        this.name = name;
        this.price = price;
        this.measurementType = measurementType;
        this.measurementAmount = measurementAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPriceFromApp(double price){
        this.price = price;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public double getMeasurementAmount() {
        return measurementAmount;
    }

    public void setMeasurementAmount(double measurementAmount) {
        this.measurementAmount = measurementAmount;
    }


}


private class Direction{
    private String direction;
    private int order;

    Direction(String direction, int order){
        this.direction = direction;
        this.order = order;
    }
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}



}
