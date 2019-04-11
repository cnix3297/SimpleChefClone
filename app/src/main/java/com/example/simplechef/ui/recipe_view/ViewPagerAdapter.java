package com.example.simplechef.ui.recipe_view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] tabTitles = new String [] { "Description", "Ingredients", "Steps" };
    private ViewDescriptionFragment viewDescriptionFragment;
    private ViewIngredientsFragment viewIngredientsFragment;
    private ViewStepsFragment viewStepsFragment;

   public ViewPagerAdapter(FragmentManager fm) {
       super(fm);
       viewDescriptionFragment = ViewDescriptionFragment.newInstance();
       viewIngredientsFragment = ViewIngredientsFragment.newInstance();
       viewStepsFragment = ViewStepsFragment.newInstance();
   }
    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;
       switch(position) {
           case 0:
               fragment = viewDescriptionFragment;
               break;
           case 1:
               fragment = viewIngredientsFragment;
               break;
           case 2:
               fragment = viewStepsFragment;
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
