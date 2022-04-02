package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import java.util.Arrays;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class GetCreditCardTest extends DatabaseTest {
    @Test
    void getAnonCard(){
        assertEquals(databaseQuery.getCard("1"),null);
    }

    @Test
    void addGetCard(){
        databaseQuery.insertCreditCard(1, "anon", "12345");
        assertEquals(databaseQuery.getCard("1"), Arrays.asList("anon", "12345"));
    }
}
