package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Account;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    private void validateAccount (Result<Account> result, Account account){
        if (account == null){
            result.addErrorMessage("No account found", ResultType.NOT_FOUND);
            return;
        }

        if(account.getSubtype() == null || account.getSubtype().isBlank()){
            result.addErrorMessage("Subtype is required", ResultType.INVALID);
        }

        if(account.getUser() == null){
            result.addErrorMessage("User is required", ResultType.INVALID);
        }
    }

    public Result<Account> findById(int accountId){
        Account found = accountRepository.findById(accountId);
        Result<Account> result = new Result<>();

        if(found == null){
            result.addErrorMessage("No account found", ResultType.NOT_FOUND);
            return result;
        }
        validateAccount(result, found);
        return result;
    }

    public Result<List<Account>> findByUser(int userId){
        List <Account> foundAccounts = accountRepository.findByUser(userId);
        Result<List<Account>> result = new Result<>();

        if(foundAccounts.isEmpty()){
            result.addErrorMessage("No accounts found", ResultType.NOT_FOUND);
        } else{
            result.setpayload(foundAccounts);
        }
        return result;
    }

}
