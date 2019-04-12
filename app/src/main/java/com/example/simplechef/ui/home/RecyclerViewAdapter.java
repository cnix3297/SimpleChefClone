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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder> {
    private final static String TAG = "RecyclerViewAdapter";
    private ArrayList<Recipe> mRecipeList;
    private OnItemClickListener mListener;

    public RecyclerViewAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList = recipeList;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ListViewHolder(v, mListener);
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

        public ListViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            recipeName = view.findViewById(R.id.textViewRecipeName);

            view.setOnClickListener(new View.OnClickListener() {
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
