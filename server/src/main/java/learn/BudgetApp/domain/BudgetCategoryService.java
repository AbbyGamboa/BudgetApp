package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetCategoryRepository;
import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetCategoryService {

    private final BudgetCategoryRepository repository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;


    public BudgetCategoryService(BudgetCategoryRepository repository, BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public Result<List<BudgetCategory>> findByBudget(int budgetId, int userId){
        Result<List<BudgetCategory>> result = new Result<>();

        Budget foundBudget = budgetRepository.findById(budgetId);
        if (foundBudget == null){
            result.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            return result;
        }

        if (foundBudget.getUser().getUserId() != userId){
            result.addErrorMessage("Budget does not belong to user", ResultType.INVALID);
        }

        if(result.isSuccess()){
            List<BudgetCategory> bcByBudget = repository.findByBudget(budgetId);
            if(bcByBudget.isEmpty()){
                result.addErrorMessage("Budget has no categories", ResultType.NOT_FOUND);
            } else{
                result.setpayload(bcByBudget);
            }
        }

        return result;
    }
}
