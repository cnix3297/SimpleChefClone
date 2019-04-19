package com.example.simplechef;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Users {
    private HashMap<String, Object> myFavorites;
    private HashMap<String, Object> myRecipes;

    public Users(){

    }
    public Users(HashMap<String, Object> myFavorites, HashMap<String, Object> myRecipes){
        this.myFavorites = myFavorites;
        this.myRecipes = myRecipes;
    }

    public HashMap<String, Object> getMyFavorites() {
        return myFavorites;
    }

    public void setMyFavorites(HashMap<String, Object> myFavorites) {
        this.myFavorites = myFavorites;
    }

    public HashMap<String, Object> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(HashMap<String, Object> myRecipes) {
        this.myRecipes = myRecipes;
    }
    public Object getMyFavoritesAtIndex(String index){
        return myFavorites.get(index);
    }


}
