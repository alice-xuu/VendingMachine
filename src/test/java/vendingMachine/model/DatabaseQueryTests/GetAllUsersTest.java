package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllUsersTest extends DatabaseTest {
    @Test
    void addedUserShown() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException {
        databaseQuery.createUser("new_user", "password", "password");

        List<User> expected = Arrays.asList(new User(5, "new_user", User.CUSTOMER));
        List<User> actual = databaseQuery.getAllUsers();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    void multipleAddedUserShown() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException {
        List<User> expected = Arrays.asList(
                new User(5, "new_user", User.CUSTOMER),
                new User(6, "new_user2", User.CUSTOMER),
                new User(7, "new_user1", User.CUSTOMER)
        );


        for (User user : expected) {
            databaseQuery.createUser(user.getUsername(), "password", "password");
        }

        List<User> actual = databaseQuery.getAllUsers();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    void addedSellerUserShown() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException {
        List<User> expected = Arrays.asList(
                new User(5, "new_user", User.CUSTOMER),
                new User(6, "new_user2", User.SELLER),
                new User(7, "new_user1", User.CUSTOMER)
        );


        for (User user : expected) {
            databaseQuery.createUser(user.getUsername(), "password", "password");
        }

        databaseQuery.assignUserRole(6, User.SELLER);

        List<User> actual = databaseQuery.getAllUsers();

        assertTrue(actual.containsAll(expected));
    }
}
