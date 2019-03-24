package com.example.simplechef.ui.recipe_create;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simplechef.R;
import com.example.simplechef.ui.Recipe;


public class StepsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    EditText input;
    LinearLayout outPut;
    Button add, delete, next;
    int count = 1;
    TextView textToolbar;
    Recipe recipe = new Recipe();
    onRecipeChangeDirectionListener onRecipeChangeDirectionListenerVar;



    public StepsFragment() {
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
        textToolbar.setText("Add Directions");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.getText().toString().equals("")){
                recipe.setDirections(input.getText().toString(),count);
                onRecipeChangeDirectionListenerVar.onRecipeChangeDirectionListenerMethod(recipe);
                TextView t = new TextView(getActivity());
                t.setText(count + ") " + recipe.getDirectionToString(count -1));
                t.setPadding(1,10,1,10);
                t.setTextSize(15);
                outPut.addView(t);
                count++;
                input.setText("");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 1) {
                    recipe.deleteLastDirection();
                    outPut.removeViewAt(count - 2);
                    onRecipeChangeDirectionListenerVar.onRecipeChangeDirectionListenerMethod(recipe);
                    count--;
                }
            }
        });

        return view;

    }

    public void getObject(View view){
        input = (EditText) view.findViewById(R.id.fragment_recipe_create_TextBox_input);
        outPut = (LinearLayout) view.findViewById(R.id.fragment_recipe_create_direction_linear_output);
        add = (Button) view.findViewById(R.id.fragment_recipe_create_direction_Button_add);
        delete = (Button) view.findViewById(R.id.fragment_recipe_create_direction_Button_delete);

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
