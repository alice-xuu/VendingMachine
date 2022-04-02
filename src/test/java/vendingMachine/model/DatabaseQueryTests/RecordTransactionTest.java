package vendingMachine.model.DatabaseQueryTests;

import vendingMachine.model.Snack;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import vendingMachine.model.InvalidInputSpecifiedException;
import vendingMachine.model.ItemDoesntExistException;
import vendingMachine.model.UserNotFoundException;
import vendingMachine.model.InvalidPaymentMethodException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RecordTransactionTest extends DatabaseTest {

    @Test
    void recordTransactionNormally() throws InvalidInputSpecifiedException, ItemDoesntExistException, UserNotFoundException,InvalidPaymentMethodException {
        assertTrue(databaseQuery.recordTransactions("anon", Arrays.asList(1,2,6), 7.80, 10.00, 2.20, "cash"));
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("item_name", "price"),
                Arrays.asList("Mineral Water", "2.60"),
                Arrays.asList("Sprite", "2.60"),
                Arrays.asList("Mars", "2.60")

        );
        List<List<String>> actual = databaseQuery.getPurchaseHistory(1);
        assertEquals(expected, actual);

    }

    @Test
    void recordTransactionNormally2() throws InvalidInputSpecifiedException, ItemDoesntExistException, UserNotFoundException, InvalidPaymentMethodException{
        assertTrue(databaseQuery.recordTransactions("anon", Arrays.asList(1,2,3,4,5,6,7), 10.00, 10.00, 0.00, "card"));

    }

    @Test
    void nullUser(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.recordTransactions(null, null, null, null, null, "card")
        );
    }

    @Test
    void nullSnacks(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.recordTransactions("anon", null, 7.80, 10.00, 2.20, "cash")
        );
    }

    @Test
    void negativeValues1(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.recordTransactions("anon", Arrays.asList(3), -7.80, -10.00, -2.20, "card")
        );
    }

    @Test
    void negativeValues2(){
        assertThrows(
            InvalidInputSpecifiedException.class,
            () -> databaseQuery.recordTransactions("anon", Arrays.asList(3), 7.80, -10.00, -2.20, "card")
        );
    }

    @Test
    void usernameDoesntExists(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.recordTransactions("neiman", Arrays.asList(5), 7.80, 10.00, 2.20,"cash")
        );
    }

    @Test
    void snackDoesntExists(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.recordTransactions("neiman", Arrays.asList(5, -12), 7.80, 10.00, 2.20,"cash")
        );
    }
    @Test
    void snackDoesntExists2(){
        assertThrows(
            UserNotFoundException.class,
            () -> databaseQuery.recordTransactions("neiman", Arrays.asList(-12), 7.80, 10.00, 2.20, "cash")
        );
    }

    @Test
    void invalidPaymentMethod(){
        assertThrows(
            InvalidPaymentMethodException.class,
            () -> databaseQuery.recordTransactions("anon", Arrays.asList(1,2,6), 7.80, 10.00, 2.20, "something")
        );
    }


}
