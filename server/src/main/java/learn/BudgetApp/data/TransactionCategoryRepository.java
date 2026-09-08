package learn.BudgetApp.data;

import learn.BudgetApp.models.TransactionCategory;

import java.time.LocalDate;
import java.util.List;

public interface TransactionCategoryRepository {

    List<TransactionCategory> findByBudget(int budgetId);

    List<TransactionCategory> findByDate(int categoryId, LocalDate start, LocalDate end);

    TransactionCategory findByTransactionId(int transactionId);

    TransactionCategory create(TransactionCategory transactionCategory);

    boolean update(TransactionCategory transactionCategory);

    boolean delete(int transactionCategoryId);

}
