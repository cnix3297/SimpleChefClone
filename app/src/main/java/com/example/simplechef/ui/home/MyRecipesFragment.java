package com.example.simplechef.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;

import java.util.List;

public class MyRecipesFragment extends Fragment {
    private final static String TAG = "MyRecipesFragment";
    private RecipeListAdapter recipeListAdapter;
    private RecyclerView recyclerView;
    private RecipesViewModel recipesViewModel;



    public static MyRecipesFragment newInstance() {
        MyRecipesFragment fragment = new MyRecipesFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        recipesViewModel.getMyRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeClass>>() {
            @Override
            public void onChanged(@Nullable List<RecipeClass> recipes) {
                recipeListAdapter.setRecipes(recipes);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        final View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recipeListAdapter = new RecipeListAdapter();
        recyclerView.setAdapter(recipeListAdapter);


        recipeListAdapter.setOnItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                if (recipesViewModel.getRecipeFromMyRecipes(position).getName() != null)
                    intent.putExtra("Name", recipesViewModel.getRecipeFromMyRecipes(position).getName());
                else
                    intent.putExtra("Name", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getCost() != null)
                    intent.putExtra("Cost", recipesViewModel.getRecipeFromMyRecipes(position).getCost().toString());
                else
                    intent.putExtra("Cost", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getDescription() != null)
                    intent.putExtra("Description", recipesViewModel.getRecipeFromMyRecipes(position).getDescription());
                else
                    intent.putExtra("Description", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getIngredientList() != null)
                    intent.putExtra("Ingredients", recipesViewModel.getRecipeFromMyRecipes(position).getIngredientList().toString());
                else
                    intent.putExtra("Ingredients", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getTime() != null)
                    intent.putExtra("Time", recipesViewModel.getRecipeFromMyRecipes(position).getTime().toString());
                else
                    intent.putExtra("Time", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getTime() != null)
                    intent.putExtra("Steps", recipesViewModel.getRecipeFromMyRecipes(position).getSteps().toString());
                else
                    intent.putExtra("Steps", "MF NULL");
                if (recipesViewModel.getRecipeFromMyRecipes(position).getTime() != null)
                    intent.putExtra("Image", recipesViewModel.getRecipeFromMyRecipes(position).getImage().toString());
                else
                    intent.putExtra("Image", "MF NULL");

                startActivity(intent);
            }

            @Override
            public void onFavoriteItemClick(int position) {

            }
        });

/*
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
*/



        return view;
    }
}
