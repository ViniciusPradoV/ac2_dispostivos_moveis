package expenses.adapter;

public class ExpenseItem {
    private double amount;
    private String description;

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
