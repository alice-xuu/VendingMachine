package vendingMachine.model.DatabaseQueryTests;

import org.junit.jupiter.api.Test;
import vendingMachine.model.DatabaseTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class generateAvailableChangeFileTest extends DatabaseTest {

    @Test
    void checkFileExists(){
        databaseQuery.generateAvailableChangeFile();
        File csvFile = new File("./build/AvailableChange.csv");
        assertTrue (csvFile.isFile());
    }

    @Test
    void checkFileContents() throws IOException {
        List<String> fractions = new ArrayList<>();
        fractions.add("5c");
        fractions.add("10c");
        fractions.add("20c");
        fractions.add("50c");
        fractions.add("$1");
        fractions.add("$2");
        fractions.add("$5");
        fractions.add("$10");
        fractions.add("$20");
        fractions.add("$50");
        fractions.add("$100");

        BufferedReader csvReader = new BufferedReader(new FileReader("./build/AvailableChange.csv"));
        String row;
        int i = 0;
        List<Integer> quantities = databaseQuery.getQuantities();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            assertEquals(fractions.get(i), data[0]);
            i++;
        }

    }
}
