package com.example.cairobanknigapp;

public class TransactionModel {
    private long timestamp;
    private double amount;
    private String type;

    public TransactionModel(long timestamp, double amount, String type) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = type;
    }

    public long getTimestamp() { return timestamp; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
}

