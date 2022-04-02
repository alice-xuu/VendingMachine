package vendingMachine.model.DatabaseQueryTests;

import javafx.scene.control.ComboBox;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ModifyCashTest extends DatabaseTest {
    @Test
    void cashTopUp(){

        List<String> coins = Arrays.asList("5c", "10c", "20c", "50c", "$1", "$2", "$5","$10","$20","$50","$100");
        for (String coin : coins) {
            databaseQuery.modifyCash(10, coin);
        }

        List<Integer> quantities = databaseQuery.getQuantities();

        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            expected.add(10);
        }

        assertIterableEquals(expected, quantities);

    }

}
