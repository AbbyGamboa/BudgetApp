package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetMapper implements RowMapper<Budget> {

    @Nullable
    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return new Budget(
                rs.getInt("budgetId"),
                user,
                rs.getBigDecimal("income").setScale(2, RoundingMode.DOWN)
        );
    }
}
