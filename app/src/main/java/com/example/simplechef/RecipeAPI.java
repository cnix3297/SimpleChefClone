package com.example.simplechef;

import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RecipeAPI {

private String foodName;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    private int foodID;
private double foodPrice;

public void getfoodID(String input){
    HttpResponse<JsonNode> response;
    try {
        response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/autocomplete?metaInformation=true&number=1&query=brocco")
                .header("X-Mashape-Key", "hNcoU7RceEmsh4RLYuEqwJ8iSgbVp1QlIMpjsn89KozljP0jbX")
                .header("Accept", "application/json")
                .asJson();
        foodName = response.getRawBody().toString();
    } catch (UnirestException e) {
        e.printStackTrace();
    }

}
}
