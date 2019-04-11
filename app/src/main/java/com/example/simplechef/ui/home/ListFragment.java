package com.example.simplechef.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simplechef.R;
import com.example.simplechef.ui.Recipe;
import com.example.simplechef.ui.recipe_view.ViewRecipeActivity;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private ArrayList<Recipe> mRecipeList;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static ListFragment newInstance(ArrayList<Recipe> list) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        //TODO FIX this
        //args.putParcelable("list", list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //View To Return
        View view = inflater.inflate(R.layout.fragment_home_recipe_list, container, false);


        // TODO:  Remove later, for testing purposes
        mRecipeList = new ArrayList<>();
        mRecipeList.add(new Recipe("Recipe1"));
        mRecipeList.add(new Recipe("Recipe2"));
        mRecipeList.add(new Recipe("Recipe3"));
        mRecipeList.add(new Recipe("Recipe4"));
        mRecipeList.add(new Recipe("Recipe5"));
        mRecipeList.add(new Recipe("Recipe6"));


        recyclerView = view.findViewById(R.id.recyclerViewList);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        listAdapter = new ListAdapter(mRecipeList);
        recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO fix later - go to item at position!
                Intent intent = new Intent(getActivity(), ViewRecipeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
