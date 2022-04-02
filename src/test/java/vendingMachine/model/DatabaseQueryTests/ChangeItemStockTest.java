package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeItemStockTest extends DatabaseTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void outputWarningIfNoItemFound() {
        String expected = String.format("Warning: Stock for 0 items where changed, please check that jfklsdjfojig is an item name %n");
        databaseQuery.changeItemStock("jfklsdjfojig" , 4);
        assertEquals(expected, errContent.toString());
    }

    @Test
    void outputWarningIfTooManyItemsFound() throws SQLException {
        String expected = String.format("Warning: Stock for 2 items where changed, please check that Sprite is a unique identifier for an item %n");

        Statement statement = statementManager.createStatement();
        statement.executeUpdate("INSERT INTO Item (name, category) VALUES ('Sprite', 'Drink')");
        databaseQuery.changeItemStock("Sprite" , 4);
        assertEquals(expected, errContent.toString());
    }
}
