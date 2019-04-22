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

                    myRecipes.clear();
                    myRecipeObjects.clear();
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

                                    recipeListAdapter = new RecipeListAdapter(myRecipeObjects, new ArrayList<String>());
                                    recyclerView.setAdapter(recipeListAdapter);


                                    recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            Intent intent = myRecipeObjects.get(position).toIntent(getActivity(), ViewRecipeActivity.class);

                                            /*Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                                            if (myRecipeObjects.get(position).getName() != null)
                                                intent.putExtra("Name", myRecipeObjects.get(position).getName());
                                            else
                                                intent.putExtra("Name", "MF NULL");
                                            if (myRecipeObjects.get(position).getCost() != null)
                                                intent.putExtra("Cost", myRecipeObjects.get(position).getCost().toString());
                                            else
                                                intent.putExtra("Cost", "MF NULL");
                                            if (myRecipeObjects.get(position).getDescription() != null)
                                                intent.putExtra("Description", myRecipeObjects.get(position).getDescription());
                                            else
                                                intent.putExtra("Description", "MF NULL");
                                            if (myRecipeObjects.get(position).getIngredientList() != null)
                                                intent.putExtra("Ingredients", myRecipeObjects.get(position).getIngredientList().toString());
                                            else
                                                intent.putExtra("Ingredients", "MF NULL");
                                            if (myRecipeObjects.get(position).getTime() != null)
                                                intent.putExtra("Time", myRecipeObjects.get(position).getTime().toString());
                                            else
                                                intent.putExtra("Time", "MF NULL");
                                            if (myRecipeObjects.get(position).getTime() != null)
                                                intent.putExtra("Steps", myRecipeObjects.get(position).getSteps().toString());
                                            else
                                                intent.putExtra("Steps", "MF NULL");
                                            if (myRecipeObjects.get(position).getTime() != null)
                                                intent.putExtra("Image", myRecipeObjects.get(position).getImage().toString());
                                            else
                                                intent.putExtra("Image", "MF NULL");*/

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
