package com.example.simplechef.ui.recipe_view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ViewDescriptionFragment extends Fragment {

    private ImageView imageViewImage;
    private TextView textViewTime, textViewCost, textViewName, textViewDescription;
    public static ViewDescriptionFragment newInstance() {
        ViewDescriptionFragment fragment = new ViewDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_description, container, false);
        imageViewImage = (ImageView)view.findViewById(R.id.imageViewRecipeImage);
        textViewTime = (TextView)view.findViewById(R.id.textViewTime);
        textViewCost = (TextView)view.findViewById(R.id.textViewCost);
        textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
        textViewName = (TextView)view.findViewById(R.id.textViewName);

        Bundle bundle = getArguments();
        textViewName.setText(bundle.getString("name"));
        textViewDescription.setText(bundle.getString("description"));
        textViewCost.setText(bundle.getString("cost"));
        textViewTime.setText(bundle.getString("time"));

        //Recipe Image
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference recipePictureReference = storage.getReference().child(bundle.getString("image"));
        recipePictureReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri)
                        .centerCrop()
                        .into(imageViewImage);
                Log.d("SUCCESS", uri.toString());


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FAILURE", e.toString());

            }
        });
        Log.d("ARGUMENTS", bundle.getString("name"));
        // Inflate the layout for this fragment
        return view;
    }
}
