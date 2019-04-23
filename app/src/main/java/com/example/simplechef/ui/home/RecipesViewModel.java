package com.example.simplechef.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.simplechef.RecipeClass;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipesViewModel extends ViewModel {
    private final static String TAG = "RecipesViewModel";
    private MutableLiveData<List<RecipeClass>> allRecipes;
    private MutableLiveData<List<RecipeClass>> myFavoriteRecipes;
    private MutableLiveData<List<RecipeClass>> myRecipes;
    private ArrayList<RecipeClass> allRecipesDataset = new ArrayList<>();
    private ArrayList<RecipeClass> myFavoritesDataset = new ArrayList<>();
    private ArrayList<RecipeClass> myRecipesDataset = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth currentUser = FirebaseAuth.getInstance();
    private ArrayList<String> tempList;


    public LiveData<List<RecipeClass>> getAllRecipes() {
        if (allRecipes == null) {
            allRecipes = new MutableLiveData<>();
            loadAllRecipes();
        }

        return allRecipes;
    }

    public LiveData<List<RecipeClass>> getFavoriteRecipes() {
        if (myFavoriteRecipes == null) {
            myFavoriteRecipes = new MutableLiveData<>();
            loadMyFavoriteRecipes();
        }

        return myFavoriteRecipes;
    }

    public LiveData<List<RecipeClass>> getMyRecipes() {
        if (myRecipes == null) {
            myRecipes = new MutableLiveData<>();
            loadMyRecipes();
        }

        return myRecipes;
    }


    private void loadAllRecipes() {
        new AsyncTask<Void, Void, List<RecipeClass>>() {
            @Override
            protected List<RecipeClass> doInBackground(Void... voids) {
                db.collection("Recipes")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecipeClass recipeobj = document.toObject(RecipeClass.class);
                                allRecipesDataset.add(recipeobj);
                                Log.d(TAG, document.getId() + " => " + recipeobj.getName());
                            }
                        }
                    }
                });
                return allRecipesDataset;
            }

            @Override
            protected void onPostExecute(List<RecipeClass> recipes) {
                super.onPostExecute(recipes);

                allRecipes.setValue(recipes);
            }
        }.execute();
    }

    private void loadMyFavoriteRecipes() {
        new AsyncTask<Void, Void, List<RecipeClass>>() {
            @Override
            protected List<RecipeClass> doInBackground(Void... voids) {
                DocumentReference docRef = db.collection("Users").document(currentUser.getUid());


                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            tempList = (ArrayList<String>) document.get("MyFavorites");

                            if (tempList != null) {
                                for (int i = 0; i < tempList.size(); i++) {
                                    db.collection("Recipes").document(tempList.get(i)).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            RecipeClass document = documentSnapshot.toObject(RecipeClass.class);
                                            myFavoritesDataset.add(document);
                                            Log.d(TAG, "Adding recipe to the favoritesDataset: " + document.getName());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
                return myFavoritesDataset;
            }

            @Override
            protected void onPostExecute(List<RecipeClass> recipes) {
                super.onPostExecute(recipes);

                myFavoriteRecipes.setValue(recipes);
            }
        }.execute();
    }

    private void loadMyRecipes() {
        new AsyncTask<Void, Void, List<RecipeClass>>() {
            @Override
            protected List<RecipeClass> doInBackground(Void... voids) {
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
                                            myRecipesDataset.add(document);
                                            Log.d(TAG, "Adding recipe to the myRecipesDataset: " + document.getName());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
                return myRecipesDataset;
            }

            @Override
            protected void onPostExecute(List<RecipeClass> recipes) {
                super.onPostExecute(recipes);

                myRecipes.setValue(recipes);
            }
        }.execute();
    }

    public RecipeClass getRecipeFromAllRecipes(int position) {
        return allRecipesDataset.get(position);
    }

    public RecipeClass getRecipeFromFavoritesRecipes(int position) {
        return myFavoritesDataset.get(position);
    }

    public void addRecipeToFavorites(RecipeClass recipe) {
        // check to make sure recipe doesnt already exist

        if (myFavoritesDataset.contains(recipe)) {
            //do nothing
        } else {

            myFavoritesDataset.add(recipe);
            myFavoriteRecipes.setValue(myFavoritesDataset);

            // update firebase
            final DocumentReference docRef = db.collection("Users").document(currentUser.getUid());
            final RecipeClass recipeToAddToFirebase = recipe;
            String recipeID = recipe.getID();
            final HashMap<String, Object> data = new HashMap<>();
            data.put("MyFavorites", FieldValue.arrayUnion(recipeID));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                ArrayList<String> favoritesList;

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Boolean remove = false;
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        if (document.contains("MyFavorites")) {

                            favoritesList = (ArrayList<String>) document.get("MyFavorites");
                            for (int i = 0; i < favoritesList.size(); i++) {
                                if (recipeToAddToFirebase.getID().equals(favoritesList.get(i))) {
                                    remove = true;
                                }
                            }

                            if (remove) {
                                docRef.update("MyFavorites", FieldValue.arrayRemove(recipeToAddToFirebase.getID()));

                            } else {
                                docRef.update(data);
                            }

                        } else {
                            docRef.set(data, SetOptions.merge());
                        }

                    } else {
                        Log.d("DocumentFailed", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public void removeRecipeFromFavorites(RecipeClass recipe) {
        myFavoritesDataset.remove(recipe);
        myFavoriteRecipes.setValue(myFavoritesDataset);
    }

    public RecipeClass getRecipeFromMyRecipes(int position) {
        return myRecipesDataset.get(position);
    }

    public void insert(RecipeClass recipe) {
    }


    public void remove(RecipeClass recipe) {

    }

    private void removeAllRecipes() {

    }
}
