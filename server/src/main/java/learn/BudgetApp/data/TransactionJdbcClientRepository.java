package learn.BudgetApp.data;

import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.mapping.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sql = """
                insert into transaction(accountId, amount, date, merchantName, description) values
                (:accountId, :amount, :date, :merchantName, :description);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("accountId", transaction.getAccount().getAccountId())
                .param("amount", transaction.getAmount())
                .param("date", transaction.getDate())
                .param("merchantName", transaction.getMerchant_name())
                .param("description", transaction.getDescription())
                .update(keyHolder, "transactionId");

        if(rowsAffected == 0){
            return null;
        }
        return transaction;
    }

    @Override
    public boolean update(Transaction transaction) {
        String sql = """
                update transaction
                set amount = :amount,
                date = :date,
                merchantName = :merchantName,
                description = :description
                where transactionId = :transactionId and accountId = :accountId;
                """;

        return jdbcClient.sql(sql)
                .param("amount", transaction.getAmount())
                .param("date", transaction.getDate())
                .param("merchantName", transaction.getMerchant_name())
                .param("description", transaction.getDescription())
                .param("transactionId", transaction.getTransactionId())
                .param("accountId", transaction.getAccount().getAccountId())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int transactionId) {
        return false;
    }

    @Override
    public List<Transaction> findByDate(int accountId, LocalDate start, LocalDate end) {
        String sql = BASE_SELECT + " where t.date >= :start and t.date <= :end and t.accountId = :accountId;";
        return jdbcClient.sql(sql).param("start", start)
                .param("end", end)
                .param("accountId", accountId)
                .query(new TransactionMapper())
                .list();
    }
}
