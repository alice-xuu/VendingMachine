package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ItemExistsTest extends DatabaseTest {
    @Test
    void existingItemExists() {
        // Check one of the default items
        boolean actual = databaseQuery.itemExists("Sprite");

        assertTrue(actual);
    }

    @Test
    void nonExistingItemDoesntExist() {
        // Check one of the default items
        boolean actual = databaseQuery.itemExists("fjskldjfskldafjsalkfjfskldafjl");

        assertFalse(actual);
    }
}
