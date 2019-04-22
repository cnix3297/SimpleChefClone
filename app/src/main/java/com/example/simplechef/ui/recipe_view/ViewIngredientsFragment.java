package com.example.simplechef.ui.recipe_view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.Ingredient;
import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.home.RecipeListAdapter;
import com.example.simplechef.ui.recipe_create.IngredientsListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ViewIngredientsFragment extends Fragment {
    private TextView textViewID;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseAuth currentUser = FirebaseAuth.getInstance();
    public View fragView;
    public static ViewIngredientsFragment newInstance() {
        ViewIngredientsFragment fragment = new ViewIngredientsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_ingredients, container, false);
        fragView = view;

        Bundle bundle = getArguments();
        textViewID.setText(bundle.getString("ID"));
        final DocumentReference docRef = db.collection("Recipes").document(bundle.getString("ID"));



        // Inflate the layout for this fragment
        return view;
    }
}
