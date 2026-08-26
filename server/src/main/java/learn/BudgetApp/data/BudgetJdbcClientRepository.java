package learn.BudgetApp.data;

import learn.BudgetApp.models.Budget;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetJdbcClientRepository implements BudgetRepository{

    private final JdbcClient jdbcClient;

    public BudgetJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    @Override
    public List<Budget> findByUser(int userId) {
        return List.of();
    }

    @Override
    public Budget findById(int budgetId) {
        return null;
    }

    @Override
    public Budget create(Budget budget) {
        return null;
    }

    @Override
    public boolean update(Budget budget) {
        return false;
    }

    @Override
    public boolean deleteById(int budgetId) {
        return false;
    }
}
