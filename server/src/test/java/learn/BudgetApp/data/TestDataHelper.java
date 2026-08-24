package learn.BudgetApp.data;

import learn.BudgetApp.models.User;

public class TestDataHelper {

    public static User existingUser() {
        return new User(1, "a@a.com", "a");
    }
    public static User secondUser(){return new  User(2, "b@b.com", "b"); }

    public static User userToCreate() {
        return new User(0, "c@c.com", "c");
    }

    public static User userAfterCreate() {
        User user = userToCreate();
        user.setUserId(3);
        return user;
    }
}
