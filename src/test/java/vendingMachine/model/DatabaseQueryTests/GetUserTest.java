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

public class GetUserTest extends DatabaseTest {

    @Test
    void getUserNormally() throws UserNotFoundException{
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("anon");
        expected.add(null);
        expected.add("customer");
        List<String> actual = databaseQuery.getUser("anon");
        assertEquals(expected, actual);
    }

    @Test
    void nullInput(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.getUser(null)
        );
    }

    @Test
    void NoneExistentUser(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.getUser("neiman")
        );
    }
}
