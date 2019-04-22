package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simplechef.R;

import org.w3c.dom.Text;

public class ViewStepsFragment extends Fragment {
        TextView output;
    public static ViewStepsFragment newInstance() {
        ViewStepsFragment fragment = new ViewStepsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_steps, container, false);
        output = (TextView) view.findViewById(R.id.textViewSteps);
        Bundle  bundle = getArguments();
        output.setText(bundle.getString("steps"));

        // Inflate the layout for this fragment
        return view;
    }
}
