package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class CardExistsTest extends DatabaseTest {
    @Test
    void nonExistantCardDoesntExist() {
        assertFalse(databaseQuery.cardExists("-1"));
    }

    @Test
    void addedCardExists() {
        databaseQuery.insertCreditCard(1, "me", "11");
        assertTrue(databaseQuery.cardExists("1"));
    }
}
