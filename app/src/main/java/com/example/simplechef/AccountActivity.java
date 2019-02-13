package com.example.simplechef;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {
    private TextView textViewName, textViewHomeGivenName, textViewFamilyName, textViewEmail, textViewId, textViewPersonalDetails,textViewSettingsRecipeHeader;
    private ImageView circleImageViewProfilePic;
    private Button buttonSignOut;
    private GoogleSignInClient mGoogleSignInClient;
    private Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        //textViewName = (TextView)findViewById(R.id.textViewHomeDisplayName);
        //First Name
        //textViewHomeGivenName = (TextView)findViewById(R.id.textViewHomeGivenName);
        //Last Name
        //textViewFamilyName = (TextView)findViewById(R.id.textViewHomeFamilyName);
        //textViewEmail = (TextView)findViewById(R.id.textViewHomeEmail);
        //Token Id
        //textViewId = (TextView)findViewById(R.id.textViewHomeId);
        circleImageViewProfilePic = (ImageView)findViewById(R.id.circleImageViewProfilePic);
        Intent intent = getIntent();
        GoogleSignInAccount acct = intent.getParcelableExtra("Account");
        //User Personal Information from GoogleSignInAccount
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        Picasso.with(this).load(personPhoto).into(circleImageViewProfilePic);
        //textViewName.setText(personName);
        //textViewHomeGivenName.setText(personGivenName);
        //textViewFamilyName.setText(personFamilyName);
        //textViewEmail.setText(personEmail);
        //textViewId.setText(personId);


        //Adding fonts
        textViewPersonalDetails = (TextView)findViewById(R.id.textViewPersonalDetails);
        textViewSettingsRecipeHeader = (TextView)findViewById(R.id.textViewSettingsRecipeHeader);
        face = Typeface.createFromAsset(getAssets(), "figure_things.otf");
        textViewPersonalDetails.setTypeface(face);
        textViewSettingsRecipeHeader.setTypeface(face);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken("60:ED:5C:D5:F6:43:AD:EE:17:62:26:8A:B6:8F:51:72:58:04:66:16")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent myIntent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
