package learn.BudgetApp.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import learn.BudgetApp.models.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJdbcClientRepositoryTest {

    @Autowired
    private UserJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Test
    void shouldFindByEmail() throws DataAccessException {
        User actual = repository.findByEmail("a@a.com");
        User expected = new User(1, "a@a.com", "a");

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindUser() throws DataAccessException {
        User actual = repository.findByEmail("dfjaid");

        assertNull(actual);
    }

    @Test
    void shouldCreateUser() throws DataAccessException {
        User expected = new User(3, "a@b.com", "ba");
        User actual = repository.create(expected);

        assertEquals(expected, actual);
        assertNotNull(actual);
    }

    @Test
    void shouldFindById() throws DataAccessException {
        User actual = repository.findById(1);
        User expected = new User(1, "a@a.com", "a");

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById() throws DataAccessException {
        User actual = repository.findById(99);

        assertNull(actual);
    }


}