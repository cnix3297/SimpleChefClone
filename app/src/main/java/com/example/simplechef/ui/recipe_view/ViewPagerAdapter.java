package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String [] tabTitles = new String [] { "Description", "Ingredients", "Steps" };
    public Bundle bundle;
   public ViewPagerAdapter(FragmentManager fm, Bundle bundle) {
       super(fm);
       this.bundle = bundle;
   }
    @Override
    public Fragment getItem(int position) {
       switch(position) {
           case 0:
               Fragment frag = new ViewDescriptionFragment().newInstance();
               frag.setArguments(bundle);
               return frag;
           case 1:
               Fragment frag1 = new ViewIngredientsFragment().newInstance();
               frag1.setArguments(bundle);
               return frag1;
           case 2:
               Fragment frag2 = new ViewStepsFragment().newInstance();
               frag2.setArguments(bundle);
               return frag2;
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
