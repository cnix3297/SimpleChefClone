package com.example.simplechef.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.ui.shared.SectionsStatePagerAdapter;
import com.example.simplechef.ui.recipe_create.activity_recipe_create;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.authenticatedContainer);
        setupViewPager(viewPager);


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuHome:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.menuSearch:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.imageViewAdd:
                        Intent myIntent = new Intent(getBaseContext(), activity_recipe_create.class);
                        startActivity(myIntent);
                        return true;
                }
                return false;
            }
        });

    }
    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new SearchFragment(), "Search");

        viewPager.setAdapter(adapter);

    }
    public void setViewPager(int FragmentNumber){
        viewPager.setCurrentItem(FragmentNumber);
    }


}
