package com.example.houselistingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity { // Change AppCompatActivity → BaseActivity

    private RecyclerView recyclerView;
    private ListingAdapter adapter;
    private List<ListingModel> listingList;
    private HouseListingDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation(); // Call this to set up the navigation bar
        getCurrentActivity();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listingList = new ArrayList<>();
        adapter = new ListingAdapter(this, listingList);
        recyclerView.setAdapter(adapter);

        dbHelper = new HouseListingDb(this);

        loadListings(); // Load listings from database

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddListingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListings(); // Reload data when returning to MainActivity
    }

    private void loadListings() {
        listingList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MyListings", null);

        if (cursor.moveToFirst()) {
            do {
                ListingModel listing = new ListingModel(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // Name
                        cursor.getString(2), // Address
                        cursor.getString(3), // Description
                        cursor.getInt(4), // Square Meters
                        cursor.getDouble(5), // Price
                        cursor.getString(6), // Phone
                        cursor.getInt(7)  // Year Built
                );
                listingList.add(listing);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
