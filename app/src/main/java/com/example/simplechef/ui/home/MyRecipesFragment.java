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

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;
import com.facebook.internal.WebDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyRecipesFragment extends Fragment {

    private ArrayList<String> myRecipes = new ArrayList<>();
    private ArrayList<RecipeClass> myRecipeObjects = new ArrayList<>();
    private ArrayList<RecipeClass> myRecipeObjectComplete = new ArrayList<>();
    //Recycler View
    RecipeListAdapter recipeListAdapter;
    RecyclerView recyclerView;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final FirebaseAuth currentUser = FirebaseAuth.getInstance();
    final DocumentReference docRef = db.collection("Users").document(currentUser.getUid());



    public static MyRecipesFragment newInstance() {
        MyRecipesFragment fragment = new MyRecipesFragment();
        return fragment;
    }

    /*public void onSearch(String search){
        recipeListAdapter.onSearchRecieved(search);
    }*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        final View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    try{
                        myRecipes.clear();
                        myRecipeObjects.clear();
                    }
                    catch (NullPointerException E) {
                    }

                    myRecipes = (ArrayList<String>) document.get("MyRecipes");

                    if (myRecipes != null) {
                        for (int i = 0; i < myRecipes.size(); i++) {
                            db.collection("Recipes").document(myRecipes.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    RecipeClass document = documentSnapshot.toObject(RecipeClass.class);
                                    myRecipeObjects.add(document);
                                    Log.d("MYITEMS", document.getID());

                                    recyclerView = view.findViewById(R.id.recyclerView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                                    recipeListAdapter = new RecipeListAdapter(myRecipeObjects);
                                    recyclerView.setAdapter(recipeListAdapter);


                                    recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {

                                            /*Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);*/
                                            Intent intent = myRecipeObjects.get(position).toIntent(getActivity(), ViewRecipeActivity.class);



                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFavoriteItemClick(int position) {

                                        }
                                    });
                                }
                            });

                        }
                    }
                }
            }
        });



        return view;
    }
}
