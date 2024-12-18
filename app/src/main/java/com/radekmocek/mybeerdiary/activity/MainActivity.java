package com.radekmocek.mybeerdiary.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.R;
import com.radekmocek.mybeerdiary.adapter.PubVisitsAdapter;
import com.radekmocek.mybeerdiary.fragment.AddPubVisitDialogFragment;
import com.radekmocek.mybeerdiary.fragment.SettingsDialogFragment;
import com.radekmocek.mybeerdiary.model.PubVisit;
import com.radekmocek.mybeerdiary.util.Const;
import com.radekmocek.mybeerdiary.util.DatabaseManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager db;
    private LinearLayoutManager layoutManager;
    private FragmentManager fragmentManager;
    private PubVisitsAdapter adPubVisits;

    private boolean isFirstOnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this);
        layoutManager = new LinearLayoutManager(this);
        fragmentManager = getSupportFragmentManager();
        adPubVisits = new PubVisitsAdapter(this, db, fragmentManager);

        RecyclerView rvPubVisits = findViewById(R.id.recyclerView_pubVisits);
        rvPubVisits.setAdapter(adPubVisits);
        rvPubVisits.setLayoutManager(layoutManager);

        findViewById(R.id.fab_addPubVisit).setOnClickListener(v -> AddPubVisitDialogFragment.newInstance().show(fragmentManager, AddPubVisitDialogFragment.TAG));

        findViewById(R.id.iconButtonSettings).setOnClickListener(v -> SettingsDialogFragment.newInstance().show(fragmentManager, SettingsDialogFragment.TAG));

        isFirstOnStart = true;
    }

    public void addPubVisit(String name) {
        PubVisit p = new PubVisit();
        p.setPubName(name);
        p.setTimestamp(Calendar.getInstance().getTime().getTime());
        p.setTotalBeers(0);
        p.setTotalCost(0);
        long id = db.addPubVisit(p);
        p.setId(id);
        adPubVisits.addPubVisit(p);
        layoutManager.scrollToPosition(0);
    }

    public void editPubVisitName(long id, String name, int rvPos) {
        db.editPubVisitPubName(id, name);
        adPubVisits.editPubVisitPubName(name, rvPos);
    }

    public void deletePubVisit(long id, int rvPos) {
        db.deletePubVisit(id);
        adPubVisits.deletePubVisit(rvPos);
    }

    public void changeToBeersActivity(PubVisit p, int pubVisitRvPos) {
        Intent intent = new Intent(this, BeersActivity.class);
        intent.putExtra(Const.INTENT_EXTRAS_KEY_PUB_VISIT_ID, p.getId());
        intent.putExtra(Const.INTENT_EXTRAS_KEY_PUB_VISIT_NAME, p.getPubName());
        intent.putExtra(Const.INTENT_EXTRAS_KEY_PUB_VISIT_RV_POS, pubVisitRvPos);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstOnStart) {
            isFirstOnStart = false;
        } else {
            adPubVisits.handleReturnFromBeersActivity(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
