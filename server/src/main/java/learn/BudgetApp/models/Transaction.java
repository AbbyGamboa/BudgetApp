package learn.BudgetApp.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    public int transactionId;
    public Account account;
    public BigDecimal amount;
    public LocalDate date;
    public String merchant_name;
    public String description;

    public Transaction(int transactionId, Account account, BigDecimal amount, LocalDate date, String merchant_name, String description) {
        this.transactionId = transactionId;
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.merchant_name = merchant_name;
        this.description = description;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId && Objects.equals(account, that.account) && Objects.equals(amount, that.amount) && Objects.equals(date, that.date) && Objects.equals(merchant_name, that.merchant_name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, account, amount, date, merchant_name, description);
    }
}
