package learn.BudgetApp.models;

import java.util.Objects;

public class TransactionCategory {
    public Transaction transaction;
    public BudgetCategory budgetCategory;

    public TransactionCategory(Transaction transaction, BudgetCategory budgetCategory) {
        this.transaction = transaction;
        this.budgetCategory = budgetCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCategory that = (TransactionCategory) o;
        return Objects.equals(transaction, that.transaction) && Objects.equals(budgetCategory, that.budgetCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, budgetCategory);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }
}
