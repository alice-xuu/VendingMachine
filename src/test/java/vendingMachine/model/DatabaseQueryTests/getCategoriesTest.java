package vendingMachine.model.DatabaseQueryTests;

import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class getCategoriesTest extends DatabaseTest {

    @Test
    void getAllDistinctCategoriesOnDatabase(){
        List<String> actual = databaseQuery.getCategories();
        List<String> expected = Arrays.asList("Drinks", "Chocolates", "Chips", "Candies");
        assertIterableEquals(expected, actual);
    }
}
