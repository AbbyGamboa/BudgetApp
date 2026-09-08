package learn.BudgetApp.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Budget {
    public int budgetId;
    public User user;
    public String name;

    public Budget(int budgetId, User user, String name) {
        this.budgetId = budgetId;
        this.user = user;
        this.name = name;
    }

    public Budget(){};

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return budgetId == budget.budgetId && Objects.equals(user, budget.user) && Objects.equals(name, budget.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetId, user, name);
    }
}
