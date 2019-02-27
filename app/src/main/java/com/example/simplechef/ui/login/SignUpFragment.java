package com.example.simplechef.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.ui.account.AccountActivity;
import com.example.simplechef.util.GlideApp;

public class SignUpFragment extends Fragment {

    private Button btn;
    private ImageView imageViewBackground;
    private FragmentActivity myContext;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.signup_fragment,container,false);
        myContext= (FragmentActivity) getActivity();

        //Toolbar setup
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sign Up");

        imageViewBackground = (ImageView)view.findViewById(R.id.imageViewBackground);
        setupImages(view);

        //Back Button to Login Screen
        btn = (Button)view.findViewById(R.id.buttonBack);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "SignUp", Toast.LENGTH_SHORT).show();
                ((LoginActivity)getActivity()).setViewPager(0);

            }
        });
        return view;
    }

    public void setupImages(View view) {
        // Glide handles auto-scaling images down to proper resolution

        GlideApp
                .with(view)
                .load(R.drawable.signup_background)
                .centerCrop()
                .into(imageViewBackground);
    }

}
