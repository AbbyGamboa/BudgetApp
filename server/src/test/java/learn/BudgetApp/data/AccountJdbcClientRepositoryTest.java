package learn.BudgetApp.data;

import learn.BudgetApp.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountJdbcClientRepositoryTest {

    @Autowired
    private AccountJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class findById{
        @Test
        void success(){
            Account expected = new Account(1, TestDataHelper.existingUser(), "Checkings");
            Account actual = repository.findById(1);

            assertEquals(expected, actual);
        }

        @Test
        void notFound(){
            Account actual = repository.findById(99);

            assertNull(actual);
        }
    }

    @Nested
    class findByUser{
        @Test
        void success(){
            List<Account> expected = List.of(new Account(1, TestDataHelper.existingUser(), "Checkings"));
            List<Account> actual = repository.findByUser(1);

            assertEquals(expected, actual);
        }

        @Test
        void notFound(){
            List<Account> actual = repository.findByUser(99);

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class create{

        @Test
        void success(){
            Account created = new Account(3, TestDataHelper.existingUser(), "Savings");
            Account actual = repository.create(created);

            assertEquals(created, actual);
        }

    }




}