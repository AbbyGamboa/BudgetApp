package learn.BudgetApp.models;

import java.util.Objects;

public class PlaidItems {
    public String plaidItemId;
    public User user;
    public String accessToken;
    public String institutionName;

    public PlaidItems(String plaidItemId, User user, String accessToken, String institutionName) {
        this.plaidItemId = plaidItemId;
        this.user = user;
        this.accessToken = accessToken;
        this.institutionName = institutionName;
    }

    public String getPlaidItemId() {
        return plaidItemId;
    }

    public void setPlaidItemId(String plaidItemId) {
        this.plaidItemId = plaidItemId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlaidItems that = (PlaidItems) o;
        return Objects.equals(plaidItemId, that.plaidItemId) && Objects.equals(user, that.user) && Objects.equals(accessToken, that.accessToken) && Objects.equals(institutionName, that.institutionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaidItemId, user, accessToken, institutionName);
    }
}
