package com.example.houselistingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    if (!(BaseActivity.this instanceof MainActivity)) {
                        startActivity(new Intent(BaseActivity.this, MainActivity.class));
                    }
                    return true;
                } else if (itemId == R.id.nav_add_listing) {
                    if (!(BaseActivity.this instanceof AddListingActivity)) {
                        startActivity(new Intent(BaseActivity.this, AddListingActivity.class));
                    }
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    // Placeholder for Favorites Activity
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    // Placeholder for Profile Activity
                    return true;
                }

                return false;
            }
        });

        // Set the selected item based on the current activity
        int selectedItemId = getCurrentActivity();
        if (selectedItemId != -1) {
            bottomNavigationView.setSelectedItemId(selectedItemId);
        }
    }
    protected int getCurrentActivity() {
        if (this instanceof MainActivity) {
            return R.id.nav_home;
        } else if (this instanceof AddListingActivity) {
            return R.id.nav_add_listing;
        }
        return -1;
    }
}
