package learn.BudgetApp.models.mapping;

import learn.BudgetApp.models.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TransactionCategoryMapper implements RowMapper<TransactionCategory> {
    @Nullable
    @Override
    public TransactionCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        User categoryUser = new User();
        categoryUser.setUserId(rs.getInt("cUserId"));
        categoryUser.setEmail(rs.getString("cEmail"));
        categoryUser.setPassword(rs.getString("cPassword"));
        if (categoryUser.getUserId() == 0){
            categoryUser = null;
        }

        Category category = new Category();
        category.setCategoryId(rs.getInt("categoryId"));
        category.setName(rs.getString("name"));
        category.setUser(categoryUser);

        Budget budget =  new Budget();
        budget.setUser(user);
        budget.setBudgetId(rs.getInt("budgetId"));
        budget.setIncome(rs.getBigDecimal("income"));

        BudgetCategory budgetCategory = new BudgetCategory();
        budgetCategory.setBudget(budget);
        budgetCategory.setCategory(category);
        budgetCategory.setPercentage(rs.getBigDecimal("percentage"));
        budgetCategory.setBudgetCategoryId(rs.getInt("budgetCategoryId"));

        Account account = new Account();
        account.setAccountId(rs.getInt("accountId"));
        account.setUser(user);
        account.setSubtype(rs.getString("subtype"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionId(rs.getInt("transactionId"));
        transaction.setDescription(rs.getString("description"));
        transaction.setDate(rs.getObject("date",LocalDate.class));
        transaction.setMerchant_name(rs.getString("merchantName"));
        transaction.setAmount(rs.getBigDecimal("amount"));


        return new TransactionCategory(transaction, budgetCategory);
    }
}
