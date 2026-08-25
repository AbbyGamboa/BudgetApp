package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaidItemMapper implements RowMapper {
    @Nullable
    @Override
    public PlaidItems mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));

        return new PlaidItems(
                rs.getString("plaidItemId"),
                user,
                rs.getString("accessToken"),
                rs.getString("institutionName")
        );
    }
}
