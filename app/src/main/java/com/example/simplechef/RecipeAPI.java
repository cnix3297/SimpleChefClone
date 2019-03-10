package com.example.simplechef;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RecipeAPI {

    private String foodName;
    private String searchName;
    public String getSearchName() {
        return searchName;
    }
    //creates a recipe object with a search input
    public RecipeAPI(String input){
        try {
            searchName = input;
            //connects to API and holds the results in hold
            String hold = new CallMashapeAsyncGetFoodName()
                    .execute()
                    .get()
                    .getBody()
                    .toString();
            //gets the name of the product from the hold varible and stores it in a temporary variable
            String[] hold1 = hold.split("\\W");
            //sets foodName to the name of the product
            if(hold1.length != 0)
                foodName = hold1[6];
            else
                foodName = null;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    private class CallMashapeAsyncGetFoodName extends AsyncTask<String, Integer, HttpResponse<JsonNode>> {

        protected HttpResponse<JsonNode> doInBackground(String... msg) {

            HttpResponse<JsonNode> request = null;
            try {
                request = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/autocomplete?metaInformation=false&number=1&query={item}")
                        .header("X-Mashape-Key", "hNcoU7RceEmsh4RLYuEqwJ8iSgbVp1QlIMpjsn89KozljP0jbX")
                        .header("accept", "application/json")
                        .routeParam("item",getSearchName())
                        .asJson();
            } catch (UnirestException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return request;
        }
        protected void onProgressUpdate(Integer...integers) {
        }

        protected void onPostExecute(HttpResponse<JsonNode> response) {

            setFoodName(response.getBody().toString());
        }
    }
}
