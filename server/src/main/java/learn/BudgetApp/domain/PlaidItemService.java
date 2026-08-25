package learn.BudgetApp.domain;

import learn.BudgetApp.data.PlaidItemRepository;
import learn.BudgetApp.data.UserRepository;
import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaidItemService {

    private final PlaidItemRepository repository;
    private final UserRepository userRepository;

    public PlaidItemService(PlaidItemRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public PlaidItems findById(String plaidId){
        return repository.findById(plaidId);
    }

    public Result<PlaidItems> create (PlaidItems plaidItems){
        //validate
        Result<PlaidItems> result = new Result<>();

        if(plaidItems == null){
            result.addErrorMessage("Plaid Item is required", ResultType.INVALID);
            return result;
        }

        if(plaidItems.getUser() == null){
            result.addErrorMessage("User is required", ResultType.INVALID);
            return result;
        }
        User user = userRepository.findById(plaidItems.getUser().getUserId());
        if(user == null){
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        if(userRepository.findByEmail(user.getEmail()).getUserId() != user.getUserId()){
            result.addErrorMessage("Incorrect credentials", ResultType.INVALID);
            return result;
        }
        //save
        PlaidItems created = repository.create(plaidItems);
        result.setpayload(created);

        return result;
    }

    public List<PlaidItems> findByUser(int user){
        return repository.findAllByUserId(user);
    }

    public boolean deleteById(String plaidItemId){
        return repository.delete(plaidItemId);
    }
}
