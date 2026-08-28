package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.TransactionRepository;
import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService service;

    @MockBean
    private TransactionRepository repository;

    @MockBean
    private AccountRepository accountRepository;

    @Nested
    class findById{
        @Test
        void success(){

            when(repository.findById(1)).thenReturn(TestDataHelper.firstTransaction());
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            Result<Transaction> expected = new Result<>();
            expected.setpayload(TestDataHelper.firstTransaction());

            Result<Transaction> actual = service.findById(1,1);

            assertEquals(expected, actual);
        }

        @Test
        void cannotFindIfNotInDB(){
            when(repository.findById(999)).thenReturn(null);
            Result<Transaction> expected = new Result<>();
            expected.addErrorMessage("Transaction not found", ResultType.NOT_FOUND);

            Result<Transaction> actual = service.findById(999,1);

            assertEquals(expected, actual);
        }

        @Test
        void cannotFindIfUserDoesNotMatch(){
            when(repository.findById(1)).thenReturn(TestDataHelper.firstTransaction());
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            Result<Transaction> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's transactions", ResultType.INVALID);

            Result<Transaction> actual = service.findById(1,99);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findByAccount{
        @Test
        void success(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(TestDataHelper.allUserOneAccounts());
            when(repository.findByAccount(1)).thenReturn(TestDataHelper.allAccountOneTransactions());


            Result<List<Transaction>> expected = new Result<>();
            expected.setpayload(TestDataHelper.allAccountOneTransactions());
            Result<List<Transaction>> actual = service.findByAccount(1, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenADifferentUserTriesToAccess(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(2)).thenReturn(List.of());

            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's accounts", ResultType.INVALID);
            Result<List<Transaction>> actual = service.findByAccount(1, 2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenAccountNotFound(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());

            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Account not found", ResultType.NOT_FOUND);
            Result<List<Transaction>> actual = service.findByAccount(99, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenNoTransactionsAreFound(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(TestDataHelper.allUserOneAccounts());
            when(repository.findByAccount(1)).thenReturn(List.of());


            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Account has no transactions", ResultType.NOT_FOUND);
            Result<List<Transaction>> actual = service.findByAccount(1, 1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findByDate{
        @Test
        void success(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(TestDataHelper.allUserOneAccounts());
            when(repository.findByDate(1,
                    LocalDate.of(2025, 1, 1),
                    LocalDate.of(2027, 1, 1))).thenReturn(TestDataHelper.allAccountOneTransactions());
            Result<List<Transaction>> expected = new Result<>();
            expected.setpayload(TestDataHelper.allAccountOneTransactions());

            Result<List<Transaction>> actual = service.findByDate(1,
                    LocalDate.of(2025, 1, 1),
                    LocalDate.of(2027, 1, 1),1);
        }

        @Test
        void failsWhenADifferentUserTriesToAccess(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(2)).thenReturn(List.of());

            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's accounts", ResultType.INVALID);
            Result<List<Transaction>> actual = service.findByDate(1, LocalDate.now(), LocalDate.now(),2);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenAccountNotFound(){
            when(accountRepository.findById(1)).thenReturn(null);

            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Account not found", ResultType.NOT_FOUND);
            Result<List<Transaction>> actual = service.findByDate(1, LocalDate.now(), LocalDate.now(),1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenNoTransactionsAreFound(){
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(TestDataHelper.allUserOneAccounts());
            when(repository.findByAccount(1)).thenReturn(List.of());


            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Account has no transactions within those dates", ResultType.NOT_FOUND);
            Result<List<Transaction>> actual = service.findByDate(1,
                    LocalDate.of(2025,1,1),
                    LocalDate.of(2025,1,1),1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenDatesAreIncorrect(){
            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Start date cannot be after end date", ResultType.INVALID);
            Result<List<Transaction>> actual = service.findByDate(1,
                    LocalDate.of(2025,1,1),
                    LocalDate.of(2024,1,1),1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenDatesAreInTheFuture(){
            Result<List<Transaction>> expected = new Result<>();
            expected.addErrorMessage("Start date cannot be after today's date", ResultType.INVALID);
            expected.addErrorMessage("End date cannot be after today's date", ResultType.INVALID);
            Result<List<Transaction>> actual = service.findByDate(1,
                    LocalDate.of(2027,1,1),
                    LocalDate.of(2027,1,1),1);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class create{
        @Test
        void success(){
            Transaction created = TestDataHelper.createdTransaction();
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(TestDataHelper.allUserOneAccounts());
            when(repository.create(created)).thenReturn(created);

            Result<Transaction> expected = new Result<>();
            expected.setpayload(created);

            Result<Transaction> actual = service.create(created, 1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenMissingData(){
            Transaction created = TestDataHelper.createdTransaction();
            created.setAmount(null);

            Result<Transaction> missingAmount = new Result<>();
            missingAmount.addErrorMessage("Amount is required and cannot be negative", ResultType.INVALID);

            Result<Transaction> actual = service.create(created, 1);

            assertEquals(missingAmount, actual);

            created.setAmount(BigDecimal.TEN);
            created.setDate(null);

            Result<Transaction> missingDate = new Result<>();
            missingDate.addErrorMessage("Date is required and cannot be in the future", ResultType.INVALID);

            actual = service.create(created, 1);

            assertEquals(missingDate, actual);

        }

        @Test
        void failsWhenDateIsInTheFuture(){
            Transaction created = TestDataHelper.createdTransaction();
            created.setDate(LocalDate.of(2030, 1, 1));

            Result<Transaction> missingAmount = new Result<>();
            missingAmount.addErrorMessage("Date is required and cannot be in the future", ResultType.INVALID);

            Result<Transaction> actual = service.create(created, 1);

            assertEquals(missingAmount, actual);
        }

        @Test
        void failsWhenUserDoesNotOwnAccount(){
            Transaction created = TestDataHelper.createdTransaction();
            when(accountRepository.findById(1)).thenReturn(TestDataHelper.existingAccount());
            when(accountRepository.findByUser(1)).thenReturn(List.of());

            Result<Transaction> expected = new Result<>();
            expected.addErrorMessage("Cannot access other user's accounts", ResultType.INVALID);

            Result<Transaction> actual = service.create(created, 1);

            assertEquals(expected, actual);
        }
    }

}