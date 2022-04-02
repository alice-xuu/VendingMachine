package vendingMachine.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


class DatabaseSetupTest extends DatabaseTest {

    @Test
    void testUserTableIsCreated() {
        String fetch_entries = "SELECT * FROM " + USER_TABLE;
        assertNotEquals("", executeQuery(fetch_entries));
    }

    @Test
    void testTransactionTableIsCreated() {
        String fetch_entries = "SELECT * FROM " + TRANSACTIONS_TABLE;
        assertNotEquals("", executeQuery(fetch_entries));
    }

    @Test
    void testItemTableIsCreated() {
        String fetch_entries = "SELECT * FROM " + ITEM_TABLE;
        assertNotEquals("", executeQuery(fetch_entries));
    }

    @Test
    void testMoneyTableIsCreated() {
        String fetch_entries = "SELECT * FROM " + MONEY_TABLE;
        assertNotEquals("", executeQuery(fetch_entries));
    }

}
