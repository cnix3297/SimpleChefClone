package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeAPI;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.home.HomeActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CreateRecipeActivity extends AppCompatActivity {
    private EditText editTextRecipeName, editTextRecipeCost, editTextRecipeTime;
    private EditText editTextIngredientName, editTextIngredientCost, editTextIngredientQuantity, editTextDirections;
    private Button buttonSubmitRecipe;
    private LinearLayout listIngredient;
    int count = 0;
    //Tabs
    private LinearLayout tabGeneral, visibleGeneral, tabIngredients, visibleIngredients, tabDirections, visibleDirections, tabAddImage, visibleAddImage;
    private ImageView imageViewAddImage;
    private Button buttonUploadImage, buttonTakeImage, addIngredient;
    private Uri imageURI;
    private RecipeClass recipeObject = new RecipeClass();
    private static final String TAG = "AddRecipe";
    private Bitmap image;
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
        context = getApplicationContext();

        setupToolbar();

        getWindowObjects();

        buttonSubmitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipeName = editTextRecipeName.getText().toString();
                String recipeCost = editTextRecipeCost.getText().toString();
                String recipeTime = editTextRecipeTime.getText().toString();
                String recipeDirections = editTextDirections.getText().toString();

                ArrayList<String> inputProblems = new ArrayList<>();

                Boolean isValidInput = true;

                if (recipeName.isEmpty()) {
                    inputProblems.add("Recipe name is missing");
                    isValidInput = false;
                }

                if (recipeCost.isEmpty()) {
                    inputProblems.add("Recipe cost is missing");
                    isValidInput = false;
                }

                if (recipeTime.isEmpty()) {
                    inputProblems.add("Recipe time is missing");
                    isValidInput = false;
                }

                if (recipeDirections.isEmpty()) {
                    inputProblems.add("Recipe directions are missing");
                    isValidInput = false;
                }

                if (recipeObject.getIngredientList().size() == 0) {
                    inputProblems.add("Recipe ingredients are missing");
                    isValidInput = false;
                }

                if (recipeObject.getCost() > 15.0) {
                    inputProblems.add("Recipe cost is above $15 limit");
                    isValidInput = false;
                }



/*                Boolean stop = false;

                if(editTextRecipeName.getText() != null) {
                    recipeObject.setName(editTextRecipeName.getText().toString());
                }
                else{
                    Toast.makeText(context, "Missing Recipe Name", Toast.LENGTH_SHORT).show();
                    stop = true;
                }
                if(editTextRecipeCost.getText() == null) {

                    if(recipeObject.getCost() > 20.0){
                        Toast.makeText(context, "Cost is to high", Toast.LENGTH_SHORT).show();
                        stop = true;
                    }
                }
                if(editTextRecipeTime.getText() != null) {
                    recipeObject.setTime(editTextRecipeTime.getText().toString());
                }
                else{
                    Toast.makeText(context, "Missing Recipe Time", Toast.LENGTH_SHORT).show();
                    stop = true;
                }
                if(editTextDirections.getText() != null) {
                    recipeObject.setSteps(editTextDirections.getText().toString());
                }
                else{
                    Toast.makeText(context, "Missing Recipe Directions", Toast.LENGTH_SHORT).show();
                    stop = true;
                }
                if(recipeObject.getIngredientList().size() > 0) {
                    //Do Nothing
                }
                else{
                    Toast.makeText(context, "You got no ingredients bro. C'mon", Toast.LENGTH_SHORT).show();
                    stop = true;
                }*/


                // if input is valid, lets process
                if(isValidInput) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth currentUser = FirebaseAuth.getInstance();
                    //Document References
                    DocumentReference newRecipeRef = db.collection("Recipes").document();


                    //Adding recipes
                    newRecipeRef.set(recipeObject);
                    String recipeID = newRecipeRef.getId();
                    Toast.makeText(context, "Recipe created!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Recipe added with id: " + recipeID);
                    //Mapping Recipe to user
                    final HashMap<String, Object> data = new HashMap<>();
                    data.put("MyRecipes", FieldValue.arrayUnion(recipeID));
                    final DocumentReference newUserRef = db.collection("Users").document(currentUser.getUid());
                    CollectionReference reference = db.collection("Users").document(currentUser.getUid()).collection("MyRecipes");

                    // Create a reference to the document associate with user
                    DocumentReference docRef = db.collection("Users").document(currentUser.getUid());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.contains("MyRecipes")) {
                                    newUserRef.update(data);
                                } else {
                                    newUserRef.set(data);
                                }

                            } else {
                                Log.d("DocumentFailed", "get failed with ", task.getException());
                            }
                        }
                    });

                    //Adding picture to firebase
                    addRecipePicturetoFirebase(image, recipeID);
                } else {
                    // input is not valid, send list of messages to Dialog and display them
                    Toast.makeText(context, "Failed to Create Recipe", Toast.LENGTH_SHORT).show();

                    CreateRecipeAlertDialogFragment dialogFragment = CreateRecipeAlertDialogFragment.newInstance(inputProblems);
                    dialogFragment.show(getSupportFragmentManager(), "dialog");
                }
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
                }
                else {
                    //ADD HEADERS
                    if(count == 0){
                        TextView j = new TextView(context);
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
                    TextView t = new TextView(context);
                    t.setText(recipeObject.getIngredientAtIndex(0).getName() + "" + recipeObject.getIngredientAtIndex(0).getPrice().toString());
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
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Add Recipe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profileIntent = new Intent(CreateRecipeActivity.this, AccountActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.action_signout:
                FirebaseAuth.getInstance().signOut();
                Intent signOutIntent = new Intent(CreateRecipeActivity.this, LoginActivity.class);
                startActivity(signOutIntent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setObjectsEmpty(){
        editTextIngredientQuantity.setText("");
        editTextIngredientName.setText("");
        editTextIngredientCost.setText("");

    }
    private void getWindowObjects(){

        // Recipe
        editTextRecipeName = (EditText) findViewById(R.id.editTextRecipeName);
        editTextRecipeCost = (EditText) findViewById(R.id.editTextRecipeCost);
        editTextRecipeTime = (EditText) findViewById(R.id.editTextRecipeTime);

        //Adding Ingredient
        editTextIngredientName = (EditText) findViewById(R.id.editTextIngredientName);
        editTextIngredientCost = (EditText) findViewById(R.id.editTextIngredientCost);
        editTextIngredientQuantity = (EditText) findViewById(R.id.editTextIngredientQuantity);
        editTextDirections = (EditText) findViewById(R.id.editTextDirections);

        //?????
        addIngredient = (Button) findViewById(R.id.buttonAddIngredient);
        listIngredient = (LinearLayout) findViewById(R.id.ingredientButtonBar);

        //tabs
        tabGeneral = (LinearLayout) findViewById(R.id.linearLayoutGeneral);
        visibleGeneral = (LinearLayout) findViewById(R.id.visibleGeneral);
        tabIngredients = (LinearLayout) findViewById(R.id.linearLayoutIngredients);
        visibleIngredients = (LinearLayout) findViewById(R.id.visibleIngredients);
        tabDirections = (LinearLayout) findViewById(R.id.linearLayoutDirections);
        visibleDirections = (LinearLayout)findViewById(R.id.visibleDirections);
        tabAddImage = (LinearLayout)findViewById(R.id.linearLayoutAddImage);
        visibleAddImage = (LinearLayout)findViewById(R.id.visibleAddImage);

        //ImageView for adding picture
        imageViewAddImage = (ImageView)findViewById(R.id.imageViewAddImage);

        //Button Declaration
        buttonTakeImage = (Button)findViewById(R.id.buttonTakeImage);
        buttonUploadImage = (Button)findViewById(R.id.buttonUploadExisting);
        buttonSubmitRecipe = (Button)findViewById(R.id.buttonSubmitRecipe);
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
            try {
                image = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imageURI);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageViewAddImage.setImageBitmap(bitmap);
            image = bitmap;
        }
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void addRecipePicturetoFirebase(final Bitmap bitmap, String recipeID) {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference recipePictureReference = storage.getReference().child("Recipes/" + recipeID + "/recipe.jpg");
        recipeObject.setImage("Recipes/" + recipeID + "/recipe.jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = recipePictureReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failure uploading file");
                Toast.makeText(context,"There was a problem uploading the image", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Success uploading file");
            }
        });
    }
}
