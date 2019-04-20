package com.example.simplechef;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Users {
    private ArrayList<String> myFavorites;
    private ArrayList<String> myRecipes;

    public Users(){

    }
    public Users(ArrayList<String> myFavorites, ArrayList<String> myRecipes){
        this.myFavorites = myFavorites;
        this.myRecipes = myRecipes;
    }

    public ArrayList<String> getMyFavorites() {
        return myFavorites;
    }

    public void setMyFavorites(ArrayList<String> myFavorites) {
        this.myFavorites = myFavorites;
    }

    public ArrayList<String> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(ArrayList<String> myRecipes) {
        this.myRecipes = myRecipes;
    }
    public String getMyFavoritesAtIndex(Integer index){
        return myFavorites.get(index);
    }


}
