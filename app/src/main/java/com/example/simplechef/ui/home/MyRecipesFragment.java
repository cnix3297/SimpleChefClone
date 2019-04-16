package com.example.simplechef.ui.home;

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
import com.example.simplechef.ui.RecipeClass;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;

import java.util.ArrayList;

public class MyRecipesFragment extends Fragment {
    public static MyRecipesFragment newInstance() {
        MyRecipesFragment fragment = new MyRecipesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(new ArrayList<RecipeClass>());
        recyclerView.setAdapter(recipeListAdapter);

        recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO bundle recipe data to send
                Bundle bundle = new Bundle();
                // TODO fix later - go to item at position!
                Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
