package com.example.simplechef.ui.recipe_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
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

        //imageViewIngredientImage = view.findViewById(R.id.imageViewIngredientImage);
        //textViewIngredientName = view.findViewById(R.id.textViewIngredientName);
        //textViewIngredientQuantitiy = view.findViewById(R.id.textViewIngredientQuantity);
        vertical = (LinearLayout) view.findViewById(R.id.LinearLayoutViewRecipeVertical);
        //vertical.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

/*
        for (int i = 0; bundle.getString("IngredientsName" + i) != null; i++){
            Glide.with(getContext())
                    .load(bundle.getString("IngredientsImage" + i))
                    .centerInside()
                    .into(imageViewIngredientImage);
            textViewIngredientQuantitiy.setText(bundle.getString("IngredientsQuantity" + i));
            textViewIngredientName.setText(bundle.getString("IngredientsName" + i));

        }
*/
        Bundle bundle = getArguments();
        for (int i = 0; bundle.getString("IngredientsName" + i) != null; i++){
            LinearLayout horizontal = new LinearLayout(getContext());
            horizontal.setOrientation(LinearLayout.HORIZONTAL);
            //horizontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView quantity = new TextView(getContext());
            quantity.setPadding(10,10,10,10);
            TextView name = new TextView(getContext());
            name.setPadding(10,10,10,10);
            ImageView picture = new ImageView(getContext());
            picture.setPadding(10,10,10,10);
            picture.setMaxWidth(90);
            picture.setMaxHeight(90);
            Log.d("", "onCreateView: " + bundle.getString("IngredientsImage" + i));
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

            picture.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.15f));
            quantity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            quantity.setGravity(Gravity.END);
            name.setGravity(Gravity.END);

            horizontal.addView(picture);
            horizontal.addView(name);
            horizontal.addView(quantity);

            vertical.addView(horizontal);
        }


        // Inflate the layout for this fragment
        return view;
    }
}
