package learn.BudgetApp.models;

import java.util.Objects;

public class TransactionCategories {
    public Transaction transaction;
    public Category category;

    public TransactionCategories(Transaction transaction, Category category) {
        this.transaction = transaction;
        this.category = category;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCategories that = (TransactionCategories) o;
        return Objects.equals(transaction, that.transaction) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, category);
    }
}
