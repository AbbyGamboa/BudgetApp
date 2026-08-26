package learn.BudgetApp.domain;

import learn.BudgetApp.data.AccountRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Result<Account> findById(int accountId){
        Account found = accountRepository.findById(accountId);
        Result<Account> result = new Result<>();

        if(found == null){
            result.addErrorMessage("No account found", ResultType.NOT_FOUND);
        } else{
            result.setpayload(found);
        }

        return result;
    }

}
