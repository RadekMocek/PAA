package com.radekmocek.mybeerdiary.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.radekmocek.mybeerdiary.model.Beer;
import com.radekmocek.mybeerdiary.model.PubVisit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private final DatabaseHelper dbh;
    private final SQLiteDatabase db;

    public DatabaseManager(Context context) {
        dbh = new DatabaseHelper(context);
        db = dbh.getReadableDatabase();
    }

    public void close() {
        dbh.close();
    }

    // PubVisits
    @SuppressLint("Range")
    public List<PubVisit> GetAllPubVisits() {
        List<PubVisit> taskList = new ArrayList<>();
        db.beginTransaction();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_VISITS, null, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PubVisit p = new PubVisit();
                    p.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                    p.setPubName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                    p.setTimestamp(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_TIMESTAMP)));
                    p.setTotalBeers(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TOTAL_BEERS)));
                    p.setTotalCost(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TOTAL_COST)));
                    taskList.add(p);
                } while (cursor.moveToNext());
            }
        } finally {
            db.endTransaction();
        }
        return taskList;
    }

    public void addPubVisit(PubVisit p) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_NAME, p.getPubName());
        cv.put(DatabaseHelper.COL_TIMESTAMP, p.getTimestamp());
        cv.put(DatabaseHelper.COL_TOTAL_BEERS, p.getTotalBeers());
        cv.put(DatabaseHelper.COL_TOTAL_COST, p.getTotalCost());
        db.insert(DatabaseHelper.TABLE_VISITS, null, cv);
    }

    public void editPubVisitPubName(int id, String name) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_NAME, name);
        db.update(DatabaseHelper.TABLE_VISITS, cv, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deletePubVisit(int id) {
        db.delete(DatabaseHelper.TABLE_VISITS, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void addBeer(Beer b) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_VISIT_ID, b.getPubVisitID());
        cv.put(DatabaseHelper.COL_NAME, b.getBreweryName());
        cv.put(DatabaseHelper.COL_DESCRIPTION, b.getDescription());
        cv.put(DatabaseHelper.COL_TIMESTAMP, b.getTimestamp());
        cv.put(DatabaseHelper.COL_DECILITRES, b.getDecilitres());
        cv.put(DatabaseHelper.COL_EPM, b.getEPM());
        cv.put(DatabaseHelper.COL_ABV, b.getABV());
        cv.put(DatabaseHelper.COL_PRICE, b.getPrice());
        db.insert(DatabaseHelper.TABLE_BEERS, null, cv);
    }
}
