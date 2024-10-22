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

        ((Toolbar) findViewById(R.id.toolbar)).setTitle(pubVisit.getPubName());

        //db = new DatabaseManager(this);
        layoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        adBeers = new BeersAdapter();

        RecyclerView rvBeers = findViewById(R.id.recyclerView_beers);
        rvBeers.setAdapter(adBeers);
        rvBeers.setLayoutManager(layoutManager);

        findViewById(R.id.fab_addBeer).setOnClickListener(v -> AddBeerDialogFragment.newInstance(pubVisit).show(fragmentManager, AddBeerDialogFragment.TAG));
    }

    public void addBeer(Beer b) {
        adBeers.addBeer(b);
    }
}
