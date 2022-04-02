package vendingMachine.model;

import java.util.List;
import java.util.ArrayList;

public interface DatabaseQuery {

    //checks if entered credentials match an entry in the database
    void VerifyUserCredentials(List<String> cred) throws InvalidUserException;

    /*
    cashier can modify the amounts of coins and notes of the vending machine
     */
    void modifyCash(Integer quantity, String cashValue);

    List<Double> calculateCash(List<Integer> quantities, Double total);

    List<Integer> findAmounts(List<Integer> finList, List<Integer> change, List<Double> denom, List<Integer> quantities, Double owed, int cashIndex);

    List<String> getFractions();

    void modifyItemDetails(String snack, String detail, String input) throws InvalidInputSpecifiedException;

    // generate a csv file containing cash and quantity
    void generateAvailableChangeFile();

    List<Integer> getQuantities();

    /*
    Input: the amount owing the user. I.e. amounts.get(1) from calculateCash, remaining is positive if not enough money given, is negative if vending machine owes user.
    Throws NotEnoughMoney Exception if user didn't input enough money.
    Throws NoAvailableChangeException if there isn't exact change for the customer.
    Output: change as list of quantities of money (order: 5c -> $100)
     */

    List<Integer> calculateChange(Double remaining, List<Integer> inserted_quantities, List<Integer> original_quantities) throws NoAvailableChangeException, NotEnoughMoneyException;

    /**
     *
     * @param itemName Name of item to check
     * @return If the item has an entry in the database
     */
    boolean itemExists(String itemName);


    /**
     *
     * @param code code of item to check
     * @return If the item has an entry in the database
     */
    boolean itemCodeExists(Integer code);

    /**
     *
     * @param itemName Name of item to check
     * @return quantity of item left
     */
    int itemStock(String itemName);

    /**
     * Should be run before every decrease in stock
     * @param itemName Name of item to check
     * @param quantity Quantity of item to check for, should be > 0
     * @return true if the item exists and has stock >= quantity, false otherwise
     */
    boolean itemInStock(String itemName, int quantity);

    /**
     *
     * @param itemName Name of item to change
     * @param changeInStock Difference in stock, can be positive or negative
     */
    void changeItemStock(String itemName, int changeInStock);

    /**
     *
     * @return list of Strings representing snack categories
     */
    List<String> getCategories();

    /**
     *
     * @param itemCategory name of snack category that we want to fetch snacks on
     * @return list of list of Strings containing name and price of each snacks of itemCategory
     */
    List<String> getItemsOfCat(String itemCategory) throws InvalidCategorySpecifiedException;

    /**
     *
     * @param userid user id to check, 1 is anonymous
     * @return whether or not the user id exists in the user table
     */
    boolean userExists(int userid);

    /**
     * Returns the 5 most recently purchased items of the user with the
     * given userid.
     * A user id
     * @param userid user id to get purchase history of, 1 is anonymous
     * @return a list containing rows that each have and item name and price
     */
    List<List<String>> getPurchaseHistory(int userid);

    /**
     * Query the database to fetch all snacks data
     * Instantiate a snack object for each snacks.
     * @return a list containing snack instances
     */
    ArrayList<Snack> getAllSnacks();

    /**
     * Checks if a username already exists
     * Checks if password matches verification password
     * If username doesnt exist yet and password is valid we add user to the database
     * @return boolean that indicates whether account creation is successful
     */
    boolean createUser(String username, String password, String verification) throws PasswordVerificationMismatchException, UsernameAlreadyExistsException, PasswordTooShortException, InvalidInputSpecifiedException;

    /**
     * Checks if a username already exists
     * @return boolean that indicates whether username already exists
     */
    boolean usernameExists(String username);

    /**
     * Associate a credit card with a customer
     */
    void insertCreditCard(int userID, String name, String number);

    /**
     * Checks if a card is associated with a user id
     * @return boolean that indicates whether a card is associated with a user id
     */
    boolean cardExists(String userID);

    /**
     * Checks if a specific card is associated with a user id
     * @return boolean that indicates whether a card is associated with a user id
     */
    boolean specificCardExists(String userID, String name, String number);

    /**
     * @return returns the card is associated with the user id
     */
    ArrayList<String> getCard(String userID);

    /**
     * Fetches data given username of account
     * @return list of string containing data
     */
    List<String> getUser(String username) throws UserNotFoundException;

    /**
     * records a single transaction
     * @return boolean value indicating whether the process was successful
     */
    boolean recordTransactions(String username, List<Integer> snacks, Double total, Double inserted, Double change, String method) throws UserNotFoundException,InvalidInputSpecifiedException, ItemDoesntExistException, InvalidPaymentMethodException;

    /**
     * creates file of the transaction summary
     */
    void cashierReportSummary();

    /**
     * creates list of available items
     */
    void sellerList();

    /**
     * Sets the user with userid to the role given
     * @param userId unique user id
     * @param role a user role
     */
    void assignUserRole(int userId, String role);

    /**
     * Returns all users currently recorded in the database
     * @return list of all users
     */
    List<User> getAllUsers();

    /**
     * records cancelled transaction
     * @param username of account who cancelled and reason code
     */
    void recordCancelled(int userid, int reason) throws InvalidReasonException, UserNotFoundException;

    /**
     * Record quantity of snack bought
     */
    void addPurchaseSnack(Integer snackCode, Integer bought) throws ItemDoesntExistException, InvalidInputSpecifiedException ;

    /**
     * generate summary of items csv file
     */
    void generateSummaryItems();

    /**
     * creates file of the user summary
     */
    void ownerUserSummary();

    /**
     * creates file of the cancelled transactions summary
     */
    void cancelledTransactionSummary();
}
