package learn.BudgetApp.data;

import learn.BudgetApp.models.Account;

import java.util.List;

public interface AccountRepository {
    Account create(Account account);

    boolean delete(int accountId);

    Account findById(int accountId);

    boolean updateAccount(Account account);

    List<Account> findByUser(int userId);

}
