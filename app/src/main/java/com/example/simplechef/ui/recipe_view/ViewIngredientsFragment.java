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

    private LinearLayout vertical;
    private ImageView imageViewIngredientImage;
    private TextView textViewIngredientName;
    private TextView textViewIngredientQuantitiy;

    public static ViewIngredientsFragment newInstance() {
        ViewIngredientsFragment fragment = new ViewIngredientsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_ingredients, container, false);

        imageViewIngredientImage = view.findViewById(R.id.imageViewIngredientImage);
        textViewIngredientName = view.findViewById(R.id.textViewIngredientName);
        textViewIngredientQuantitiy = view.findViewById(R.id.textViewIngredientQuantity);



        Bundle bundle = getArguments();
        for (int i = 0; bundle.getString("IngredientsName" + i) != null; i++){
            Glide.with(getContext())
                    .load(bundle.getString("IngredientsImage" + i))
                    .centerInside()
                    .into(imageViewIngredientImage);
            textViewIngredientQuantitiy.setText(bundle.getString("IngredientsQuantity" + i));
            textViewIngredientName.setText(bundle.getString("IngredientsName" + i));

        }

        // Inflate the layout for this fragment
        return view;
    }
}
