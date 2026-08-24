package learn.BudgetApp.domain;

import learn.BudgetApp.models.User;
import org.springframework.stereotype.Service;
import learn.BudgetApp.data.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUserId(int userId){
        return repository.findById(userId);
    }

    public Result<User> authenticate(String username, String proposedPassword){
        Result<User> result = new Result<>();
        User existingUser = repository.findByUsername(username);
        if(existingUser == null){
            result.addErrorMessage("User not found", ResultType.NOT_FOUND);
            return result;
        }

        if(existingUser.getPassword().equals(proposedPassword)){
            result.setpayload(existingUser);
        } else{
            result.addErrorMessage("Incorrect password", ResultType.INVALID);
        }

        return result;
    }

    public Result<User> create(User user){
        Result<User> result = new Result<>();
        if(user.getUsername().isBlank()){
            result.addErrorMessage("Username cannot be blank", ResultType.INVALID);
        }

        if(user.getPassword().isBlank()){
            result.addErrorMessage("Password cannot be blank", ResultType.INVALID);
        }

        if(repository.findByUsername(user.getUsername()) != null){
            result.addErrorMessage("Username is already taken", ResultType.INVALID);
        }

        if(result.isSuccess()){
            User created = repository.create(user);
            result.setpayload(created);
        }

        return result;

    }
}
