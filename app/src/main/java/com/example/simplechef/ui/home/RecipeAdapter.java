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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private final static String TAG = "RecipeAdapter";
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private OnItemClickListener mListener;

/*
    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        recipes = recipeList;
    }
*/


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:  called.");
        Recipe currentRecipe = recipes.get(position);
        holder.recipeName.setText(currentRecipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        // TODO use better functions than this one later on
        notifyDataSetChanged();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        public TextView recipeName;

        public RecipeHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.textViewRecipeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
