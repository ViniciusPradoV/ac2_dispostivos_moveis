package com.example.ac2_programacao_dispositivos_moveis.expenses.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_programacao_dispositivos_moveis.R;
import com.example.ac2_programacao_dispositivos_moveis.expenses.adapter.ExpenseItem;
import com.example.ac2_programacao_dispositivos_moveis.expenses.adapter.ExpensesAdapter;
import com.example.ac2_programacao_dispositivos_moveis.login.LoginActivity;
import com.example.ac2_programacao_dispositivos_moveis.repositories.ExpensesRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class ExpensesFragment extends Fragment {

    private List<ExpenseItem> expenseItems = new ArrayList<>();
    private ExpensesAdapter expensesAdapter;
    private TextView textViewTotalAmount;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ExpensesRepository expenseRepository = new ExpensesRepository();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        RecyclerView recyclerViewExpenses = rootView.findViewById(R.id.recyclerViewExpenses);
        textViewTotalAmount = rootView.findViewById(R.id.textViewTotalAmount);

        expensesAdapter = new ExpensesAdapter(expenseItems, new ExpensesRepository());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExpenses.setLayoutManager(layoutManager);
        recyclerViewExpenses.setAdapter(expensesAdapter);

        loadExpenseData();

        updateTotalAmount();

        Button addButton = rootView.findViewById(R.id.buttonAddExpense);
        addButton.setOnClickListener(v -> promptForExpenseAmount());

        Button btnLogout = rootView.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });


        return rootView;
    }

    private void loadExpenseData() {
        Log.d("uid", firebaseAuth.getCurrentUser().getUid());
        expenseRepository.getExpenses(firebaseAuth.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), expenses -> {
            expenseItems.clear();
            expenseItems.addAll(expenses);
            expensesAdapter.notifyDataSetChanged();
            updateTotalAmount();
        });
    }

    private void promptForExpenseAmount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Nova despesa");

        final EditText amountInput = new EditText(requireContext());
        amountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        amountInput.setHint("Insira a quantidade");

        final EditText descriptionInput = new EditText(requireContext());
        descriptionInput.setHint("Insira a descrição da despesa");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(amountInput);
        layout.addView(descriptionInput);
        builder.setView(layout);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String enteredAmountString = amountInput.getText().toString();
            String enteredDescription = descriptionInput.getText().toString();
            try {
                double enteredAmount = Double.parseDouble(enteredAmountString);
                ExpenseItem newExpenseItem = new ExpenseItem(enteredAmount, enteredDescription, firebaseAuth.getCurrentUser().getUid());

                expenseRepository.addExpense(newExpenseItem, task -> {
                    if (task.isSuccessful()) {
                        expenseItems.add(newExpenseItem);
                        expensesAdapter.notifyItemInserted(expenseItems.size() - 1);
                        updateTotalAmount();
                    } else {
                        Toast.makeText(requireContext(), "Erro ao adicionar despesa!", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Quantia inválida!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateTotalAmount() {
        double totalAmount = calculateTotalAmount();
        textViewTotalAmount.setText(getString(R.string.total_amount_format, totalAmount));
    }

    private double calculateTotalAmount() {
        double totalAmount = 0.0;
        for (ExpenseItem item : expenseItems) {
            totalAmount += item.getAmount();
        }
        return totalAmount;
    }
}
