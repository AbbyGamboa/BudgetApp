package learn.BudgetApp.data;

import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.mapping.BudgetMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetJdbcClientRepository implements BudgetRepository{

    private final JdbcClient jdbcClient;

    public BudgetJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    private final String BASE_SELECT = """
            select b.budgetId, b.userId, b.income, u.email, u.password from budget b
                inner join user u on b.userId = u.userId""";

    @Override
    public List<Budget> findByUser(int userId) {
        String sql = BASE_SELECT + " where b.userId = ?";
        return jdbcClient.sql(sql).param(userId).query(new BudgetMapper()).list();
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
