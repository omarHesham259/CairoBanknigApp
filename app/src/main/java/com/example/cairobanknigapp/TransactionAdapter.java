package com.example.cairobanknigapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<TransactionModel> transactions;

    public TransactionAdapter(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionModel transaction = transactions.get(position);

        holder.typeTextView.setText("Service: " + transaction.getType());
        holder.amountTextView.setText("Amount: " + String.format("%.2f L.E", transaction.getAmount()));

        // Format the timestamp to real readable time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(new Date(transaction.getTimestamp()));
        holder.dateTextView.setText("Date: " + formattedDate);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView, amountTextView, dateTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.tv_service_value);
            amountTextView = itemView.findViewById(R.id.tv_amount_value);
            dateTextView = itemView.findViewById(R.id.tv_date);
        }
    }
}
