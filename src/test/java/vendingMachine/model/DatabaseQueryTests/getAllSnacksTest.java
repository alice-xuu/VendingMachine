package vendingMachine.model.DatabaseQueryTests;

import vendingMachine.model.Snack;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class getAllSnacksTest extends DatabaseTest {

    public Snack createSnack(int code, String name, String category) {
        Snack snack = new Snack();
        snack.setCost(2.60);
        snack.setCode(code);
        snack.setName(name);
        snack.setCategory(category);
        snack.setShoppingBasket(0);
        return snack;
    }

    @Test
    void getAllSnacks(){
        int code = 1;
        ArrayList<Snack> actual = databaseQuery.getAllSnacks();
        ArrayList<Snack> expected = new ArrayList<>();
        expected.add(createSnack(code++, "Mineral Water", "Drinks"));
        expected.add(createSnack(code++, "Sprite", "Drinks"));
        expected.add(createSnack(code++, "Coca cola", "Drinks"));
        expected.add(createSnack(code++, "Pepsi", "Drinks"));
        expected.add(createSnack(code++, "Juice", "Drinks"));

        expected.add(createSnack(code++, "Mars", "Chocolates"));
        expected.add(createSnack(code++, "M&M", "Chocolates"));
        expected.add(createSnack(code++, "Bounty", "Chocolates"));
        expected.add(createSnack(code++, "Sneakers", "Chocolates"));

        expected.add(createSnack(code++, "Smiths", "Chips"));
        expected.add(createSnack(code++, "Pringles", "Chips"));
        expected.add(createSnack(code++, "Kettle", "Chips"));
        expected.add(createSnack(code++, "Thins", "Chips"));

        expected.add(createSnack(code++, "Mentos", "Candies"));
        expected.add(createSnack(code++, "Sour Patch", "Candies"));
        expected.add(createSnack(code++, "Skittles", "Candies"));
        for(int i = 0; i < actual.size(); i++){
            assertEquals(actual.get(i).getCost(), expected.get(i).getCost());
            assertEquals(actual.get(i).getCode(), expected.get(i).getCode());
            assertEquals(actual.get(i).getName(), expected.get(i).getName());
            assertEquals(actual.get(i).getCategory(), expected.get(i).getCategory());
            assertEquals(actual.get(i).getShoppingBasket(), expected.get(i).getShoppingBasket());
        }
    }
}
