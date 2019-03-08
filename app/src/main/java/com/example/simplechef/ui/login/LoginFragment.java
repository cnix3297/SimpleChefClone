package com.example.simplechef.ui.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//Google SDK Imports
import com.example.simplechef.R;
import com.example.simplechef.util.GlideApp;



public class LoginFragment extends Fragment {
    private Button buttonLogIn, buttonSignUp, buttonSignOut, buttonGoogleLogin, buttonFacebookLogin;
    private ImageView imageViewBackground, imageViewGoogleIcon, imageViewOrLine1, imageViewOrLine2;
    private TextView textViewOr, textViewEmail, textViewPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        setupUiElements(view);
        setupImages(view);

        return view;
    }



    public void setupUiElements(View view) {

        imageViewBackground = (ImageView)view.findViewById(R.id.imageViewBackground);

        // buttons
        buttonLogIn = (Button) view.findViewById(R.id.buttonLogIn);
        buttonFacebookLogin = (Button) view.findViewById(R.id.buttonFacebookLogin);
        buttonGoogleLogin = (Button) view.findViewById(R.id.buttonGoogleLogin);
        buttonSignUp = (Button) view.findViewById(R.id.buttonSignUp);

        textViewEmail = (TextView)view.findViewById(R.id.textViewEmail);
        textViewPassword = (TextView)view.findViewById(R.id.textViewPassword);

        // button listeners
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();

                ((LoginActivity)getActivity()).signInWithEmailandPassword(email, password);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).setViewPager(1);
            }
        });

        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signInFacebook();
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).signInGoogle();
            }
        });
    }


    private void setupImages(View view) {
        // Glide handles auto-scaling images down to proper resolution
        GlideApp
                .with(view)
                .load(R.drawable.login_background)
                .centerCrop()
                .into(imageViewBackground);
    }


}
