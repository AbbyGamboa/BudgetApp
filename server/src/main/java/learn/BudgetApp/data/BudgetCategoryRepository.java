package learn.BudgetApp.data;

import learn.BudgetApp.models.BudgetCategory;

import java.util.List;

public interface BudgetCategoryRepository {

    List<BudgetCategory> findByBudget(int budgetId);

    BudgetCategory findById(int budgetCategoryId);

    boolean updatePercentage(BudgetCategory budgetCategory);

    BudgetCategory create(BudgetCategory budgetCategory);

    boolean delete(int budgetCategoryId);

}
