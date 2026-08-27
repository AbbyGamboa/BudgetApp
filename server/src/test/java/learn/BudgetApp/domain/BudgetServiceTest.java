package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BudgetServiceTest {

    @Autowired
    BudgetService service;

    @MockBean
    BudgetRepository repository;

    @MockBean
    UserRepository userRepository;

    @Nested
    class findById{
        @Test
        void success(){
            Budget budget = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000));
            when(repository.findById(1)).thenReturn(budget);

            Result<Budget> expected = new Result<>();
            expected.setpayload(budget);
            Result<Budget> actual = service.findById(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failureFromMisMatchUsers(){
            Budget budget = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000));
            when(repository.findById(1)).thenReturn(budget);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Cannot access other's budgets", ResultType.INVALID);
            Result<Budget> actual = service.findById(1, 2);

            assertEquals(expected, actual);
        }

        @Test
        void failureCannotFindId(){
            when(repository.findById(1)).thenReturn(null);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            Result<Budget> actual = service.findById(1, 2);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class update{
        @Test
        void success(){
            Budget updated = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4500));

            when(repository.findById(1)).thenReturn(updated);
            when(repository.update(updated)).thenReturn(true);

            Result<Budget> actual = service.update(updated, 1);

            Result<Budget> expected = new Result<>();
            expected.setpayload(updated);

            assertEquals(expected, actual);

        }

        @Test
        void cannotUpdateAnotherUserBudget(){
            User user = TestDataHelper.existingUser();
            user.setUserId(2);
            Budget original = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000));
            Budget updated = new Budget(1, user, BigDecimal.valueOf(5000));

            when(repository.findById(1)).thenReturn(original);

            Result<Budget> actual = service.update(updated, 2);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Cannot change ownership of a Budget", ResultType.INVALID);

            assertEquals(expected, actual);

        }

        @Test
        void cannotMakeIncomeNegative(){
            Budget updated = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(-4500));

            when(repository.findById(1)).thenReturn(updated);
            when(repository.update(updated)).thenReturn(true);

            Result<Budget> actual = service.update(updated, 1);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Income must be higher than 0", ResultType.INVALID);

            assertEquals(expected, actual);
        }

        @Test
        void cannotMakeIncomeZero(){
            Budget updated = new Budget(1, TestDataHelper.existingUser(), BigDecimal.ZERO);

            when(repository.findById(1)).thenReturn(updated);
            when(repository.update(updated)).thenReturn(true);

            Result<Budget> actual = service.update(updated, 1);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Income must be higher than 0", ResultType.INVALID);

            assertEquals(expected, actual);
        }
    }
}