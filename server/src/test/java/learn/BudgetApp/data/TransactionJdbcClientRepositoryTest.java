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
}