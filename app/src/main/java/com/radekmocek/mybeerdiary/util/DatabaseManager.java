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
        List<PubVisit> collection = new ArrayList<>();
        db.beginTransaction();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_VISITS, null, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PubVisit p = new PubVisit();
                    p.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                    p.setPubName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                    p.setTimestamp(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_TIMESTAMP)));
                    p.setTotalBeers(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TOTAL_BEERS)));
                    p.setTotalCost(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TOTAL_COST)));
                    collection.add(p);
                } while (cursor.moveToNext());
            }
        } finally {
            db.endTransaction();
        }
        return collection;
    }

    public long addPubVisit(PubVisit p) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_NAME, p.getPubName());
        cv.put(DatabaseHelper.COL_TIMESTAMP, p.getTimestamp());
        cv.put(DatabaseHelper.COL_TOTAL_BEERS, p.getTotalBeers());
        cv.put(DatabaseHelper.COL_TOTAL_COST, p.getTotalCost());
        return db.insert(DatabaseHelper.TABLE_VISITS, null, cv);
    }

    public void editPubVisitPubName(long id, String name) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_NAME, name);
        db.update(DatabaseHelper.TABLE_VISITS, cv, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deletePubVisit(long id) {
        db.delete(DatabaseHelper.TABLE_VISITS, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Beers
    @SuppressLint("Range")
    public List<Beer> GetAllBeers(long pubVisitID) {
        List<Beer> collection = new ArrayList<>();
        db.beginTransaction();
        try (Cursor cursor = db.query(DatabaseHelper.TABLE_BEERS, null, DatabaseHelper.COL_VISIT_ID + " = ?", new String[]{String.valueOf(pubVisitID)}, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Beer b = new Beer();
                    b.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                    b.setPubVisitID(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_VISIT_ID)));
                    b.setBreweryName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
                    b.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_DESCRIPTION)));
                    b.setTimestamp(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_TIMESTAMP)));
                    b.setDecilitres(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_DECILITRES)));
                    b.setEPM(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COL_EPM)));
                    b.setABV(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COL_ABV)));
                    b.setPrice(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_PRICE)));
                    collection.add(b);
                } while (cursor.moveToNext());
            }
        } finally {
            db.endTransaction();
        }
        return collection;
    }

    public long addBeer(Beer b, PubVisit p) {
        // Prepare Beer
        ContentValues cvB = new ContentValues();
        cvB.put(DatabaseHelper.COL_VISIT_ID, b.getPubVisitID());
        cvB.put(DatabaseHelper.COL_NAME, b.getBreweryName());
        cvB.put(DatabaseHelper.COL_DESCRIPTION, b.getDescription());
        cvB.put(DatabaseHelper.COL_TIMESTAMP, b.getTimestamp());
        cvB.put(DatabaseHelper.COL_DECILITRES, b.getDecilitres());
        cvB.put(DatabaseHelper.COL_EPM, b.getEPM());
        cvB.put(DatabaseHelper.COL_ABV, b.getABV());
        cvB.put(DatabaseHelper.COL_PRICE, b.getPrice());
        // Edit totals in TABLE_VISITS
        int newTotalBeers = p.getTotalBeers() + 1;
        int newTotalCost = p.getTotalCost() + b.getPrice();
        ContentValues cvPV = new ContentValues();
        cvPV.put(DatabaseHelper.COL_TOTAL_BEERS, newTotalBeers);
        cvPV.put(DatabaseHelper.COL_TOTAL_COST, newTotalCost);
        db.update(DatabaseHelper.TABLE_VISITS, cvPV, DatabaseHelper.COL_ID + " = ?", new String[]{String.valueOf(p.getId())});
        p.setTotalBeers(newTotalBeers);
        p.setTotalCost(newTotalCost);
        // Add Beer to TABLE_BEERS
        return db.insert(DatabaseHelper.TABLE_BEERS, null, cvB);
    }
}
