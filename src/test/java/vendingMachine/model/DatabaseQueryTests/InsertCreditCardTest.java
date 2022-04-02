package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class InsertCreditCardTest extends DatabaseTest {
    @Test
    void insertNormally(){
        databaseQuery.insertCreditCard(1, "anon", "12345");
    }
}
