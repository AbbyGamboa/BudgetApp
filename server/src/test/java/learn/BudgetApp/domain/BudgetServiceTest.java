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
import java.math.RoundingMode;

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

        @Test
        void updateToIncomeWithChange(){
            Budget updated = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4500.06).setScale(2, RoundingMode.DOWN));

            when(repository.findById(1)).thenReturn(updated);
            when(repository.update(updated)).thenReturn(true);

            Result<Budget> actual = service.update(updated, 1);

            Result<Budget> expected = new Result<>();
            expected.setpayload(updated);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class create{
        @Test
        void success(){
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.valueOf(75.50));

            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 1);

            Result<Budget> expected = new Result<>();
            expected.setpayload(create);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserIsIncorrect(){
            User differentUser = TestDataHelper.existingUser();
            differentUser.setUserId(2);
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.valueOf(75.50));

            when(userRepository.findById(2)).thenReturn(TestDataHelper.existingUser());
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 2);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Cannot create a budget for another user", ResultType.INVALID);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserDoesNotExist(){
            User doesNotExist = TestDataHelper.existingUser();
            doesNotExist.setUserId(999);
            Budget create = new Budget(3, doesNotExist, BigDecimal.valueOf(75.50));

            when(userRepository.findById(999)).thenReturn(null);
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 99);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("User not found", ResultType.NOT_FOUND);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIncomeZero(){
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.ZERO);

            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 1);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Income must be higher than 0", ResultType.INVALID);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIncomeNegative(){
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.valueOf(-1));

            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 1);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Income must be higher than 0", ResultType.INVALID);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIncomeHasTooManyDecimalPlaces(){
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.valueOf(10.00012));

            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.create(create)).thenReturn(create);
            Result<Budget> actual = service.create(create, 1);

            Result<Budget> expected = new Result<>();
            expected.addErrorMessage("Income must have 2 decimal places", ResultType.INVALID);

            assertEquals(expected, actual);
        }
    }
}