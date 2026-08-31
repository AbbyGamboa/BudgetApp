package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetCategoryRepository;
import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.models.BudgetCategory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BudgetCategoryServiceTest {

    @Autowired
    private BudgetCategoryService service;

    @MockBean
    private BudgetCategoryRepository bcRepository;

    @MockBean
    private BudgetRepository budgetRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Nested
    class findByBudget{
        @Test
        void success(){
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(bcRepository.findByBudget(1)).thenReturn(TestDataHelper.budgetOneBCList());

            Result<List<BudgetCategory>> expected = new Result<>();
            expected.setpayload(TestDataHelper.budgetOneBCList());
            Result<List<BudgetCategory>> actual = service.findByBudget(1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetNotFound(){
            when(budgetRepository.findById(1)).thenReturn(null);

            Result<List<BudgetCategory>> expected = new Result<>();
            expected.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            Result<List<BudgetCategory>> actual = service.findByBudget(1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetDoesNotBelongToUser(){
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());

            Result<List<BudgetCategory>> expected = new Result<>();
            expected.addErrorMessage("Budget does not belong to user", ResultType.INVALID);
            Result<List<BudgetCategory>> actual = service.findByBudget(1,2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBCListIsEmpty(){
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(bcRepository.findByBudget(1)).thenReturn(List.of());

            Result<List<BudgetCategory>> expected = new Result<>();
            expected.addErrorMessage("Budget has no categories", ResultType.NOT_FOUND);
            Result<List<BudgetCategory>> actual = service.findByBudget(1,1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findById{
        @Test
        void success(){
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());

            Result<BudgetCategory> expected = new Result<>();
            expected.setpayload(TestDataHelper.budgetCategory());
            Result<BudgetCategory> actual = service.findById(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserIsIncorrect(){
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot access another user's budget categories", ResultType.INVALID);
            Result<BudgetCategory> actual = service.findById(1, 2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenNotFound(){
            when(bcRepository.findById(1)).thenReturn(null);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Budget Category not found", ResultType.NOT_FOUND);
            Result<BudgetCategory> actual = service.findById(1, 2);

            assertEquals(expected, actual);
        }

    }

}