package learn.BudgetApp.domain;

import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Category;
import learn.BudgetApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;


    public CategoryService(CategoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Result<List<Category>> findByUser(int userId) {
        Result<List<Category>> result = new Result<>();

        User found = userRepository.findById(userId);
        if (found == null) {
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        List<Category> categories = repository.findAllCategoriesForUser(userId);
        if(categories.isEmpty()){
            result.addErrorMessage("User has no categories", ResultType.NOT_FOUND);
        }

        if(result.isSuccess()){
            result.setpayload(categories);
        }
        return result;
    }

    public Result<Category> findById(int categoryId, int userId){
        Result<Category> result = new Result<>();

        Category category = repository.findById(categoryId);
        if (category == null){
            result.addErrorMessage("Category not found", ResultType.NOT_FOUND);
            return result;
        }

        if(category.getUser() != null && category.getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access other user's categories", ResultType.INVALID);
        }

        if(result.isSuccess()){
            result.setpayload(category);
        }

        return result;
    }
}
