package com.example.simplechef;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//Google SDK Imports
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private Button buttonLogIn, buttonSignUp, buttonSignOut, buttonGoogleLogin, buttonFacebookLogin;
    private ImageView imageViewGoogleIcon, imageViewOrLine1, imageViewOrLine2;
    private TextView textViewOr, textViewUsername, textViewPassword;
    // private SignInButton buttonGoogleLogin;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUiElements();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Robs SHA1:  99:27:60:14:F0:E9:FB:CF:F9:8F:53:F3:B5:3C:DC:FA:CB:8A:06:43
        // Colbys SHA1:  60:ED:5C:D5:F6:43:AD:EE:17:62:26:8A:B6:8F:51:72:58:04:66:16
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken("99:27:60:14:F0:E9:FB:CF:F9:8F:53:F3:B5:3C:DC:FA:CB:8A:06:43")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // standard login listener
        buttonLogIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                standardLogin();
                LoginAnimation();   //TODO:  move all Google login stuff to googleLogin(),
                                    //TODO:  expire LoginAnimation() ?

            }
        });

        // facebook login listener
        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

        // google login listener
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });


    }

    public void setupUiElements() {

        //Associate buttonLogIn & buttonSignUp button to variables
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        buttonFacebookLogin = (Button) findViewById(R.id.buttonFacebookLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
    }

    public void standardLogin() {
        //TODO: handle standard login
    }

    public void facebookLogin() {
        //TODO: handle facebook login
    }

    public void googleLogin() {
        //TODO: handle google login

    }

    public void LoginAnimation() {

/*
        //Animation types
        Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        Animation FadeHalf = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_50);
        Animation FadeThirds = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_75);
        //Fade In
        buttonGoogleLogin.startAnimation(move);
        //loginButton.startAnimation(move);
        textViewOr.startAnimation(FadeThirds);
        imageViewOrLine1.startAnimation(FadeHalf);
        imageViewOrLine2.startAnimation(FadeHalf);


        buttonSignUp.setVisibility(View.INVISIBLE);
        //loginButton.setVisibility(View.VISIBLE);
        buttonGoogleLogin.setVisibility(View.VISIBLE);
        textViewOr.setVisibility(View.VISIBLE);
        imageViewOrLine1.setVisibility(View.VISIBLE);
        imageViewOrLine2.setVisibility(View.VISIBLE);
*/

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonGoogleLogin:
                        signIn();
                        break;

                }
            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.d("STATE", "Signin()");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()) {

                Log.d("STATE", "SIGN IN SUCCESSFUL");
                //Start Home Intent
                GoogleSignInAccount acct = result.getSignInAccount();
                Intent myIntent = new Intent(LoginActivity.this, AccountActivity.class);
                myIntent.putExtra("Account", acct);
                startActivity(myIntent);
                finish();
        }
        else{
            Log.d("Login", "SIGN IN FAILURE" + result.getStatus().getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void updateInterface(boolean b){

    }
}
