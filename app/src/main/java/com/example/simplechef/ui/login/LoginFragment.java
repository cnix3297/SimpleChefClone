package com.example.simplechef.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//Google SDK Imports
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.R;
import com.example.simplechef.util.GlideApp;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginFragment extends Fragment {
    private Button buttonLogIn, buttonSignUp, buttonSignOut, buttonGoogleLogin, buttonFacebookLogin;
    private ImageView imageViewBackground, imageViewGoogleIcon, imageViewOrLine1, imageViewOrLine2;
    private TextView textViewOr, textViewEmail, textViewPassword;
    private ConstraintLayout loginCountainer;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    //Fragment Class
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;

    //ViewPager
    private ViewPager viewPager;

    //Circle Image View
    private CircleImageView circleImageViewProfilePic;

    //CallBackManager
    private CallbackManager callbackManager;
    private Context context = ((LoginActivity)(getActivity()));
    private static final int RC_SIGN_IN = 9001;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        setupUiElements(view);
        setupImages(view);
        return view;

    }
    public void setupUiElements(View view) {


        imageViewBackground = (ImageView)view.findViewById(R.id.imageViewBackground);

        // buttons
        buttonLogIn = (Button) view.findViewById(R.id.buttonLogIn);
        buttonFacebookLogin = (Button) view.findViewById(R.id.buttonFacebookLogin);
        buttonGoogleLogin = (Button) view.findViewById(R.id.buttonGoogleLogin);
        buttonSignUp = (Button) view.findViewById(R.id.buttonSignUp);

        textViewEmail = (TextView)view.findViewById(R.id.textViewEmail);
        textViewPassword = (TextView)view.findViewById(R.id.textViewPassword);

        // button listeners
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();

                ((LoginActivity)getActivity()).signInWithEmailandPassword(email, password);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).setViewPager(1);
            }
        });

        buttonFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInFacebook();
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signInGoogle();
            }
        });
    }


    private void setupImages(View view) {
        // Glide handles auto-scaling images down to proper resolution
        GlideApp
                .with(view)
                .load(R.drawable.login_background)
                .centerCrop()
                .into(imageViewBackground);
    }

    /*
    private void signInGoogle() {
        // Configure Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
   */


    private void signInFacebook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);

        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        //Graph Request
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        Intent myIntent = new Intent(getActivity(), AccountActivity.class);
                                        myIntent.putExtras(getFacebookData(object));
                                        startActivity(myIntent);
                                        getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Log.d("STATE", "Facebook Login Canceled");

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("STATE", exception.toString());

                    }
                });
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("name"))
                bundle.putString("name", object.getString("name"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));

        } catch (Exception e) {
            Log.d("Bundle Exception: ", "BUNDLE Exception : " + e.toString());
        }

        return bundle;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
