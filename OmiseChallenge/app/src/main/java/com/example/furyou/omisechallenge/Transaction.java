package com.example.furyou.omisechallenge;

/**
 * class containing all the information regarding a transaction (name, token & amount)
 **/

public class Transaction {
    private String name;
    private String token;
    private String amount;

    public Transaction(String name, String token, String amount) {
        this.name = name;
        this.token = token;
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

    public String getAmount() {
        return this.amount;
    }
}
