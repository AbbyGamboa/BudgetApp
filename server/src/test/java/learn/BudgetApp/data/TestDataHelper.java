package learn.BudgetApp.data;

import learn.BudgetApp.models.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class TestDataHelper {

    public static User existingUser() {
        return new User(1, "a@a.com", "a");
    }
    public static User secondUser(){return new  User(2, "b@b.com", "b"); }

    public static User userToCreate() {
        return new User(0, "c@c.com", "c");
    }

    public static User userAfterCreate() {
        User user = userToCreate();
        user.setUserId(3);
        return user;
    }

    public static Account existingAccount(){
        return new Account(1, TestDataHelper.existingUser(), "Checkings");
    }

    public static Transaction firstTransaction(){
        return new Transaction(1, existingAccount(), BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN), LocalDate.of(2026, 6, 8), "Target", "Online buy");
    }

    public static Account secondAccount(){
        return  new Account(2, secondUser(), "Savings");
    }

    public static List<Transaction> allAccountOneTransactions(){
        return List.of(firstTransaction(), new Transaction(2, existingAccount(), BigDecimal.TEN.setScale(2, RoundingMode.DOWN), LocalDate.of(2026, 6,9), "Speedway", "Gas"));
    }

    public static List<Account> allUserOneAccounts(){
        return List.of(new Account(1, TestDataHelper.existingUser(), "Checkings"));
    }

    public static Transaction createdTransaction(){
        return new Transaction(3, TestDataHelper.existingAccount(), BigDecimal.valueOf(10.50), LocalDate.now(), null, null);
    }

    public static List<Category> categoriesForUserOne(){
        return List.of(new Category(2, "Groceries", TestDataHelper.existingUser()), new Category(1, "Tuition", null));
    }

    public static Category firstCategory(){
        return new Category(1, "Tuition", null);
    }

    public static Category customCategory(){
        return new Category(2, "Groceries", existingUser());
    }

    public static Category createCategory(){
        return new Category(4, "Insurance", existingUser());
    }

    public static Budget budgetOne(){
        return new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000).setScale(2, RoundingMode.DOWN));
    }

    public static List<BudgetCategory> budgetOneBCList(){
        return List.of(new BudgetCategory(1, budgetOne(), firstCategory(), BigDecimal.valueOf(15)),
                new BudgetCategory(2, budgetOne(), customCategory(), BigDecimal.valueOf(30)));
    }

    public static BudgetCategory budgetCategory(){
        return new BudgetCategory(1, budgetOne(), firstCategory(), BigDecimal.valueOf(15));
    }
}
