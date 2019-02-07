package com.example.simplechef;

import android.media.FaceDetector;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button Login, FacebookLogin, GoogleLogin;
    private Button Signup;
    private ImageView FaceBookIcon, GoogleIcon, ORLine1, ORLine2;
    private TextView LoginPage_OR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate Login & Signup button to variables
        Login = (Button)findViewById(R.id.LogInBtn);
        Signup = (Button)findViewById(R.id.SignUpBtn);
        FacebookLogin = (Button)findViewById(R.id.FacebookLogin);
        FaceBookIcon = (ImageView)findViewById(R.id.FacebookIcon);
        GoogleLogin = (Button)findViewById(R.id.GoogleLogin);
        GoogleIcon = (ImageView)findViewById(R.id.GoogleIcon);
        LoginPage_OR = (TextView) findViewById(R.id.OR);
        ORLine1 = (ImageView) findViewById(R.id.ORStartLine);
        ORLine2 = (ImageView) findViewById(R.id.OREndLine);

        //Login click listener
        Login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Animation types
                Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
                Animation FadeHalf = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_50);
                Animation FadeThirds = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_75);
                //Fade In
                GoogleLogin.startAnimation(move);
                FacebookLogin.startAnimation(move);
                LoginPage_OR.startAnimation(FadeThirds);
                ORLine1.startAnimation(FadeHalf);
                ORLine2.startAnimation(FadeHalf);
                FaceBookIcon.startAnimation(move);
                GoogleIcon.startAnimation(move);



                Signup.setVisibility(View.INVISIBLE);
                FacebookLogin.setVisibility(View.VISIBLE);
                GoogleLogin.setVisibility(View.VISIBLE);
                GoogleIcon.setVisibility(View.VISIBLE);
                FaceBookIcon.setVisibility(View.VISIBLE);
                LoginPage_OR.setVisibility(View.VISIBLE);
                ORLine1.setVisibility(View.VISIBLE);
                ORLine2.setVisibility(View.VISIBLE);

            }
        });
    }
}
