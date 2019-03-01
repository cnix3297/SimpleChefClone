package com.example.simplechef.ui.login;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.simplechef.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    private static final String TAG = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // initialize db
        db = FirebaseFirestore.getInstance();

        // initialize auth
        mAuth = FirebaseAuth.getInstance();

        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.fragmentContainer);

        // used to delay this view (start/title screen)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupViewPager(viewPager);
            }
        }, 2000);

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        //TODO: check to see if user is signed in (non-null) and go to correct fragment/activity accordingly
        //TODO: maybe this goes in the Handler() run() function?  Not sure.
        if (user != null) {
            // send to home activity
        } else {
            // send to login fragment
        }
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

}
