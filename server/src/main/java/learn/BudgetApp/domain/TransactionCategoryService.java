package learn.BudgetApp.domain;

import learn.BudgetApp.data.*;
import learn.BudgetApp.models.Budget;
import learn.BudgetApp.models.BudgetCategory;
import learn.BudgetApp.models.Transaction;
import learn.BudgetApp.models.TransactionCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryService {

    private final TransactionCategoryRepository repository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public TransactionCategoryService(TransactionCategoryRepository repository, BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        this.repository = repository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
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

    public Result<TransactionCategory> findByTransaction(int transactionId, int userId){
        Result<TransactionCategory> result = new Result<>();

        Transaction found = transactionRepository.findById(transactionId);

        if (found == null){
            result.addErrorMessage("Transaction not found", ResultType.NOT_FOUND);
            return result;
        }

        if (found.getAccount().getUser().getUserId() != userId){
            result.addErrorMessage("Cannot access other user's transactions", ResultType.NOT_FOUND);
        }

        if (result.isSuccess()) {
            TransactionCategory transactionCategory = repository.findByTransactionId(transactionId);
            if (transactionCategory != null) {
                result.setpayload(transactionCategory);
            } else {
                result.addErrorMessage("Cannot find by transaction", ResultType.NOT_FOUND);
            }
        }

        return result;
    }
}
