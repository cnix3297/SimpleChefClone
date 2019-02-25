package com.example.simplechef.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class SignUpFragment extends Fragment {

    private Button btn;
    private ImageView imageViewSignUpBackground;
    private TextView textViewBirthday;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.signup_fragment,container,false);
        myContext= (FragmentActivity) getActivity();

        //Set Up Background Image using Glide & form spinners
        imageViewSignUpBackground = (ImageView)view.findViewById(R.id.imageViewSignUpBackground);
        setupImages(view);

        //TextView Listeners
        textViewBirthday = (TextView)view.findViewById(R.id.textViewBirthday);
        textViewBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(myContext.getSupportFragmentManager(), "date picker");

            }
        });

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
        Glide
                .with(view)
                .load(R.drawable.login_background)
                //.fitCenter()
                .centerCrop()
                .placeholder(R.drawable.login_background)
                .into(imageViewSignUpBackground);

    }
}
