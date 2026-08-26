package learn.BudgetApp.data;

import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.mapping.AccountMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountJdbcClientRepository implements AccountRepository{

    private final JdbcClient jdbcClient;

    public AccountJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Account create(Account account) {

        return null;
    }

    @Override
    public boolean delete(int accountId) {
        return false;
    }


    private final String BASE_SELECT = """
            select a.accountId, a.userId, a.subtype, u.email, u.password from account a
                inner join user u on a.userId = u.userId""";
    @Override
    public Account findById(int accountId) {
        String sql = BASE_SELECT + " where accountId = ?;";
        return jdbcClient.sql(sql).param(accountId).query(new AccountMapper()).optional().orElse(null);
    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public List<Account> findByUser(int userId) {
        String sql = BASE_SELECT + "where u.userId = ?;";
        return jdbcClient.sql(sql).param(userId).query(new AccountMapper()).list();
    }
}
