package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.*;
import vendingMachine.model.DatabaseTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CalculateCashTest extends DatabaseTest {

    @Test
    void allDenoms(){
        //tests
        //DataBaseQueryCalculateCashTest
        List<Integer> quantities = new ArrayList<>();
        int num = 11;
        for (int i = 0; i < num; i++) {
            quantities.add(1);
        }
        // sum = 188.85

        Double total = 10.00;

        List<Double> amounts = databaseQuery.calculateCash(quantities, total);

        List<Double> expected = new ArrayList<>();
        expected.add(188.85);
        expected.add(-178.85);
        assertIterableEquals(expected, amounts);

    }

    @Test
    void noCash(){
        List<Integer> quantities = new ArrayList<>();
        int num = 11;
        for (int i = 0; i < num; i++) {
            quantities.add(0);
        }
        Double total = 10.00;

        List<Double> amounts = databaseQuery.calculateCash(quantities, total);

        List<Double> expected = new ArrayList<>();
        expected.add(0.00);
        expected.add(10.00);
        assertIterableEquals(expected, amounts);
    }

    @Test
    void exactCash(){
        List<Integer> quantities = new ArrayList<>();
        int num = 11;
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);
        quantities.add(1);
        quantities.add(0);
        quantities.add(0);
        quantities.add(0);

        Double total = 10.00;

        List<Double> amounts = databaseQuery.calculateCash(quantities, total);

        List<Double> expected = new ArrayList<>();
        expected.add(10.00);
        expected.add(0.00);
        assertIterableEquals(expected, amounts);
    }

}
