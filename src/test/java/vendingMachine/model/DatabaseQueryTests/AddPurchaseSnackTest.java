package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddPurchaseSnackTest extends DatabaseTest{

    @Test
    void AddNormally() throws ItemDoesntExistException, InvalidInputSpecifiedException {
        databaseQuery.addPurchaseSnack(1, 5);
    }

    @Test
    void unknownItem(){
        assertThrows(ItemDoesntExistException.class,
        () -> databaseQuery.addPurchaseSnack(-1, 3));
    }

    @Test
    void invalidQuantity(){
        assertThrows(InvalidInputSpecifiedException.class,
        () -> databaseQuery.addPurchaseSnack(1, -10));
    }
}
