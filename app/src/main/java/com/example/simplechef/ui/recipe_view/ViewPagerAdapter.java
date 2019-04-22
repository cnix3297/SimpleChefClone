package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.simplechef.RecipeClass;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] tabTitles = new String [] { "Description", "Ingredient", "Steps" };
    public RecipeClass recipeClass;
    public ViewPagerAdapter(FragmentManager fm) {
       super(fm);

   }
    @Override
    public Fragment getItem(int position) {
       switch(position) {
           case 0:
               return ViewDescriptionFragment.newInstance(recipeClass);
           case 1:
               return ViewIngredientsFragment.newInstance(recipeClass);
           case 2:
               return ViewStepsFragment.newInstance(recipeClass);
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
