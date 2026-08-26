package learn.BudgetApp.data;

import learn.BudgetApp.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

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




}