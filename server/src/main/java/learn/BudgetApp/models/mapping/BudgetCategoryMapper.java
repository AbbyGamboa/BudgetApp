package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.Category;
import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetCategoryMapper implements RowMapper<BudgetCategory> {

    @Nullable
    @Override
    public BudgetCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        Budget budget = new Budget();
        User budgetUser = new User();

        budgetUser.setUserId(rs.getInt("budgetUserId"));
        budgetUser.setEmail(rs.getString("budgetUserEmail"));
        budgetUser.setPassword(rs.getString("budgetUserPassword"));

        budget.setBudgetId(rs.getInt("budgetId"));
        budget.setUser(budgetUser);
        budget.setIncome(rs.getBigDecimal("income"));

        Category category = new Category();
        User categoryUser = new User();

        categoryUser.setUserId(rs.getInt("categoryUserId"));
        categoryUser.setEmail(rs.getString("categoryUserEmail"));
        categoryUser.setPassword(rs.getString("categoryUserPassword"));

        category.setCategoryId(rs.getInt("categoryId"));
        if(categoryUser.getUserId() == 0){
            category.setUser(null);
        } else{
            category.setUser(categoryUser);
        }
        category.setName(rs.getString("name"));

        return new BudgetCategory(
                rs.getInt("budgetCategoryId"),
                budget,
                category,
                rs.getBigDecimal("percentage")
        );
    }
}
