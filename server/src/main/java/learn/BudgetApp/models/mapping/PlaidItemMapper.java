package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaidItemMapper implements RowMapper<PlaidItems> {

    @Override
    public PlaidItems mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return new PlaidItems(
                rs.getString("plaidItemId"),
                user,
                rs.getString("accessToken"),
                rs.getString("institutionName")
        );
    }
}
