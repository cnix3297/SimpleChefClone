package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.RecipeAPI;
import com.example.simplechef.ui.Recipe;

import java.util.ArrayList;


/**

 */
public class CreateIngredientsFragment extends Fragment {
    Spinner measurement;
    EditText quantity,ingredientName,price;
    Button addIngredient, next, delete;

    LinearLayout listIngredient;
    TextView error, textToolbar;
    Recipe recipe = new Recipe();
    int count = 0;
    onRecipeChangeIngredientListener onRecipeChangeIngredientListenerVar;






    public CreateIngredientsFragment() {
        // Required empty public constructor
    }

    public interface onRecipeChangeIngredientListener
    {
        public void onRecipeChangeIngredientListenerMethod(Recipe recipe);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_create_incredients, container, false);
        // Inflate the layout for this fragment

        getWindowObjects(view);
        textToolbar = ((CreateRecipeActivity)getActivity()).findViewById(R.id.toolbar_title);
        textToolbar.setText("Add Ingredients");
        //items are being added but spinner is not visible
        final ArrayList<String> list = new ArrayList<>();
        list.add("tsp");
        list.add("Fl.Oz.");
        list.add("tbs");
        list.add("lb");
        list.add("oz");
        list.add("cups");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, list);
        measurement.setAdapter(adapter);
        measurement.setPadding(8,8,8,8);
        measurement.setPrompt("Metric");
        Log.d("spinner", measurement.getSelectedItem() + "");


        addIngredient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                error.setVisibility(View.INVISIBLE);

                //making sure values are not null
                if(quantity.getText().toString().equals("") && ingredientName.getText().toString().equals("") &&
                        price.getText().toString().equals("")){
                    displayError("Make sure values are not empty");
                    Log.d("TAG", "onClick: null values");

                }else {
                    //add header to linear layout
                    if(count == 0){
                        TextView j = new TextView(getActivity());
                        j.setText("Amount \t Measurement \t Ingredient \t Price ");
                        listIngredient.addView(j);
                    }

                    //ask the API for ingrident
                    RecipeAPI getAPI = new RecipeAPI(ingredientName.getText().toString());
                    Log.d("test", "hmm");
                    if(getAPI.getFoodName() == null) {
                        recipe.setIngredients(ingredientName.getText().toString(), Double.parseDouble(price.getText().toString()), measurement.getSelectedItem().toString(), Double.parseDouble(quantity.getText().toString()));
                        displayError("Please double check your ingredient");
                        onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                    }else {
                        recipe.setIngredients(getAPI.getFoodName(), Double.parseDouble(price.getText().toString()), measurement.getSelectedItem().toString(), Double.parseDouble(quantity.getText().toString()));
                        onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                    }
                    //add ingredient to linear layout
                    TextView t = new TextView(getActivity());
                    t.setText(recipe.getIngredientToString(count));
                    t.setPadding(1,10,1,10);
                    t.setTextSize(20);
                    listIngredient.addView(t);
                    count++;
                    setObjectsEmpty();
                    Log.d("linear layout", "onClick: " + list.size());

                }

            }

        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count != 0){
                recipe.deleteLastIngredient();
                listIngredient.removeViewAt(count);
                onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                count--;
                }

            }
        });
        return view;
    }
    private void setObjectsEmpty(){
        quantity.setText("");
        ingredientName.setText("");
        price.setText("");

    }
    private void displayError(String errorr){
       error.setText("ERROR! " + errorr);
       error.setVisibility(View.VISIBLE);
    }

    private void getWindowObjects(View view){
        measurement = (Spinner) view.findViewById(R.id.fragment_activity_recipe_create_s1_size_spinner);
        quantity = (EditText) view.findViewById(R.id.fragment_activity_recipe_create_s1_editText_quantity);
        ingredientName = (EditText) view.findViewById(R.id.fragment_activity_recipe_create_s1_editText_ingredient);
        price = (EditText) view.findViewById(R.id.fragment_activity_recipe_create_s1_editText_price);
        addIngredient = (Button) view.findViewById(R.id.fragment_activity_recipe_create_s1_button_addIngredient);
        listIngredient = (LinearLayout) view.findViewById(R.id.fragment_activity_recipe_create_s1_linearLayout_recipeView);
        error = (TextView) view.findViewById(R.id.fragment_activity_recipe_create_s1_TextView_error);
        delete = (Button) view.findViewById(R.id.fragment_activity_recipe_button_delete);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try{
        onRecipeChangeIngredientListenerVar = (onRecipeChangeIngredientListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ "must override onRecipeChange");
        }
    }
}
