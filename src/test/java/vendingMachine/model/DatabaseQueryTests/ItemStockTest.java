package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class ItemStockTest extends DatabaseTest {
    @Test
    void defaultItemStock() {
        int actual = databaseQuery.itemStock("Sprite");

        assertEquals(DEFAULT_STOCK, actual);
    }

    @Test
    void nonExistantItemNoStock() {
        int actual = databaseQuery.itemStock("fjskldhfgsa gsdajfl");

        assertEquals(0, actual);
    }

    @Test
    void addedItemStock() {
        databaseQuery.changeItemStock("Sprite", 5);
        int actual = databaseQuery.itemStock("Sprite");

        assertEquals(DEFAULT_STOCK + 5, actual);
    }

    @Test
    void removedItemStock() {
        databaseQuery.changeItemStock("Sprite", -5);
        int actual = databaseQuery.itemStock("Sprite");

        assertEquals(DEFAULT_STOCK - 5, actual);
    }
}
