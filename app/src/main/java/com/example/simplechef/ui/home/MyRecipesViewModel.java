package com.example.simplechef.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.simplechef.RecipeClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesViewModel extends ViewModel {
    private final static String TAG = "MyRecipesViewModel";
    private MutableLiveData<List<RecipeClass>> myRecipesList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth currentUser = FirebaseAuth.getInstance();
    private ArrayList<RecipeClass> myRecipeObjects = new ArrayList<>();
    private ArrayList<String> tempList = new ArrayList<>();


    public LiveData<List<RecipeClass>> getRecipes() {
        if (myRecipesList == null) {
            myRecipesList = new MutableLiveData<List<RecipeClass>>();
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
                                    RecipeClass document = documentSnapshot.toObject(RecipeClass.class);
                                    myRecipeObjects.add(document);
                                }
                            });
                        }
                    }

                    myRecipesList.setValue(myRecipeObjects);
                }
            }
        });
    }
}
