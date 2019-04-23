package com.example.simplechef.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;

import java.util.List;

public class AllRecipesFragment extends Fragment  {
    private static final String TAG = "AllRecipesFragment";
    //Firebase
    private RecipeListAdapter recipeListAdapter;
    private RecipesViewModel recipesViewModel;
    private RecyclerView recyclerView;

    public static AllRecipesFragment newInstance() {
        AllRecipesFragment fragment = new AllRecipesFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        recipesViewModel.getAllRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeClass>>() {
            @Override
            public void onChanged(@Nullable List<RecipeClass> recipes) {
                recipeListAdapter.setRecipes(recipes);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //View To Return
        final View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipeListAdapter = new RecipeListAdapter();
        recyclerView.setAdapter(recipeListAdapter);

        recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = recipesViewModel.getRecipeFromAllRecipes(position).toIntent(getActivity(), ViewRecipeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFavoriteItemClick(int position) {
                // get the recipe at the current position in the recyclerview
                RecipeClass recipe = recipesViewModel.getRecipeFromAllRecipes(position);
                // set it to favorite
                recipe.setFavorite(true);
                // add it to the favorites recyclerview
                recipesViewModel.addRecipeToFavorites(recipe);

            }
        });


        return view;
    }

}
