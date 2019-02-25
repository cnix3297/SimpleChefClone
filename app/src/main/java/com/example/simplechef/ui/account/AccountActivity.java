package com.example.simplechef.ui.account;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

//Import Layout and Activity Classes
import com.example.simplechef.R;
import com.example.simplechef.ui.login.LoginActivity;


import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private TextView textViewName, textViewHomeGivenName, textViewFamilyName, textViewEmail, textViewAccountHeader, textViewPersonalDetails,textViewSettingsRecipeHeader;
    private ImageView circleImageViewProfilePic;
    private Button buttonSignOut;
    private GoogleSignInClient mGoogleSignInClient;
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
            InitializeGoogle();
        }
        else{
            LoginType = "Facebook";
            InitializeFacebook();
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
    private void InitializeGoogle(){
        circleImageViewProfilePic = (CircleImageView)findViewById(R.id.circleImageViewProfilePic);
        Intent intent = getIntent();
        GoogleSignInAccount acct = intent.getParcelableExtra("AccountG");
        //User Personal Information from GoogleSignInAccount
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        Picasso.with(this).load(personPhoto).into(circleImageViewProfilePic);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken("60:ED:5C:D5:F6:43:AD:EE:17:62:26:8A:B6:8F:51:72:58:04:66:16")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Initialize fonts
        InitializeFonts();

    }
    private void InitializeFacebook() {
        circleImageViewProfilePic = (CircleImageView)findViewById(R.id.circleImageViewProfilePic);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.containsKey("profile_pic")) {
            Picasso.with(this).load(bundle.getString("profile_pic")).into(circleImageViewProfilePic);
        }
        Log.d("INFORMATION: " , bundle.getString("idFacebook") + ":" + bundle.getString("name")+ ":" + bundle.getString("birthday")+ ":" + bundle.get("gender"));

        InitializeFonts();
    }
    private void InitializeFonts(){
        //Adding fonts
        textViewPersonalDetails = (TextView) findViewById(R.id.textViewPersonalDetails);
        textViewSettingsRecipeHeader = (TextView) findViewById(R.id.textViewSettingsRecipeHeader);
        textViewAccountHeader = (TextView)findViewById(R.id.textViewAccountHeader);
        face = Typeface.createFromAsset(getAssets(), "figure_things.otf");
        textViewPersonalDetails.setTypeface(face);
        textViewSettingsRecipeHeader.setTypeface(face);
        textViewAccountHeader.setTypeface(face);
    }
    private void signOut() {
        switch (LoginType) {
            case "Google":
                mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent myIntent = new Intent(AccountActivity.this, LoginActivity.class);
                        startActivity(myIntent);
                        overridePendingTransition(R.anim.slide_in_right, 0);

                    }
                });
                break;
            case "Facebook":
                if (AccessToken.getCurrentAccessToken() == null) {
                    return; // already logged out
                }

                new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {

                        LoginManager.getInstance().logOut();

                    }
                }).executeAsync();
                Intent myIntent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_right, 0);

                break;
        }

    }
}

