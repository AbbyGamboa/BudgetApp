package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetCategoryRepository;
import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.CategoryRepository;
import learn.BudgetApp.data.TransactionCategoryRepository;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.TransactionCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

    private final TransactionCategoryRepository repository;
    private final BudgetRepository budgetRepository;


    public TransactionCategoryService(TransactionCategoryRepository repository, BudgetRepository budgetRepository) {
        this.repository = repository;
        this.budgetRepository = budgetRepository;
    }

    public Result<List<TransactionCategory>> findByBudget(int budgetId, int userId){
        Result<List<TransactionCategory>> result = new Result<>();

        Budget found = budgetRepository.findById(budgetId);
        if(found == null){
            result.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            return result;
        }

        if (found.getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access other user's budgets", ResultType.INVALID);
        }

        if(result.isSuccess()){
            List<TransactionCategory> transactionCategoryList = repository.findByBudget(budgetId);

            if(transactionCategoryList.isEmpty()){
                result.addErrorMessage("Budget has no transaction categories", ResultType.NOT_FOUND);
            } else {
                result.setpayload(transactionCategoryList);
            }
        }

        return result;
    }
}
