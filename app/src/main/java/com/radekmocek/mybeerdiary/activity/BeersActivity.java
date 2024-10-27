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
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.model.PubVisitInfoCrate;
import com.radekmocek.mybeerdiary.util.Calc;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

public class BeersActivity extends AppCompatActivity {

    private DatabaseManager db;
    private LinearLayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private BeersAdapter adBeers;

    private PubVisit pubVisit;

    private TextView textViewNBeers;
    private TextView textViewTotalPrice;
    private TextView textViewPermille;
    private TextView textViewSober;
    private int userWeight;
    private boolean isUserMale;

    private FloatingActionButton fabAddBeer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            pubVisit = (PubVisit) args.getSerializable("pubVisit");
        }

        Toolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setTitle(pubVisit.getPubName());
        topAppBar.setNavigationOnClickListener(v -> finish());

        db = new DatabaseManager(this);
        layoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        adBeers = new BeersAdapter(db, pubVisit.getId(), fragmentManager);

        textViewNBeers = findViewById(R.id.BAB_TextView_nBeers);
        textViewTotalPrice = findViewById(R.id.BAB_TextView_totalPrice);
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
            AddBeerDialogFragment.newInstanceAddMode(pubVisit).show(fragmentManager, AddBeerDialogFragment.TAG);
        });

        layoutManager.scrollToPosition(adBeers.getItemCount() - 1);

        updateBottomAppBarInfo();
    }

    public void enableFAB() {
        fabAddBeer.setEnabled(true);
        fabAddBeer.setImageResource(R.drawable.ico_add);
    }

    public void addBeer(Beer b) {
        long id = db.addBeer(b, pubVisit);
        b.setId(id);
        int rvPos = adBeers.addBeer(b);
        layoutManager.scrollToPosition(rvPos);
        updateBottomAppBarInfo();
    }

    public void editBeer(long id, Beer newB, int priceChange, int rvPos) {
        db.editBeer(id, newB, priceChange, pubVisit);
        adBeers.editBeer(newB, rvPos);
        updateBottomAppBarInfo();
    }

    public void deleteBeer(Beer b, int rvPos) {
        db.deleteBeer(b, pubVisit);
        adBeers.deleteBeer(rvPos);
        updateBottomAppBarInfo();
    }

    private void updateBottomAppBarInfo() {
        textViewNBeers.setText(adBeers.getItemCount() + " piv");
        PubVisitInfoCrate crate = Calc.getPubVisitInfo(adBeers, userWeight, isUserMale);
        textViewTotalPrice.setText(crate.totalCost + " Kč");
        textViewPermille.setText(crate.permille + " promile");
        textViewSober.setText(crate.sober + " po posledním pivu");
    }
}
