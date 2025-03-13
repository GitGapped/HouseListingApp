package com.example.houselistingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddListingActivity extends AppCompatActivity {

    private EditText nameInput, addressInput, descriptionInput, sqMetersInput, priceInput, phoneInput, yearBuiltInput;
    private Button submitButton;
    private HouseListingDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);
        addressInput = findViewById(R.id.addressInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        sqMetersInput = findViewById(R.id.sqMetersInput);
        priceInput = findViewById(R.id.priceInput);
        phoneInput = findViewById(R.id.phoneInput);
        yearBuiltInput = findViewById(R.id.yearBuiltInput);
        submitButton = findViewById(R.id.submitButton);

        dbHelper = new HouseListingDb(this);

        // Handle submit button click
        submitButton.setOnClickListener(view -> saveListing());
    }

    private void saveListing() {
        String name = nameInput.getText().toString();
        String address = addressInput.getText().toString();
        String description = descriptionInput.getText().toString();
        String sqMeters = sqMetersInput.getText().toString();
        String price = priceInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String yearBuilt = yearBuiltInput.getText().toString();

        if (name.isEmpty() || address.isEmpty() || price.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("description", description);
        values.put("square_meters", sqMeters);
        values.put("price", price);
        values.put("phone_number", phone);
        values.put("year_built", yearBuilt);

        long result = db.insert("MyListings", null, values);
        if (result != -1) {
            Toast.makeText(this, "Listing added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding listing", Toast.LENGTH_SHORT).show();
        }
    }
}
