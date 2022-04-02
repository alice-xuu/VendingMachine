package vendingMachine.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseSetup{

    /*--------------------------------------
    Instantiate Connection and Statement
    --------------------------------------*/
    private StatementManager statementManager;

/*----------------------------------------------------------------------------
|XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|
----------------------------------------------------------------------------*/

    /*---------------------------------------------------
    Print table entries
    ----------------------------------------------------*/
    public void seeTable(String tableName, List<String> arguments){
        try{
            Statement statement = statementManager.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            for (String argument : arguments) {
                System.out.print(argument + " | ");
            }
            System.out.println();
            while(rs.next()){
                for (String argument : arguments) {
                    System.out.print(rs.getString(argument) + " | ");
                }
                System.out.println();
            }
            System.out.println();

        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /*---------------------------------------------------
    insertTransaction
    ----------------------------------------------------*/
     private void insertTransaction(int userid, List<Integer> snacks, double total, double inserted, double change, String method) throws SQLException {
         String insertStmt = "INSERT INTO Transactions (userid, datetime, total, inserted, change, method)" +
                 "VALUES (?, DATE('now','localtime'), ?, ?, ?, ?)";
         PreparedStatement ps = statementManager.prepareStatement(insertStmt);
         ps.setInt(1, userid);
         ps.setDouble(2, total);
         ps.setDouble(3, inserted);
         ps.setDouble(4, change);
         ps.setString(5, method);
         ps.executeUpdate();

         String selectStmt = "SELECT MAX(transaction_id) AS transaction_id FROM Transactions";
         PreparedStatement ps2 = statementManager.prepareStatement(selectStmt);
         ResultSet result =  ps2.executeQuery();
         if(result.next()){
             int transaction_id = result.getInt("transaction_id");
             for(Integer each: snacks){
                 insertStmt = "INSERT INTO SnacksBought " +
                         "VALUES (?, ?)";
                 PreparedStatement ps3 = statementManager.prepareStatement(insertStmt);
                 ps3.setInt(1, transaction_id);
                 ps3.setInt(2, each);

                 ps3.executeUpdate();
             }
         }
         result.close();
     }

     /*---------------------------------------------------
     insertReason
     ----------------------------------------------------*/
      private void insertReason(String reason) throws SQLException {
          String insertStmt = "INSERT INTO CancelReason (reason) VALUES (?)";
          PreparedStatement ps = statementManager.prepareStatement(insertStmt);
          ps.setString(1, reason);

          ps.executeUpdate();
      }

    /*---------------------------------------------------
    insertUser
    ----------------------------------------------------*/
     private void insertUser(String username, String password, String role) throws SQLException {
         String insertStmt = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
         PreparedStatement ps = statementManager.prepareStatement(insertStmt);
         ps.setString(1, username);
         ps.setString(2, password);
         ps.setString(3, role);

         ps.executeUpdate();
     }

    /*---------------------------------------------------
    insertItem
    ----------------------------------------------------*/
    private void insertItem(String name, String category, int code) throws SQLException {
        String insertStmt = "INSERT INTO Item (code, name, category) VALUES (?,?,?)";
        PreparedStatement ps = statementManager.prepareStatement(insertStmt);
        ps.setInt(1, code);
        ps.setString(2, name);
        ps.setString(3, category);

        ps.executeUpdate();
    }

    /*---------------------------------------------------
    insertItem
    ----------------------------------------------------*/
    private void setUpItemSold(List<Integer> codes) throws SQLException {
        for(int i = 0; i < codes.size(); i++){
            String insertStmt = "INSERT INTO ItemSold (code) VALUES (?)";
            PreparedStatement ps = statementManager.prepareStatement(insertStmt);
            ps.setInt(1, codes.get(i));
            ps.executeUpdate();
        }
    }

    /*---------------------------------------------------
    getCodes
    ----------------------------------------------------*/
    private List<Integer> getCodes() throws SQLException {
        List<Integer> codes = new ArrayList<>();
        String selectStatement = "SELECT code FROM Item";
        PreparedStatement ps = statementManager.prepareStatement(selectStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            codes.add(rs.getInt("code"));
        }
        return codes;
    }




    /*---------------------------------------------------
    insertMoney
    ----------------------------------------------------*/
    private void insertMoney(String fraction) throws SQLException {
        String insertStmt = "INSERT INTO Money (fraction) VALUES (?)";
        PreparedStatement ps = statementManager.prepareStatement(insertStmt);
        ps.setString(1, fraction);
        ps.executeUpdate();
    }

/*----------------------------------------------------------------------------
|XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|
----------------------------------------------------------------------------*/

    public DatabaseSetup(StatementManager statementManager){
        try{
            this.statementManager = statementManager;

            //used to execute queries
            Statement statement = this.statementManager.createStatement();

/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            User Table
            contains all the user accounts that have been created
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS User");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                    "userid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username STRING UNIQUE, password STRING, " +
                    "role STRING DEFAULT 'customer' CHECK(role = \"customer\" or role = \"seller\" or role = \"cashier\" or role = \"owner\")" +
                    ")");

            //list that contains attribute names of User Table
            List<String> userArgs = Arrays.asList("userid", "username", "password", "role");
            insertUser("anon", null, User.CUSTOMER);
            insertUser("Mustafa", "Fulwala", User.SELLER);
            insertUser("Patrick", "Wong", User.OWNER);
            insertUser("Jessica", "Aryanto", User.CASHIER);

            //the following method call is equivalent to
            /*
                SELECT * FROM User;
                    seeTable("User", userArgs);
             */

/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            Item Table
            contains snacks available on vending machine
            ---------------------------------------------------*/

            statement.executeUpdate("DROP TABLE IF EXISTS Item");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Item ("
            + "code INTEGER, "
            + "name STRING, category STRING, quantity INTEGER DEFAULT (7), "
            + "price NUMERIC DEFAULT (2.60), "
            + "CHECK (quantity <= 15 AND quantity >= 0), "
            + "PRIMARY KEY(code, name))");



            //list that contains attribute names of Item Table
            List<String> itemArgs = new ArrayList<>();
            itemArgs.add("code");
            itemArgs.add("name");
            itemArgs.add("category");
            itemArgs.add("quantity");
            itemArgs.add("price");
            int i = 1;
            insertItem("Mineral Water", "Drinks", i++);
            insertItem("Sprite", "Drinks", i++);
            insertItem("Coca cola", "Drinks", i++);
            insertItem("Pepsi", "Drinks", i++);
            insertItem("Juice", "Drinks", i++);

            insertItem("Mars", "Chocolates", i++);
            insertItem("M&M", "Chocolates", i++);
            insertItem("Bounty", "Chocolates", i++);
            insertItem("Sneakers", "Chocolates", i++);

            insertItem("Smiths", "Chips", i++);
            insertItem("Pringles", "Chips", i++);
            insertItem("Kettle", "Chips", i++);
            insertItem("Thins", "Chips", i++);

            insertItem("Mentos", "Candies", i++);
            insertItem("Sour Patch", "Candies", i++);
            insertItem("Skittles", "Candies", i++);

            //the following method call is equivalent to
            //SELECT * FROM Item;
            // seeTable("Item", itemArgs);

/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            ItemSold Table
            contains how many items have been sold
            ---------------------------------------------------*/

            statement.executeUpdate("DROP TABLE IF EXISTS ItemSold");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ItemSold ("
            + "code INTEGER PRIMARY KEY, "
            + "sold INTEGER DEFAULT (0), "
            + "CHECK (sold >= 0))");

            setUpItemSold(getCodes());


/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            Transaction Table
            contains all transactions done on vending machine
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS Transactions");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Transactions ("
            + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "userid INTEGER, datetime DATETIME, "
            + "total NUMERIC, inserted NUMERIC, change NUMERIC, method STRING,"
            + "FOREIGN KEY (userid) REFERENCES User(userid))");

            //list that contains attribute names of Transaction Table
            List<String> transactionArgs = new ArrayList<>();
            transactionArgs.add("transaction_id");
            transactionArgs.add("userid");
            transactionArgs.add("datetime");
            transactionArgs.add("total");
            transactionArgs.add("inserted");
            transactionArgs.add("change");
            transactionArgs.add("method");

            //the following method call is equivalent to
            //SELECT * FROM Transaction;
            // seeTable("Transactions", transactionArgs);

/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            CancelledTrans Table
            contains all transactions done on vending machine
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS CancelReason");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CancelReason ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "reason STRING)");

            insertReason("2 minutes idle");
            insertReason("Pressed cancel button");
            insertReason("Cancelled payment");
            insertReason("not enough change");

            //list that contains attribute names of Transaction Table
            List<String> cancelReasonArgs = new ArrayList<>();
            cancelReasonArgs.add("id");
            cancelReasonArgs.add("reason");

/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            CancelledTrans Table
            contains all transactions done on vending machine
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS CancelledTrans");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS CancelledTrans ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "userid INTEGER, datetime DATETIME, "
            + "reason INTEGER, "
            + "FOREIGN KEY (userid) REFERENCES User(userid), "
            + "FOREIGN KEY (reason) REFERENCES CancelReason(id))");

            //list that contains attribute names of Transaction Table
            List<String> cancelledTransactionArgs = new ArrayList<>();
            cancelledTransactionArgs.add("id");
            cancelledTransactionArgs.add("username");
            cancelledTransactionArgs.add("datetime");
            cancelledTransactionArgs.add("reason");



/*----------------------------------------------------------------------------*/


            /*---------------------------------------------------
            SnacksBought Table
            contains all snacks that was bought in every transactions
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS SnacksBought");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS SnacksBought ("
            + "transaction_id INTEGER, "
            + "snackid INTEGER, "
            + "FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id),"
            + "FOREIGN KEY (snackid) REFERENCES Item(code))");
/*----------------------------------------------------------------------------*/

            /*---------------------------------------------------
            Money Table
            contains quantity of different fractions of money
            ---------------------------------------------------*/

            statement.executeUpdate("DROP TABLE IF EXISTS Money");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Money ("
            +  "rank INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "fraction STRING, quantity INTEGER DEFAULT (5), "
            + "CHECK (quantity >= 0))");



            //list that contains attribute names of Money Table
            List<String> moneyArgs = new ArrayList<>();
            moneyArgs.add("fraction");
            moneyArgs.add("quantity");
            insertMoney("5c");
            insertMoney("10c");
            insertMoney("20c");
            insertMoney("50c");
            insertMoney("$1");
            insertMoney("$2");
            insertMoney("$5");
            insertMoney("$10");
            insertMoney("$20");
            insertMoney("$50");
            insertMoney("$100");

            // the following method call is equivalent to
            // SELECT * FROM Money;
            // seeTable("Money", moneyArgs);


/*----------------------------------------------------------------------------*/

            /*---------------------------------------------------
            Credit Card Table
            contains all the saved credit cards
            ---------------------------------------------------*/
            statement.executeUpdate("DROP TABLE IF EXISTS Cards");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Cards ("
                    + "userid INT REFERENCES User(userid), "
                    + "name TEXT, "
                    + "number TEXT, "
                    + "PRIMARY KEY(userid, name, number))");

            /*----------------------------------------------------------------------------*/


        }
        catch(SQLException e){

            //if the error message is "out of memory"
            //it probably means no database file is found
            e.printStackTrace();
        }
    }

}
