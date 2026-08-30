package learn.BudgetApp.data;

import learn.BudgetApp.models.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAllCategoriesForUser(int userId);

    Category findById(int categoryId);

    Category create(Category category);

    boolean delete(int categoryId);

}
