package learn.BudgetApp.data;

import learn.BudgetApp.models.Transaction;
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

    @Override
    public Transaction findById(int transactionId) {
        return null;
    }

    @Override
    public List<Transaction> findByAccount(int accountId) {
        return List.of();
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
        return List.of();
    }
}
