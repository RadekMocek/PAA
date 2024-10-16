package com.radekmocek.mybeerdiary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.util.Conv;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

import java.util.Calendar;
import java.util.List;

// https://guides.codepath.com/android/using-the-recyclerview
public class PubVisitsAdapter extends RecyclerView.Adapter<PubVisitsAdapter.ViewHolder> {

    private final List<PubVisit> collection;
    private final DatabaseManager db;

    public PubVisitsAdapter(DatabaseManager db) {
        this.db = db;
        collection = db.GetAllPubVisits();
        collection.sort(PubVisit.comparator);
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pub_visit, parent, false);
        return new ViewHolder(itemView);
    }

    // Involves populating data into the item through the holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        PubVisit item = collection.get(position);

        // Set item views based on your views and data model
        holder.pubName.setText(item.getPubName());
        holder.timestamp.setText(Conv.longDate2str(item.getTimestamp()));
        holder.totals.setText(item.getTotalBeers() + " piv, " + item.getTotalCost() + " Kč");

        // Events
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
        });
        holder.itemView.setOnLongClickListener(v -> {
            Toast.makeText(v.getContext(), "hold", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return collection.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView pubName;
        public TextView timestamp;
        public TextView totals;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            pubName = itemView.findViewById(R.id.pubVisit_pubName);
            timestamp = itemView.findViewById(R.id.pubVisit_timestamp);
            totals = itemView.findViewById(R.id.pubVisit_totals);
        }
    }

    // Data manipulation functions
    public void AddPubVisit(String name) {
        PubVisit p = new PubVisit();
        p.setPubName(name);
        p.setTimestamp(Calendar.getInstance().getTime().getTime());
        p.setTotalBeers(0);
        p.setTotalCost(0);
        db.AddPubVisit(p);
        collection.add(0, p);
        notifyItemInserted(0);
    }
}
