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
import android.widget.TextView;
import android.widget.Toast;


import com.example.simplechef.R;
import com.example.simplechef.util.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class SignUpFragment extends Fragment {

    private Button buttonSignUp;
    private TextView textViewEmail;
    private TextView textViewPassword;
    private ImageView imageViewBackground;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.signup_fragment,container,false);


        //Toolbar setup
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");

        setupUiElements(view);
        setupImages(view);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();

                createAccount(email, password);

            }
        });

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

    }


    public void createAccount(String email, String password) {

        Log.d(TAG, "Create Account:"+email);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ((LoginActivity)getActivity()).updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            ((LoginActivity)getActivity()).updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
