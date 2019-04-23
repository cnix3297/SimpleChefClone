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

                Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                if (recipesViewModel.getRecipeFromAllRecipes(position).getName() != null)
                    intent.putExtra("Name", recipesViewModel.getRecipeFromAllRecipes(position).getName());
                else
                    intent.putExtra("Name", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getCost() != null)
                    intent.putExtra("Cost", recipesViewModel.getRecipeFromAllRecipes(position).getCost().toString());
                else
                    intent.putExtra("Cost", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getDescription() != null)
                    intent.putExtra("Description", recipesViewModel.getRecipeFromAllRecipes(position).getDescription());
                else
                    intent.putExtra("Description", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getIngredientList() != null)
                    intent.putExtra("Ingredients", recipesViewModel.getRecipeFromAllRecipes(position).getIngredientList().toString());
                else
                    intent.putExtra("Ingredients", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getTime() != null)
                    intent.putExtra("Time", recipesViewModel.getRecipeFromAllRecipes(position).getTime().toString());
                else
                    intent.putExtra("Time", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getTime() != null)
                    intent.putExtra("Steps", recipesViewModel.getRecipeFromAllRecipes(position).getSteps().toString());
                else
                    intent.putExtra("Steps", "MF NULL");
                if (recipesViewModel.getRecipeFromAllRecipes(position).getTime() != null)
                    intent.putExtra("Image", recipesViewModel.getRecipeFromAllRecipes(position).getImage().toString());
                else
                    intent.putExtra("Image", "MF NULL");

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
