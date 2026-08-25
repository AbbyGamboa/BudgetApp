package learn.BudgetApp.data;

import learn.BudgetApp.models.PlaidItems;

import java.util.List;

public interface PlaidItemRepository {

    PlaidItems findById(String plaidItemId);

    List<PlaidItems> findAllByUserId(int userId);

    PlaidItems create(PlaidItems plaidItems);

    boolean delete(String plaidItemId);
}
