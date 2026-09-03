package learn.BudgetApp.data;

import learn.BudgetApp.models.TransactionCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

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
    }

}