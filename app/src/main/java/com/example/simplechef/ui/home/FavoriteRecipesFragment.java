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

public class FavoriteRecipesFragment extends Fragment {
    private static final String TAG = "FavoriteRecipesFragment";
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private RecipesViewModel recipesViewModel;

    public static FavoriteRecipesFragment newInstance() {
        FavoriteRecipesFragment fragment = new FavoriteRecipesFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        recipesViewModel.getFavoriteRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeClass>>() {
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
        View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);
        //Recycler View

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipeListAdapter = new RecipeListAdapter();
        recyclerView.setAdapter(recipeListAdapter);

        recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = recipesViewModel.getRecipeFromFavoritesRecipes(position).toIntent(getActivity(), ViewRecipeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFavoriteItemClick(int position) {
                // get the recipe at the current position in the recyclerview
                RecipeClass selectedRecipe = recipesViewModel.getRecipeFromAllRecipes(position);

                if (selectedRecipe.isFavorite()) {
                    recipesViewModel.removeRecipeFromFavorites(selectedRecipe);
                } else {
                    // add it to the favorites
                    recipesViewModel.addRecipeToFavorites(selectedRecipe);
                }

            }
        });

        return view;
    }


}
