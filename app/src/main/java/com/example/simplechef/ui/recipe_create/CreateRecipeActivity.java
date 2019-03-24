package com.example.simplechef.ui.recipe_create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.simplechef.R;

import com.example.simplechef.ui.Recipe;
import com.example.simplechef.ui.home.HomeActivity;
import com.example.simplechef.ui.shared.SectionsStatePagerAdapter;

public class CreateRecipeActivity extends AppCompatActivity implements IngredientsFragment.onRecipeChangeIngredientListener, StepsFragment.onRecipeChangeDirectionListener, DescriptionFragment.onRecipeChangeExtraListener {
    ViewPager fragmentContainer;
    Recipe mainRecipe;
    /*private FirebaseFirestore db;*/
    private TextView toolbar_title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
        mainRecipe = new Recipe();
         fragmentContainer= (ViewPager) findViewById(R.id.activity_create_recipe_fragment_view);

        setupViewPager(fragmentContainer);
        /*db = FirebaseFirestore.getInstance();*/

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText("Add Ingredients");

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentContainer.getCurrentItem() == 0){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
                else {
                    setViewPager(fragmentContainer.getCurrentItem() - 1);

                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_recipe_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IngredientsFragment(), "Ingredient");
        adapter.addFragment(new StepsFragment(), "direction");
        adapter.addFragment(new DescriptionFragment(), "extra");
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int FragmentNumber){
        fragmentContainer.setCurrentItem(FragmentNumber);
    }

    @Override
    public void onRecipeChangeIngredientListenerMethod(Recipe recipe) {
        mainRecipe.setIngredients(recipe.getIngredients());
    }

    @Override
    public void onRecipeChangeDirectionListenerMethod(Recipe recipe) {
        mainRecipe.setDirections(recipe.getDirections());
    }

    @Override
    public void onRecipeChangeExtraListenerMethod(Recipe recipe) {
        /*mainRecipe.setDescription(recipe.getDescription());
        Log.d("description passed", "onRecipeChangeExtraListenerMethod: ");
        mainRecipe.setCost(recipe.getCost());
        Log.d("cost passed", "onRecipeChangeExtraListenerMethod: ");
        mainRecipe.setCompletionTime(recipe.getCompletionTime());
        Log.d("time passed", "onRecipeChangeExtraListenerMethod: ");
        CollectionReference dbRecipe = db.collection("Recipe");
        Log.d("dbRecipe passed", "onRecipeChangeExtraListenerMethod: ");
        recipe.setRank(5);
        recipe.setDate(new Date().toString());
        Log.d(recipe.getDate(), "onRecipeChangeExtraListenerMethod: ");
        dbRecipe.add(mainRecipe);*/
    }



}
