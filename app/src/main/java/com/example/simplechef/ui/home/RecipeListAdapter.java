package com.example.simplechef.ui.home;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {
    private final static String TAG = "RecipeListAdapter";
    private List<RecipeClass> recipes = new ArrayList<>();
    private OnRecipeItemClickListener mListener;
    private Context context;
    private View view;


    RecipeListAdapter(){

    }

    public interface OnRecipeItemClickListener {
        void onItemClick(int position);
        void onFavoriteItemClick(int position);
    }

    public void setOnItemClickListener(OnRecipeItemClickListener listener) {
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
    public void onBindViewHolder(@NonNull final RecipeHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder:  called.");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //Getting object for Recipe
        RecipeClass currentRecipe = recipes.get(position);
        holder.recipeName.setText(currentRecipe.getName());
        holder.recipeCost.setText(formatter.format(currentRecipe.getCost()));
        holder.recipeDescription.setText(currentRecipe.getSteps());


        //Adding Image to Recycler view item
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference recipePictureReference = storage.getReference().child(currentRecipe.getImage());
        recipePictureReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .centerCrop()
                        .into(holder.recipeImage);
                Log.d("SUCCESS", uri.toString());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FAILURE", e.toString());

            }
        });

        //On Create Check Favorites
        /*for(int i = 0; i < favoritesList.size(); i++){
            String currentFavorite = favoritesList.get(i);
            if(currentRecipe.getID().equals(currentFavorite)){
                holder.recipeAddToFavorites.setImageResource(android.R.drawable.btn_star_big_on);
                break;
            }
            else{
                holder.recipeAddToFavorites.setImageResource(android.R.drawable.btn_star_big_off);

            }

        }*/


        Log.d("RecipeHolder", currentRecipe.getName());
        Log.d("RecipeHolder", currentRecipe.getImage());

    }

    public void setRecipes(List<RecipeClass> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        public TextView recipeName, recipeCost, recipeDescription;
        public ImageView recipeImage;
        public ImageButton recipeAddToFavorites;
        public RecipeHolder(View itemView, final OnRecipeItemClickListener listener) {
            super(itemView);
            context = itemView.getContext();
            view = itemView;
            recipeName = itemView.findViewById(R.id.textViewRecipeName);
            recipeCost = itemView.findViewById(R.id.textViewRecipeCost);
            recipeDescription = itemView.findViewById(R.id.textViewRecipeDescription);
            recipeImage = itemView.findViewById(R.id.imageViewRecipeImage);
            recipeAddToFavorites = itemView.findViewById(R.id.imageButtonFavorite);

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


            recipeAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (recipes.get(position).isFavorite()) {
                                recipeAddToFavorites.setImageResource(android.R.drawable.btn_star_big_on);
                            }
                            else{
                                recipeAddToFavorites.setImageResource(android.R.drawable.btn_star_big_off);
                            }

                            listener.onFavoriteItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
