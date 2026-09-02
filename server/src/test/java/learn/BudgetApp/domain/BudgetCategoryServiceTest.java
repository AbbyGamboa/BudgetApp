package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetCategoryRepository;
import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
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

    @Nested
    class update{
        @Test
        void success(){
            BudgetCategory updated = TestDataHelper.budgetCategory();
            updated.setPercentage(BigDecimal.valueOf(10));
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());
            when(bcRepository.updateAmount(updated)).thenReturn(true);

            Result<BudgetCategory> expected = new Result<>();
            expected.setpayload(updated);

            Result<BudgetCategory> actual = service.updatePercentage(updated, 1, 1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetIsNotDefined(){
            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Budget Category is not defined", ResultType.NOT_FOUND);

            Result<BudgetCategory> actual = service.updatePercentage(null, 1,1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenInvalidBC(){
            BudgetCategory invalid = TestDataHelper.budgetCategory();
            invalid.setBudget(null);
            when(budgetRepository.findById(1)).thenReturn(null);
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());

            Result<BudgetCategory> expectNullBudget = new Result<>();
            expectNullBudget.addErrorMessage("Budget is required", ResultType.INVALID);

            Result<BudgetCategory> actual = service.updatePercentage(invalid, 1,1,1);

            assertEquals(expectNullBudget, actual);

        }

        @Test
        void failsWhenChangingBudgetOrCategory(){
            BudgetCategory updated = TestDataHelper.budgetCategory();
            Budget invalidBudget = TestDataHelper.budgetOne();
            invalidBudget.setBudgetId(2);
            updated.setBudget(invalidBudget);

            when(budgetRepository.findById(2)).thenReturn(invalidBudget);
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());
            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot change a budget or category", ResultType.INVALID);

            Result<BudgetCategory> actual = service.updatePercentage(updated, 1,2,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenAccessingAnotherUsersBudgetCategories(){
            BudgetCategory updated = TestDataHelper.budgetCategory();
            Budget invalidBudget = TestDataHelper.budgetOne();
            User invalidUser = TestDataHelper.secondUser();

            invalidBudget.setUser(invalidUser);

            when(budgetRepository.findById(1)).thenReturn(invalidBudget);
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(1)).thenReturn(updated);
            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot access another user's budget category", ResultType.INVALID);

            Result<BudgetCategory> actual = service.updatePercentage(updated, 1,1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsInRepo(){
            BudgetCategory updated = TestDataHelper.budgetCategory();
            updated.setPercentage(BigDecimal.valueOf(10));
            when(budgetRepository.findById(1)).thenReturn(TestDataHelper.budgetOne());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());
            when(bcRepository.updateAmount(updated)).thenReturn(false);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot update budget category", ResultType.INVALID);

            Result<BudgetCategory> actual = service.updatePercentage(updated, 1,1,1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class create{
        @Test
        void success(){
            BudgetCategory created = TestDataHelper.createdBC();

            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(5)).thenReturn(null);
            when(bcRepository.findByBudget(2)).thenReturn(List.of());
            when(bcRepository.create(created)).thenReturn(created);

            Result<BudgetCategory> expected = new Result<>();
            expected.setpayload(created);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBCIsNull(){

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Budget Category is not defined", ResultType.NOT_FOUND);

            Result<BudgetCategory> actual = service.create(null, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBudgetNotFound(){
            BudgetCategory created = TestDataHelper.createdBC();
            when(budgetRepository.findById(2)).thenReturn(null);
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Budget is required", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenCategoryNotFound(){
            BudgetCategory created = TestDataHelper.createdBC();
            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(null);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Category is required", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIncorrectAmount(){
            BudgetCategory created = TestDataHelper.createdBC();
            created.setPercentage(BigDecimal.valueOf(-100));
            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Percentage is required and must be positive", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenBCAlreadyCreated(){
            BudgetCategory created = TestDataHelper.createdBC();
            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(5)).thenReturn(TestDataHelper.createdBC());

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot create budgetCategory that is already made", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenAlreadyEstablishedInBudget(){
            BudgetCategory created = TestDataHelper.createdBC();
            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(5)).thenReturn(null);
            when(bcRepository.findByBudget(2)).thenReturn(List.of(TestDataHelper.createdBC()));


            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot have duplicate categories in the same budget", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenRepoFails(){
            BudgetCategory created = TestDataHelper.createdBC();

            when(budgetRepository.findById(2)).thenReturn(TestDataHelper.budgetTwo());
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(bcRepository.findById(5)).thenReturn(null);
            when(bcRepository.findByBudget(2)).thenReturn(List.of());
            when(bcRepository.create(created)).thenReturn(null);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot update budget category", ResultType.INVALID);

            Result<BudgetCategory> actual = service.create(created, 2, 2, 1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class delete{
        @Test
        void success(){
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());
            when(bcRepository.delete(1)).thenReturn(true);

            Result<BudgetCategory> expected = new Result<>();
            expected.setpayload(TestDataHelper.budgetCategory());

            Result<BudgetCategory> actual = service.delete(1, 1);

            assertEquals(expected, actual);

        }

        @Test
        void failsWhenBCNotFound(){
            when(bcRepository.findById(1)).thenReturn(null);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot delete a budget category that does not exist", ResultType.NOT_FOUND);

            Result<BudgetCategory> actual = service.delete(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserIsDifferent(){
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot delete a budget category that is not yours", ResultType.INVALID);

            Result<BudgetCategory> actual = service.delete(1, 2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenRepoFails(){
            when(bcRepository.findById(1)).thenReturn(TestDataHelper.budgetCategory());
            when(bcRepository.delete(1)).thenReturn(false);

            Result<BudgetCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot delete", ResultType.INVALID);

            Result<BudgetCategory> actual = service.delete(1, 1);

            assertEquals(expected, actual);
        }
    }
}