package vendingMachine.model;

import java.sql.*;
import java.io.File;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;


public abstract class DatabaseTest {
    /*------------------------------------------------------
     * Constants
     -------------------------------------------------------*/
    protected static final String DB_NAME = "database.db";
    protected static final String USER_TABLE = "User";
    protected static final String ITEM_TABLE = "Item";
    protected static final String MONEY_TABLE = "Money";
    protected static final String TRANSACTIONS_TABLE = "Transactions";
    protected static final String URL = "jdbc:sqlite:" + DB_NAME;
    protected static final int DEFAULT_STOCK = 7;

    protected static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /*
     * Test setup
     */
    protected Connection connection;
    protected DatabaseQuery databaseQuery;
    protected StatementManager statementManager;

    @BeforeEach
    protected void setUp() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
            e.printStackTrace();
        }
        statementManager = new StatementManager(connection);
        new DatabaseSetup(statementManager);
        databaseQuery = new DatabaseQueryImp(statementManager);
    }

    @AfterEach
    protected void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Could not close database");
        }
        File dbFile = new File(DB_NAME);
        if (!dbFile.delete()) {
            System.err.println("Database file could not be deleted");
        }
    }

    protected int getLatestTransactionId() throws SQLException {
        String getId = "SELECT MAX(transaction_id) AS id FROM Transactions";
        PreparedStatement getStatement = statementManager.prepareStatement(getId);

        ResultSet result = getStatement.executeQuery();
        return result.getInt("id");
    }


    protected void changeTransactionDate(int tranactionId, String date) throws SQLException {
        String insertStmt = "UPDATE Transactions\n" +
                "SET datetime = DATE(?,'localtime')\n" +
                "WHERE transaction_id = ?;";
        PreparedStatement ps = statementManager.prepareStatement(insertStmt);
        ps.setString(1, date);
        ps.setInt(2, tranactionId);

        ps.executeUpdate();
    }

    /**
     * Helper test method to add users to database
     * @param username username of user
     * @param password password of user
     * @param role role of user
     * @throws SQLException if something goes wrong
     */
    protected void insertUser(String username, String password, String role) throws SQLException {
        String insertStmt = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
        PreparedStatement ps = statementManager.prepareStatement(insertStmt);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, role);

        ps.executeUpdate();
    }

    /*
     * Executes any sql query and returns result as a string
     */
    protected String executeQuery(String query) {
        final String colSep = "\t\t";
        StringBuilder resultString = new StringBuilder();
        try {
            ResultSet resultSet = statementManager.createStatement().executeQuery(query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();

            for(int i = 1; i <= columnCount; ++i) {
                resultString.append(resultSetMetaData.getColumnName(i));
                resultString.append(colSep);
            }
            resultString.append('\n');

            while(resultSet.next()) {
                for(int i = 1; i <= columnCount; ++i) {
                    resultString.append(resultSet.getString(i));
                    resultString.append(colSep);
                }
                resultString.append('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Could not query " + DB_NAME);
        }

        return resultString.toString();
    }
}
