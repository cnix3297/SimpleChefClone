package com.example.simplechef.ui.recipe_create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.simplechef.R;



public class fragment_recipe_create_extra1 extends Fragment {
        ImageView picture;
        EditText  price,time,description;
        Button sendToDatabase;




    public fragment_recipe_create_extra1() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_recipe_create_extra1, container, false);
        setObjects(view);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(bitmap);
    }

    private void setObjects(View view){
        picture = view.findViewById(R.id.fragment_recipe_create_extra_imageView_camera);
        price = view.findViewById(R.id.fragment_recipe_create_price);
        time = view.findViewById(R.id.fragment_create_recipe_EditText_time);
        description = view.findViewById(R.id.fragment_recipe_create_extra1_description);
        sendToDatabase = view.findViewById(R.id.fragment_recipe_create_extra_Button_done);
    }

}
