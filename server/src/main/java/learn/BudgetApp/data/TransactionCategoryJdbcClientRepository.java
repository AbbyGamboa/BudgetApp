package learn.BudgetApp.data;

import learn.BudgetApp.models.TransactionCategory;
import learn.BudgetApp.models.mapping.TransactionCategoryMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionCategoryJdbcClientRepository implements TransactionCategoryRepository{

    private final JdbcClient jdbcClient;

    public TransactionCategoryJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SELECT = """
            select tc.transactionId, tc.budgetCategoryId, au.userId, au.email, au.password, cu.userId as cUserId,
            cu.email as cEmail, cu.password as cPassword, c.categoryId, c.name, b.budgetId, b.income, bc.percentage,
            bc.budgetCategoryId, a.accountId, a.subtype, t.description, t.date, t.merchantName, t.amount
            from transaction_categories tc
            inner join transaction t on tc.transactionId = t.transactionId
            inner join account a on t.accountId = a.accountId
            inner join user au on a.userId = au.userId
            inner join budget_category bc on tc.budgetCategoryId = bc.budgetCategoryId
            inner join budget b on bc.budgetId = b.budgetId
            inner join user bu on b.userId = bu.userId
            inner join categories c on bc.categoryId = c.categoryId
            left join user cu on c.userId = cu.userId""";

    @Override
    public List<TransactionCategory> findByBudget(int budgetId) {
        final String sql = BASE_SELECT + " where b.budgetId = ?";
        return jdbcClient.sql(sql).param(budgetId).query(new TransactionCategoryMapper()).list();
    }

    @Override
    public List<TransactionCategory> findByDate(int categoryId, LocalDate start, LocalDate end) {
        String sql = BASE_SELECT + " where c.categoryId = :categoryId and t.date >= :start and t.date <= :end;";
        return jdbcClient.sql(sql).param("categoryId", categoryId).param("start", start).param("end", end)
                .query(new TransactionCategoryMapper()).list();
    }

    @Override
    public TransactionCategory findByTransactionId(int transactionId) {
        String sql = BASE_SELECT + " where t.transactionId = ?;";
        return jdbcClient.sql(sql).param(transactionId).query(new TransactionCategoryMapper()).optional().orElse(null);
    }

    @Override
    public TransactionCategory create(TransactionCategory transactionCategory) {
        return null;
    }

    @Override
    public boolean update(TransactionCategory transactionCategory) {
        return false;
    }

    @Override
    public boolean delete(int transactionCategoryId) {
        return false;
    }
}
