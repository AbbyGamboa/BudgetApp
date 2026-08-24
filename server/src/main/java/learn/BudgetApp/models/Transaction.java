package learn.BudgetApp.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
    public int plaidTransactionId;
    public Account plaidAccount;
    public BigDecimal amount;
    public String merchantName;
    public String description;
    public boolean pending;

    public Transaction(int plaidTransactionId, Account plaidAccount, BigDecimal amount, String merchantName, String description, boolean pending) {
        this.plaidTransactionId = plaidTransactionId;
        this.plaidAccount = plaidAccount;
        this.amount = amount;
        this.merchantName = merchantName;
        this.description = description;
        this.pending = pending;
    }

    public int getPlaidTransactionId() {
        return plaidTransactionId;
    }

    public void setPlaidTransactionId(int plaidTransactionId) {
        this.plaidTransactionId = plaidTransactionId;
    }

    public Account getPlaidAccount() {
        return plaidAccount;
    }

    public void setPlaidAccount(Account plaidAccount) {
        this.plaidAccount = plaidAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return plaidTransactionId == that.plaidTransactionId && pending == that.pending && Objects.equals(plaidAccount, that.plaidAccount) && Objects.equals(amount, that.amount) && Objects.equals(merchantName, that.merchantName) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaidTransactionId, plaidAccount, amount, merchantName, description, pending);
    }
}
