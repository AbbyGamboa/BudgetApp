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
}
