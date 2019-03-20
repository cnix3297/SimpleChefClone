package com.example.simplechef.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.simplechef.R;
import com.example.simplechef.ui.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    private ViewPager viewPager;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize db
        db = FirebaseFirestore.getInstance();

        // initialize auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void setupViewPager(ViewPager viewPager){


        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignUpFragment(), "SignUp");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int FragmentNumber){
        viewPager.setCurrentItem(FragmentNumber);
    }

    public void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    public void updateUI(FirebaseUser user) {
        // check to see if a user is already logged in or not
        if (user != null) {
            // send to home activity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // send to login fragment
            SectionsStatePagerAdapter sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
            viewPager = (ViewPager) findViewById(R.id.fragmentContainer);
            setupViewPager(viewPager);
        }
    }


}
