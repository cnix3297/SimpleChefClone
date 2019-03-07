package com.example.simplechef.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.simplechef.R;
import com.example.simplechef.util.GlideApp;

public class SignUpFragment extends Fragment {

    private Button buttonSignUp;
    private TextView textViewEmail;
    private TextView textViewPassword;

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

    private void setupImages(View view) {
        // Glide handles auto-scaling images down to proper resolution

        GlideApp
                .with(view)
                .load(R.drawable.signup_background)
                .centerCrop()
                .into(imageViewBackground);
    }

    private void setupUiElements(View view) {

        // images
        imageViewBackground = (ImageView) view.findViewById(R.id.imageViewBackground);

        //Back Button to Login Screen
        buttonSignUp = (Button)view.findViewById(R.id.buttonSignUp);

        textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        textViewPassword = (TextView) view.findViewById(R.id.textViewPassword);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();

                ((LoginActivity)getActivity()).createAccount(email, password);

            }
        });
    }



}
