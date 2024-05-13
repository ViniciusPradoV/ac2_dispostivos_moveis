package com.example.ac2_programacao_dispositivos_moveis.expenses.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_programacao_dispositivos_moveis.R;

import java.util.List;

import com.example.ac2_programacao_dispositivos_moveis.repositories.ExpensesRepository;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder> {
    private final ExpensesRepository expensesRepository;


    private List<ExpenseItem> expenseList;

    public ExpensesAdapter(List<ExpenseItem> expenseList, ExpensesRepository expensesRepository) {
        this.expenseList = expenseList;
        this.expensesRepository = expensesRepository;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        ExpenseItem expenseItem = expenseList.get(position);
        holder.bindExpense(expenseItem);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewAmount;
        private final TextView textViewDescription;

        ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }

        void bindExpense(ExpenseItem expenseItem) {
            textViewAmount.setText(String.format("%.2f R$", expenseItem.getAmount()));
            textViewDescription.setText(expenseItem.getDescription());
        }
    }

}
