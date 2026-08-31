package learn.BudgetApp.data;

import learn.BudgetApp.models.BudgetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BudgetCategoryJdbcClientRepositoryTest {

    @Autowired
    private BudgetCategoryJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class findByBudget{

        @Test
        void success(){
            List<BudgetCategory> expected = TestDataHelper.budgetOneBCList();
            List<BudgetCategory> actual = repository.findByBudget(1);

            assertEquals(expected, actual);
        }

        @Test
        void emptyListWhenBudgetDoesNotExist(){
            List<BudgetCategory> actual = repository.findByBudget(99);

            assertEquals(0, actual.size());
        }

    }

    @Nested
    class findById{
        @Test
        void success(){
            BudgetCategory expected = TestDataHelper.budgetCategory();
            BudgetCategory actual = repository.findById(1);
            assertEquals(expected, actual);
        }

        @Test
        void notFound(){
            BudgetCategory actual = repository.findById(99);
            assertNull(actual);
        }
    }

}