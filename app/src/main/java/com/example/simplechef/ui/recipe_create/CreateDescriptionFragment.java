package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.ui.Recipe;
import com.squareup.picasso.Picasso;

import java.net.URI;


public class CreateDescriptionFragment extends Fragment {
        private ImageButton addPicture;
        private ImageView picture;
        private EditText  price,time,description;
        private Button buttonSubmit;
        private Recipe recipe;
        private TextView textToolbar;
        private Uri mImageUri;
        onRecipeChangeExtraListener onRecipeChangeExtraListenerVar;




    public CreateDescriptionFragment() {
        // Required empty public constructor
    }
    public interface onRecipeChangeExtraListener
    {
        public void onRecipeChangeExtraListenerMethod(Recipe recipe);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_create_description, container, false);

        //Initialize Objects
        addPicture = view.findViewById(R.id.addImageButton);
        picture = view.findViewById(R.id.imageViewDisplayImage);
        price = view.findViewById(R.id.editTextPrice);
        time = view.findViewById(R.id.editTextTime);
        description = view.findViewById(R.id.editTextDescription);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        //Set Title of Toolbar to Directions
        textToolbar = ((CreateRecipeActivity)getActivity()).findViewById(R.id.toolbar_title);
        textToolbar.setText("Directions");


        recipe = new Recipe();

        //OnClickListener for Image Button
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open File Chooser
                openFileChooser();

                //Take Picture Option
                /*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                */
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(true){
                recipe.setDescription(description.getText().toString());
                recipe.setCost(Double.parseDouble(price.getText().toString()));
                Log.d("time", "onClick: " + time.getText().toString());
                String[] hold = time.getText().toString().split("\\W");
                recipe.setCompletionTime(Integer.parseInt(hold[0]) * 60 * 60 + Integer.parseInt(hold[1]) * 60);
                Log.d("maths", "onClick: " + recipe.getCompletionTime());
                /*onRecipeChangeExtraListenerVar.onRecipeChangeExtraListenerMethod(recipe);
*/
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            // glide will follow imageview's width, height and scaleType
            Glide.with(this)
                    .load(mImageUri)
                    .into(picture);
        }
        else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(bitmap);
        }
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    /*private boolean isInputsValid(){
        boolean check = true;
        if (time.getText().toString().matches(".*\\W.{2}")){

            time.setBackgroundColor(0xFFFFFF);
        }else {
            time.setBackgroundColor(0xffff0000);
            check = false;
        }


        if(description.getText().length() == 0){
            description.setBackgroundColor(0xffff0000);
            check = false;
        }else
            time.setBackgroundColor(0xFFFFFF);

        if (price.getText().length() == 0){
            price.setBackgroundColor(0xffff0000);
            check = false;
        }else
            time.setBackgroundColor(0xFFFFFF);

        if(recipe.getPicture() == null){
            check = false;
            picture.setBackgroundColor(0xffff0000);

        }else
            time.setBackgroundColor(0xFFFFFF);
        return check;
    }*/
}
