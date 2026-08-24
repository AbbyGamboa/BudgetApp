package learn.BudgetApp.models;

import java.util.Objects;

public class Account {
    public int plaidAccountId;
    public PlaidItems plaidItem;
    public String name;
    public String subtype;

    public Account(int plaidAccountId, PlaidItems plaidItem, String name, String subtype) {
        this.plaidAccountId = plaidAccountId;
        this.plaidItem = plaidItem;
        this.name = name;
        this.subtype = subtype;
    }

    public int getPlaidAccountId() {
        return plaidAccountId;
    }

    public void setPlaidAccountId(int plaidAccountId) {
        this.plaidAccountId = plaidAccountId;
    }

    public PlaidItems getPlaidItem() {
        return plaidItem;
    }

    public void setPlaidItem(PlaidItems plaidItem) {
        this.plaidItem = plaidItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return plaidAccountId == account.plaidAccountId && Objects.equals(plaidItem, account.plaidItem) && Objects.equals(name, account.name) && Objects.equals(subtype, account.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaidAccountId, plaidItem, name, subtype);
    }
}