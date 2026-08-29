package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Category;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Nullable
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapRow(rs,rowNum);

        return new Category(
                rs.getInt("categoryId"),
                rs.getString("name"),
                user
        );
    }
}
