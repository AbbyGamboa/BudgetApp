package learn.BudgetApp.models;

import java.math.BigDecimal;
import java.util.Objects;

public class BudgetCategory {

    public int budgetCategoryId;
    public Budget budget;
    public Category category;
    public BigDecimal percentage;

    public BudgetCategory(int budgetCategoryId, Budget budget, Category category, BigDecimal percentage) {
        this.budgetCategoryId = budgetCategoryId;
        this.budget = budget;
        this.category = category;
        this.percentage = percentage;
    }

    public int getBudgetCategoryId() {
        return budgetCategoryId;
    }

    public void setBudgetCategoryId(int budgetCategoryId) {
        this.budgetCategoryId = budgetCategoryId;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BudgetCategory that = (BudgetCategory) o;
        return budgetCategoryId == that.budgetCategoryId && Objects.equals(budget, that.budget) && Objects.equals(category, that.category) && Objects.equals(percentage, that.percentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetCategoryId, budget, category, percentage);
    }
}
