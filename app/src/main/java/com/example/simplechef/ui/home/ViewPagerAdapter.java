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
    private ListFragment recipesFragment, favoritesFragment, myRecipesFragment;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        recipesFragment = ListFragment.newInstance(recipes);
        favoritesFragment = ListFragment.newInstance(favorites);
        myRecipesFragment = ListFragment.newInstance(myRecipes);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = recipesFragment;
                break;
            case 1:
                fragment = favoritesFragment;
                break;
            case 2:
                fragment = myRecipesFragment;
                break;
            default:
                return null;
        }
        return fragment;
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
