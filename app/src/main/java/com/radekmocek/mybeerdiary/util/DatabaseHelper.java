package com.radekmocek.mybeerdiary.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "beerDB";
    private static final int DB_VERSION = 2;

    // Tables
    public static final String TABLE_VISITS = "pub_visits";

    // Common attributes
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_TIMESTAMP = "timestamp";

    // TABLE_VISITS attributes
    public static final String COL_TOTAL_BEERS = "total_beers";
    public static final String COL_TOTAL_COST = "total_cost";

    // Create table statements
    private static final String CREATE_TABLE_VISITS = "CREATE TABLE " + TABLE_VISITS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, "
            + COL_TIMESTAMP + " INTEGER, "
            + COL_TOTAL_BEERS + " INTEGER, "
            + COL_TOTAL_COST + " INTEGER)";

    // F
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VISITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITS);
        onCreate(db);
    }
}
