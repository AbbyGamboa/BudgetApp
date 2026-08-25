package learn.BudgetApp.domain;

import learn.BudgetApp.data.PlaidItemRepository;
import learn.BudgetApp.data.TestDataHelper;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlaidItemServiceTest {
    @Autowired
    PlaidItemService service;

    @MockBean
    PlaidItemRepository repository;

    @MockBean
    UserRepository userRepository;

    @Nested
    class Create{
        @Test
        void successfullyCreate(){
            PlaidItems creating = new PlaidItems("TESTING",
                    TestDataHelper.existingUser(),"TEST TOKEN", "FAKE BANK");

            when(userRepository.findById(1)).thenReturn(TestDataHelper.existingUser());
            when(userRepository.findByEmail("a@a.com")).thenReturn(TestDataHelper.existingUser());
            when(repository.create(creating)).thenReturn(creating);

            Result<PlaidItems> expected = new Result<>();
            expected.setpayload(creating);
            Result<PlaidItems> actual = service.create(creating);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotSucceedIfUserDoesNotExist(){
            User doesNotExist = TestDataHelper.existingUser();
            doesNotExist.setUserId(999);
            PlaidItems creating = new PlaidItems("TESTING",
                    TestDataHelper.existingUser(),"TEST TOKEN", "FAKE BANK");

            when(userRepository.findById(999)).thenReturn(null);

            Result<PlaidItems> expected = new Result<>();
            expected.addErrorMessage("User not found", ResultType.NOT_FOUND);
            Result<PlaidItems> actual = service.create(creating);

            assertEquals(expected, actual);
        }


        @Test
        void shouldNotSucceedIfUserIsIncorrect(){
            User doesNotExist = TestDataHelper.existingUser();
            doesNotExist.setUserId(2);
            PlaidItems creating = new PlaidItems("TESTING",
                    doesNotExist,"TEST TOKEN", "FAKE BANK");

            when(userRepository.findById(2)).thenReturn(doesNotExist);
            when(userRepository.findByEmail("a@a.com")).thenReturn(TestDataHelper.existingUser());

            Result<PlaidItems> expected = new Result<>();
            expected.addErrorMessage("Incorrect credentials", ResultType.INVALID);
            Result<PlaidItems> actual = service.create(creating);

            assertEquals(expected, actual);
        }

    }
}