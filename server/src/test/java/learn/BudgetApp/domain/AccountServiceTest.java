package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {
    @Autowired
    AccountService service;

    @MockBean
    AccountRepository repository;

    @MockBean
    UserRepository userRepository;

    @Nested
    class findById{
        @Test
        void happyPath(){
            Account account = new Account(1, TestDataHelper.existingUser(), "Checkings");
            when(repository.findById(1)).thenReturn(account);
            Result<Account> actual = service.findById(1);
            Result<Account> expected = new Result<>();
            expected.setpayload(account);

            assertEquals(expected, actual);
        }

        @Test
        void accountNotFound(){
            when(repository.findById(999)).thenReturn(null);
            Result<Account> actual = service.findById(99);
            Result<Account> expected = new Result<>();
            expected.addErrorMessage("No account found", ResultType.NOT_FOUND);

            assertEquals(expected, actual);
        }

        @Test
        void accountMissingSubType(){
            Account account = new Account(1, TestDataHelper.existingUser(), null);
            when(repository.findById(1)).thenReturn(account);
            Result<Account> actual = service.findById(1);
            Result<Account> expectedNull = new Result<>();
            expectedNull.addErrorMessage("Subtype is required", ResultType.INVALID);

            assertEquals(expectedNull, actual);

            account = new Account(1, TestDataHelper.existingUser(), "");
            when(repository.findById(1)).thenReturn(account);
            actual = service.findById(1);
            Result<Account> expectedBlank = new Result<>();
            expectedBlank.addErrorMessage("Subtype is required", ResultType.INVALID);

            assertEquals(expectedBlank, actual);
        }

        @Test
        void accountMissingUser(){
            Account account = new Account(1, null, "TESTING");
            when(repository.findById(1)).thenReturn(account);
            Result<Account> actual = service.findById(1);
            Result<Account> expectedNull = new Result<>();
            expectedNull.addErrorMessage("User is required", ResultType.INVALID);

            assertEquals(expectedNull, actual);
        }
    }

    @Nested
    class findByUser{
        @Test
        void happyPath(){
            List<Account> accounts = List.of(new Account(1, TestDataHelper.existingUser(), "Checkings"));
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findByUser(1)).thenReturn(accounts);

            Result<List<Account>> actual = service.findByUser(1);
            Result<List<Account>> expected = new Result<>();
            expected.setpayload(accounts);

            assertEquals(expected, actual);
        }

        @Test
        void unhappyNoAccounts(){
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findByUser(1)).thenReturn(List.of());

            Result<List<Account>> actual = service.findByUser(1);
            Result<List<Account>> expected = new Result<>();
            expected.addErrorMessage("No accounts found", ResultType.NOT_FOUND);

            assertEquals(expected, actual);
        }

        @Test
        void unhappyUserNotFound(){
            when(userRepository.findById(99)).thenReturn(null);

            Result<List<Account>> actual = service.findByUser(1);
            Result<List<Account>> expected = new Result<>();
            expected.addErrorMessage("User not found", ResultType.NOT_FOUND);

            assertEquals(expected, actual);
        }

        @Test
        void unhappyAccountMissingInformation(){
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findByUser(1)).thenReturn(List.of(
                    new Account(1, TestDataHelper.existingUser(), null)
            ));

            Result<List<Account>> actual = service.findByUser(1);
            Result<List<Account>> expected = new Result<>();
            expected.addErrorMessage("Subtype is required", ResultType.INVALID);

            assertEquals(expected, actual);
        }

        @Test
        void unhappyAccountMismatchUser(){
            User user = TestDataHelper.existingUser();
            user.setUserId(2);
            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(repository.findByUser(1)).thenReturn(List.of(
                    new Account(1, user, "Checking")
            ));

            Result<List<Account>> actual = service.findByUser(1);
            Result<List<Account>> expected = new Result<>();
            expected.addErrorMessage("User does not match", ResultType.INVALID);

            assertEquals(expected, actual);
        }
    }

}