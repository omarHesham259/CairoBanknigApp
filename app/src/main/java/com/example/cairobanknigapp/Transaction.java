package com.example.cairobanknigapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Transaction extends AppCompatActivity {

    RecyclerView transactionList;
    Dbhelper dbHelper;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction); // name of the transaction page XML file

        transactionList = findViewById(R.id.recyclerView); //
        transactionList.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new Dbhelper(this);

        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        currentUser = prefs.getString("current_user", null);

        if (currentUser != null) {
            List<TransactionModel> transactions = dbHelper.getAllTransactions(currentUser);
            TransactionAdapter adapter = new TransactionAdapter(transactions);
            transactionList.setAdapter(adapter);
        } else {
            finish(); // No user logged in
        }
    }
}