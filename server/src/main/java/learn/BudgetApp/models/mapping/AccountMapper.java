package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {


    @Nullable
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return new Account(
                rs.getInt("accountId"),
                user,
                rs.getString("subtype")
        );
    }
}
