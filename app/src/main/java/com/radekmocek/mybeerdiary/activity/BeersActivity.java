package com.radekmocek.mybeerdiary.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.fragment.AddBeerDialogFragment;
import com.radekmocek.mybeerdiary.model.PubVisit;

public class BeersActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

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

        fragmentManager = getSupportFragmentManager();

        findViewById(R.id.fab_addBeer).setOnClickListener(v -> AddBeerDialogFragment.newInstance().show(fragmentManager, AddBeerDialogFragment.TAG));
    }
}
