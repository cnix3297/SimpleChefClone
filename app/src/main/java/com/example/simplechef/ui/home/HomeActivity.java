package com.example.simplechef.ui.home;

import android.accounts.Account;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.simplechef.R;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.example.simplechef.ui.shared.SectionsStatePagerAdapter;
import com.example.simplechef.ui.recipe_create.CreateRecipeActivity;
import com.google.firebase.auth.FirebaseAuth;

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
                        Intent myIntent = new Intent(getBaseContext(), CreateRecipeActivity.class);
                        startActivity(myIntent);
                        return true;
                }
                return false;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profileIntent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.action_signout:
                FirebaseAuth.getInstance().signOut();
                Intent signOutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(signOutIntent);
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return super.onCreateOptionsMenu(menu);
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
