package com.example.simplechef.ui.recipe_view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ViewDescriptionFragment extends Fragment {
    private ImageView imageViewImage;
    RecipeClass recipeClass;
    private TextView nameTextView, costTextView, timeTextView, descriptionTextView;
    /*public String name, description, ingredients, cost, time, steps, image;
    public Bundle desriptionBundle;*/

    public static ViewDescriptionFragment newInstance(RecipeClass recipe) {
        ViewDescriptionFragment fragment = new ViewDescriptionFragment();
        fragment.recipeClass = recipe;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    /*
        this.name = desriptionBundle.getString("name");
        this.description = desriptionBundle.getString("description");
        this.ingredients = desriptionBundle.getString("ingredients");
        this.cost = desriptionBundle.getString("cost");
        this.time = desriptionBundle.getString("time");
        this.steps = desriptionBundle.getString("steps");
        this.image = desriptionBundle.getString("image");
*/
        View view = inflater.inflate(R.layout.fragment_recipe_view_description, container, false);
        imageViewImage = (ImageView)view.findViewById(R.id.imageViewImage);
        nameTextView = (TextView) view.findViewById(R.id.textViewName);
        nameTextView.setText(recipeClass.getName());
        costTextView = (TextView) view.findViewById(R.id.textViewCost);
        costTextView.setText("~ $" + recipeClass.getCost());
        timeTextView = (TextView) view.findViewById(R.id.textViewTime);
        timeTextView.setText(recipeClass.getTime());
        descriptionTextView = (TextView) view.findViewById(R.id.textViewDescription);
        descriptionTextView.setText(recipeClass.getDescription());
        //Adding Image to Recycler view item
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference recipePictureReference = storage.getReference().child(recipeClass.getImage());
        recipePictureReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri)
                        .centerCrop()
                        .into(imageViewImage);
                Log.d("SUCCESS", uri.toString());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FAILURE", "FAILED TO LOAD RECIPE IMAGE INTO RECIPE VIEWER");

            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}
