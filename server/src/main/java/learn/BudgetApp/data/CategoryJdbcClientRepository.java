package learn.BudgetApp.data;

import learn.BudgetApp.models.Category;
import learn.BudgetApp.models.mapping.CategoryMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryJdbcClientRepository implements CategoryRepository{

    private final JdbcClient jdbcClient;

    public CategoryJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SELECT = """
            select c.categoryId, c.name, c.userId, u.email, u.password
            from categories c left outer join user u on c.userId = u.userId
            """;


    @Override
    public List<Category> findAllCategoriesForUser(int userId) {
        String sql = BASE_SELECT + " where c.userId is null or c.userId = ?;";

        return jdbcClient.sql(sql).param(userId).query(new CategoryMapper()).list();
    }

    @Override
    public Category findById(int categoryId) {
        String sql = BASE_SELECT + " where c.categoryId = ?;";
        return jdbcClient.sql(sql).param(categoryId).query(new CategoryMapper()).optional().orElse(null);
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public boolean delete(Category category) {
        return false;
    }
}
