package com.example.houselistingapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AddListingActivity extends BaseActivity { // Change AppCompatActivity → BaseActivity

    private static final String CHANNEL_ID = "house_listing_channel";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 100;

    private EditText nameInput, addressInput, descriptionInput, sqMetersInput, priceInput, phoneInput, yearBuiltInput;
    private Button submitButton;
    private HouseListingDb dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        setupBottomNavigation(); // Call this to set up the navigation bar
        getCurrentActivity();


        nameInput = findViewById(R.id.nameInput);
        addressInput = findViewById(R.id.addressInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        sqMetersInput = findViewById(R.id.sqMetersInput);
        priceInput = findViewById(R.id.priceInput);
        phoneInput = findViewById(R.id.phoneInput);
        yearBuiltInput = findViewById(R.id.yearBuiltInput);
        submitButton = findViewById(R.id.submitButton);

        dbHelper = new HouseListingDb(this);
        createNotificationChannel();

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
            requestNotificationPermission();
            finish();
        } else {
            Toast.makeText(this, "Error adding listing", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                sendNotification();
            }
        } else {
            sendNotification();
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Ensure you have an icon in res/drawable
                .setContentTitle("New Listing Added")
                .setContentText("A new house listing has been added successfully!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            notificationManager.notify(1, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "House Listings";
            String description = "Notifications for new house listings";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotification();
            } else {
                Toast.makeText(this, "Notification permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
