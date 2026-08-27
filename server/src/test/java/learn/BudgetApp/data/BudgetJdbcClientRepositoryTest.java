package learn.BudgetApp.data;

import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BudgetJdbcClientRepositoryTest {

    @Autowired
    private BudgetRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class findByUser{
        @Test
        void success(){
            List<Budget> expected = List.of(new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000)));
            List<Budget> actual = repository.findByUser(1);

            assertEquals(expected, actual);
        }

        @Test
        void ListEmptyWhenUserNotFound(){
            List<Budget> actual = repository.findByUser(999);

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class findById{
        @Test
        void success(){
           Budget actual = repository.findById(1);
           Budget expected = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(4000));

           assertEquals(expected, actual);
        }

        @Test
        void NotFoundWhenNotThere(){
            Budget actual = repository.findById(99);

            assertNull(actual);
        }
    }

    @Nested
    class update{
        @Test
        void success(){
            Budget update = new Budget(1, TestDataHelper.existingUser(), BigDecimal.valueOf(5000));
            boolean actual = repository.update(update);

            assertTrue(actual);
        }

        @Test
        void failToUpdateWithIncorrectUserAndBudget(){
            Budget update = new Budget(2, TestDataHelper.existingUser(), BigDecimal.valueOf(5000));
            boolean actual = repository.update(update);

            assertFalse(actual);
        }

    }

    @Nested
    class create{
        @Test
        void success(){
            Budget create = new Budget(3, TestDataHelper.existingUser(), BigDecimal.valueOf(950.40));
            Budget actual = repository.create(create);

            assertEquals(create, actual);
        }
    }

}