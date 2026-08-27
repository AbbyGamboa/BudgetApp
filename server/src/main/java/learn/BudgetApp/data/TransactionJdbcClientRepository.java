package learn.BudgetApp.data;

import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.mapping.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionJdbcClientRepository implements TransactionRepository{

    private final JdbcClient jdbcClient;

    public TransactionJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SELECT = """
            select t.transactionId, t.accountId, t.amount, t.date, t.merchantName,
            t.description, a.userId, a.subtype, u.email, u.password
            from transaction t inner join account a on t.accountId = a.accountId
            inner join user u on a.userId = u.userId
            """;
    @Override
    public Transaction findById(int transactionId) {
        final String sql = BASE_SELECT + " where t.transactionId = ?;";
        return jdbcClient.sql(sql).param(transactionId).query(new TransactionMapper()).optional().orElse(null);
    }

    @Override
    public List<Transaction> findByAccount(int accountId) {
        final String sql = BASE_SELECT + " where a.accountId = ?;";

        return jdbcClient.sql(sql).param(accountId).query(new TransactionMapper()).list();
    }

    @Override
    public Transaction create(Transaction transaction) {
        return null;
    }

    @Override
    public boolean update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean deleteById(int transactionId) {
        return false;
    }

    @Override
    public List<Transaction> findByDate(LocalDate start, LocalDate end) {
        String sql = BASE_SELECT + " where t.date >= :start and t.date <= :end;";
        return jdbcClient.sql(sql).param("start", start)
                .param("end", end)
                .query(new TransactionMapper())
                .list();
    }
}
