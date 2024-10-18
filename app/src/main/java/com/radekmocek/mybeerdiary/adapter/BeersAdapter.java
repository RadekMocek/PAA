package com.radekmocek.mybeerdiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.model.Beer;

import java.util.ArrayList;
import java.util.List;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.ViewHolder> {

    private final List<Beer> collection;

    public BeersAdapter() {
        collection = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beer, parent, false);
        return new BeersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Beer item = collection.get(position);

        // Set item views based on your views and data model
        holder.breweryName.setText(item.getBreweryName());

        // Events
        // ...
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView breweryName;

        public ViewHolder(View itemView) {
            super(itemView);

            breweryName = itemView.findViewById(R.id.beer_breweryName);
        }
    }

    // Data manipulation functions
    public void addBeer(Beer b) {
        int len = collection.size();
        collection.add(b);
        notifyItemInserted(len);
    }
}
