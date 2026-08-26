package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Budget;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository repository;
    private final UserRepository userRepository;


    public BudgetService(BudgetRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

   public List<Budget> findByUserId(int userId) {return repository.findByUser(userId);}

    public Result<Budget> findById(int budgetId, int userId){
        Result<Budget> result = new Result<>();

        // if we try to access a budget that does not belong to us, we will be told no
        Budget budget = repository.findById(budgetId);
        if (budget == null){
            result.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            return result;
        }

        validate(result, budget);
        if(result.isSuccess()){
            if (budget.getUser().getUserId() == userId){
                result.setpayload(budget);
            } else{
                result.addErrorMessage("Cannot access other's budgets", ResultType.INVALID);
            }
        }

        return result;

    }

    public void validate(Result<Budget> result, Budget budget){
        if (budget.getUser() == null){
            result.addErrorMessage("User is required", ResultType.INVALID);
        }

        if(budget.getUser().getEmail() == null){
            result.addErrorMessage("User requires email", ResultType.INVALID);
        }

        if(budget.getUser().getPassword() == null){
            result.addErrorMessage("User requires password", ResultType.INVALID);
        }


        if(budget.getIncome() == null){
            result.addErrorMessage("Income is required", ResultType.INVALID);
        }
    }

}
