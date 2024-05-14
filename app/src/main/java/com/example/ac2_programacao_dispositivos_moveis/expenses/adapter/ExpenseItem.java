package com.example.ac2_programacao_dispositivos_moveis.expenses.adapter;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ExpenseItem implements Serializable {
    private double amount;
    private String description;

    private String uid;

    public ExpenseItem() {

    }
    public ExpenseItem(double amount, String description, String uid) {
        this.amount = amount;
        this.description = description;
        this.uid = uid;
        Log.d("uid inside expense item", uid);
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        amount = aInputStream.readDouble();
        description = aInputStream.readUTF();
        uid = aInputStream.readUTF();
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        aOutputStream.writeDouble(amount);
        aOutputStream.writeUTF(description);
        aOutputStream.writeUTF(uid);
    }


    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getUid() {
        return uid;
    }

}
