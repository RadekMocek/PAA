package com.radekmocek.mybeerdiary.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.adapter.BeersAdapter;
import com.radekmocek.mybeerdiary.fragment.AddBeerDialogFragment;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.model.PubVisitInfoCrate;
import com.radekmocek.mybeerdiary.util.Calc;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

public class BeersActivity extends AppCompatActivity {

    private DatabaseManager db;
    private LinearLayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private BeersAdapter adBeers;

    private long pubVisitID;
    private String pubVisitName;
    private int pubVisitRvPos;
    private int totalCost;

    private TextView textViewNBeers;
    private TextView textViewTotalCost;
    private TextView textViewPermille;
    private TextView textViewSober;
    private int userWeight;
    private boolean isUserMale;

    private FloatingActionButton fabAddBeer;

    boolean hasAnythingChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            pubVisitID = args.getLong(Const.INTENT_EXTRAS_KEY_PUB_VISIT_ID);
            pubVisitName = args.getString(Const.INTENT_EXTRAS_KEY_PUB_VISIT_NAME);
            pubVisitRvPos = args.getInt(Const.INTENT_EXTRAS_KEY_PUB_VISIT_RV_POS);
        }

        Toolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setTitle(pubVisitName);
        topAppBar.setNavigationOnClickListener(v -> finish());

        db = new DatabaseManager(this);
        layoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        adBeers = new BeersAdapter(db, pubVisitID, fragmentManager);

        textViewNBeers = findViewById(R.id.BAB_TextView_nBeers);
        textViewTotalCost = findViewById(R.id.BAB_TextView_totalCost);
        textViewPermille = findViewById(R.id.BAB_TextView_permille);
        textViewSober = findViewById(R.id.BAB_TextView_sober);
        SharedPreferences sharedPreferences = getSharedPreferences(Const.PREFS_NAME, Context.MODE_PRIVATE);
        userWeight = sharedPreferences.getInt(Const.PREFS_KEY_WEIGHT, Const.PREFS_DEFAULT_WEIGHT);
        isUserMale = sharedPreferences.getBoolean(Const.PREFS_KEY_IS_MALE, Const.PREFS_DEFAULT_IS_MALE);

        RecyclerView rvBeers = findViewById(R.id.recyclerView_beers);
        rvBeers.setAdapter(adBeers);
        rvBeers.setLayoutManager(layoutManager);

        fabAddBeer = findViewById(R.id.fab_addBeer);
        fabAddBeer.setOnClickListener(v -> {
            fabAddBeer.setEnabled(false);
            fabAddBeer.setImageResource(R.drawable.ico_hourglass);
            AddBeerDialogFragment.newInstanceAddMode(pubVisitID).show(fragmentManager, AddBeerDialogFragment.TAG);
        });

        layoutManager.scrollToPosition(adBeers.getItemCount() - 1);

        updateBottomAppBarInfoAndDatabaseTotals(false);
        hasAnythingChanged = false; // Must be called after `updateBottomAppBarInfo()` cause that sets it to true :—)
    }

    public void enableFAB() {
        fabAddBeer.setEnabled(true);
        fabAddBeer.setImageResource(R.drawable.ico_add);
    }

    public void addBeer(Beer b) {
        long id = db.addBeer(b);
        b.setId(id);
        int rvPos = adBeers.addBeer(b);
        layoutManager.scrollToPosition(rvPos);
        updateBottomAppBarInfoAndDatabaseTotals(true);
    }

    public void editBeer(long id, Beer newB, int rvPos) {
        db.editBeer(id, newB);
        adBeers.editBeer(newB, rvPos);
        updateBottomAppBarInfoAndDatabaseTotals(true);
    }

    public void deleteBeer(Beer b, int rvPos) {
        db.deleteBeer(b);
        adBeers.deleteBeer(rvPos);
        updateBottomAppBarInfoAndDatabaseTotals(true);
    }

    private void updateBottomAppBarInfoAndDatabaseTotals(boolean updateDatabaseTotals) {
        int totalBeers = adBeers.getItemCount();

        textViewNBeers.setText(totalBeers + " piv");
        PubVisitInfoCrate crate = Calc.getPubVisitInfo(adBeers, userWeight, isUserMale);
        textViewTotalCost.setText(crate.totalCost + " Kč");
        textViewPermille.setText(crate.permille + " promile");
        textViewSober.setText(crate.sober + " po posledním pivu");
        totalCost = crate.totalCost;
        hasAnythingChanged = true;

        if (updateDatabaseTotals) db.editPubVisitTotals(pubVisitID, totalBeers, totalCost);
    }

    @Override
    protected void onPause() {
        super.onPause();
        int totalBeers = adBeers.getItemCount();

        SharedPreferences sharedPreferences2 = getSharedPreferences(Const.PREFS2_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putBoolean(Const.PREFS2_KEY_IS_UPDATE_NECESSARY, hasAnythingChanged);
        editor2.putInt(Const.PREFS2_KEY_PUB_VISIT_RV_POS, pubVisitRvPos);
        editor2.putInt(Const.PREFS2_KEY_TOTAL_BEERS, totalBeers);
        editor2.putInt(Const.PREFS2_KEY_TOTAL_COST, totalCost);
        editor2.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
