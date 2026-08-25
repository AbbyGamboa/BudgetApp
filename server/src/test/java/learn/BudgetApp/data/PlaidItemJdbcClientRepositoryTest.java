package learn.BudgetApp.data;

import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

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

    @Test
    void shouldNotFindById(){
        PlaidItems actual = repository.findById("abejoiwj192031");

        assertNull(actual);
    }

    @Test
    void shouldListOut(){
        List<PlaidItems> expected = List.of( new PlaidItems("123", TestDataHelper.existingUser(), "abc123", "US Bank"));
        List<PlaidItems> actual = repository.findAllByUserId(1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindNothingIfUserDoesNotExist(){
        List<PlaidItems> actual = repository.findAllByUserId(99);

        assertEquals(0, actual.size());
    }

    @Test
    void shouldCreatePlaidItem(){
        PlaidItems plaidItem = new PlaidItems(
                "456",
                TestDataHelper.existingUser(),
                "xyz789",
                "Chase"
        );

        PlaidItems actual = repository.create(plaidItem);
        assertEquals(plaidItem, actual);
    }



}