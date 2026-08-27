package learn.BudgetApp.data;

import learn.BudgetApp.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

    Transaction findById(int transactionId);

    List<Transaction> findByAccount(int accountId);

    Transaction create(Transaction transaction);

    boolean update(Transaction transaction);

    boolean deleteById(int transactionId);

    List<Transaction> findByDate(LocalDate start, LocalDate end);
}
