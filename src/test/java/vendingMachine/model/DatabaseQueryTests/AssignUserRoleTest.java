package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssignUserRoleTest extends DatabaseTest {
    @Test
    void changeToSeller() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException, UserNotFoundException {
        List<String> expected;
        List<String> actual;

        // Create test user
        databaseQuery.createUser("new_user", "password", "password");
        expected = Arrays.asList("5", "new_user", "password", "customer");
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

        // Set role to seller
        databaseQuery.assignUserRole(5, User.SELLER);
        expected = Arrays.asList("5", "new_user", "password", User.SELLER);
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);
    }

    @Test
    void changeToOwner() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException, UserNotFoundException {
        List<String> expected;
        List<String> actual;

        // Create test user
        databaseQuery.createUser("new_user", "password", "password");
        expected = Arrays.asList("5", "new_user", "password", "customer");
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

        // Set role to owner
        databaseQuery.assignUserRole(5, User.OWNER);
        expected = Arrays.asList("5", "new_user", "password", User.OWNER);
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);
    }

    @Test
    void changeToCashier() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException, UserNotFoundException {
        List<String> expected;
        List<String> actual;

        // Create test user
        databaseQuery.createUser("new_user", "password", "password");
        expected = Arrays.asList("5", "new_user", "password", "customer");
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

        // Set role to cashier
        databaseQuery.assignUserRole(5, User.CASHIER);
        expected = Arrays.asList("5", "new_user", "password", User.CASHIER);
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);
    }

    @Test
    void changeToCustomer() throws PasswordTooShortException, PasswordVerificationMismatchException, InvalidInputSpecifiedException, UsernameAlreadyExistsException, UserNotFoundException {
        List<String> expected;
        List<String> actual;

        // Create test user
        databaseQuery.createUser("new_user", "password", "password");
        expected = Arrays.asList("5", "new_user", "password", "customer");
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

        // Set role to cashier
        databaseQuery.assignUserRole(5, User.CASHIER);
        expected = Arrays.asList("5", "new_user", "password", User.CASHIER);
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

        // Set role to customer
        databaseQuery.assignUserRole(5, User.CUSTOMER);
        expected = Arrays.asList("5", "new_user", "password", User.CUSTOMER);
        actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);
    }
}
