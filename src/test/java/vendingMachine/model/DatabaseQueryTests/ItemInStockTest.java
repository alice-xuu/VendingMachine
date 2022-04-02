package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import static org.junit.jupiter.api.Assertions.*;

public class ItemInStockTest extends DatabaseTest {

    @Test
    void itemInStockIsInStock() {
        boolean actual = databaseQuery.itemInStock("Sprite", 5);
        assertTrue(actual);
    }

    @Test
    void itemEqualStockIsInStock() {
        boolean actual = databaseQuery.itemInStock("Sprite", DEFAULT_STOCK);
        assertTrue(actual);
    }

    @Test
    void lowItemStockIsNotInStock() {
        boolean actual = databaseQuery.itemInStock("Sprite", 8);
        assertFalse(actual);
    }

    @Test
    void nonExistantItemIsNotInStock() {
        boolean actual = databaseQuery.itemInStock("jijrewfsdfs", 4);
        assertFalse(actual);
    }

    @Test
    void addedItemStockIsInStock() {
        databaseQuery.changeItemStock("Sprite", 5);
        boolean actual = databaseQuery.itemInStock("Sprite", DEFAULT_STOCK + 5);

        assertTrue(actual);
    }

    @Test
    void addedItemStockIsNotInStock() {
        databaseQuery.changeItemStock("Sprite", 3);
        boolean actual = databaseQuery.itemInStock("Sprite", DEFAULT_STOCK + 5);

        assertFalse(actual);
    }

    @Test
    void removedItemStockIsInStock() {
        databaseQuery.changeItemStock("Sprite", -5);
        boolean actual = databaseQuery.itemInStock("Sprite", DEFAULT_STOCK - 5);

        assertTrue(actual);
    }

    @Test
    void removedItemStockIsNotInStock() {
        databaseQuery.changeItemStock("Sprite", -5);
        boolean actual = databaseQuery.itemInStock("Sprite", DEFAULT_STOCK - 3);

        assertFalse(actual);
    }
}
