package vendingMachine.model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import java.util.*;
import java.lang.Math;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class DatabaseQueryImp implements DatabaseQuery {

    /*--------------------------------------
    Instantiate variable to check whether
    user is admin or not and SQL Database
    --------------------------------------*/
    private boolean isAdmin = false;
    private StatementManager statementManager;
    private List<String> fractions;

    @Override
    public List<String> getFractions() {
        return fractions;
    }

    public DatabaseQueryImp(StatementManager statementManager) {
        this.statementManager = statementManager;
        this.fractions = new ArrayList<>();
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
    }

    //checks if sql query returns anything, true = is empty, false = not empty
    public static boolean isResultSetEmpty(ResultSet rs) throws SQLException {
        return (!rs.isBeforeFirst() && rs.getRow() == 0);
    }

    /*
    Seller selects an item from a dropdown, select an item detail from a dropdown,
    then modifyItemDetails is called to update that detail.
     */

    @Override
    public void modifyItemDetails(String snack, String detail, String input) throws InvalidInputSpecifiedException{

        try{
            if (snack == null || detail == null || input == null){
                throw new InvalidInputSpecifiedException("Invalid Selections");
            }
            if (input.equals("")){
                throw new InvalidInputSpecifiedException("Invalid Input");
            }


            if (detail.equals("name")){
                if(itemExists(input)){
                    throw new InvalidInputSpecifiedException("Cannot have duplicate snacks name");
                }
                String modifyDetail = "UPDATE Item SET name = ? WHERE name = ?";
                PreparedStatement ps = statementManager.prepareStatement(modifyDetail);
                ps.setString(2, snack);
                ps.setString(1, input);
                ps.executeUpdate();
            }

            else if (detail.equals("category")){
                boolean found = false;
                for (String category : getCategories()){
                    if (input.equals(category)) {
                        found = true;
                        break;
                    }
                }
                if (!found){
                    throw new InvalidInputSpecifiedException("Invalid category specified");
                }
                else{
                    String modifyDetail = "UPDATE Item SET category = ? WHERE name = ?";
                    PreparedStatement ps = statementManager.prepareStatement(modifyDetail);
                    ps.setString(2, snack);
                    ps.setString(1, input);
                    ps.executeUpdate();
                }
            }
            else if (detail.equals("code")){
                String getCode = "SELECT code FROM Item WHERE name = ?";
                PreparedStatement getStatement = statementManager.prepareStatement(getCode);

                getStatement.setString(1, snack);
                ResultSet resultSet = getStatement.executeQuery();

                int snackCode = resultSet.getInt("code");
                resultSet.close();
                try{
                    int num = Integer.parseInt(input);
                    if (num < 0){
                        throw new InvalidInputSpecifiedException("Please specify a positive integer\nthat doesn't already exist");
                    }
                    if (itemCodeExists(num)){
                        throw new InvalidInputSpecifiedException("Code already exists\nPlease specify another value");
                    }
                    else{
                        String modifyDetail = "UPDATE Item SET code = ? WHERE name = ?";
                        PreparedStatement ps = statementManager.prepareStatement(modifyDetail);
                        ps.setString(2, snack);
                        ps.setInt(1, num);
                        ps.executeUpdate();

                        modifyDetail = "UPDATE ItemSold SET code = ? WHERE code = ?";
                        ps = statementManager.prepareStatement(modifyDetail);
                        ps.setInt(1, num);
                        ps.setInt(2, snackCode);
                        ps.executeUpdate();

                    }
                } catch (NumberFormatException e) {
                    throw new InvalidInputSpecifiedException("Please specify a positive integer\nthat doesn't exist already");
                }
            }
            else if (detail.equals("quantity")){
                try{
                    int num = Integer.parseInt(input);
                    if (num < 0 || num > 15){
                        throw new InvalidInputSpecifiedException("Please specify a value between 0 and 15.");
                    }
                    else {
                        String modifyDetail = "UPDATE Item SET quantity = ? WHERE name = ?";
                        PreparedStatement ps = statementManager.prepareStatement(modifyDetail);
                        ps.setString(2, snack);
                        ps.setInt(1, num);
                        ps.executeUpdate();
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidInputSpecifiedException("Please specify a value between 0 and 15.");
                }
            }
            else { // price
                try{
                    double num = Double.parseDouble(input);
                    if (num < 0){
                        throw new InvalidInputSpecifiedException("Please specify a positive number");
                    }
                    else{
                        double rounded = Math.round(num * 100.0) / 100.0;
                        String modifyDetail = "UPDATE Item SET price = ? WHERE name = ?";
                        PreparedStatement ps = statementManager.prepareStatement(modifyDetail);
                        ps.setString(2, snack);
                        ps.setDouble(1, rounded);
                        ps.executeUpdate();
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidInputSpecifiedException("Please specify a positive number");
                }
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // generate a csv file containing cash and quantity
    @Override
    public void generateAvailableChangeFile() {
        List<Integer> quantities = getQuantities();
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < quantities.size(); i++) {
            List<String> row = new ArrayList<>();
            row.add(fractions.get(i));
            row.add(String.valueOf(quantities.get(i)));
            rows.add(row);
        }

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter("./build/AvailableChange.csv");

            for (List<String> rowInfo : rows) {
                csvWriter.append(String.join(",", rowInfo));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*###############################################################################

    MODIFY CASH

################################################################################*/

    //checks if entered credentials match an entry in the database
    @Override
    public void VerifyUserCredentials(List<String> cred) throws InvalidUserException {
        try {
            String searchUser = "SELECT * FROM User WHERE username = ? AND password = ?";
            PreparedStatement ps1 = statementManager.prepareStatement(searchUser);
            ps1.setString(1, cred.get(0));
            ps1.setString(2, cred.get(1));

            ResultSet result1 = ps1.executeQuery();
            if (isResultSetEmpty(result1)) {
                result1.close();
                throw new InvalidUserException("User doesn't exist");
            }
            result1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Integer> getQuantities() {
        List<Integer> quantities = new ArrayList<>();
        try {
            String getQuantities = "SELECT quantity FROM Money";
            PreparedStatement ps1 = statementManager.prepareStatement(getQuantities);
            ResultSet result1 = ps1.executeQuery();

            while (result1.next()) {
                int quantity = result1.getInt("quantity");
                quantities.add(quantity);
            }
            result1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return quantities;
    }

    /*----------------------------------------------------------------------------*/

    /*
    cashier can modify the amounts of coins and notes of the vending machine
     */
    @Override
    public void modifyCash(Integer quantity, String cashValue) {
        try {
            String updateMoney = "UPDATE Money SET quantity = ? WHERE fraction = ?";
            PreparedStatement ps = statementManager.prepareStatement(updateMoney);

            ps.setInt(1, quantity);
            ps.setString(2, cashValue);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*###############################################################################

    USER

################################################################################*/

    @Override
    public boolean usernameExists(String username) {
        String existsSQL = "SELECT 1 FROM User WHERE username = ?";
        PreparedStatement exists = statementManager.prepareStatement(existsSQL);

        try {
            exists.setString(1, username);
            ResultSet resultSet = exists.executeQuery();
            boolean value = resultSet.isBeforeFirst();
            resultSet.close();

            return value;
        } catch (SQLException e) {
            return false;
        }
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public boolean createUser(String username, String password, String verification) throws PasswordVerificationMismatchException, UsernameAlreadyExistsException, PasswordTooShortException, InvalidInputSpecifiedException {
        if (username == null || password == null || verification == null) {
            throw new InvalidInputSpecifiedException("invalid input specified");
        }
        if (usernameExists(username)) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        if (password.length() < 8) {
            throw new PasswordTooShortException("password needs to be at least 8 characters long");
        }
        if (!password.equals(verification)) {
            throw new PasswordVerificationMismatchException("verification doesn't match");
        }
        String createAccount = "INSERT INTO User (username, password) VALUES (?, ?)";
        PreparedStatement createStatement = statementManager.prepareStatement(createAccount);

        try {
            createStatement.setString(1, username);
            createStatement.setString(2, password);
            int result = createStatement.executeUpdate();
            return (result == 1);
        } catch (SQLException e) {
            return false;
        }
    }


    /*----------------------------------------------------------------------------*/

    @Override
    public List<String> getUser(String username) throws UserNotFoundException {
        if (username == null) {
            throw new UserNotFoundException("User doesn't exist");
        }
        if (!usernameExists(username)) {
            throw new UserNotFoundException("User doesn't exist");
        }
        String fetchUser = "SELECT * FROM User WHERE username = ?";
        PreparedStatement fetchStatement = statementManager.prepareStatement(fetchUser);
        List<String> details = new ArrayList<>();

        try {
            fetchStatement.setString(1, username);
            ResultSet result = fetchStatement.executeQuery();
            details.add(Integer.toString(result.getInt("userid")));
            details.add(result.getString("username"));
            details.add(result.getString("password"));
            details.add(result.getString("role"));
            result.close();

            return details;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public boolean userExists(int userid) {
        String existsSql = "SELECT 1 FROM User WHERE userid = ?";
        PreparedStatement existsStatement = statementManager.prepareStatement(existsSql);

        try {
            existsStatement.setInt(1, userid);
            ResultSet resultSet = existsStatement.executeQuery();
            boolean value = resultSet.isBeforeFirst();
            resultSet.close();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

/*###############################################################################

    HISTORY

################################################################################*/

    @Override
    public List<List<String>> getPurchaseHistory(int userid) {
        String historySql = "" +
                "SELECT\n" +
                "    DISTINCT I.name AS item_name, printf(\"%.2f\", I.price) AS price\n" +
                "FROM\n" +
                "    Transactions\n" +
                "    JOIN SnacksBought SB ON Transactions.transaction_id = SB.transaction_id\n" +
                "    JOIN Item I ON I.code = SB.snackid\n" +
                "WHERE\n" +
                "    userid = ?\n" +
                "ORDER BY datetime DESC\n" +
                "LIMIT 5;";
        PreparedStatement historyStatement = statementManager.prepareStatement(historySql);

        List<List<String>> history = new ArrayList<>();

        history.add(Arrays.asList("item_name", "price"));

        try {
            historyStatement.setInt(1, userid);
            ResultSet resultSet = historyStatement.executeQuery();
            while (resultSet.next()) {
                List<String> itemEntry = Arrays.asList(
                        resultSet.getString("item_name"),
                        resultSet.getString("price")
                );
                history.add(itemEntry);
            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }

/*###############################################################################

    PAY BY CASH

################################################################################*/

      /*
      Input: a list of every coin/note quantities from the frontend, total amount of user's transaction
      In the order: 5c, 10c, 50c, $1, $2, $10, $20, $50, %100
      Calculates the amount of money.
      Output: List (strings): the current amount, remaining amount
      This function would be called everytime the user presses +- buttons.
       */

    @Override
    public List<Double> calculateCash(List<Integer> quantities, Double total) {

        List<Double> denom = new ArrayList<>();
        denom.add(0.05);
        denom.add(0.10);
        denom.add(0.20);
        denom.add(0.50);
        denom.add(1.00);
        denom.add(2.00);
        denom.add(5.00);
        denom.add(10.00);
        denom.add(20.00);
        denom.add(50.00);
        denom.add(100.00);

        Double current = 0.00;

        for (int i = 0; i < quantities.size(); i++) {
            current += quantities.get(i) * denom.get(i);
        }

        current = Math.round(current * 20.0) / 20.0;

        Double remaining = total - current;
        remaining = Math.round(remaining * 20.0) / 20.0;

        List<Double> amounts = new ArrayList<>();

        amounts.add(current);
        amounts.add(remaining);

        return amounts;
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public List<Integer> findAmounts(List<Integer> finList, List<Integer> change, List<Double> denom, List<Integer> quantities, Double owed, int cashIndex) {

        if (owed == 0) {
            finList.addAll(change);
            while (cashIndex < denom.size()) {
                finList.add(0);
                cashIndex++;
            }
            return finList;
        }
        if (cashIndex > denom.size() - 1) {
            return null;
        }

        if (change == null) {
            change = new ArrayList<>();
        }
        Double money = denom.get(cashIndex);

        int num_money = (int) Math.min(Math.round(owed / money * 100.0) / 100.0, quantities.get(cashIndex));

        cashIndex++;

        for (int i = num_money; i >= 0; i--) {
            double subtracted = owed - money * i;
            double rounded = Math.round(subtracted * 100.0) / 100.0;
            change = findAmounts(finList, change, denom, quantities, rounded, cashIndex);
            if (change != null) {
                change.add(i);

                return change;
            }
        }
        return change;
    }

    /*----------------------------------------------------------------------------*/

      /*
      Input: the amount owing the user. I.e. amounts.get(1) from calculateCash, remaining is positive if not enough money given, is negative if vending machine owes user.
      Throws NotEnoughMoney Exception if user didn't input enough money.
      Throws NoAvailableChangeException if there isn't exact change for the customer.
      Output: change as list of quantities of money (order: 5c -> $100)
       */
      public List<Integer> calculateChange(Double remaining, List<Integer> inserted_quantities, List<Integer> original_quantities) throws NoAvailableChangeException, NotEnoughMoneyException {
          List<Integer> change = new ArrayList<>();
          List<Integer> finList = new ArrayList<>();

          try{
              //check if user inputted enough money
              if (remaining > 0){
                  throw new NotEnoughMoneyException("Not enough money given");
              }

              List<Integer> quantities = getQuantities();

              // update the database with the user's inserted cash
              for (int i = 0; i < quantities.size(); i++) {
                  int new_quantity = inserted_quantities.get(i) + original_quantities.get(i);
                  modifyCash(new_quantity, getFractions().get(i));
              }

              quantities = getQuantities();

              List<Double> denom = new ArrayList<>();
              denom.add(0.05);
              denom.add(0.10);
              denom.add(0.20);
              denom.add(0.50);
              denom.add(1.00);
              denom.add(2.00);
              denom.add(5.00);
              denom.add(10.00);
              denom.add(20.00);
              denom.add(50.00);
              denom.add(100.00);

              Double owed = Math.abs(remaining);

              DecimalFormat df = new DecimalFormat("#.##");

              Collections.reverse(denom);
              Collections.reverse(quantities);

              int cashIndex = 0;
              findAmounts(finList, change, denom, quantities, owed, cashIndex);

              if (finList.size() == 0){
                  // reset the database to original state
                  for (int i = 0; i < quantities.size(); i++) {
                      int old_quantity = original_quantities.get(i);
                      modifyCash(old_quantity, getFractions().get(i));
                  }
                  throw new NoAvailableChangeException("No available change");
              }

              String updateMoney = "UPDATE Money SET quantity = ? WHERE fraction = ?";
              PreparedStatement ps2 = statementManager.prepareStatement(updateMoney);

              Collections.reverse(quantities);

              for (int i = 0; i < quantities.size(); i++){
                  ps2.setInt(1, quantities.get(i) - finList.get(i));
                  ps2.setString(2, fractions.get(i));

                  ps2.executeUpdate();
              }
          }
          catch(SQLException e){
              e.printStackTrace();
          }
          return finList;

      }


/*###############################################################################

    ITEMS

################################################################################*/


    @Override
    public boolean itemExists(String itemName) {
        String existsSql = "SELECT 1 FROM Item WHERE name = ?";
        PreparedStatement existsStatement = statementManager.prepareStatement(existsSql);

        try {
            existsStatement.setString(1, itemName);
            ResultSet resultSet = existsStatement.executeQuery();
            boolean value = resultSet.isBeforeFirst();
            resultSet.close();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }


    /*----------------------------------------------------------------------------*/

    @Override
    public boolean itemCodeExists(Integer code) {
        String existsSql = "SELECT 1 FROM Item WHERE code = ?";
        PreparedStatement existsStatement = statementManager.prepareStatement(existsSql);

        try {
            existsStatement.setInt(1, code);
            ResultSet resultSet = existsStatement.executeQuery();

            boolean value = resultSet.isBeforeFirst();
            resultSet.close();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }


    /*----------------------------------------------------------------------------*/


    @Override
    public int itemStock(String itemName) {
        if (!itemExists(itemName)) {
            return 0;
        }

        // Currently assuming item name is unique.
        String existsSql = "SELECT quantity FROM Item WHERE name = ? LIMIT 1";
        PreparedStatement inStockStatement = statementManager.prepareStatement(existsSql);

        try {
            inStockStatement.setString(1, itemName);
            ResultSet resultSet = inStockStatement.executeQuery();
            int value = resultSet.getInt("quantity");
            resultSet.close();
            return value;
        } catch (SQLException e) {
            e.printStackTrace();

            return 0;
        }
    }

    /*----------------------------------------------------------------------------*/


    @Override
    public boolean itemInStock(String itemName, int quantity) {
        return itemStock(itemName) >= quantity;
    }

    /*----------------------------------------------------------------------------*/


    @Override
    public void changeItemStock(String itemName, int changeInStock) {
        // Currently assuming item name is unique.
        String changeStockSql = "UPDATE Item SET quantity = quantity + ? WHERE name = ?";
        PreparedStatement changeStockStatement = statementManager.prepareStatement(changeStockSql);

        try {
            changeStockStatement.setInt(1, changeInStock);
            changeStockStatement.setString(2, itemName);
            int row_changed = changeStockStatement.executeUpdate();

            if (row_changed > 1) {
                // Note: should be removed if the item identifier is enforced as unique by database

                // %n represents the platfrom specific line break
                String err_mag = "Warning: Stock for %d items where changed, please check that %s is a unique identifier for an item %n";
                System.err.printf(err_mag, row_changed, itemName);
            } else if (row_changed == 0) {
                String err_mag = "Warning: Stock for %d items where changed, please check that %s is an item name %n";
                System.err.printf(err_mag, row_changed, itemName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*----------------------------------------------------------------------------*/


    @Override
    public List<String> getCategories() {
        String getCatSQL = "SELECT DISTINCT category FROM Item";
        PreparedStatement getCatStatement = statementManager.prepareStatement(getCatSQL);
        List<String> catList = new ArrayList<>();
        try {
            ResultSet resultSet = getCatStatement.executeQuery();
            while (resultSet.next()) {
                catList.add(resultSet.getString("category"));
            }
            resultSet.close();

            return catList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public ArrayList<Snack> getAllSnacks() {
        ArrayList<Snack> Items = new ArrayList<>();
        try {
            String getItemSQL = "SELECT code, name, category, quantity, price FROM Item ORDER BY code";
            PreparedStatement getItemStatement = statementManager.prepareStatement(getItemSQL);

            ResultSet resultSet = getItemStatement.executeQuery();
            while (resultSet.next()) {
                Snack snack = new Snack();
                Double price = Math.round(resultSet.getDouble("price") * 20.0) / 20.0;
                snack.setCost(price);
                snack.setCode(resultSet.getInt("code"));
                snack.setName(resultSet.getString("name"));
                snack.setCategory(resultSet.getString("category"));
                snack.setShoppingBasket(0);
                Items.add(snack);
            }
            resultSet.close();

            return Items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*----------------------------------------------------------------------------*/

    @Override
    public List<String> getItemsOfCat(String itemCategory) throws InvalidCategorySpecifiedException {
        List<String> Items = new ArrayList<>();
        try {
            List<String> categories = getCategories();
            boolean valid = false;
            for (String category : categories) {
                if (category.equals(itemCategory)) {
                    valid = true;
                }
            }
            if (!valid) {
                throw new InvalidCategorySpecifiedException("Invalid category specified");
            }

            String getItemSQL = "SELECT name, price FROM Item WHERE category = ?";
            PreparedStatement getItemStatement = statementManager.prepareStatement(getItemSQL);
            getItemStatement.setString(1, itemCategory);

            ResultSet resultSet = getItemStatement.executeQuery();
            while (resultSet.next()) {
                Double price = Math.round(resultSet.getDouble("price") * 20.0) / 20.0;
                String stringified_snack = String.format("%s $%.2f", resultSet.getString("name"), price);
                Items.add(stringified_snack);
            }
            resultSet.close();

            return Items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertCreditCard(int userID, String name, String number) {

        try {
            String insertStmt = "INSERT INTO Cards VALUES(?, ?, ?)";
            PreparedStatement insertCardStatement = statementManager.prepareStatement(insertStmt);
            insertCardStatement.setInt(1, userID);
            insertCardStatement.setString(2, name);
            insertCardStatement.setString(3, number);
            insertCardStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cardExists(String userID) {
        String existsSQL = "SELECT 1 FROM Cards WHERE userID = ?";
        PreparedStatement exists = statementManager.prepareStatement(existsSQL);

        try {
            exists.setString(1, userID);
            ResultSet resultSet = exists.executeQuery();
            boolean value = resultSet.isBeforeFirst();
            resultSet.close();

            return value;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean specificCardExists(String userID, String name, String number) {
        String existsSQL = "SELECT* FROM Cards WHERE userid = ? AND name = ? AND number = ?";
        PreparedStatement exists = statementManager.prepareStatement(existsSQL);

        try {
            exists.setString(1, userID);
            exists.setString(2, name);
            exists.setString(3, number);
            ResultSet resultSet = exists.executeQuery();

            boolean value = resultSet.isBeforeFirst();
            resultSet.close();
            return value;
        } catch (SQLException e) {
            return false;
        }
    }



    @Override
    public ArrayList<String> getCard(String userID) {
        String existsSQL = "SELECT name, number FROM Cards WHERE userID = ?";
        PreparedStatement exists = statementManager.prepareStatement(existsSQL);

        try {
            exists.setString(1, userID);
            ResultSet resultSet = exists.executeQuery();
            if (resultSet.next()) {
                ArrayList<String> ret = new ArrayList<>();
                ret.add(resultSet.getString("name"));
                ret.add(resultSet.getString("number"));

                return ret;
            }
            resultSet.close();
            return null;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean recordTransactions(String username, List<Integer> snacks, Double total, Double inserted, Double change, String method) throws UserNotFoundException,InvalidInputSpecifiedException, ItemDoesntExistException, InvalidPaymentMethodException{
        if (!usernameExists(username)) {
            throw new UserNotFoundException("User doesn't exist");
        }
        if(snacks == null) {
            throw new InvalidInputSpecifiedException("need to specify at least one snack");
        }
        for(Integer each : snacks){
            if (!itemCodeExists(each)) {
                throw new ItemDoesntExistException("snack doesnt exist");
            }
        }
        if(total < 0 || inserted < 0) {
            throw new InvalidInputSpecifiedException("cannot specify negative value");
        }
        if(method != "cash" && method != "card") {
            throw new InvalidPaymentMethodException("invalid payment method");
        }

        List<String> user = getUser(username);
        Integer id = Integer.parseInt(user.get(0));
        String record = "INSERT INTO Transactions ("
        + "userid, datetime, total, inserted, change, method)"
        + " VALUES (?, DATETIME('now','localtime'), ?, ?, ?, ?)";
        PreparedStatement Statement = statementManager.prepareStatement(record);

        try{
            Statement.setInt(1, id);
            Statement.setDouble(2, total);
            Statement.setDouble(3, inserted);
            Statement.setDouble(4, change);
            Statement.setString(5, method);
            int result = Statement.executeUpdate();
        } catch(SQLException e){
            return false;
        }

        String getId = "SELECT MAX(transaction_id) AS id FROM Transactions";
        PreparedStatement getStatement = statementManager.prepareStatement(getId);

        try {
            ResultSet result = getStatement.executeQuery();
            if (result.next()) {
                int transaction_id = result.getInt("id");
                for (Integer each : snacks) {
                    String inserting = "INSERT INTO SnacksBought"
                            + " VALUES (?,?)";
                    PreparedStatement recordStatement = statementManager.prepareStatement(inserting);
                    recordStatement.setInt(1, transaction_id);
                    recordStatement.setInt(2, each);
                    recordStatement.executeUpdate();
                }
            }
            result.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

/*###############################################################################

  CASHIER TRANSACTION REPORT

################################################################################*/

    @Override
    public void cashierReportSummary(){
        String sql_report = "SELECT datetime, GROUP_CONCAT(name, ';') AS snacks, inserted, change, method FROM "+
                            "Transactions NATURAL JOIN SnacksBought JOIN " +
                            "Item on (snackid = code) " +
                            "GROUP BY transaction_id " +
                            "ORDER BY datetime ASC ";
        List<List<String>> rows = new ArrayList<>();
        PreparedStatement getStatement = statementManager.prepareStatement(sql_report);
        try {
            ResultSet results = getStatement.executeQuery();
            while (results.next()) {
                List<String> newRow = new ArrayList<>();
                newRow.add(results.getString("datetime"));
                newRow.add(results.getString("snacks"));
                newRow.add(Double.toString(results.getDouble("inserted")));
                double value = results.getDouble("change");
                value = value * 10.0/10.0;
                if (value < 0){
                    value *= -1;
                }
                newRow.add(Double.toString(value));
                newRow.add(results.getString("method"));
                rows.add(newRow);
            }
            results.close();

        } catch(SQLException e){
            e.printStackTrace();
        }

        FileWriter csvWriter = null;
            try {
                csvWriter = new FileWriter("./build/TransactionHistory.csv");

                for (List<String> rowInfo : rows){
                    csvWriter.append(String.join(",", rowInfo));
                    csvWriter.append("\n");
                }
                csvWriter.flush();
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void sellerList(){

        String sql_report = "SELECT\n" +
                "  code, name, category, quantity, price\n" +
                "FROM\n" +
                "  Item\n";

        List<List<String>> rows = new ArrayList<>();
        PreparedStatement getStatement = statementManager.prepareStatement(sql_report);
        try {
            ResultSet results = getStatement.executeQuery();
            while (results.next()) {
                List<String> newRow = new ArrayList<>();
                newRow.add(results.getString("name"));
                newRow.add(results.getString("category"));
                newRow.add(Double.toString(results.getDouble("quantity")));
                newRow.add(Double.toString(results.getDouble("price")));
                rows.add(newRow);
            }
            results.close();

        } catch(SQLException e){
            e.printStackTrace();
        }

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter("./build/availableItems.csv");

            for (List<String> rowInfo : rows){
                csvWriter.append(String.join(",", rowInfo));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignUserRole(int userId, String role) {
        String setRoleSQL = "UPDATE User SET role = ? WHERE userid = ?;";
        PreparedStatement setRole = statementManager.prepareStatement(setRoleSQL);

        try {
            setRole.setString(1, role);
            setRole.setInt(2, userId);
            setRole.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String allUsersSQL = "SELECT userid AS userId, username, role FROM User WHERE username != 'anon'";
        PreparedStatement allUsersStmt = statementManager.prepareStatement(allUsersSQL);

        List<User> users = new ArrayList<>();
        try {
            ResultSet allUsersRs = allUsersStmt.executeQuery();
            while (allUsersRs.next()) {
                users.add(new User(
                        allUsersRs.getInt("userId"),
                        allUsersRs.getString("username"),
                        allUsersRs.getString("role")
                ));
            }
            allUsersRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

/*###############################################################################

    TRANSACTIONS

################################################################################*/

    @Override
    public void recordCancelled(int userid, int reason) throws InvalidReasonException, UserNotFoundException {
        if(!userExists(userid)){
            throw new UserNotFoundException("user not found");
        }
        if(reason < 1 || reason > 4){
            throw new InvalidReasonException("unknown reason specified");
        }
        String insert = "INSERT INTO CancelledTrans (userid, datetime, reason) VALUES (?, DATETIME('now','localtime'), ?)";

        PreparedStatement insertStatement = statementManager.prepareStatement(insert);
        try{
            insertStatement.setInt(1, userid);
            insertStatement.setInt(2, reason);
            insertStatement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void addPurchaseSnack(Integer snackCode, Integer bought) throws ItemDoesntExistException, InvalidInputSpecifiedException {
        if(!itemCodeExists(snackCode)) {
            throw new ItemDoesntExistException("snack doesnt exist");
        }
        if(bought < 0){
            throw new InvalidInputSpecifiedException("cannot specify negative quantity");
        }
        String update = "UPDATE ItemSold SET sold = sold + ? WHERE code = ?";
        PreparedStatement updateStatement = statementManager.prepareStatement(update);
        try{
            updateStatement.setInt(1, bought);
            updateStatement.setInt(2, snackCode);
            updateStatement.executeUpdate();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }


    @Override
    public void generateSummaryItems(){
        String getDetails = "SELECT code, name, sold FROM ItemSold NATURAL JOIN Item";
        PreparedStatement getStatement = statementManager.prepareStatement(getDetails);

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter("./build/ItemSoldSummary.csv");
            ResultSet result = getStatement.executeQuery();
            while(result.next()){
                List<String> rowInfo = new ArrayList<>();
                rowInfo.add(result.getString("code"));
                rowInfo.add(result.getString("name"));
                rowInfo.add(result.getString("sold"));
                csvWriter.append(String.join(",", rowInfo));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void ownerUserSummary(){
        List<User> users = getAllUsers();

        List<List<String>> rows = new ArrayList<>();

        for(User element:users){
            List<String> newRow = new ArrayList<>();
            newRow.add(element.getUsername());
            newRow.add(element.getRole());
            rows.add(newRow);
        }

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter("./build/UserSummary.csv");

            for (List<String> rowInfo : rows){
                csvWriter.append(String.join(",", rowInfo));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelledTransactionSummary(){
        String sql_report = "SELECT datetime, U.username, CR.reason as reason FROM CancelledTrans CT" +
                " JOIN CancelReason CR ON (CT.reason=CR.id) NATURAL JOIN User U ";
        List<List<String>> rows = new ArrayList<>();
        PreparedStatement getStatement = statementManager.prepareStatement(sql_report);
        try {
            ResultSet results = getStatement.executeQuery();
            while (results.next()) {
                List<String> newRow = new ArrayList<>();
                newRow.add(results.getString("datetime"));
                newRow.add(results.getString("username"));
                newRow.add(results.getString("reason"));
                rows.add(newRow);
            }
            results.close();

        } catch(SQLException e){
            e.printStackTrace();
        }

        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter("./build/CancelledSummary.csv");

            for (List<String> rowInfo : rows){
                csvWriter.append(String.join(",", rowInfo));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
