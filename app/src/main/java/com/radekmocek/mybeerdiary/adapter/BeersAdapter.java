package com.radekmocek.mybeerdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.fragment.EditBeerDialogFragment;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.util.Conv;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

import java.util.List;

public class BeersAdapter extends RecyclerView.Adapter<BeersAdapter.ViewHolder> {

    private final FragmentManager fragmentManager;
    private final Context context;
    private final List<Beer> collection;

    public BeersAdapter(DatabaseManager db, long pubVisitID, FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;

        collection = db.GetAllBeers(pubVisitID);
        collection.sort(Beer.comparator);
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
        String breweryName = item.getBreweryName();
        holder.breweryName.setText(!breweryName.isEmpty() ? breweryName : "Pivo");

        String description = item.getDescription();
        if (!description.isEmpty()) holder.description.setText(description);
        else holder.description.setVisibility(View.GONE);

        holder.timestamp.setText(Conv.longDate2str(item.getTimestamp()));

        int decilitres = item.getDecilitres();
        holder.decilitres.setText(Conv.nBeerDecilitres2str(decilitres));
        switch (decilitres) {
            case 3:
                holder.decilitresIcon.setImageResource(R.drawable.ico_measure);
                break;
            case 5:
                holder.decilitresIcon.setImageResource(R.drawable.ico_measure_full);
                break;
            default:
                holder.decilitresIcon.setImageResource(R.drawable.ico_measure_slider);
                break;
        }

        holder.EPM.setText(String.valueOf(item.getEPM()) + "°");
        holder.ABV.setText(String.valueOf(item.getABV()) + " %");
        holder.price.setText(String.valueOf(item.getPrice()) + " Kč");

        // Events
        holder.itemView.setOnLongClickListener(v -> {
            EditBeerDialogFragment.newInstance().show(fragmentManager, EditBeerDialogFragment.TAG);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView breweryName;
        public TextView description;
        public TextView timestamp;
        public TextView decilitres;
        public TextView EPM;
        public TextView ABV;
        public TextView price;
        public ImageView decilitresIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            breweryName = itemView.findViewById(R.id.beer_breweryName);
            description = itemView.findViewById(R.id.beer_description);
            timestamp = itemView.findViewById(R.id.beer_timestamp);
            decilitres = itemView.findViewById(R.id.beer_decilitres);
            EPM = itemView.findViewById(R.id.beer_EPM);
            ABV = itemView.findViewById(R.id.beer_ABV);
            price = itemView.findViewById(R.id.beer_price);
            decilitresIcon = itemView.findViewById(R.id.beer_ico_decilitres);
        }
    }

    // Data manipulation functions
    public void addBeer(Beer b) {
        int len = collection.size();
        collection.add(b);
        notifyItemInserted(len);
    }
}
