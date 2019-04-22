package com.example.simplechef.ui.recipe_view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.simplechef.R;
import com.example.simplechef.RecipeClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ViewIngredientsFragment extends Fragment {
    private RecipeClass recipe;
    LinearLayout verticalIngreidientList;
    public static ViewIngredientsFragment newInstance(RecipeClass recipeClass) {
        ViewIngredientsFragment fragment = new ViewIngredientsFragment();
        fragment.recipe = recipeClass;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_view_ingredients, container, false);
        verticalIngreidientList = (LinearLayout) view.findViewById(R.id.VerticalLinearIngredientsListThisWasCoded8BeersDeep_SmileyFace);
        for (int i = 0; i < recipe.getIngredientList().size(); i++){
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
            Glide.with(getContext())
                    .load(recipe.getIngredientAtIndex(i).getImage())
                    .centerInside()
                    .into(picture);

            quantity.setText(recipe.getIngredientAtIndex(i).getQuantity());
            quantity.setTextSize(20);
            name.setText(recipe.getIngredientAtIndex(i).getName());
            name.setTextSize(20);
            horizontal.addView(quantity);
            horizontal.addView(name);
            /*verticalIngreidientList.addView(horizontal);*/
            horizontal.addView(picture);
            verticalIngreidientList.addView(horizontal);


        }


        // Inflate the layout for this fragment
        return view;
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;

        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            Log.d("", "onPostExecute: " + "task completed");
        }
    }
}
