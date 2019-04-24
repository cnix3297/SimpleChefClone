package com.example.simplechef.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.example.simplechef.ui.recipe_create.CreateRecipeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements AllRecipesFragment.OnFavoriteListener {

    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "HomeActivity";
    private EditText editTextSearchPopUp;
    private Context context;
    private View view;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        Activity activity = (Activity) getApplicationContext();
        view = ((HomeActivity) context).view;

        viewPager = findViewById(R.id.pager);
        viewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        // ToolBar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);

        // Tabs setup with View Pager
        tabLayout = findViewById(R.id.tabsHome);
        tabLayout.setupWithViewPager(viewPager);


        //Search
        editTextSearchPopUp = (EditText) findViewById(R.id.editTextSearch);
        editTextSearchPopUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                search();
                return false;
            }
        });


        // Bottom Nav setup
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuHome:
                        //TODO:home menu
                        return true;
                    case R.id.menuSearch:
                        //TODO:this is where the animation will go for search
                        if(findViewById(R.id.editTextPopUpSearch).getVisibility() == View.VISIBLE){
                            findViewById(R.id.editTextPopUpSearch).setVisibility(View.INVISIBLE);
                        }
                        else{
                            findViewById(R.id.editTextPopUpSearch).setVisibility(View.VISIBLE);
                            Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_bottom);
                            a.reset();
                            findViewById(R.id.editTextPopUpSearch).clearAnimation();
                            findViewById(R.id.editTextPopUpSearch).startAnimation(a);
                        }
                        return true;
                    case R.id.imageViewAdd:
                        Intent myIntent = new Intent(getBaseContext(), CreateRecipeActivity.class);
                        startActivity(myIntent);
                        return true;
                }
                return false;
            }
        });

        viewPagerAdapter.setSelected(0);
    }

    public void search(){
        String hold = editTextSearchPopUp.getText().toString();


        viewPagerAdapter.search(hold,viewPagerAdapter.getSelected());



        /*FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference reference = db.collection("Recipes");
        Query query = reference.whereEqualTo("name", editTextSearchPopUp.getText().toString());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        RecipeClass recipe = document.toObject(RecipeClass.class);
                        Log.d(TAG, "onComplete: " + recipe.getName());
                        Intent intent = recipe.toIntent(getApplicationContext(), ViewRecipeActivity.class);
                        startActivity(intent);
                        break;
                    }
                }else{
                    Toast toast = Toast.makeText(context, "Search Error", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });*/

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


    @Override
    public void onFavoriteSelected(RecipeClass recipe) {
        FavoriteRecipesFragment favoriteRecipesFragment;
            favoriteRecipesFragment = (FavoriteRecipesFragment) viewPager.getAdapter().instantiateItem(viewPager, 1);

         if (favoriteRecipesFragment != null) {
             favoriteRecipesFragment.modifyFavorite(recipe);
         }


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof AllRecipesFragment) {
            AllRecipesFragment allRecipesFragment = (AllRecipesFragment) fragment;
            allRecipesFragment.setOnFavoriteListener(this);
        }
    }
}
