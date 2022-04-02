package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import java.util.Arrays;
import vendingMachine.model.InvalidUserException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class VerifyUserCredentialsTest extends DatabaseTest {
    @Test
    void verifyJessica() throws InvalidUserException{
        databaseQuery.VerifyUserCredentials(Arrays.asList("Jessica", "Aryanto"));
    }

    @Test
    void errorUser(){
        assertThrows(
            InvalidUserException.class,
            () -> databaseQuery.VerifyUserCredentials(Arrays.asList("Mumbo Jumbo", null))
        );
    }
}
