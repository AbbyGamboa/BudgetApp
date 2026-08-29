package learn.BudgetApp.data;

import learn.BudgetApp.models.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionJdbcClientRepositoryTest {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class findById{
        @Test
        void success(){
            Transaction expected = TestDataHelper.firstTransaction();
            Transaction actual = repository.findById(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIdNotReal(){
            Transaction actual = repository.findById(99);

            assertNull(actual);
        }
    }

    @Nested
    class findByAccount{
        @Test
        void success(){
            List<Transaction> expected = TestDataHelper.allAccountOneTransactions();
            List<Transaction> actual = repository.findByAccount(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWithAccountNotInDB(){
            List<Transaction> actual = repository.findByAccount(99);

            assertEquals(0, actual.size());
        }
    }

    @Nested
    class findByDate{
        @Test
        void success(){
            List<Transaction> expected =TestDataHelper.allAccountOneTransactions();
            List<Transaction> actual = repository.findByDate(1, LocalDate.of(2025, 1,1), LocalDate.of(2027,1,1));

            assertEquals(expected, actual);
        }

        @Test
        void nothingFound(){
            List<Transaction> actual = repository.findByDate(1, LocalDate.now(), LocalDate.now());
            assertEquals(0, actual.size());
        }
    }

    @Nested
    class create{
        @Test
        void success(){
            Transaction expected = new Transaction(3, TestDataHelper.existingAccount(), BigDecimal.valueOf(10.50), LocalDate.now(), null, null);
            Transaction actual = repository.create(expected);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class update{
        @Test
        void success(){
            Transaction updated = TestDataHelper.firstTransaction();
            updated.setDate(LocalDate.now());

            boolean actual = repository.update(updated);
            assertTrue(actual);
        }

        @Test
        void fails(){
            Transaction notInDB= TestDataHelper.createdTransaction();

            boolean actual = repository.update(notInDB);
            assertFalse(actual);
        }
    }
}