package learn.BudgetApp.data;

import learn.BudgetApp.models.User;

public interface UserRepository {
    public User findByEmail(String email) throws DataAccessException;

    public User findByUsername(String username) throws DataAccessException;

    public User create(User user) throws DataAccessException;

    public User findById(int userId) throws DataAccessException;

}
