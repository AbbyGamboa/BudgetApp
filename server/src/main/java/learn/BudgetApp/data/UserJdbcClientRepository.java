package learn.BudgetApp.data;

import learn.BudgetApp.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository{

    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User findByEmail(String email) {
        String sql = """
                select * from user where email =?;
                """;
        return jdbcClient.sql(sql).param(email).query(User.class).optional().orElse(null);
    }

    @Override
    public User create(User user) {
        String sql = """
                insert into user(email, username, password) values
                (:email, :username, :password);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .update(keyHolder, "userId");

        if(rowsAffected == 0){
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    public User findById(int userId){
        String sql = """
                select * from user where userId =?;
                """;

        return jdbcClient.sql(sql).param(userId).query(User.class).optional().orElse(null);
    }


}
