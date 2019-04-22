package com.example.simplechef.ui.recipe_view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.home.HomeActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewRecipeActivity extends AppCompatActivity {
    private final static String TAG = "ViewRecipeActivity";
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    public String name, description, ingredients, cost, time, steps, image;
    private RecipeClass recipeClass = new RecipeClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        //Get Data
        recipeClass.setName(getIntent().getStringExtra("Name"));
        recipeClass.setDescription(getIntent().getStringExtra("Description"));

        /*this.ingredients = getIntent().getStringExtra("Ingredients");*/
        recipeClass.setCost(Double.parseDouble(getIntent().getStringExtra("Cost")));
        recipeClass.setTime(getIntent().getStringExtra("Time"));
        recipeClass.setSteps(getIntent().getStringExtra("Steps"));
        recipeClass.setImage(getIntent().getStringExtra("Image"));
        for (int i = 0; getIntent().getStringExtra("IngredientsName" + i) == "" ||getIntent().getStringExtra("IngredientsName" + i) != null; i++){
            recipeClass.AddIngredient(getIntent().getStringExtra("IngredientsName" + i), getIntent().getStringExtra("IngredientsQuantity" + i), getIntent().getStringExtra("IngredientsImage" + i));
        }


        //Fragment Setup
        viewPager = findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.recipeClass = recipeClass;
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
