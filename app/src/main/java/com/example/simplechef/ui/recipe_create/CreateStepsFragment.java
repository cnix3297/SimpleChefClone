package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.ui.Recipe;


public class CreateStepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    EditText input;
    LinearLayout outPut;
    Button add, delete, next;
    int count = 1;
    TextView textToolbar;
    Recipe recipe = new Recipe();
    onRecipeChangeDirectionListener onRecipeChangeDirectionListenerVar;



    public CreateStepsFragment() {
        // Required empty public constructor
    }
    public interface onRecipeChangeDirectionListener
    {
        public void onRecipeChangeDirectionListenerMethod(Recipe recipe);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_create_steps, container, false);

        // Inflate the layout for this fragment
        getObject(view);


        textToolbar = ((CreateRecipeActivity)getActivity()).findViewById(R.id.toolbar_title);
        textToolbar.setText("Info");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().equals("")) {
                    //Create Button
                    final ImageButton removeBtn = new ImageButton(getActivity());
                    removeBtn.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT));
                    removeBtn.setBackgroundResource(R.drawable.trashcan);
                    removeBtn.setId(count-1);
                    removeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (outPut.getChildCount() == 0) {

                            }
                            else if ((removeBtn.getId()) > outPut.getChildCount() - 1 ){
                                outPut.removeViewAt(0);
                                if(outPut.getChildCount() == 0){
                                    count = 1;
                                }
                            }
                            else {
                                outPut.removeViewAt(removeBtn.getId());
                                if(outPut.getChildCount() == 0){
                                    count = 1;
                                }

                            }

                        }
                    });
                    //Set Directions? Eventually move from recipe to recipeclass
                    recipe.setDirections(input.getText().toString(), count);
                    onRecipeChangeDirectionListenerVar.onRecipeChangeDirectionListenerMethod(recipe);

                    //Add Variables to subview
                    LinearLayout aView = new LinearLayout(getActivity());
                    aView.setPadding(10,10,20,10);

                    TextView t = new TextView(getActivity());
                    t.setTextSize(23);
                    t.setText(count + " " + recipe.getDirectionToString(count - 1));
                    t.setPadding(1, 10, 1, 10);
                    aView.addView(t);
                    aView.addView(removeBtn);

                    //add subview to view
                    outPut.setGravity(Gravity.CENTER);
                    outPut.addView(aView);


                    count++;
                    input.setText("");
                }
            }
        });


        return view;

    }

    public void getObject(View view){
        input = (EditText) view.findViewById(R.id.fragment_recipe_create_TextBox_input);
        outPut = (LinearLayout) view.findViewById(R.id.fragment_recipe_create_direction_linear_output);
        add = (Button) view.findViewById(R.id.fragment_recipe_create_direction_Button_add);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try{
            onRecipeChangeDirectionListenerVar = (onRecipeChangeDirectionListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+ "must override onRecipeChange");
        }
    }

}
