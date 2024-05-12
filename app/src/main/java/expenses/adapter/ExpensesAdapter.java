package expenses.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_programacao_dispositivos_moveis.R;

import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder> {

    private List<ExpenseItem> expenseList;

    public ExpensesAdapter(List<ExpenseItem> expenseList) {
        this.expenseList = expenseList;
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

    public void submitList(List<ExpenseItem> newExpenseList) {
        this.expenseList = newExpenseList;
        notifyDataSetChanged();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewAmount;
        private TextView textViewDescription;

        ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }

        void bindExpense(ExpenseItem expenseItem) {
            textViewAmount.setText(String.format("$%.2f", expenseItem.getAmount()));
            textViewDescription.setText(expenseItem.getDescription());
        }
    }

}
