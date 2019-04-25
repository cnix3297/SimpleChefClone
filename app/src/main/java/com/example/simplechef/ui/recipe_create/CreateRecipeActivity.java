package com.example.simplechef.ui.recipe_create;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.android.gms.common.api.GoogleApiClient;

import com.bumptech.glide.Glide;
import com.example.simplechef.Ingredient;
import com.bumptech.glide.request.RequestOptions;
import com.example.simplechef.R;
import com.example.simplechef.RecipeAPI;
import com.example.simplechef.RecipeClass;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.ui.home.HomeActivity;
import com.example.simplechef.ui.login.LoginActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.squareup.seismic.ShakeDetector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CreateRecipeActivity extends AppCompatActivity implements ShakeDetector.Listener {
    private EditText editTextRecipeName, editTextRecipeCost, editTextRecipeTime;
    private EditText editTextIngredientName,editTextRecipeDescription, editTextIngredientQuantity, editTextDirections;
    private Button buttonSubmitRecipe;
    //private LinearLayout listIngredient;
    private RecyclerView ingredientsRecyclerView;
    int count = 0;
    //Tabs
    private LinearLayout tabGeneral, visibleGeneral, tabIngredients, visibleIngredients, tabDirections, visibleDirections, tabAddImage, visibleAddImage;
    private ImageView imageViewAddImage;
    private Button buttonUploadImage, buttonTakeImage, buttonAddIngredient, buttonClearAllIngredients;
    private Uri imageURI;
    private RecipeClass recipeObject = new RecipeClass();
    private static final String TAG = "AddRecipe";
    private Bitmap image;
    private Context context;
    private IngredientsListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Double> array = new ArrayList<>();
    LocationManager locationManager;
    LocationListener locationListener;
    ShakeDetector shakeDetector;
    SensorManager sensorManager;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);
        context = getApplicationContext();
        shakeDetector = new ShakeDetector(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector.start(sensorManager);
        ingredientList = new ArrayList<>();

        setupToolbar();
        getWindowObjects();

        layoutManager = new LinearLayoutManager(this);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new IngredientsListAdapter(ingredientList);
        ingredientsRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new IngredientsListAdapter.OnItemClickListener() {
            @Override
            public void onDeleteIngredientItemClick(int position) {
                Toast.makeText(context, "Ingredient removed", Toast.LENGTH_SHORT).show();
                ingredientList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(getApplication());
                location.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    // Logic to handle location object
                                    array.add( location.getLatitude());
                                    array.add( location.getLongitude());
                                    Log.d("coordinates", array.toString());
                                }
                            }
                        });

            } else {
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
                // Show rationale and request permission.
            }

            return;
        }

        buttonSubmitRecipe.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //location services
                //Getting current location



                String recipeName = editTextRecipeName.getText().toString();
                String recipeDescription = editTextRecipeDescription.getText().toString();
                String recipeCost = editTextRecipeCost.getText().toString();
                String recipeTime = editTextRecipeTime.getText().toString();
                String recipeDirections = editTextDirections.getText().toString();


                ArrayList<String> inputProblems = new ArrayList<>();

                Boolean isValidInput = true;

                if (recipeName.isEmpty()) {
                    inputProblems.add("Recipe name is missing");
                    isValidInput = false;
                }
                if (recipeDescription.isEmpty()) {
                    inputProblems.add("Recipe description is missing");
                    isValidInput = false;
                }
                if (recipeCost.isEmpty()) {
                    inputProblems.add("Recipe cost is missing");
                    isValidInput = false;
                }
                if(!recipeCost.isEmpty()) {
                    if (Double.valueOf(recipeCost) > 15.0) {
                        inputProblems.add("Recipe cost is above $15 limit");
                        isValidInput = false;
                    }
                }

                if (recipeTime.isEmpty()) {
                    inputProblems.add("Recipe time is missing");
                    isValidInput = false;
                }

                if (recipeObject.getIngredientList().size() == 0) {
                    inputProblems.add("Recipe ingredients are missing");
                    isValidInput = false;
                }

                if (recipeDirections.isEmpty()) {
                    inputProblems.add("Recipe directions are missing");
                    isValidInput = false;
                }


                // if input is valid, lets process
                if(isValidInput) {

                    //Input data into RecipeObject
                    recipeObject.setName(recipeName);
                    recipeObject.setCost(Double.valueOf(recipeCost));
                    recipeObject.setSteps(recipeDirections);
                    recipeObject.setTime(recipeTime);
                    recipeObject.setDescription(recipeDescription);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth currentUser = FirebaseAuth.getInstance();
                    //Document References
                    DocumentReference newRecipeRef = db.collection("Recipes").document();

                    String recipeID = newRecipeRef.getId();
                    recipeObject.setID(recipeID);
                    //Adding picture to firebase
                    addRecipePicturetoFirebase(image, recipeID);

                    //Adding recipes
                    newRecipeRef.set(recipeObject);

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


                    Intent myIntent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(myIntent);
                } else {
                    // input is not valid, send list of messages to Dialog and display them
                    Toast.makeText(context, "Failed to Create Recipe", Toast.LENGTH_SHORT).show();

                    CreateRecipeAlertDialogFragment dialogFragment = CreateRecipeAlertDialogFragment.newInstance(inputProblems);
                    dialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            }
        });

        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String varIngredientQuantity = editTextIngredientQuantity.getText().toString();
                String varIngredientName = editTextIngredientName.getText().toString();
                //String varIngredientCost = editTextIngredientCost.getText().toString();


                //Form validation
                if(varIngredientQuantity.equals("") && varIngredientName.equals("")){
                    Log.d("INGREDIENT ERROR", "NULL VALUES");
                }
                else {
                    RecipeAPI getAPI = new RecipeAPI(varIngredientName);
                    Ingredient ingredient = new Ingredient(varIngredientName, varIngredientQuantity, getAPI.getSearchImage());
                    if (getAPI.getFoodName() == null) {
                        Toast.makeText(getApplicationContext(), "Ingredient Doesn't exist try again", Toast.LENGTH_SHORT).show();
                    } else {
                        //ADD HEADERS
                        //if(count == 0){
                        //    TextView j = new TextView(context);
                        //    j.setText("Amount \t Measurement \t Ingredient \t Price ");
                        //    //listIngredient.addView(j);
                        //}

                        //ask the API for ingredient

                        if (getAPI.getFoodName() == null) {
                            recipeObject.AddIngredient(varIngredientName, varIngredientQuantity, getAPI.getSearchImage());
                            ingredientList.add(ingredient);
                            mAdapter.notifyItemInserted(ingredientList.size());
                            //onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                        } else {
                            recipeObject.AddIngredient(getAPI.getFoodName(), varIngredientQuantity, getAPI.getSearchImage());
                            ingredientList.add(ingredient);
                            mAdapter.notifyItemInserted(ingredientList.size());
                            //onRecipeChangeIngredientListenerVar.onRecipeChangeIngredientListenerMethod(recipe);
                        }

                        //add ingredient to linear layout
                        //TextView t = new TextView(context);
                        //t.setText(recipeObject.getIngredientAtIndex(0).getName() + "" + recipeObject.getIngredientAtIndex(0).getPrice().toString());
                        //t.setPadding(1,10,1,10);
                        //t.setTextSize(20);
                        //t.setTextColor(Color.BLACK);
                        //listIngredient.addView(t);
                        //count++;
                        //setObjectsEmpty();
                        //Log.d("linear layout", "onClick: " + list.size());

                        editTextIngredientName.getText().clear();
                        editTextIngredientQuantity.getText().clear();
                    }
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

        buttonClearAllIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllIngredients();
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
    protected void onStart() {
        shakeDetector.start(sensorManager);
        super.onStart();
    }

    @Override
    protected void onStop() {
        shakeDetector.stop();
        super.onStop();
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

    }
    private void getWindowObjects(){

        // Recipe
        editTextRecipeName = (EditText) findViewById(R.id.editTextRecipeName);
        editTextRecipeTime = (EditText) findViewById(R.id.editTextRecipeTime);
        editTextRecipeCost = (EditText) findViewById(R.id.editTextRecipeCost);

        //Adding Ingredient
        editTextIngredientName = (EditText) findViewById(R.id.editTextIngredientName);
        editTextRecipeDescription = (EditText)findViewById(R.id.editTextRecipeDescription);
        editTextIngredientQuantity = (EditText) findViewById(R.id.editTextIngredientQuantity);
        editTextDirections = (EditText) findViewById(R.id.editTextDirections);

        // RecyclerView
        ingredientsRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewIngredients);

        //?????
        buttonAddIngredient = (Button) findViewById(R.id.buttonAddIngredient);
        buttonClearAllIngredients = (Button) findViewById(R.id.buttonClearAllIngredients);
        //listIngredient = (LinearLayout) findViewById(R.id.ingredientButtonBar);

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
            imageViewAddImage.setMaxWidth(20);
            imageViewAddImage.setMaxHeight(20);
            // glide will follow imageview's width, height and scaleType
            Glide.with(this)
                    .load(imageURI)
                    .centerCrop()
                    .into(imageViewAddImage);
            try {
                image = MediaStore.Images.Media.getBitmap(context.getContentResolver(),imageURI);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Glide.with(this)
                    .load(bitmap)
                    .centerCrop()
                    .into(imageViewAddImage);
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


    public void clearAllIngredients() {
        Toast.makeText(context, "Ingredient list cleared", Toast.LENGTH_SHORT).show();
        ingredientList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void hearShake() {
        clearAllIngredients();
        //editTextIngredientName,editTextRecipeDescription, editTextIngredientQuantity, editTextDirections
        editTextIngredientName.setText("");
        editTextRecipeDescription.setText("");
        editTextIngredientQuantity.setText("");
        editTextDirections.setText("");
        imageViewAddImage.setImageIcon(null);
        Toast.makeText(context, "Starting Over", Toast.LENGTH_SHORT).show();
    }
}
