package com.radekmocek.mybeerdiary.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.adapter.BeersAdapter;
import com.radekmocek.mybeerdiary.fragment.AddBeerDialogFragment;
import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

public class BeersActivity extends AppCompatActivity {

    private DatabaseManager db;
    private LinearLayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private BeersAdapter adBeers;

    private PubVisit pubVisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            pubVisit = (PubVisit) args.getSerializable("pubVisit");
        }

        ((Toolbar) findViewById(R.id.topAppBar)).setTitle(pubVisit.getPubName());

        db = new DatabaseManager(this);
        layoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        adBeers = new BeersAdapter(db, pubVisit.getId(), fragmentManager, this);

        RecyclerView rvBeers = findViewById(R.id.recyclerView_beers);
        rvBeers.setAdapter(adBeers);
        rvBeers.setLayoutManager(layoutManager);

        findViewById(R.id.fab_addBeer).setOnClickListener(v -> AddBeerDialogFragment.newInstanceAddMode(pubVisit).show(fragmentManager, AddBeerDialogFragment.TAG));
    }

    public void addBeer(Beer b) {
        long id = db.addBeer(b, pubVisit);
        b.setId(id);
        int rvPos = adBeers.addBeer(b);
        layoutManager.scrollToPosition(rvPos);
    }

    public void editBeer(long id, Beer newB, int priceChange, int rvPos) {
        db.editBeer(id, newB, priceChange, pubVisit);
        adBeers.editBeer(newB, rvPos);
    }

    public void deleteBeer(Beer b, int rvPos) {
        db.deleteBeer(b, pubVisit);
        adBeers.deleteBeer(rvPos);
    }
}
