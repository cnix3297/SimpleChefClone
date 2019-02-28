package com.example.simplechef.ui.login;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.simplechef.R;

public class LoginActivity extends AppCompatActivity {

    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.fragmentContainer);
        setupViewPager(viewPager);

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
