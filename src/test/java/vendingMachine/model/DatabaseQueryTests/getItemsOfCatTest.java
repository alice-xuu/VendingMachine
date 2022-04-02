package vendingMachine.model.DatabaseQueryTests;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;
import vendingMachine.model.InvalidCategorySpecifiedException;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class getItemsOfCatTest extends DatabaseTest {
    @Test
    void getDrinks() throws InvalidCategorySpecifiedException{
        List<String> actual = databaseQuery.getItemsOfCat("Drinks");
        List<String> expected = new ArrayList<>();
        expected.add("Mineral Water $2.60");
        expected.add("Sprite $2.60");
        expected.add("Coca cola $2.60");
        expected.add("Pepsi $2.60");
        expected.add("Juice $2.60");
        assertIterableEquals(expected, actual);
    }

    @Test
    void getChocolates() throws InvalidCategorySpecifiedException{
        List<String> actual = databaseQuery.getItemsOfCat("Chocolates");
        List<String> expected = new ArrayList<>();
        expected.add("Mars $2.60");
        expected.add("M&M $2.60");
        expected.add("Bounty $2.60");
        expected.add("Sneakers $2.60");
        assertIterableEquals(expected, actual);
    }

    @Test
    void getChips() throws InvalidCategorySpecifiedException{
        List<String> actual = databaseQuery.getItemsOfCat("Chips");
        List<String> expected = new ArrayList<>();
        expected.add("Smiths $2.60");
        expected.add("Pringles $2.60");
        expected.add("Kettle $2.60");
        expected.add("Thins $2.60");
        assertIterableEquals(expected, actual);
    }

    @Test
    void getCandies() throws InvalidCategorySpecifiedException{
        List<String> actual = databaseQuery.getItemsOfCat("Candies");
        List<String> expected = new ArrayList<>();
        expected.add("Mentos $2.60");
        expected.add("Sour Patch $2.60");
        expected.add("Skittles $2.60");
        assertIterableEquals(expected, actual);
    }

    @Test
    void getGivenInvalidCategory(){
        assertThrows(
                        InvalidCategorySpecifiedException.class,
                        () -> databaseQuery.getItemsOfCat("Minions")
                );
    }

}
