package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificCardExistsTest extends DatabaseTest {
    @Test
    void nonExistantCardDoesntExist() {
        assertFalse(databaseQuery.specificCardExists("-1", "ddd", "ddd"));
    }

    @Test
    void addedCardExists() {
        String name = "ddd";
        String number = "ddd";

        databaseQuery.insertCreditCard(1, name, number);
        assertTrue(databaseQuery.specificCardExists("1", name, number));
    }

    @Test
    void otherUserCardDoesntExist() {
        String name = "ddd";
        String number = "ddd";

        databaseQuery.insertCreditCard(2, name, number);
        assertFalse(databaseQuery.specificCardExists("1", name, number));
    }

    @Test
    void otherCardNameDoesntExist() {
        String name = "ddd";
        String number = "ddd";

        databaseQuery.insertCreditCard(1, name, number);
        assertFalse(databaseQuery.specificCardExists("1", name + "1", number));
    }

    @Test
    void otherCardNumberDoesntExist() {
        String name = "ddd";
        String number = "ddd";

        databaseQuery.insertCreditCard(1, name, number);
        assertFalse(databaseQuery.specificCardExists("1", name, number + "1"));
    }
}
