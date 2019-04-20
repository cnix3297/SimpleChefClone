package com.example.simplechef.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.Users;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllRecipesFragment extends Fragment  {
    private static final String TAG = "AllRecipesFragment";
    //Firebase
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseAuth currentUser = FirebaseAuth.getInstance();
    final DocumentReference docRef = db.collection("Users").document(currentUser.getUid());
    public Boolean remove = false;
    private ArrayList<RecipeClass> recipeList = new ArrayList<>();
    private ArrayList<String> favoritesList = new ArrayList<>();
    private RecipeListAdapter recipeListAdapter;
    public static AllRecipesFragment newInstance() {
        AllRecipesFragment fragment = new AllRecipesFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //View To Return
        final View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);

        //Get Favorites List
        initiateFavoritesList();

        //Access NoSql Database
        //DocumentReference docRef = db.collection("Recipe").document("4S0ycFz9A05IWKs3249d");

        //Get Info from Recipe Collection
        db.collection("Recipes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ClearObject(true);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        RecipeClass recipeobj = document.toObject(RecipeClass.class);
                        AddObject(recipeobj);
                        Log.d("Recipes", document.getId() + " => " + recipeobj.getName());
                    }

                    //Recycler View Init & Data Pass
                    final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    Log.d("BEFORE", recipeList.toString());
                    recipeListAdapter = new RecipeListAdapter(recipeList, favoritesList);
                    recyclerView.setAdapter(recipeListAdapter);


                    recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // TODO bundle recipe data to send
                            Bundle bundle = new Bundle();
                            // TODO fix later - go to item at position!
                            Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFavoriteItemClick(int position) {
                            Log.d("Favorites", "is clicked");
                            final RecipeClass currentRecipe = recipeList.get(position);
                            String recipeID = currentRecipe.getID();

                            // Create a reference to the document associate with user

                            final HashMap<String, Object> data = new HashMap<>();
                            data.put("MyFavorites", FieldValue.arrayUnion(recipeID));


                            //Executes if we do not remove the recipe id
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        if (document.contains("MyFavorites")) {

                                            favoritesList = (ArrayList<String>) document.get("MyFavorites");
                                            for (int i = 0; i < favoritesList.size(); i++) {
                                                if (currentRecipe.getID().equals(favoritesList.get(i))) {
                                                    remove = true;
                                                }
                                            }
                                            if (remove) {
                                                docRef.update("MyFavorites", FieldValue.arrayRemove(currentRecipe.getID()));

                                            } else {
                                                docRef.update(data);
                                            }
                                            remove = false;
                                        } else {
                                            docRef.set(data, SetOptions.merge());
                                        }

                                    } else {
                                        Log.d("DocumentFailed", "get failed with ", task.getException());
                                    }
                                }
                            });


                        }

                    });




                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }

        });



        return view;
    }
    public void AddObject(RecipeClass obj){
        this.recipeList.add(obj);
    }
    public void ClearObject(Boolean clear){
        if(clear)
            this.recipeList.clear();
    }
    public void initiateFavoritesList(){
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.contains("MyFavorites")) {
                        favoritesList = (ArrayList<String>) document.get("MyFavorites");
                    }

                } else {
                    Log.d("DocumentFailed", "get failed with ", task.getException());
                }
            }
        });
    }

}
