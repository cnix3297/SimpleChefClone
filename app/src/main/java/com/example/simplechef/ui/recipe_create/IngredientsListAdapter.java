package com.example.simplechef.ui.recipe_create;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.simplechef.Ingredient;
import com.example.simplechef.R;

import java.util.ArrayList;


public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {
    private static final String TAG = "IngredientsListAdapter";
    private ArrayList<Ingredient> mIngredientItems;
    private OnItemClickListener mListener;


    public IngredientsListAdapter(ArrayList<Ingredient> ingredientItems) {
        mIngredientItems = ingredientItems;
    }

    public interface OnItemClickListener {
        void onDeleteIngredientItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.ingredients_list_item, parent, false);
        return new IngredientsViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Ingredient ingredient = mIngredientItems.get(position);

        ingredientsViewHolder.ingredientName.setText(ingredient.getName());
        ingredientsViewHolder.ingredientQuantity.setText(ingredient.getQuantity());
    }



    @Override
    public int getItemCount() {
        return mIngredientItems.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientName, ingredientQuantity;
        public ImageButton imageButtonDeleteItem;

        public IngredientsViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            ingredientName = itemView.findViewById(R.id.textViewIngredientName);
            ingredientQuantity = itemView.findViewById(R.id.textViewIngredientQuantity);
            imageButtonDeleteItem = itemView.findViewById(R.id.imageButtonRemoveItem);


            imageButtonDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                           listener.onDeleteIngredientItemClick(position);
                        }
                    }
                }
            });
        }

    }
}
