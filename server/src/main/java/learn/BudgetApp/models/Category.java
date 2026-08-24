package learn.BudgetApp.models;

import java.util.Objects;

public class Category {
    public int categoryId;
    public String name;
    public User user;

    public Category(int categoryId, String name, User user) {
        this.categoryId = categoryId;
        this.name = name;
        this.user = user;
    }

    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId && Objects.equals(name, category.name) && Objects.equals(user, category.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, user);
    }
}
