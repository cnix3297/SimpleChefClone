package com.example.simplechef.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.simplechef.R;
import com.example.simplechef.util.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class SignUpFragment extends Fragment {

    private Button btn;
    private ImageView imageViewBackground;
    private FragmentActivity myContext;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.signup_fragment,container,false);
        myContext= (FragmentActivity) getActivity();

        //Toolbar setup
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");

        setupUiElements(view);
        setupImages(view);

        return view;
    }

    public void setupImages(View view) {
        // Glide handles auto-scaling images down to proper resolution

        GlideApp
                .with(view)
                .load(R.drawable.signup_background)
                .centerCrop()
                .into(imageViewBackground);
    }

    public void setupUiElements(View view) {

        // images
        imageViewBackground = (ImageView) view.findViewById(R.id.imageViewBackground);

        //Back Button to Login Screen
        btn = (Button)view.findViewById(R.id.buttonBack);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SignUp", Toast.LENGTH_SHORT).show();
                ((StartActivity)getActivity()).setViewPager(0);

            }
        });
    }
}
