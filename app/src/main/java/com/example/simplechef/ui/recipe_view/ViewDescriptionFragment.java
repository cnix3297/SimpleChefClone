package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplechef.R;

public class ViewDescriptionFragment extends Fragment {

    public static ViewDescriptionFragment newInstance() {
        ViewDescriptionFragment fragment = new ViewDescriptionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_description, container, false);


        // Inflate the layout for this fragment
        return view;
    }
}
