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
            expected.addErrorMessage("Cannot access other's transactions", ResultType.INVALID);

            Result<Transaction> actual = service.findById(1,99);

            assertEquals(expected, actual);
        }
    }


}