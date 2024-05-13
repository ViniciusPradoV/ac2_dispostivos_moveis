package com.example.ac2_programacao_dispositivos_moveis.expenses.adapter;

import java.io.Serializable;

public class ExpenseItem implements Serializable {
    private double amount;
    private String description;

    public ExpenseItem() {
    }

    public ExpenseItem(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
