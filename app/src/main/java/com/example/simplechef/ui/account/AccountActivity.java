package com.example.simplechef.ui.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.simplechef.R;

import java.net.URI;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String mUsername, mEmail;
    private URI mPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mUsername = mCurrentUser.getDisplayName();
        mEmail = mCurrentUser.getEmail();
        //TODO:  mPhoto

        //TODO:  display these on Account page UI

    }
}

