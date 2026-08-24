package learn.BudgetApp.models;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return budgetId == budget.budgetId && Objects.equals(user, budget.user) && Objects.equals(income, budget.income);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, user, income);
    }
}
