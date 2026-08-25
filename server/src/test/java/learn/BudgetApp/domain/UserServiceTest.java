package learn.BudgetApp.domain;

import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Nested
    class Authenticate{
        @Test
        void failWhenUserNotFound() throws DataAccessException {
            String notFound = "idk@gmail.com";
            Result<User> result = service.authenticate(notFound, "idkPass");

            assertEquals(ResultType.NOT_FOUND, result.getResultType());
            assertTrue(result.getErrorMessages().contains("User not found"));
        }

        @Test
        void failsWhenProposedPasswordDoesNotMatchDatabasePassword() throws DataAccessException{
            String email = "user@user.com";

            when(repository.findByEmail(email)).thenReturn(new User(3, email, "password"));
            Result<User> result = service.authenticate(email, "Wrong_password");

            assertEquals(ResultType.INVALID, result.getResultType());
            assertTrue(result.getErrorMessages().contains("Incorrect password"));
        }

        @Test
        void happyPath() throws DataAccessException{
            String email = "user@user.com";
            String password = "correct";

            when(repository.findByEmail(email)).thenReturn(new User(3, email, password));
            Result<User> result = service.authenticate(email, password);

            assertEquals(0, result.getErrorMessages().size());
            assertNotNull(result.getpayload());
        }
    }

    @Nested
    class Create{
        @Test
        void createFailsWhenEmailIsBlank() throws DataAccessException {
            User toCreate = TestDataHelper.userToCreate();
            toCreate.setEmail("");

            Result<User> actual = service.create(toCreate);

            assertEquals(ResultType.INVALID, actual.getResultType());
            assertTrue(actual.getErrorMessages().contains("Email cannot be blank"));
        }

        @Test
        void createFailsWhenPasswordIsBlank() throws DataAccessException {
            User toCreate = TestDataHelper.userToCreate();
            toCreate.setPassword("");

            Result<User> actual = service.create(toCreate);

            assertEquals(ResultType.INVALID, actual.getResultType());
            assertTrue(actual.getErrorMessages().contains("Password cannot be blank"));
        }

        @Test
        void createFailsWhenEmailIsDuplicate() throws DataAccessException {
            when(repository.findByEmail(TestDataHelper.userToCreate().getEmail())).thenReturn(TestDataHelper.existingUser());

            Result<User> actual = service.create(TestDataHelper.userToCreate());

            assertEquals(ResultType.INVALID, actual.getResultType());
            assertTrue(actual.getErrorMessages().contains("Email is already taken"));
        }

        @Test
        void createHappyPath() throws DataAccessException {
            when(repository.create(TestDataHelper.userToCreate())).thenReturn(TestDataHelper.userAfterCreate());

            Result<User> actual = service.create(TestDataHelper.userToCreate());

            assertTrue(actual.isSuccess());
            assertEquals(TestDataHelper.userAfterCreate(), actual.getpayload());
        }
    }

}