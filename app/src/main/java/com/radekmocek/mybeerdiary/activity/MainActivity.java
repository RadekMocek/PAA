package com.radekmocek.mybeerdiary.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.adapter.PubVisitsAdapter;
import com.radekmocek.mybeerdiary.fragment.AddPubVisitDialogFragment;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;
    private PubVisitsAdapter adPubVisits;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Toolbar) findViewById(R.id.toolbar)).setTitle("Pub visits");

        db = new DatabaseManager(this);
        adPubVisits = new PubVisitsAdapter(db);
        layoutManager = new LinearLayoutManager(this);

        RecyclerView rvPubVisits = findViewById(R.id.recyclerView_pubVisits);
        rvPubVisits.setAdapter(adPubVisits);
        rvPubVisits.setLayoutManager(layoutManager);

        findViewById(R.id.fab_addPubVisit).setOnClickListener(v -> {
            AddPubVisitDialogFragment.newInstance().show(getSupportFragmentManager(), AddPubVisitDialogFragment.TAG);
        });
    }

    public void addPubVisit(String name) {
        adPubVisits.AddPubVisit(name);
        layoutManager.scrollToPosition(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
