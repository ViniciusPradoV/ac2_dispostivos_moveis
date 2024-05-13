package com.example.ac2_programacao_dispositivos_moveis.models;

import java.util.Date;

public class Expense {
    private double amount;
    private String description;
    private Date timestamp;

    public Expense() {
    }

    public Expense(double amount, String description, Date timestamp) {
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

}