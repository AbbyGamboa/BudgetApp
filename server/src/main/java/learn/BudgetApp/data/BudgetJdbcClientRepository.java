package learn.BudgetApp.data;

import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.mapping.BudgetMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sql = BASE_SELECT + " where b.budgetId = ?";
        return jdbcClient.sql(sql).param(budgetId).query(new BudgetMapper()).optional().orElse(null);
    }

    @Override
    public Budget create(Budget budget) {
        String sql = """
                insert into budget (userId, income) 
                values (:userId, :income);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("userId", budget.getUser().getUserId())
                .param("income", budget.getIncome())
                .update(keyHolder, "budgetId");

        if(rowsAffected == 0){
            return null;
        }
        return budget;
    }

    @Override
    public boolean update(Budget budget) {
        String sql = """
                update budget
                set income =?
                where budgetId = ? and userId = ?;
                """;

        return  jdbcClient.sql(sql).param(budget.getIncome())
                .param(budget.getBudgetId())
                .param(budget.getUser().getUserId())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int budgetId) {
        return false;
    }
}
