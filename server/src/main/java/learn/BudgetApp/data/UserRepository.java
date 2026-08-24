package learn.BudgetApp.data;

import learn.BudgetApp.models.User;

public interface UserRepository {
    User findByEmail(String email) throws DataAccessException;

    User findByUsername(String username) throws DataAccessException;

    User create(User user) throws DataAccessException;

    User findById(int userId) throws DataAccessException;

}
