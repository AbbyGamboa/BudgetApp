package learn.BudgetApp.models;

import java.util.Objects;

public class Account {
    public int accountId;
    public User user;
    public String subtype;

    public Account(int accountId, User user, String subtype) {
        this.accountId = accountId;
        this.user = user;
        this.subtype = subtype;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && Objects.equals(user, account.user) && Objects.equals(subtype, account.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, user, subtype);
    }
}
