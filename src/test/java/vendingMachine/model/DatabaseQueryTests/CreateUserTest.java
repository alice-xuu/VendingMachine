package vendingMachine.model.DatabaseQueryTests;

import vendingMachine.model.Snack;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import vendingMachine.model.InvalidInputSpecifiedException;
import vendingMachine.model.UsernameAlreadyExistsException;
import vendingMachine.model.PasswordVerificationMismatchException;
import vendingMachine.model.PasswordTooShortException;
import vendingMachine.model.UserNotFoundException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends DatabaseTest {

    @Test
    void createUserNormally() throws InvalidInputSpecifiedException, UsernameAlreadyExistsException, PasswordTooShortException, PasswordVerificationMismatchException, UserNotFoundException{
        assertTrue(databaseQuery.createUser("new_user", "password", "password"));
        List<String> expected = new ArrayList<>();
        expected.add("5");
        expected.add("new_user");
        expected.add("password");
        expected.add("customer");
        List<String> actual = databaseQuery.getUser("new_user");
        assertEquals(expected, actual);

    }

    @Test
    void nullInput1(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.createUser(null, null, null)
        );
    }

    @Test
    void nullInput2(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.createUser("user", null, null)
        );
    }

    @Test
    void nullInput3(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.createUser("user", "password", null)
        );
    }

    @Test
    void passwordTooShort(){
        assertThrows(
            PasswordTooShortException.class,
            () -> databaseQuery.createUser("test", "test", "test")
        );
    }

    @Test
    void usernameAlreadyExists(){
        assertThrows(
            UsernameAlreadyExistsException.class,
            () -> databaseQuery.createUser("anon", "password", "password")
        );
    }

    @Test
    void passwordVerificationMismatch(){
        assertThrows(
            PasswordVerificationMismatchException.class,
            () -> databaseQuery.createUser("new", "password", "mismatch")
        );
    }
}
