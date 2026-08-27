package learn.BudgetApp.data;

import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
        return new Transaction(1, existingAccount(), BigDecimal.valueOf(100), LocalDate.of(2026, 6, 8), "Target", "Online buy");
    }

    public static List<Transaction> allAccountOneTransactions(){
        return List.of(firstTransaction(), new Transaction(2, existingAccount(), BigDecimal.TEN, LocalDate.of(2026, 6,9), "Speedway", "Gas"));
    }

    public static List<Account> allUserOneAccounts(){
        return List.of(new Account(1, TestDataHelper.existingUser(), "Checkings"));
    }
}
