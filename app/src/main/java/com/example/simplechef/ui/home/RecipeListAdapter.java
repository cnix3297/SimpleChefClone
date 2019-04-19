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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.NumberFormat;
import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {
    private final static String TAG = "RecipeListAdapter";
    private ArrayList<RecipeClass> recipes;
    private OnItemClickListener mListener;
    private Context context;
    private View view;


    RecipeListAdapter(ArrayList<RecipeClass> list){
        this.recipes = list;
        Log.d("CONSTRUCTOR CALLED", this.recipes.toString());

    }

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


        Log.d("RecipeHolder", currentRecipe.getName());
        Log.d("RecipeHolder", currentRecipe.getImage());

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        public TextView recipeName, recipeCost, recipeDescription;
        public ImageView recipeImage;
        public ImageButton recipeAddToFavorites;
        public RecipeHolder(View itemView, final OnItemClickListener listener) {
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
                            RecipeClass currentRecipe = recipes.get(position);
                            FirebaseAuth currentUser = FirebaseAuth.getInstance();

                        }
                    }
                }
            });
        }
    }

}
