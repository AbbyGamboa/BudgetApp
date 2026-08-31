package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetCategoryMapper implements RowMapper<BudgetCategory> {

    @Nullable
    @Override
    public BudgetCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        BudgetMapper budgetMapper = new BudgetMapper();
        Budget budget = budgetMapper.mapRow(rs, rowNum);

        CategoryMapper categoryMapper = new CategoryMapper();
        Category category = categoryMapper.mapRow(rs, rowNum);

        return new BudgetCategory(
                rs.getInt("budgetCategoryId"),
                budget,
                category,
                rs.getBigDecimal("percentage")
        );
    }
}
