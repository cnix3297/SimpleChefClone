package com.example.simplechef.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemText;

        public ListViewHolder(View view) {
            super(view);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
