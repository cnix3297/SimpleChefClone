package com.example.simplechef.ui.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplechef.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.simplechef.R;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivity";
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private String mUsername, mEmail;
    private String mPhotoURL;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private CircleImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        setupToolbar();

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        imageViewPhoto = findViewById(R.id.circleImageViewProfilePic);


        mUsername = mCurrentUser.getDisplayName();
        mEmail = mCurrentUser.getEmail();
        mPhotoURL = mCurrentUser.getPhotoUrl().toString();
        Drawable pic = LoadImageFromWebOperations(mPhotoURL);


        textViewUsername.setText(mUsername);
        textViewEmail.setText(mEmail);
        imageViewPhoto.setImageDrawable(pic);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView)findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "is name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}



/*
public class AccountFragment extends Fragment {

    private TextView textViewAccountHeader, textViewPersonalDetails,textViewSettingsRecipeHeader;
    private ImageView circleImageViewProfilePic;
    private Button buttonSignOut;
    private GoogleSignInClient mGoogleSignInClient;
    private Typeface face;
    private String LoginType;
    private Context context;
    private View view;
    private Intent intent;

    private static final String TAG = "AccountFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        view = inflater.inflate(R.layout.fragment_account, container, false);
        context = (HomeActivity)getActivity();
        intent = ((FragmentActivity) context).getIntent();
        final String LoginAccount;
        if(intent.hasExtra("AccountG")){
            LoginType = "Google";
            InitializeGoogle();
        }
        else{
            LoginType = "Facebook";
            //InitializeFacebook();
        }

        //buttonSignOut click listener for google
        buttonSignOut = (Button)view.findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        return view;
    }
    private void InitializeGoogle(){
        circleImageViewProfilePic = (CircleImageView)view.findViewById(R.id.circleImageViewProfilePic);
        GoogleSignInAccount acct = intent.getParcelableExtra("AccountG");
        //User Personal Information from GoogleSignInAccount
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();
        Picasso.with(getActivity()).load(personPhoto).into(circleImageViewProfilePic);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken("60:ED:5C:D5:F6:43:AD:EE:17:62:26:8A:B6:8F:51:72:58:04:66:16")
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        //Initialize fonts
        InitializeFonts();

    }
    private void InitializeFacebook() {
        circleImageViewProfilePic = (CircleImageView)view.findViewById(R.id.circleImageViewProfilePic);
        Bundle bundle = intent.getExtras();
        if(bundle.containsKey("profile_pic")) {
            Picasso.with(getActivity()).load(bundle.getString("profile_pic")).into(circleImageViewProfilePic);
        }
        Log.d("INFORMATION: " , bundle.getString("idFacebook") + ":" + bundle.getString("name")+ ":" + bundle.getString("birthday")+ ":" + bundle.get("gender"));

        InitializeFonts();
    }
    private void InitializeFonts(){
        //Adding fonts
        textViewPersonalDetails = (TextView) view.findViewById(R.id.textViewPersonalDetails);
        textViewSettingsRecipeHeader = (TextView) view.findViewById(R.id.textViewSettingsRecipeHeader);
        textViewAccountHeader = (TextView)view.findViewById(R.id.textViewAccountHeader);
        face = Typeface.createFromAsset(getActivity().getAssets(), "figure_things.otf");
        textViewPersonalDetails.setTypeface(face);
        textViewSettingsRecipeHeader.setTypeface(face);
        textViewAccountHeader.setTypeface(face);
    }
    private void signOut() {

        switch (LoginType) {
            case "Google":
                mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent myIntent = new Intent(context, LoginActivity.class);
                        startActivity(myIntent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, 0);
                        Log.d(TAG, "Signing out of Google login");
                    }
                });
                break;
*/
/*
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
                Intent myIntent = new Intent(context, LoginActivity.class);
                startActivity(myIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, 0);

                break;
*//*

            default:
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(context, LoginActivity.class);
                startActivity(myIntent);
                Log.d(TAG, "Signing out of standard login");
                break;
        }

    }
}
*/

