package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserExistsTest extends DatabaseTest {

    @Test
    void anonymousUserExists() {
        assertTrue(databaseQuery.userExists(1));
    }

    @Test
    void nonExistantUserDoesntExist() {
        assertFalse(databaseQuery.userExists(-1));
    }

    @Test
    void addedUserExists() throws SQLException {
        assertFalse(databaseQuery.userExists(5));
        insertUser("test", "test", "customer");
        assertTrue(databaseQuery.userExists(5));
    }
}
