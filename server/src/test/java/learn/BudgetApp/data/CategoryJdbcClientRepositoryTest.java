package learn.BudgetApp.data;

import learn.BudgetApp.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryJdbcClientRepositoryTest {

    @Autowired
    public JdbcClient jdbcClient;

    @Autowired
    public CategoryRepository repository;

    @BeforeEach
    void setUp(){jdbcClient.sql("call set_known_good_state();").update();}

    @Nested
    class findByUser{
        @Test
        void success(){
            List<Category> expected = TestDataHelper.categoriesForUserOne();
            List<Category> actual = repository.findAllCategoriesForUser(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenUserDoesNotExist(){
            List<Category> actual = repository.findAllCategoriesForUser(99);

            assertEquals(List.of(new Category(1, "Tuition", null)), actual);

        }
    }

    @Nested
    class findById{
        @Test
        void success(){
            Category expected = new Category(1, "Tuition", null);
            Category actual = repository.findById(1);

            assertEquals(expected, actual);
        }

        @Test
        void failsWhenIdNotFound(){
            Category actual = repository.findById(99);

            assertNull(actual);
        }
    }

}