package vendingMachine.model.DatabaseQueryTests;

import vendingMachine.model.Snack;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import vendingMachine.model.InvalidReasonException;
import vendingMachine.model.UserNotFoundException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RecordCancelledTest extends DatabaseTest {

    @Test
    void twoMinutesIdle() throws InvalidReasonException, UserNotFoundException{
        databaseQuery.recordCancelled(1, 1);
    }

    @Test
    void PressedCancelNotPaymentScreen() throws InvalidReasonException, UserNotFoundException{
        databaseQuery.recordCancelled(1, 2);
    }

    @Test
    void PressedCancelCardPaymentScreen() throws InvalidReasonException, UserNotFoundException{
        databaseQuery.recordCancelled(1, 3);
    }

    @Test
    void PressedCancelNoChangePaymentScreen() throws InvalidReasonException, UserNotFoundException{
        databaseQuery.recordCancelled(1, 4);
    }

    @Test
    void InvalidUser() {
        assertThrows(UserNotFoundException.class,
        () -> databaseQuery.recordCancelled(-1, 1));
    }

    @Test
    void InvalidReasonHigher() {
        assertThrows(InvalidReasonException.class,
        () -> databaseQuery.recordCancelled(1, 10));
    }

    @Test
    void InvalidReasonLower() {
        assertThrows(InvalidReasonException.class,
        () -> databaseQuery.recordCancelled(1, -1));
    }


}
