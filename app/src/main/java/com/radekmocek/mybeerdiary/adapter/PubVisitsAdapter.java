package com.radekmocek.mybeerdiary.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.activity.MainActivity;
import com.radekmocek.mybeerdiary.fragment.EditPubVisitDialogFragment;
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.Conv;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

import java.util.List;

// https://guides.codepath.com/android/using-the-recyclerview
public class PubVisitsAdapter extends RecyclerView.Adapter<PubVisitsAdapter.ViewHolder> {

    private final MainActivity mainActivity;
    private final FragmentManager fragmentManager;
    private final List<PubVisit> collection;

    public PubVisitsAdapter(MainActivity mainActivity, DatabaseManager db, FragmentManager fragmentManager) {
        this.mainActivity = mainActivity;
        this.fragmentManager = fragmentManager;

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
        //holder.totals.setText(item.getTotalBeers() + " piv, " + item.getTotalCost() + " Kč");
        holder.totals.setText(holder.itemView.getResources().getString(R.string.itemPubVisit_totals, item.getTotalBeers(), item.getTotalCost()));

        // Events
        holder.itemView.setOnClickListener(v -> mainActivity.changeToBeersActivity(item, position));
        holder.itemView.setOnLongClickListener(v -> {
            EditPubVisitDialogFragment.newInstance(item, position).show(fragmentManager, EditPubVisitDialogFragment.TAG);
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

    // https://stackoverflow.com/a/52718361
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // https://stackoverflow.com/a/58106787
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void handleReturnFromBeersActivity(Context context) {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(Const.PREFS2_NAME, Context.MODE_PRIVATE);
        boolean isUpdateNecessary = sharedPreferences2.getBoolean(Const.PREFS2_KEY_IS_UPDATE_NECESSARY, false);
        if (isUpdateNecessary) {
            int rvPos = sharedPreferences2.getInt(Const.PREFS2_KEY_PUB_VISIT_RV_POS, -1);
            if (rvPos >= 0 && rvPos < collection.size()) {
                PubVisit p = collection.get(rvPos);
                p.setTotalBeers(sharedPreferences2.getInt(Const.PREFS2_KEY_TOTAL_BEERS, p.getTotalBeers()));
                p.setTotalCost(sharedPreferences2.getInt(Const.PREFS2_KEY_TOTAL_COST, p.getTotalCost()));
                notifyItemChanged(rvPos);
            }
        }
    }

    // Data manipulation functions
    public void addPubVisit(PubVisit p) {
        collection.add(0, p);
        notifyItemInserted(0);
    }

    public void editPubVisitPubName(String name, int rvPos) {
        collection.get(rvPos).setPubName(name);
        notifyItemChanged(rvPos);
    }

    public void deletePubVisit(int rvPos) {
        collection.remove(rvPos);
        notifyItemRemoved(rvPos);
    }
}
