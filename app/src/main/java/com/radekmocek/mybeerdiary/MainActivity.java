package com.radekmocek.mybeerdiary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radekmocek.mybeerdiary.adapter.PubVisitsAdapter;
import com.radekmocek.mybeerdiary.model.PubVisit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Toolbar) findViewById(R.id.toolbar)).setTitle("Pub visits");

        List<PubVisit> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PubVisit p = new PubVisit();
            p.setPubName("Pub " + i);
            list.add(p);
        }

        RecyclerView rvPubVisits = findViewById(R.id.recyclerView_pubVisits);
        PubVisitsAdapter adPubVisits = new PubVisitsAdapter(list);
        rvPubVisits.setAdapter(adPubVisits);
        rvPubVisits.setLayoutManager(new LinearLayoutManager(this));
    }
}
