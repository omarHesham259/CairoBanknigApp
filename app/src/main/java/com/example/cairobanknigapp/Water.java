package com.example.cairobanknigapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class Water extends AppCompatActivity {
    EditText serviceNumberEditText, amountEditText;
    Button payButton;
    Spinner serviceProviderSpinner;
    Dbhelper dbHelper;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        dbHelper = new Dbhelper(this);

        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        currentUser = prefs.getString("current_user", null);

        if (currentUser == null) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView usernameText = findViewById(R.id.username);
        if (usernameText != null) {
            usernameText.setText(currentUser);
        }

        serviceProviderSpinner = findViewById(R.id.Spinner_Water_id);
        serviceNumberEditText = findViewById(R.id.edit_user_number);
        amountEditText = findViewById(R.id.edit_amount);
        payButton = findViewById(R.id.pay);


        // Pay button click listener
        payButton.setOnClickListener(v -> {
            String serviceNumber = serviceNumberEditText.getText().toString().trim();
            String amountStr = amountEditText.getText().toString().trim();

            if (serviceNumber.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double balance = dbHelper.getBalance(currentUser);

            if (amount > balance) {
                Toast.makeText(this, "Insufficient balance", Toast.LENGTH_SHORT).show();
            } else {
                // Deduct amount and log transaction
                dbHelper.updateBalance(currentUser, balance - amount);
                String provider = serviceProviderSpinner.getSelectedItem().toString();
                String details = provider + " - " + serviceNumber;

                dbHelper.insertTransaction(currentUser, "Water", amount, details);

                // Go back to home page
                startActivity(new Intent(this, HomePage.class));
                MediaPlayer mediaPlayer = MediaPlayer.create(this ,R.raw.payment_sound);
                mediaPlayer.start();
                finish();
                Toast.makeText(this, "Water bill paid successfully!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}