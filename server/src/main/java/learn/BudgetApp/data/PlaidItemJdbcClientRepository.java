package learn.BudgetApp.data;

import learn.BudgetApp.models.PlaidItems;
import learn.BudgetApp.models.mapping.PlaidItemMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaidItemJdbcClientRepository implements PlaidItemRepository{

    private final JdbcClient jdbcClient;

    public PlaidItemJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SQL =  """
                select pi.plaidItemId, u.userId, u.email, u.password, pi.accessToken, pi.institutionName
                from plaid_Items pi
                join user u on pi.userId = u.userId
                """;

    @Override
    public PlaidItems findById(String plaidItemId) throws DataAccessException{
       final String sql = BASE_SQL + " where pi.plaidItemId = ?;";
        return jdbcClient.sql(sql).param(plaidItemId).query(new PlaidItemMapper()).optional().orElse(null);
    }

    @Override
    public List<PlaidItems> findAllByUserId(int userId) {
        final String sql = BASE_SQL + "where u.userId = ?;";

        return jdbcClient.sql(sql).param(userId).query(new PlaidItemMapper()).list();
    }

    @Override
    public PlaidItems create(PlaidItems plaidItems) {
        final String sql = """
                insert into plaid_items(plaidItemId, userId, accessToken, institutionName) values
                (:plaidItemId, :userId, :accessToken, :institutionName);
                """;

        jdbcClient.sql(sql)
                .param("plaidItemId", plaidItems.getPlaidItemId())
                .param("userId", plaidItems.getUser().getUserId())
                .param("accessToken", plaidItems.getAccessToken())
                .param("institutionName", plaidItems.getInstitutionName())
                .update();


        return plaidItems;
    }

    @Override
    public boolean delete(String plaidItemId) {
        return false;
    }
}
