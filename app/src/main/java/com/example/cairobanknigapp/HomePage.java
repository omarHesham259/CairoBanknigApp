package com.example.cairobanknigapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class HomePage extends AppCompatActivity {

    Dbhelper dbHelper;
    TextView balanceText;
    TextView usernameText;
    String currentUser;

    @Override
    protected void onResume() {
        super.onResume();
        updateBalance(); // Refresh balance every time the screen resumes
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbHelper = new Dbhelper(this);
        balanceText = findViewById(R.id.balance_value);
        usernameText = findViewById(R.id.username); // Shows "Welcome, USERNAME!"

        // Try to get username from Intent
        Intent intent = getIntent();
        currentUser = intent.getStringExtra("username");

        // Fallback to SharedPreferences
        if (currentUser == null) {
            SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
            currentUser = prefs.getString("current_user", null);
        }

        if (currentUser == null) {
            Toast.makeText(this, "No user logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        usernameText.setText("Welcome, " + currentUser + "!");
        updateBalance();



        findViewById(R.id.Transaction_Lin).setOnClickListener(v -> {
            Intent intent1 = new Intent(this, Transaction.class);
            startActivity(intent1);
        });

        findViewById(R.id.Transfer_Lin).setOnClickListener(v -> {
            Intent i = new Intent(this, Transfers.class);
            i.putExtra("username", currentUser);
            startActivity(i);
        });




        findViewById(R.id.flash_Lin).setOnClickListener(v -> {
            Intent i = new Intent(this, Electricity.class);
            i.putExtra("username", currentUser);
            startActivity(i);
        });

        findViewById(R.id.internet).setOnClickListener(v -> {
            Intent i = new Intent(this, Internet.class);
            i.putExtra("username", currentUser);
            startActivity(i);
        });

        findViewById(R.id.drop).setOnClickListener(v -> {
            Intent i = new Intent(this, Water.class);
            i.putExtra("username", currentUser);
            startActivity(i);
        });

        findViewById(R.id.flame).setOnClickListener(v -> {
            Intent i = new Intent(this, Gas.class);
            i.putExtra("username", currentUser);
            startActivity(i);
        });
    }

    private void updateBalance() {
        double balance = dbHelper.getBalance(currentUser);
        balanceText.setText("Balance: " + balance + " EGP");
    }
}