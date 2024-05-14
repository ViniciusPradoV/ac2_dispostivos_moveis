package com.example.ac2_programacao_dispositivos_moveis.expenses.adapter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        amount = aInputStream.readDouble();
        description = aInputStream.readUTF();
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeDouble(amount);
        aOutputStream.writeUTF(description);
    }


    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
