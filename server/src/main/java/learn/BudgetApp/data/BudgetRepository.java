package learn.BudgetApp.data;

import learn.BudgetApp.models.Budget;

import java.util.List;

public interface BudgetRepository {
    List<Budget> findByUser(int userId);

    Budget findById(int budgetId);

    Budget create(Budget budget);

    boolean update(Budget budget);

    boolean deleteById(int budgetId);

}
