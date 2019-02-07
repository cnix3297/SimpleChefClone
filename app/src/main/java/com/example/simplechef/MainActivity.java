package com.example.simplechef;

import android.media.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button Login, FacebookLogin, GoogleLogin;
    private Button Signup;
    private ImageView FaceBookIcon, GoogleIcon;
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

        //Login click listener
        Login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Signup.setVisibility(View.INVISIBLE);
                FacebookLogin.setVisibility(View.VISIBLE);
                GoogleLogin.setVisibility(View.VISIBLE);
                GoogleIcon.setVisibility(View.VISIBLE);
                FaceBookIcon.setVisibility(View.VISIBLE);

            }
        });
    }
}
