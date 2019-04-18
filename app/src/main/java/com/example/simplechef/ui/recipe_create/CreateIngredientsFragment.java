package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeAPI;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.Recipe;
import com.google.api.Distribution;

import java.net.URI;
import java.util.ArrayList;


/**

 */
public class CreateIngredientsFragment extends Fragment {
    EditText editTextRecipeName, editTextRecipeCost, editTextRecipeTime;
    EditText editTextIngredientName, editTextIngredientCost, editTextIngredientQuantity;
    Button buttonSubmitRecipe;
    LinearLayout listIngredient;
    TextView error, textToolbar;
    Recipe recipe = new Recipe();
    int count = 0;
    onRecipeChangeIngredientListener onRecipeChangeIngredientListenerVar;
    //Tabs
    private LinearLayout tabGeneral, visibleGeneral, tabIngredients, visibleIngredients, tabDirections, visibleDirections, tabAddImage, visibleAddImage;
    private ImageView imageViewAddImage;
    private Button buttonUploadImage, buttonTakeImage, addIngredient;
    private Uri imageURI;
    private RecipeClass recipeObject = new RecipeClass();






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


        /*
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

*/

        buttonSubmitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:VALIDATION, FIELD VALUES,

                String recipeName = editTextRecipeName.getText().toString();
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String varIngredientQuantity = editTextIngredientQuantity.getText().toString();
                String varIngredientName = editTextIngredientName.getText().toString();
                String varIngredientCost = editTextIngredientCost.getText().toString();

                //Form validation
                if(varIngredientQuantity.equals("") && varIngredientName.equals("") && varIngredientCost.equals("")){
                    Log.d("INGREDIENT ERROR", "NULL VALUES");
                }else {
                    //ADD HEADERS
                    if(count == 0){
                        TextView j = new TextView(getActivity());
                        j.setText("Amount \t Measurement \t Ingredient \t Price ");
                        listIngredient.addView(j);
                    }

                    //ask the API for ingredient
                    RecipeAPI getAPI = new RecipeAPI(varIngredientName);
                    if(getAPI.getFoodName() == null) {
                        recipeObject.AddIngredient(varIngredientName, Double.parseDouble(varIngredientCost), (varIngredientQuantity));
                        //onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                    }else {
                        recipeObject.AddIngredient(getAPI.getFoodName(), Double.parseDouble(varIngredientCost), (varIngredientQuantity));
                        //onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                    }

                    //add ingredient to linear layout
                    TextView t = new TextView(getActivity());
                    t.setText(recipeObject.getIngredientAtIndex(0).getName() + "" + recipeObject.getIngredientAtIndex(0).getPrice());
                    t.setPadding(1,10,1,10);
                    t.setTextSize(20);
                    t.setTextColor(Color.BLACK);
                    listIngredient.addView(t);
                    count++;
                    setObjectsEmpty();
                    //Log.d("linear layout", "onClick: " + list.size());

                }

            }

        });

/*
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
        */
        tabIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleIngredients.getVisibility() == View.INVISIBLE) {
                    visibleIngredients.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = visibleIngredients.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleIngredients.setLayoutParams(params);

                }
                else {
                    visibleIngredients.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = visibleIngredients.getLayoutParams();
                    params.height = 0;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleIngredients.setLayoutParams(params);
                }
            }
        });
        tabGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleGeneral.getVisibility() == View.INVISIBLE) {
                    visibleGeneral.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = visibleGeneral.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleGeneral.setLayoutParams(params);

                }
                else {
                    visibleGeneral.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = visibleGeneral.getLayoutParams();
                    params.height = 0;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleGeneral.setLayoutParams(params);
                }
            }
        });
        tabDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleDirections.getVisibility() == View.INVISIBLE) {
                    visibleDirections.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = visibleDirections.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleDirections.setLayoutParams(params);

                }
                else {
                    visibleDirections.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = visibleDirections.getLayoutParams();
                    params.height = 0;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleDirections.setLayoutParams(params);
                }
            }
        });
        tabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visibleAddImage.getVisibility() == View.INVISIBLE) {
                    visibleAddImage.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = visibleAddImage.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleAddImage.setLayoutParams(params);
                }
                else {
                    visibleAddImage.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = visibleAddImage.getLayoutParams();
                    params.height = 0;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    visibleAddImage.setLayoutParams(params);
                }
            }
        });
        buttonTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open File Chooser
                openFileChooser();
            }
        });
        return view;
    }
    private void setObjectsEmpty(){
        editTextIngredientQuantity.setText("");
        editTextIngredientName.setText("");
        editTextIngredientCost.setText("");

    }
    private void getWindowObjects(View view){
        //measurement = (Spinner) view.findViewById(R.id.fragment_activity_recipe_create_s1_size_spinner);

        // Recipe
        editTextRecipeName = (EditText) view.findViewById(R.id.editTextRecipeName);
        editTextRecipeCost = (EditText) view.findViewById(R.id.editTextRecipeCost);
        editTextRecipeTime = (EditText) view.findViewById(R.id.editTextRecipeTime);

        //Adding Ingredient
        editTextIngredientName = (EditText) view.findViewById(R.id.editTextIngredientName);
        editTextIngredientCost = (EditText) view.findViewById(R.id.editTextPrice);
        editTextIngredientQuantity = (EditText) view.findViewById(R.id.editTextIngredientQuantity);

        //?????
        addIngredient = (Button) view.findViewById(R.id.buttonAddIngredient);
        listIngredient = (LinearLayout) view.findViewById(R.id.fragment_activity_recipe_create_s1_linearLayout_recipeView);

        //tabs
        tabGeneral = (LinearLayout)view.findViewById(R.id.linearLayoutGeneral);
        visibleGeneral = (LinearLayout)view.findViewById(R.id.visibleGeneral);
        tabIngredients = (LinearLayout)view.findViewById(R.id.linearLayoutIngredients);
        visibleIngredients = (LinearLayout)view.findViewById(R.id.visibleIngredients);
        tabDirections = (LinearLayout)view.findViewById(R.id.linearLayoutDirections);
        visibleDirections = (LinearLayout)view.findViewById(R.id.visibleDirections);
        tabAddImage = (LinearLayout)view.findViewById(R.id.linearLayoutAddImage);
        visibleAddImage = (LinearLayout)view.findViewById(R.id.visibleAddImage);

        //ImageView for adding picture
        imageViewAddImage = (ImageView)view.findViewById(R.id.imageViewAddImage);

        //Button Declaration
        buttonTakeImage = (Button)view.findViewById(R.id.buttonTakeImage);
        buttonUploadImage = (Button)view.findViewById(R.id.buttonUploadExisting);
        buttonSubmitRecipe = (Button)view.findViewById(R.id.buttonSubmitRecipe);


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

    //Adding an Image to New Recipe
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            // glide will follow imageview's width, height and scaleType
            Glide.with(this)
                    .load(imageURI)
                    .into(imageViewAddImage);
        }
        else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageViewAddImage.setImageBitmap(bitmap);
        }
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
}
