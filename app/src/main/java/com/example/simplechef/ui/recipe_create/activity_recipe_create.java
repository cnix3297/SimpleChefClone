package com.example.simplechef.ui.recipe_create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.simplechef.R;

import com.example.simplechef.ui.Recipe;
import com.example.simplechef.ui.login.SectionsStatePagerAdapter;

public class activity_recipe_create extends AppCompatActivity implements recipe_create_fragment_s1.onRecipeChangeIngredientListener, fragment_recipe_create_direction.onRecipeChangeDirectionListener {
    ViewPager fragmentContainer;
    Recipe mainRecipe;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
        mainRecipe = new Recipe();
         fragmentContainer= (ViewPager) findViewById(R.id.activity_create_recipe_fragment_view);

        setupViewPager(fragmentContainer);
    }
    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new recipe_create_fragment_s1(), "Ingredient");
        adapter.addFragment(new fragment_recipe_create_direction(), "direction");
        adapter.addFragment(new fragment_recipe_create_extra1(), "extra");
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
}
