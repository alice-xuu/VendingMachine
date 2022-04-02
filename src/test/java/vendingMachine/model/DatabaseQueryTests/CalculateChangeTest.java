package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import vendingMachine.model.NoAvailableChangeException;
import vendingMachine.model.NotEnoughMoneyException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalculateChangeTest extends DatabaseTest {

    public List<Integer> createQuantities(int fivec, int tenc, int twentyc, int fiftyc,
                                   int oned, int twod, int fived, int tend,
                                   int twentyd, int fiftyd, int hund){
        List<Integer> quantities = new ArrayList<>();
        quantities.add(fivec);
        quantities.add(tenc);
        quantities.add(twentyc);
        quantities.add(fiftyc);
        quantities.add(oned);
        quantities.add(twod);
        quantities.add(fived);
        quantities.add(tend);
        quantities.add(twentyd);
        quantities.add(fiftyd);
        quantities.add(hund);
        return quantities;
    }

    @Test
    void notEnoughMoney() {

        List<Integer> inserted_quantities = createQuantities(0,0,0,0,0,0,0,0,0,0,0);
        List<Integer> original_quantities = databaseQuery.getQuantities();

        assertThrows(NotEnoughMoneyException.class,
                () -> databaseQuery.calculateChange(10.00, inserted_quantities, original_quantities)
        );
    }

    @Test
    void exactMoney() throws NotEnoughMoneyException, NoAvailableChangeException{
        List<Integer> inserted = createQuantities(0,0,0,0,0,0,0,1,0,0,0);
        Double total = 10.00;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);
        List<Integer> original_quantities = databaseQuery.getQuantities();

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, original_quantities);

        List<Integer> expectedChange = createQuantities(0,0,0,0,0,0,0,0,0,0,0);

        assertIterableEquals(change, expectedChange);
    }

    //default change available in vending machine
    @Test
    void needChange() throws NotEnoughMoneyException, NoAvailableChangeException {
        List<Integer> inserted = createQuantities(0,0,0,0,0,0,0,1,0,0,0);
        Double total = 6.00;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);
        List<Integer> original_quantities = databaseQuery.getQuantities();

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, original_quantities);

        List<Integer> expectedChange = createQuantities(0,0,0,0,0,2,0,0,0,0,0);

        assertIterableEquals(change, expectedChange);
    }

    // limited change available
    // 3 x 5c and 0 x 10c
    // total = $0.05
    // inserted = $0.20
    // should return 3 x 5c
    @Test
    void limitedChangeAvailable() throws NotEnoughMoneyException, NoAvailableChangeException, SQLException{
        String updateMoney = "UPDATE Money SET quantity = ? WHERE fraction = ?";
        PreparedStatement ps = statementManager.prepareStatement(updateMoney);
        ps.setInt(1, 3);
        ps.setString(2, "5c");
        ps.executeUpdate();
        ps.setInt(1, 0);
        ps.setString(2, "10c");
        ps.executeUpdate();

        List<Integer> inserted = createQuantities(0,0,1,0,0,0,0,0,0,0,0);
        Double total = 0.05;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);
        List<Integer> original_quantities = databaseQuery.getQuantities();

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, original_quantities);

        List<Integer> expectedChange = createQuantities(3,0,0,0,0,0,0,0,0,0,0);

        assertIterableEquals(change, expectedChange);

    }


    @Test
    void prioritiseHigh() throws NotEnoughMoneyException, NoAvailableChangeException{
        List<Integer> inserted = createQuantities(0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0);
        Double total = 0.00;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);
        List<Integer> original_quantities = databaseQuery.getQuantities();

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, original_quantities);

        List<Integer> expectedChange = createQuantities(0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0);
        assertIterableEquals(change, expectedChange);
    }

    @Test
    void prioritiseHighSmallVal() throws NotEnoughMoneyException, NoAvailableChangeException {
        List<Integer> inserted = createQuantities(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Double total = 0.00;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);
        List<Integer> originalQuantities = databaseQuery.getQuantities();

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, originalQuantities);

        List<Integer> expectedChange = createQuantities(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertIterableEquals(change, expectedChange);
    }

    // check database updated correctly with user cash inserts
    @Test
    void databaseUpdates() throws NotEnoughMoneyException, NoAvailableChangeException {
        List<Integer> inserted = createQuantities(0,0,0,0,0,0,0,0,0,0,1);
        List<Integer> originalQuantities = databaseQuery.getQuantities();

        Double total = 50.00;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);

        List<Integer> change = databaseQuery.calculateChange(amounts.get(1), inserted, originalQuantities);

        List<Integer> currentQuantities = databaseQuery.getQuantities();
        List<Integer> expectedQuantities = createQuantities(5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 6);

    }

    @Test
    void normalCase() throws NotEnoughMoneyException, NoAvailableChangeException {
        List<Integer> inserted = createQuantities(0,0,0,0,0,0,0,0,0,0,1);
        List<Integer> originalQuantities = databaseQuery.getQuantities();

        Double total = 10.40;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);

        List<Integer> actualChange = databaseQuery.calculateChange(amounts.get(1), inserted, originalQuantities);

        // Change of $89.60
        List<Integer> expectedChange = createQuantities(0, 1, 0, 1, 0, 2, 1, 1, 1, 1, 0);

        assertIterableEquals(expectedChange, actualChange);

    }

    @Test
    void notEnoughChange() throws SQLException {
        String updateMoney = "UPDATE Money SET quantity = 0;";
        PreparedStatement ps = statementManager.prepareStatement(updateMoney);
        ps.executeUpdate();

        List<Integer> inserted = createQuantities(0,0,0,0,0,0,0,0,0,0,1000);

        Double total = 1.0;
        List<Double> amounts = databaseQuery.calculateCash(inserted, total);

        List<Integer> expectedQuantities = databaseQuery.getQuantities();

        assertThrows(
                NoAvailableChangeException.class,
                () -> databaseQuery.calculateChange(amounts.get(1), inserted, expectedQuantities));

        List<Integer> actualQuantities = databaseQuery.getQuantities();

        assertIterableEquals(expectedQuantities, actualQuantities);
    }
}
