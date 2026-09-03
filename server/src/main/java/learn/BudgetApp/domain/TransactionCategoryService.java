package learn.BudgetApp.domain;

import learn.BudgetApp.data.*;
import learn.BudgetApp.models.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionCategoryService {

    private final TransactionCategoryRepository repository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionCategoryService(TransactionCategoryRepository repository, BudgetRepository budgetRepository, TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
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

    public Result<List<TransactionCategory>> findByNameAndDate(int categoryId, LocalDate start, LocalDate end, int userId){
        Result<List<TransactionCategory>> result = new Result<>();

        Category found = categoryRepository.findById(categoryId);
        if(found == null){
            result.addErrorMessage("Cannot find category", ResultType.NOT_FOUND);
            return result;
        }

        List<Category> forUser = categoryRepository.findAllCategoriesForUser(userId);
        if(!forUser.contains(found) && found.getUser() != null){
            result.addErrorMessage("Cannot access another user's categories", ResultType.INVALID);
            return result;
        }

        validDates(result, start, end);
        if(!result.isSuccess()){
            return  result;
        }

        List<TransactionCategory> foundByNameAndDate = repository.findByDate(categoryId, start, end);
        if(foundByNameAndDate.isEmpty()){
            result.addErrorMessage("No transactions found", ResultType.NOT_FOUND);
        } else{
            result.setpayload(foundByNameAndDate);
        }

        return result;
    }

    private void validDates(Result<List<TransactionCategory>> result, LocalDate start, LocalDate end){
        if(start == null || end == null){
            result.addErrorMessage("Date missing", ResultType.NOT_FOUND);
            return;
        }

        if(start.isAfter(end)){
            result.addErrorMessage("Start date cannot be after end date", ResultType.INVALID);
        }

        if(start.isAfter(LocalDate.now())){
            result.addErrorMessage("Start date cannot be after today's date", ResultType.INVALID);
        }

        if(end.isAfter(LocalDate.now())){
            result.addErrorMessage("End date cannot be after today's date", ResultType.INVALID);
        }

    }
}
