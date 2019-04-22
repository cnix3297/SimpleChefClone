package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;

public class ViewStepsFragment extends Fragment {
    private RecipeClass recipeClass;
    private TextView stepsTextView;
    public static ViewStepsFragment newInstance(RecipeClass recipe) {
        ViewStepsFragment fragment = new ViewStepsFragment();
        fragment.recipeClass = recipe;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_steps, container, false);
        stepsTextView = (TextView) view.findViewById(R.id.textViewSteps);
        stepsTextView.setText(recipeClass.getSteps());

        // Inflate the layout for this fragment
        return view;
    }
}
