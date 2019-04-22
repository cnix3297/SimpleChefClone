package com.example.simplechef.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyRecipesViewModel extends ViewModel {
    private final static String TAG = "MyRecipesViewModel";
    private MutableLiveData<ArrayList<RecipeClass>> myRecipesList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth currentUser = FirebaseAuth.getInstance();
    private ArrayList<String> tempList = new ArrayList<>();


    public LiveData<ArrayList<RecipeClass>> getRecipe() {
        if (myRecipesList == null) {
            myRecipesList = new MutableLiveData<ArrayList<RecipeClass>>();
            loadRecipes();
        }

        return myRecipesList;
    }


    private void loadRecipes() {
        DocumentReference docRef = db.collection("Users").document(currentUser.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    tempList = (ArrayList<String>) document.get("MyRecipes");

                    if (tempList != null) {
                        for (int i = 0; i < tempList.size(); i++) {
                            db.collection("Recipes").document(tempList.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    RecipeClass recipe = documentSnapshot.toObject(RecipeClass.class);
                                    myRecipesList.setValue(recipe);

                                    Log.d(TAG, recipe.getID());


                                }
                            });

                        }
                    }
                }
            }
        });
    }
}
