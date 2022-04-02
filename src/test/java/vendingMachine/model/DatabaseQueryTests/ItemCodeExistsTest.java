package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ItemCodeExistsTest extends DatabaseTest {
    @Test
    void existingItemCodeExists() {
        assertTrue(databaseQuery.itemCodeExists(2));
    }

    @Test
    void nonExistingItemDoesntExist() {
        assertFalse(databaseQuery.itemCodeExists(-10));
    }
}
