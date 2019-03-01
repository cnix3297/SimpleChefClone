package com.example.simplechef.ui.account;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//Import Layout and Activity Classes
import com.example.simplechef.R;


public class AccountActivity extends AppCompatActivity {
    private TextView textViewName, textViewHomeGivenName, textViewFamilyName, textViewEmail, textViewAccountHeader, textViewPersonalDetails,textViewSettingsRecipeHeader;
    private ImageView circleImageViewProfilePic;
    private Button buttonSignOut;
    private Typeface face;
    private String LoginType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        final String LoginAccount;
        if(intent.hasExtra("AccountG")){
            LoginType = "Google";
        }
        else{
            LoginType = "Facebook";
        }

        //buttonSignOut click listener for google
        buttonSignOut = (Button)findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    private void signOut() {
        switch (LoginType) {
            case "Facebook":
                break;
        }

    }
}

