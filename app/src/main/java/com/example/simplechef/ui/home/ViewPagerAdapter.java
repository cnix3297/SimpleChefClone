package com.example.simplechef.ui.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "ViewPagerAdapter";
    private String [] tabTitles = new String [] { "Recipes", "Favorites", "My Recipes"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public AllRecipesFragment allRecipesFragment = AllRecipesFragment.newInstance();
    public FavoriteRecipesFragment favoriteRecipesFragment = FavoriteRecipesFragment.newInstance();
    public MyRecipesFragment myRecipesFragment = MyRecipesFragment.newInstance();
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return allRecipesFragment;
            case 1:
                return favoriteRecipesFragment;
            case 2:
                return myRecipesFragment;
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
    public void search(String hold){
        Log.d(TAG, "search: " + hold);
        allRecipesFragment.search(hold);
    }

}
