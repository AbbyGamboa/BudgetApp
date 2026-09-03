package learn.BudgetApp.data;

import learn.BudgetApp.models.TransactionCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionCategoryJdbcClientRepositoryTest {

    @Autowired
    public TransactionCategoryJdbcClientRepository repository;

    @Autowired
    public JdbcClient jdbcClient;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class listByBudget{

        @Test
        void success(){
            List<TransactionCategory> actual = repository.findByBudget(1);
            List<TransactionCategory> expected = TestDataHelper.tcOfBudgetOne();

            assertEquals(expected, actual);
        }

        @Test
        void nothingListsWhenBudgetIdDoesNotExist(){
            List<TransactionCategory> actual = repository.findByBudget(99);
            List<TransactionCategory> expected = List.of();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class findByTransactionId{
        @Test
        void success(){
            TransactionCategory expected = TestDataHelper.firstTC();
            TransactionCategory actual = repository.findByTransactionId(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenTransactionIdNotFound(){
            TransactionCategory actual = repository.findByTransactionId(99);

            assertNull(actual);
        }
    }

    @Nested
    class findByDate{
        @Test
        void success(){
            List<TransactionCategory> expected = List.of(TestDataHelper.secondTC());
            List<TransactionCategory> actual = repository.findByDate(1, LocalDate.of(2025, 1, 1), LocalDate.now());

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenNameUnknown(){
            List<TransactionCategory> expected = List.of();
            List<TransactionCategory> actual = repository.findByDate(99, LocalDate.of(2025, 1, 1), LocalDate.now());

            assertEquals(expected, actual);
        }
    }

}