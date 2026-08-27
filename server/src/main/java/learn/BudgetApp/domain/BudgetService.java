package learn.BudgetApp.domain;

import learn.BudgetApp.data.BudgetRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public Result<Budget> update(Budget budget, int userId){
        Result<Budget> result = new Result<>();
        if (budget == null){
            result.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            return result;
        }

        if(budget.getBudgetId() <= 0){
            result.addErrorMessage("Budget id is required", ResultType.INVALID);
        }

        boolean providedUserId = budget.getUser() != null;
        Budget existing = repository.findById(budget.getBudgetId());

        if (existing == null){
            result.addErrorMessage("Budget id is not found", ResultType.NOT_FOUND);
            return result;
        }

        if(providedUserId && existing.getUser().getUserId() != userId){
            result.addErrorMessage("Cannot change ownership of a Budget", ResultType.INVALID);
        }
        budget.setUser(existing.getUser());
        validate(result, budget);


        validateIncome(result, budget.getIncome());
        if (result.isSuccess()){
            if (repository.update(budget)){
                result.setpayload(budget);
            } else {
                result.addErrorMessage("Budget id was not found", ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public Result<Budget> create(Budget budget, int userId){
        Result<Budget> result = new Result<>();

        if (budget == null){
            result.addErrorMessage("Budget not found", ResultType.NOT_FOUND);
            return result;
        }
        User user = userRepository.findById(userId);
        if (user == null){
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        budget.setUser(user);
        validate(result, budget);

        if (result.isSuccess()){
            validateIncome(result, budget.getIncome());
            if (result.isSuccess()){
                result.setpayload(repository.create(budget));
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

    private void validateIncome(Result<Budget> results, BigDecimal income){
        if (income == null){
            return;
        }

        if(income.signum() != 1){
            results.addErrorMessage("Income must be higher than 0", ResultType.INVALID);
        }

        if (income.scale() > 2){
            results.addErrorMessage("Income must have 2 decimal places", ResultType.INVALID);
        }


    }

}
