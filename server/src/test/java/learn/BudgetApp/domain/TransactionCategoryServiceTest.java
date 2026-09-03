package learn.BudgetApp.domain;

import learn.BudgetApp.data.*;
import learn.BudgetApp.models.TransactionCategory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionCategoryServiceTest {

    @Autowired
    private TransactionCategoryService service;

    @MockBean
    private TransactionCategoryRepository tcRepository;

    @MockBean
    private BudgetRepository budgetRepository;

    @Nested
    class findByBudget{
        @Test
        void success(){
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(tcRepository.findByBudget(1)).thenReturn(TestDataHelper.tcOfBudgetOne());

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.setpayload(TestDataHelper.tcOfBudgetOne());

            Result<List<TransactionCategory>> actual = service.findByBudget(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetNotFound(){
            when(budgetRepository.findById(1)).thenReturn(null);

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("Budget not found", ResultType.NOT_FOUND);

            Result<List<TransactionCategory>> actual = service.findByBudget(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetUserAndUserAreNotTheSame(){
            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's budgets", ResultType.INVALID);

            Result<List<TransactionCategory>> actual = service.findByBudget(2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenListIsEmpty(){
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(tcRepository.findByBudget(1)).thenReturn(List.of());

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("Budget has no transaction categories", ResultType.NOT_FOUND);

            Result<List<TransactionCategory>> actual = service.findByBudget(1, 1);

            assertEquals(expected, actual);
        }
    }


}