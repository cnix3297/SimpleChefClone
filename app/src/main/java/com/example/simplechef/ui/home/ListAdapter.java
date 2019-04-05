package com.example.simplechef.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.ui.Recipe;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private final static String TAG = "ListAdapter";
    private ArrayList<Recipe> mRecipeList;

    public ListAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:  called.");
        Recipe currentRecipe = mRecipeList.get(position);
        holder.recipeName.setText(currentRecipe.getName());
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }



    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;

        public ListViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.textViewRecipeName);
        }
    }

}
