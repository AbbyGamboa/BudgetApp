package learn.BudgetApp.data;

import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.Category;
import learn.BudgetApp.models.mapping.BudgetCategoryMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public boolean updateAmount(BudgetCategory budgetCategory) {
        String sql = """
                update budget_category
                set percentage = ?
                where budgetCategoryId = ? and budgetId = ? and categoryId = ?;
                """;

        return jdbcClient.sql(sql).param(budgetCategory.getPercentage())
                .param(budgetCategory.getBudgetCategoryId())
                .param(budgetCategory.getBudget().getBudgetId())
                .param(budgetCategory.getCategory().getCategoryId())
                .update() > 0;
    }

    @Override
    public BudgetCategory create(BudgetCategory budgetCategory) {
        String sql = """
                insert into budget_category(budgetId, categoryId, percentage)
                values(:budgetId, :categoryId, :percentage);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("budgetId", budgetCategory.getBudget().getBudgetId())
                .param("categoryId", budgetCategory.getCategory().getCategoryId())
                .param("percentage", budgetCategory.getPercentage())
                .update(keyHolder, "budgetCategoryId");

        if (rowsAffected <= 0){
            return null;
        }
        return budgetCategory;
    }

    @Override
    public boolean delete(int budgetCategoryId) {
        return false;
    }
}
