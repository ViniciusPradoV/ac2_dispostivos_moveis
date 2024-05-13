package com.example.ac2_programacao_dispositivos_moveis.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import com.example.ac2_programacao_dispositivos_moveis.expenses.adapter.ExpenseItem;

public class ExpensesRepository {
    MutableLiveData<List<ExpenseItem>> expensesLiveData = new MutableLiveData<>();
    public void addExpense(ExpenseItem expenseItem, OnCompleteListener<DocumentReference> onCompleteListener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("com/example/ac2_programacao_dispositivos_moveis/expenses").add(expenseItem)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    throw new RuntimeException(e.getCause());
                });
    }

    public LiveData<List<ExpenseItem>> getExpenses() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("com/example/ac2_programacao_dispositivos_moveis/expenses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<ExpenseItem> expenses = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ExpenseItem expenseItem = document.toObject(ExpenseItem.class);
                    expenses.add(expenseItem);
                }
                expensesLiveData.setValue(expenses);
            } else {
              if(task.isCanceled())
                  throw new RuntimeException("Tarefa foi cancelada");
            }
        }).addOnFailureListener(
                e -> {
                    throw new RuntimeException(e.getCause());
                }
        );

        return expensesLiveData;

    }
}
