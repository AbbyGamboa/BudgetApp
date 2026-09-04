package learn.BudgetApp.domain;

import learn.BudgetApp.data.*;
import learn.BudgetApp.models.TransactionCategory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
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

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private BudgetCategoryRepository budgetCategoryRepository;

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

    @Nested
    class findByTransaction{
        @Test
        void success(){
            when(transactionRepository.findById(1)).thenReturn(TestDataHelper.firstTransaction());
            when(tcRepository.findByTransactionId(1)).thenReturn(TestDataHelper.firstTC());

            Result<TransactionCategory> expected = new Result<>();
            expected.setpayload(TestDataHelper.firstTC());
            Result<TransactionCategory> actual = service.findByTransaction(1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenTransactionNotFound(){
            when(transactionRepository.findById(1)).thenReturn(null);

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Transaction not found", ResultType.NOT_FOUND);
            Result<TransactionCategory> actual = service.findByTransaction(1,1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserDiffers(){
            when(transactionRepository.findById(1)).thenReturn(TestDataHelper.firstTransaction());

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's transactions", ResultType.NOT_FOUND);
            Result<TransactionCategory> actual = service.findByTransaction(1,2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenRepoFails(){
            when(transactionRepository.findById(1)).thenReturn(TestDataHelper.firstTransaction());
            when(tcRepository.findByTransactionId(1)).thenReturn(null);

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot find by transaction", ResultType.NOT_FOUND);
            Result<TransactionCategory> actual = service.findByTransaction(1,1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findByCategoryAndDate{
        @Test
        void success(){
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(categoryRepository.findAllCategoriesForUser(1)).thenReturn(TestDataHelper.categoriesForUserOne());
            when(tcRepository.findByDate(1, LocalDate.of(2025,1, 1), LocalDate.now())).thenReturn(
                    List.of(TestDataHelper.secondTC())
            );

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.setpayload(List.of(TestDataHelper.secondTC()));

            Result<List<TransactionCategory>> actual = service.findByNameAndDate(1, LocalDate.of(2025, 1, 1), LocalDate.now(), 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenCategoryNotFound(){
            when(categoryRepository.findById(1)).thenReturn(null);

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("Cannot find category", ResultType.NOT_FOUND);

            Result<List<TransactionCategory>> actual = service.findByNameAndDate(1, LocalDate.of(2025, 1, 1), LocalDate.now(), 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserDoesNotOwnCategory(){
            when(categoryRepository.findById(2)).thenReturn(TestDataHelper.customCategory());

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("Cannot access another user's categories", ResultType.INVALID);

            Result<List<TransactionCategory>> actual = service.findByNameAndDate(2, LocalDate.of(2025, 1, 1), LocalDate.now(), 2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenDatesInvalid(){
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(categoryRepository.findAllCategoriesForUser(1)).thenReturn(TestDataHelper.categoriesForUserOne());

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("End date cannot be after today's date", ResultType.INVALID);

            Result<List<TransactionCategory>> actual = service.findByNameAndDate(1, LocalDate.of(2025, 1, 1), LocalDate.of(2027,1,1), 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenNothingFoundInList(){
            when(categoryRepository.findById(1)).thenReturn(TestDataHelper.firstCategory());
            when(categoryRepository.findAllCategoriesForUser(1)).thenReturn(TestDataHelper.categoriesForUserOne());
            when(tcRepository.findByDate(1, LocalDate.of(2025,1, 1), LocalDate.now())).thenReturn(
                    List.of()
            );

            Result<List<TransactionCategory>> expected = new Result<>();
            expected.addErrorMessage("No transactions found", ResultType.NOT_FOUND);

            Result<List<TransactionCategory>> actual = service.findByNameAndDate(1, LocalDate.of(2025, 1, 1), LocalDate.now(), 1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class create{
        @Test
        void success(){
            TransactionCategory created = TestDataHelper.createdTC();

            when(transactionRepository.findById(3)).thenReturn(TestDataHelper.thirdTransaction());
            when(budgetCategoryRepository.findById(3)).thenReturn(TestDataHelper.secondUserBC());
            when(tcRepository.create(created)).thenReturn(created);

            Result<TransactionCategory> expected = new Result<>();
            expected.setpayload(created);

            Result<TransactionCategory> actual = service.create(created, 2,3, 3);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenTransactionIsNull(){
            TransactionCategory created = null;

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Transaction category invalid", ResultType.NOT_FOUND);

            Result<TransactionCategory> actual = service.create(created, 2,3,3);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenMissingContent(){
            TransactionCategory created = new TransactionCategory(null, null);
            when(transactionRepository.findById(99)).thenReturn(null);
            when(budgetCategoryRepository.findById(3)).thenReturn(TestDataHelper.secondUserBC());
            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Transaction is required", ResultType.INVALID);

            Result<TransactionCategory> actual = service.create(created, 2,99,3);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenDifferentUser(){
            TransactionCategory created = TestDataHelper.createdTC();

            when(transactionRepository.findById(3)).thenReturn(TestDataHelper.thirdTransaction());
            when(budgetCategoryRepository.findById(3)).thenReturn(TestDataHelper.secondUserBC());

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Cannot access another user's account", ResultType.INVALID);

            Result<TransactionCategory> actual = service.create(created, 1,3,3);

            assertEquals(expected, actual);
        }

        @Test
        void failsInRepo(){
            TransactionCategory created = TestDataHelper.createdTC();

            when(transactionRepository.findById(3)).thenReturn(TestDataHelper.thirdTransaction());
            when(budgetCategoryRepository.findById(3)).thenReturn(TestDataHelper.secondUserBC());
            when(tcRepository.create(created)).thenReturn(null);

            Result<TransactionCategory> expected = new Result<>();
            expected.addErrorMessage("Could not create", ResultType.INVALID);

            Result<TransactionCategory> actual = service.create(created, 2,3,3);

            assertEquals(expected, actual);
        }

    }


}