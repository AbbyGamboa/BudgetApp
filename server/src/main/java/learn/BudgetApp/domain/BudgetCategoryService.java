package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetCategoryRepository;
import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.Category;
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

    public Result<BudgetCategory> findById(int budgetCategoryId, int userId){
        Result<BudgetCategory> result = new Result<>();

        BudgetCategory found = repository.findById(budgetCategoryId);

        if(found == null){
            result.addErrorMessage("Budget Category not found", ResultType.NOT_FOUND);
            return result;
        }

        if (found.getBudget().getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access another user's budget categories", ResultType.INVALID);
        }

        if(result.isSuccess()){
            result.setpayload(found);
        }
        return result;
    }


    public Result<BudgetCategory> updatePercentage(BudgetCategory budgetCategory, int userId, int budgetId, int categoryId){
        Result<BudgetCategory> result = new Result<>();

        if (budgetCategory == null){
            result.addErrorMessage("Budget Category is not defined", ResultType.NOT_FOUND);
            return result;
        }
        setBudgetAndCategory(budgetCategory, budgetId, categoryId);

        validateBC(result, budgetCategory);
        if(!result.isSuccess()){
            return result;
        }

        BudgetCategory found = repository.findById(budgetCategory.getBudgetCategoryId());
        if(found == null){
            result.addErrorMessage("Cannot find budgetCategory", ResultType.NOT_FOUND);
            return result;
        }
        if(found.getBudget().getBudgetId() != budgetCategory.getBudget().getBudgetId() || found.getCategory().getCategoryId() != budgetCategory.getCategory().getCategoryId()){
             result.addErrorMessage("Cannot change a budget or category", ResultType.INVALID);
        }

        if(found.getBudget().getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access another user's budget category", ResultType.INVALID);
        }

        if(result.isSuccess()){
            boolean successfulUpdate = repository.updateAmount(budgetCategory);
            if(successfulUpdate){
                result.setpayload(budgetCategory);
            } else{
                result.addErrorMessage("Cannot update budget category", ResultType.INVALID);
            }
        }
        return result;
    }

    public void setBudgetAndCategory(BudgetCategory budgetCategory, int budgetId, int categoryId){
        Budget foundBudget = budgetRepository.findById(budgetId);
        budgetCategory.setBudget(foundBudget);

        Category foundCategory = categoryRepository.findById(categoryId);
        budgetCategory.setCategory(foundCategory);
    }

    public Result<BudgetCategory> create(BudgetCategory budgetCategory, int userId, int budgetId, int categoryId){
        Result<BudgetCategory> result = new Result<>();

        if (budgetCategory == null){
            result.addErrorMessage("Budget Category is not defined", ResultType.NOT_FOUND);
            return result;
        }
        setBudgetAndCategory(budgetCategory, budgetId, categoryId);

        validateBC(result, budgetCategory);
        if(!result.isSuccess()){
            return result;
        }

        BudgetCategory found = repository.findById(budgetCategory.getBudgetCategoryId());
        if(found != null){
            result.addErrorMessage("Cannot create budgetCategory that is already made", ResultType.INVALID);
            return result;
        }

        if(budgetCategory.getBudget().getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access another user's budget category", ResultType.INVALID);
        }

        validateCreatingBC(result, budgetCategory);

        if(result.isSuccess()){
            BudgetCategory created = repository.create(budgetCategory);
            if(created != null){
                result.setpayload(budgetCategory);
            } else{
                result.addErrorMessage("Cannot update budget category", ResultType.INVALID);
            }
        }
        return result;

    }

    public Result<BudgetCategory> delete(int budgetCategoryId, int userId){
        Result<BudgetCategory> result = new Result<>();

        BudgetCategory exists = repository.findById(budgetCategoryId);
        if(exists ==null){
            result.addErrorMessage("Cannot delete a budget category that does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if (exists.getBudget().getUser().getUserId() != userId){
            result.addErrorMessage("Cannot delete a budget category that is not yours", ResultType.INVALID);
        }

        if(result.isSuccess()){
            boolean delete = repository.delete(budgetCategoryId);
            if(delete){
                result.setpayload(exists);
            } else{
                result.addErrorMessage("Cannot delete", ResultType.INVALID);
            }
        }

        return result;
    }

    public void validateBC(Result<BudgetCategory> result, BudgetCategory budgetCategory){
        if (budgetCategory.getBudget() == null){
            result.addErrorMessage("Budget is required", ResultType.INVALID);
        }

        if(budgetCategory.getCategory() == null){
            result.addErrorMessage("Category is required", ResultType.INVALID);
        }

        if(budgetCategory.getPercentage() ==null|| budgetCategory.getPercentage().signum() == 0 || budgetCategory.getPercentage().signum() == -1){
            result.addErrorMessage("Percentage is required and must be positive", ResultType.INVALID);
        }
    }

    public void validateCreatingBC(Result<BudgetCategory> result, BudgetCategory budgetCategory){
        List<BudgetCategory> fromBudget = repository.findByBudget(budgetCategory.getBudget().getBudgetId());

        for (BudgetCategory bc: fromBudget){
            if (bc.getBudget().getBudgetId() == budgetCategory.getBudget().getBudgetId() && bc.getCategory().getCategoryId() == budgetCategory.getCategory().getCategoryId()){
                result.addErrorMessage("Cannot have duplicate categories in the same budget", ResultType.INVALID);
            }
        }
    }
}
