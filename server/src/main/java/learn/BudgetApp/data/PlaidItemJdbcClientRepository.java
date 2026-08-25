package learn.BudgetApp.data;

import learn.BudgetApp.models.PlaidItems;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaidItemJdbcClientRepository implements PlaidItemRepository{

    private final JdbcClient jdbcClient;

    public PlaidItemJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private final String BASE_SQL =  """
                select pi.plaidItemId, u.id, pi.accessToken, pi.institutionName
                from plaid_Items pi 
                join user u on pi.user_id = u.id
                """;

    @Override
    public PlaidItems findById(String plaidItemId) {
       final String sql = BASE_SQL + "where pi.plaidItemId = ?;";
        return jdbcClient.sql(sql).param(plaidItemId).query(PlaidItems.class).optional().orElse(null);
    }

    @Override
    public List<PlaidItems> findAllByUserId(int userId) {


        return List.of();
    }

    @Override
    public PlaidItems create(PlaidItems plaidItems) {
        return null;
    }

    @Override
    public boolean delete(String plaidItemId) {
        return false;
    }
}
