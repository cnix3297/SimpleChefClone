package com.example.simplechef.ui.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.simplechef.ui.Recipe;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] tabTitles = new String [] { "Recipes", "Favorites", "My Recipes"};
    private ArrayList<Recipe> recipes, favorites, myRecipes;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return RecipeListFragment.newInstance(recipes);
            case 1:
                return RecipeListFragment.newInstance(favorites);
            case 2:
                return RecipeListFragment.newInstance(myRecipes);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
