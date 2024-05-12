package expenses.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
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

import java.util.ArrayList;
import java.util.List;

import expenses.adapter.ExpenseItem;
import expenses.adapter.ExpensesAdapter;


public class ExpensesFragment extends Fragment {

    private List<ExpenseItem> expenseItems = new ArrayList<>();
    private ExpensesAdapter expensesAdapter;
    private TextView textViewTotalAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        RecyclerView recyclerViewExpenses = rootView.findViewById(R.id.recyclerViewExpenses);
        textViewTotalAmount = rootView.findViewById(R.id.textViewTotalAmount);

        expensesAdapter = new ExpensesAdapter(expenseItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExpenses.setLayoutManager(layoutManager);
        recyclerViewExpenses.setAdapter(expensesAdapter);

        loadExpenseData();

        updateTotalAmount();

        Button addButton = rootView.findViewById(R.id.buttonAddExpense);
        addButton.setOnClickListener(v -> promptForExpenseAmount());

        return rootView;
    }

    private void loadExpenseData() {
        expenseItems.add(new ExpenseItem(50.00, "A"));
        expenseItems.add(new ExpenseItem(25.00, "B"));
        expenseItems.add(new ExpenseItem(12.99, "C"));
        expensesAdapter.notifyDataSetChanged();
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
                expenseItems.add(new ExpenseItem(enteredAmount, enteredDescription));
                expensesAdapter.notifyItemInserted(expenseItems.size() - 1);

                updateTotalAmount();
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
