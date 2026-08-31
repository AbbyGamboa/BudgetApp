package learn.BudgetApp.data;

import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.mapping.BudgetCategoryMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BudgetCategoryJdbcClientRepository implements BudgetCategoryRepository{

    private final JdbcClient jdbcClient;

    public BudgetCategoryJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SELECT = """
            select
                bc.budgetCategoryId,
                bc.percentage,
                b.budgetId,
                b.income,
                c.name,
                c.categoryId,
                bu.userId AS budgetUserId,
                bu.email AS budgetUserEmail,
                bu.password AS budgetUserPassword,
                cu.userId AS categoryUserId,
                cu.email AS categoryUserEmail,
                cu.password AS categoryUserPassword
            from budget_category bc
                inner  join budget b on bc.budgetId = b.budgetId
                inner join user bu on b.userId = bu.userId
                inner join categories c on bc.categoryId = c.categoryId
                left join user cu on c.userId = cu.userId""";

    @Override
    public List<BudgetCategory> findByBudget(int budgetId) {
        String sql = BASE_SELECT + " where b.budgetId = ?;";

        return jdbcClient.sql(sql).param(budgetId).query(new BudgetCategoryMapper()).list();
    }

    @Override
    public BudgetCategory findById(int budgetCategoryId) {
        String sql = BASE_SELECT + " where bc.budgetCategoryId =?;";
        return jdbcClient.sql(sql).param(budgetCategoryId).query(new BudgetCategoryMapper()).optional().orElse(null);
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
