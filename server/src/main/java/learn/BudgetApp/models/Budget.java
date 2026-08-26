package learn.BudgetApp.models;

import java.math.BigDecimal;

public class Budget {
    public int budgetId;
    public User user;
    public BigDecimal income;

    public Budget(int budgetId, User user, BigDecimal income) {
        this.budgetId = budgetId;
        this.user = user;
        this.income = income;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
