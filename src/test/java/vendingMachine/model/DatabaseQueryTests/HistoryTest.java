package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest extends DatabaseTest {


    private final List<List<String>> startingHistory = Arrays.asList(
            Arrays.asList("item_name", "price")
    );

    @Test
    void existingTransactionIsShown() {
        List<List<String>> expected = startingHistory;

        List<List<String>> actual = databaseQuery.getPurchaseHistory(1);

        assertEquals(expected, actual);
    }

    @Test
    void newTransactionIsShown() throws SQLException, UserNotFoundException, InvalidInputSpecifiedException, ItemDoesntExistException, InvalidPaymentMethodException {
        databaseQuery.recordTransactions("anon", Collections.singletonList(2), 7.80, 10.00, 2.20, "cash");

        List<List<String>> expected = new ArrayList<>(startingHistory);
        expected.add(1, Arrays.asList("Sprite", "2.60"));
        List<List<String>> actual = databaseQuery.getPurchaseHistory(1);

        assertEquals(expected, actual);
    }

    @Test
    void newOnlyLatestFiveShown() throws SQLException, UserNotFoundException, InvalidInputSpecifiedException, ItemDoesntExistException, InvalidPaymentMethodException  {
        databaseQuery.recordTransactions("anon", Collections.singletonList(2), 7.80, 10.00, 2.20, "cash");
        changeTransactionDate(getLatestTransactionId(), "2019-01-01");

        databaseQuery.recordTransactions("anon", Collections.singletonList(3), 7.80, 10.00, 2.20, "cash");
        changeTransactionDate(getLatestTransactionId(), "2019-02-01");

        databaseQuery.recordTransactions("anon", Collections.singletonList(4), 7.80, 10.00, 2.20, "cash");
        changeTransactionDate(getLatestTransactionId(), "2019-03-01");

        databaseQuery.recordTransactions("anon", Collections.singletonList(5), 7.80, 10.00, 2.20, "cash");
        changeTransactionDate(getLatestTransactionId(), "2019-04-01");

        databaseQuery.recordTransactions("anon", Collections.singletonList(6), 7.80, 10.00, 2.20, "card");
        changeTransactionDate(getLatestTransactionId(), "2019-05-01");


        List<List<String>> expected = new ArrayList<>(startingHistory);
        expected.addAll(Arrays.asList(
                Arrays.asList("Mars", "2.60"),
                Arrays.asList("Juice", "2.60"),
                Arrays.asList("Pepsi", "2.60"),
                Arrays.asList("Coca cola", "2.60"),
                Arrays.asList("Sprite", "2.60")
        ));
        List<List<String>> actual = databaseQuery.getPurchaseHistory(1);

        assertEquals(expected, actual);
    }

    @Test
    void onlyUserTransactionsShown() throws SQLException, UserNotFoundException, InvalidInputSpecifiedException, ItemDoesntExistException, PasswordVerificationMismatchException, PasswordTooShortException, UsernameAlreadyExistsException, InvalidPaymentMethodException  {
        databaseQuery.createUser("test", "testtest", "testtest");
        databaseQuery.recordTransactions("test", Collections.singletonList(2), 7.80, 10.00, 2.20, "card");

        List<List<String>> expected = new ArrayList<>(startingHistory);
        List<List<String>> actual = databaseQuery.getPurchaseHistory(1);

        assertEquals(expected, actual);
    }
}
