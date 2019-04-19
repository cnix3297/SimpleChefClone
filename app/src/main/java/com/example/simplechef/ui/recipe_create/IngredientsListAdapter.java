package com.example.simplechef.ui.recipe_create;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplechef.Ingredients;

import java.util.ArrayList;


public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {
    private static final String TAG = "IngredientsListAdapter";
    private ArrayList<Ingredients> mIngredientsItems;

    public IngredientsListAdapter(ArrayList<Ingredients> ingredientsItems) {
        mIngredientsItems = ingredientsItems;
    }

    @NonNull
    @Override
    public IngredientsListAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int position) {

    }



    @Override
    public int getItemCount() {
        return mIngredientsItems.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {

        public IngredientsViewHolder(View v) {
            super(v);
        }
    }
}
