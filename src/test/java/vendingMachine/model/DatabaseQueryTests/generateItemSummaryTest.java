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

public class generateItemSummaryTest extends DatabaseTest {
    @Test
    void generateFileAndCheck(){
        databaseQuery.generateSummaryItems();
        File csvFile = new File("./build/ItemSoldSummary.csv");
        assertTrue (csvFile.isFile());
    }

}
