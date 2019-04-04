package com.example.simplechef.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        HomeFragment homeFragment = new HomeFragment();
        i = i + 1;


        return homeFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
