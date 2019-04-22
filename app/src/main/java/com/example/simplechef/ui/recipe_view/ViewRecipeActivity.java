package com.example.simplechef.ui.recipe_view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.home.HomeActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;

public class ViewRecipeActivity extends AppCompatActivity {
    private final static String TAG = "ViewRecipeActivity";
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Log.d("Name", getIntent().getStringExtra("Name"));
        Log.d("Description", getIntent().getStringExtra("Description"));
        Log.d("Ingredients", getIntent().getStringExtra("Ingredients"));
        Log.d("Cost", getIntent().getStringExtra("Cost"));
        Log.d("Time", getIntent().getStringExtra("Time"));
        Log.d("Steps", getIntent().getStringExtra("Steps"));
        Log.d("Image", getIntent().getStringExtra("Image"));


        //bundle
        Bundle bundle = new Bundle();
        bundle.putString("name", getIntent().getStringExtra("Name"));
        bundle.putString("ID", getIntent().getStringExtra("ID"));
        bundle.putString("description", getIntent().getStringExtra("Description"));
        bundle.putString("ingredients", getIntent().getStringExtra("Ingredients"));
        bundle.putString("cost", getIntent().getStringExtra("Cost"));
        bundle.putString("time", getIntent().getStringExtra("Time"));
        bundle.putString("steps", getIntent().getStringExtra("Steps"));
        bundle.putString("image", getIntent().getStringExtra("Image"));



        viewPager = findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), bundle);
        viewPager.setAdapter(viewPagerAdapter);



        setupToolbar();

        tabLayout = findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(viewPager);


    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView)findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Recipe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profileIntent = new Intent(ViewRecipeActivity.this, AccountActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.action_signout:
                FirebaseAuth.getInstance().signOut();
                Intent signOutIntent = new Intent(ViewRecipeActivity.this, LoginActivity.class);
                startActivity(signOutIntent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
