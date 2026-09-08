package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionMapper implements RowMapper<Transaction> {
    @Nullable
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.mapRow(rs, rowNum);

        return new Transaction(
                rs.getInt("transactionId"),
                account,
                rs.getBigDecimal("amount"),
                rs.getObject("date", LocalDate.class),
                rs.getString("merchantName"),
                rs.getString("description")
        );
    }
}
