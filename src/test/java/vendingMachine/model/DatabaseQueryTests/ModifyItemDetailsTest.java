package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ModifyItemDetailsTest extends DatabaseTest{

    @Test
    void modifyName() throws InvalidInputSpecifiedException{
        String snackName = "Sprite";
        String detail = "name";
        String input = "New";

        databaseQuery.modifyItemDetails(snackName, detail, input);

        assertTrue(databaseQuery.itemExists(input));
    }

    @Test
    void modifyCategory() throws InvalidInputSpecifiedException{
        String snackName = "Sprite";
        String detail = "category";
        String input = "Chips";

        databaseQuery.modifyItemDetails(snackName, detail, input);

        ArrayList<Snack> snackList = databaseQuery.getAllSnacks();
        for (Snack snack : snackList) {
            if (snack.getName().equals(snackName)) {
                assertEquals(input, snack.getCategory());
            }
        }
    }

    @Test
    void modifyCode() throws InvalidInputSpecifiedException{
        String snackName = "Sprite";
        String detail = "code";
        String input = "123";
        int expected = 123;

        databaseQuery.modifyItemDetails(snackName, detail, input);

        ArrayList<Snack> snackList = databaseQuery.getAllSnacks();
        for (Snack snack : snackList) {
            if (snack.getName().equals(snackName)) {
                assertEquals(expected, snack.getCode());
            }
        }

    }

    @Test
    void modifyQuantity() throws InvalidInputSpecifiedException{
        String snackName = "Sprite";
        String detail = "quantity";
        String input = "10";
        int expected = 10;

        databaseQuery.modifyItemDetails(snackName, detail, input);

        ArrayList<Snack> snackList = databaseQuery.getAllSnacks();
        assertEquals(expected, databaseQuery.itemStock(snackName));
    }

    @Test
    void modifyPrice() throws InvalidInputSpecifiedException{
        String snackName = "Sprite";
        String detail = "price";
        String input = "10.00";
        double expected = 10.00;

        databaseQuery.modifyItemDetails(snackName, detail, input);

        ArrayList<Snack> snackList = databaseQuery.getAllSnacks();
        for (Snack snack : snackList) {
            if (snack.getName().equals(snackName)) {
                assertEquals(expected, snack.getCost());
            }
        }
    }

    @Test
    void noSelections() {

        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(null, null, null)
        );
    }

    @Test
    void noInput() {
        String snackName = "Sprite";
        String detail = "code";
        String input = "";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void invalidCategory() {
        String snackName = "Sprite";
        String detail = "category";
        String input = "fruit";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void stringCode() {
        String snackName = "Sprite";
        String detail = "code";
        String input = "asdf";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void negativeCode() {
        String snackName = "Sprite";
        String detail = "code";
        String input = "-100";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void codeExists() {
        String snackName = "Sprite";
        String detail = "code";
        String input = "1";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void stringQuantity() {
        String snackName = "Sprite";
        String detail = "quantity";
        String input = "asdf";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void negativeQuantity() {
        String snackName = "Sprite";
        String detail = "quantity";
        String input = "-100";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void tooLargeQuantity() {
        String snackName = "Sprite";
        String detail = "quantity";
        String input = "100";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void notIntQuantity() {
        String snackName = "Sprite";
        String detail = "quantity";
        String input = "2.22";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void stringPrice() {
        String snackName = "Sprite";
        String detail = "price";
        String input = "asdf";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }

    @Test
    void negativePrice() {
        String snackName = "Sprite";
        String detail = "price";
        String input = "-100";
        assertThrows(InvalidInputSpecifiedException.class,
                () -> databaseQuery.modifyItemDetails(snackName, detail, input)
        );
    }


}
