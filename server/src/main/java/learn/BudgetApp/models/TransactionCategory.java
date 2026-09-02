package learn.BudgetApp.models;

import java.util.Objects;

public class TransactionCategory {
    public Transaction transaction;
    public Category category;

    public TransactionCategory(Transaction transaction, Category category) {
        this.transaction = transaction;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCategory that = (TransactionCategory) o;
        return Objects.equals(transaction, that.transaction) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, category);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
