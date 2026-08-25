package learn.BudgetApp.data;

import learn.BudgetApp.models.PlaidItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaidItemJdbcClientRepositoryTest {

    @Autowired
    private PlaidItemJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup(){jdbcClient.sql("call set_known_good_state();").update();}

    @Test
    void findById(){
        PlaidItems expected = new PlaidItems("123", TestDataHelper.existingUser(), "abc123", "US Bank");
        PlaidItems actual = repository.findById("123");

        assertEquals(expected, actual);
    }


}