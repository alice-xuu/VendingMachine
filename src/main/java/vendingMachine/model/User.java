package vendingMachine.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class User {
    public final static String CUSTOMER = "customer";
    public final static String OWNER = "owner";
    public final static String CASHIER = "cashier";
    public final static String SELLER = "seller";

    private final SimpleIntegerProperty userId;
    private final SimpleStringProperty username;
    private final SimpleStringProperty role;

    public User(int userId, String username, String role) {
        this.userId = new SimpleIntegerProperty(userId);
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
    }


    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getRole(), user.getRole());
    }
}
