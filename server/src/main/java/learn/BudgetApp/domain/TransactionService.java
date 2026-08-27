package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.TransactionRepository;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final AccountRepository accountRepository;


    public TransactionService(TransactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public Result<Transaction> findById(int transactionId, int userId){
        // if the id we are looking for does not belong to us, we cannot view it
        Result<Transaction> result = new Result<>();

        Transaction found = repository.findById(transactionId);
        if (found == null){
            result.addErrorMessage("Transaction not found", ResultType.NOT_FOUND);
            return result;
        }
        validateTransaction(result, found);
        if(result.isSuccess()){
            Account accountFound = accountRepository.findById(found.getAccount().getAccountId());

            if(accountFound.getUser().getUserId() != userId){
                result.addErrorMessage("Cannot access other user's transactions", ResultType.INVALID);
            }

            if(result.isSuccess()){
                result.setpayload(found);
            }
        }

        return result;
    }

    public Result<List<Transaction>> findByAccount(int accountId, int userId){
        Result<List<Transaction>> result = new Result<>();
        assureCorrectAccountAndUser(result, accountId, userId);

        if(result.isSuccess()){
            List<Transaction> transactions = repository.findByAccount(accountId);

            if (transactions.isEmpty()){
                result.addErrorMessage("Account has no transactions", ResultType.NOT_FOUND);
            } else{
                result.setpayload(transactions);
            }
        }


        return result;
    }

    public Result<List<Transaction>> findByDate(int accountId, LocalDate start, LocalDate end, int userId){
        Result<List<Transaction>> result = new Result<>();

        validDates(result, start, end);
        if(!result.isSuccess()){
            return result;
        }

        assureCorrectAccountAndUser(result, accountId, userId);
        if(result.isSuccess()){
            List<Transaction> transactions = repository.findByDate(accountId, start, end);
            if (transactions.isEmpty()){
                result.addErrorMessage("Account has no transactions within those dates", ResultType.NOT_FOUND);
            } else{
                result.setpayload(transactions);
            }
        }

        return result;
    }

    private void assureCorrectAccountAndUser(Result<List<Transaction>> result, int accountId, int userId){
        Account found = accountRepository.findById(accountId);
        if(found == null){
            result.addErrorMessage("Account not found", ResultType.NOT_FOUND);
            return;
        }

        List<Account> usersAccounts = accountRepository.findByUser(userId);
        if(!usersAccounts.contains(found)){
            result.addErrorMessage("Cannot access other user's accounts", ResultType.INVALID);
        }

    }

    private void validDates(Result<List<Transaction>> result, LocalDate start, LocalDate end){
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

    private void validateTransaction(Result<Transaction> result, Transaction transaction){
        if(transaction.getAccount() == null){
            result.addErrorMessage("Account is required", ResultType.INVALID);
        }

        if(transaction.getAmount() == null| transaction.getAmount().signum() == 0 || transaction.getAmount().signum() == -1){
            result.addErrorMessage("Amount is required and cannot be negative", ResultType.INVALID);
        }

        if(transaction.getDate() == null | transaction.getDate().isAfter(LocalDate.now())){
            result.addErrorMessage("Date is required and cannot be in the future", ResultType.INVALID);
        }


    }
}
