package com.example.simplechef.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.simplechef.R;
import com.example.simplechef.ui.login.SectionsStatePagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private BottomNavigationView bottomNavigationView;

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
                    case R.id.menuAccount:
                        viewPager.setCurrentItem(2);
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
        adapter.addFragment(new AccountFragment(), "Account");

        viewPager.setAdapter(adapter);

    }
    public void setViewPager(int FragmentNumber){
        viewPager.setCurrentItem(FragmentNumber);
    }

}