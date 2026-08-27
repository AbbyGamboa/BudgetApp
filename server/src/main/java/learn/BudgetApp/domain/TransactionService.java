package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.TransactionRepository;
import learn.BudgetApp.models.Account;
import learn.BudgetApp.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                result.addErrorMessage("Cannot access other's transactions", ResultType.INVALID);
            }

            if(result.isSuccess()){
                result.setpayload(found);
            }
        }

        return result;
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
