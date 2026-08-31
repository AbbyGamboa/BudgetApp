package learn.BudgetApp.data;

import learn.BudgetApp.models.BudgetCategory;

import java.util.List;

public class BudgetCategoryJdbcClientRepository implements BudgetCategoryRepository{
    @Override
    public List<BudgetCategory> findByBudget(int budgetId) {
        return List.of();
    }

    @Override
    public BudgetCategory findById(int budgetCategoryId) {
        return null;
    }

    @Override
    public boolean updatePercentage(BudgetCategory budgetCategory) {
        return false;
    }

    @Override
    public BudgetCategory create(BudgetCategory budgetCategory) {
        return null;
    }

    @Override
    public boolean delete(int budgetCategoryId) {
        return false;
    }
}
