package com.example.houselistingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HouseListingDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HouseListing.db";
    private static final int DATABASE_VERSION = 1;

    public HouseListingDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MyListings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "address TEXT, " +
                "description TEXT, " +
                "square_meters INTEGER, " +
                "price REAL, " +
                "phone_number TEXT, " +
                "year_built INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MyListings");
        onCreate(db);
    }
}
