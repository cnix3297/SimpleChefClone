package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.google.api.LogDescriptor;

public class ViewIngredientsFragment extends Fragment {
    LinearLayout vertical;
    public static ViewIngredientsFragment newInstance() {
        ViewIngredientsFragment fragment = new ViewIngredientsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_ingredients, container, false);
        vertical = (LinearLayout) view.findViewById(R.id.LinearLayoutViewRecipeVertical);

        Bundle bundle = getArguments();
        for (int i = 0; bundle.getString("IngredientsName" + i) != null; i++){
            LinearLayout horizontal = new LinearLayout(getContext());
            horizontal.setOrientation(LinearLayout.HORIZONTAL);
            horizontal.setPadding(10,10,10,10);
            TextView quantity = new TextView(getContext());
            quantity.setPadding(10,10,10,10);
            TextView name = new TextView(getContext());
            name.setPadding(10,10,10,10);
            ImageView picture = new ImageView(getContext());
            picture.setPadding(10,10,10,10);
            picture.setMaxWidth(20);
            picture.setMaxHeight(20);
            Log.d("Image", "onCreateView: " + bundle.getString("IngredientsImage" + i));
            Log.d("Name", "onCreateView: " + bundle.getString("IngredientsName" + i));
            Glide.with(getContext())
                    .load(bundle.getString("IngredientsImage" + i))
                    .centerInside()
                    .into(picture);
            quantity.setText(bundle.getString("IngredientsQuantity" + i));
            quantity.setTextSize(20);
            name.setText(bundle.getString("IngredientsName" + i));
            name.setTextSize(20);
            horizontal.addView(picture);
            horizontal.addView(name);
            horizontal.addView(quantity);

            vertical.addView(horizontal);
        }
        // Inflate the layout for this fragment
        return view;
    }
}
