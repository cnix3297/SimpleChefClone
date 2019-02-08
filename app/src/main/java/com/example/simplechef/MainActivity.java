package com.example.simplechef;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogIn, buttonFacebookLogin, buttonGoogleLogin, buttonSignUp;
    private ImageView imageViewFacebookIcon, imageViewGoogleIcon, imageViewOrLine1, imageViewOrLine2;
    private TextView textViewOr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate buttonLogIn & buttonSignUp button to variables
        buttonLogIn = (Button)findViewById(R.id.buttonLogIn);
        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);
        buttonFacebookLogin = (Button)findViewById(R.id.buttonFacebookLogin);
        imageViewFacebookIcon = (ImageView)findViewById(R.id.imageViewFacebookIcon);
        buttonGoogleLogin = (Button)findViewById(R.id.buttonGoogleLogin);
        imageViewGoogleIcon = (ImageView)findViewById(R.id.imageViewGoogleIcon);
        textViewOr = (TextView) findViewById(R.id.textViewOr);
        imageViewOrLine1 = (ImageView) findViewById(R.id.imageViewOrLine1);
        imageViewOrLine2 = (ImageView) findViewById(R.id.imageViewOrLine2);

        //buttonLogIn click listener
        buttonLogIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Animation types
                Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
                Animation FadeHalf = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_50);
                Animation FadeThirds = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_75);
                //Fade In
                buttonGoogleLogin.startAnimation(move);
                buttonFacebookLogin.startAnimation(move);
                textViewOr.startAnimation(FadeThirds);
                imageViewOrLine1.startAnimation(FadeHalf);
                imageViewOrLine2.startAnimation(FadeHalf);
                imageViewFacebookIcon.startAnimation(move);
                imageViewGoogleIcon.startAnimation(move);



                buttonSignUp.setVisibility(View.INVISIBLE);
                buttonFacebookLogin.setVisibility(View.VISIBLE);
                buttonGoogleLogin.setVisibility(View.VISIBLE);
                imageViewGoogleIcon.setVisibility(View.VISIBLE);
                imageViewFacebookIcon.setVisibility(View.VISIBLE);
                textViewOr.setVisibility(View.VISIBLE);
                imageViewOrLine1.setVisibility(View.VISIBLE);
                imageViewOrLine2.setVisibility(View.VISIBLE);

            }
        });
    }
}
