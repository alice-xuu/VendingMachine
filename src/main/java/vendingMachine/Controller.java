package vendingMachine;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vendingMachine.model.DatabaseQuery;
import vendingMachine.model.Snack;
import vendingMachine.view.*;
import vendingMachine.model.InvalidInputSpecifiedException;
import vendingMachine.model.ItemDoesntExistException;
import vendingMachine.model.UserNotFoundException;
import vendingMachine.model.InvalidPaymentMethodException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 * This class encapsulates all information needed by
 * screens
 */
public class Controller {
    private DatabaseQuery dbQuery;

    private ArrayList<Snack> snacks;
    private String[] userDetails;
    private Stage primaryStage;

    /* Screens */
    private ChooseSnacksScreen chooseSnacksScreen;
    private CheckoutScreen checkoutScreen;
    private CardPaymentScreen cardPaymentScreen;
    private CashPaymentScreen cashPaymentScreen;
    private LoginScreen loginScreen;
    private SignUpScreen signUpScreen;
    private ModifyCashScreen modifyCashScreen;
    private HistoryScreen historyScreen;
    private cashierDashboardScreen cashierDashboard;
    private SellerDashboardScreen sellerDashboard;
    private OwnerDashboardScreen ownerDashboard;
    private ChangeScreen changeScreen;
    private ModifyItemDetailsScreen modifyItemDetailsScreen;
    private AssignRolesScreen assignRolesScreen;
    private OwnerDashboardScreen ownerDashboardScreen;

    private SaveCard saveCard;
    private String cardName;
    private String cardNumber;

    private boolean Idle;
    private boolean noChange;
    private boolean paying;
    private boolean timeout;

    public int seconds;
    public final Timer timer;
    private final TimerTask task;
;
    public void createScreens(){
        chooseSnacksScreen = new ChooseSnacksScreen(this);
        checkoutScreen = new CheckoutScreen(this);
        cardPaymentScreen = new CardPaymentScreen(this);
        cashPaymentScreen = new CashPaymentScreen(this);
        loginScreen = new LoginScreen(this);
        signUpScreen = new SignUpScreen(this);
        modifyCashScreen = new ModifyCashScreen(this);
        historyScreen = new HistoryScreen(this);
        cashierDashboard = new cashierDashboardScreen(this);
        sellerDashboard = new SellerDashboardScreen(this);
        ownerDashboard = new OwnerDashboardScreen(this);
        changeScreen = new ChangeScreen(this);
        saveCard = new SaveCard(this);
        modifyItemDetailsScreen = new ModifyItemDetailsScreen(this);
        assignRolesScreen = new AssignRolesScreen(this);
        ownerDashboardScreen = new OwnerDashboardScreen(this);
    }

    public Controller(DatabaseQuery dbQuery, Stage primaryStage) {
        this.dbQuery = dbQuery;
        this.snacks = this.dbQuery.getAllSnacks();
        this.userDetails = new String[] {"1", "anon", null, "customer"};
        this.primaryStage = primaryStage;

        seconds = 0;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (getUserDetails()[3].equals("customer")){
                    seconds++;
                }
                if (seconds >= 120){
                    Platform.runLater(() -> {
                        timeout = true;
                        cancelTransaction();
                    });
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

        createScreens();

        this.primaryStage.setTitle("Lite Snacks");
        timeout = false;
        Idle = true;
        noChange = false;
        paying = false;
        toChooseSnacksScreen();
    }

    public void cancelTransaction() {
        try{
            if(timeout){
                getDbQuery().recordCancelled(Integer.parseInt(this.getUserDetails()[0]), 1);
            }
            else if(!Idle && !checkNoChange() && !checkPaying()){
                getDbQuery().recordCancelled(Integer.parseInt(this.getUserDetails()[0]), 2);
            } else if(!Idle && checkNoChange() && checkPaying()) {
                getDbQuery().recordCancelled(Integer.parseInt(getUserDetails()[0]), 4);
            } else if(!Idle && !checkNoChange() && checkPaying()) {
                getDbQuery().recordCancelled(Integer.parseInt(getUserDetails()[0]), 3);
            }
            timeout = false;
            Idle = true;
            setNoChange(false);
            setPaying(false);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        for (Snack each : getSnacks()) {
            int bought = each.getShoppingBasket();
            each.setShoppingBasket(0);
            getDbQuery().changeItemStock(each.getName(), bought);
        }
        seconds = 0;
        logOut();
        toChooseSnacksScreen();
    }

    /**
     * Changes the stage scene to the one given
     * @param scene scene to change stage to
     */
    private void setScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /* Controller state getters */


    public ArrayList<Snack> getSnacks() {
        return snacks;
    }

    public String[] getUserDetails() {
        /* 0: user id
            1: username
            2: password
            3: role
        */

        return this.userDetails;
    }

    public DatabaseQuery getDbQuery() {
        return dbQuery;
    }

    public boolean checkIdle() {
        return Idle;
    }

    public boolean checkNoChange(){
        return noChange;
    }

    public boolean checkPaying(){
        return paying;
    }

    /* Methods to move to another screen */


    public void toChooseSnacksScreen() {
        setScene(chooseSnacksScreen.getScene());
    }

    public void toCheckoutScreen() {
        setScene(checkoutScreen.getScene());
    }

    public void toCardPaymentScreen() {
        setScene(cardPaymentScreen.getScene());
    }

    public void toCashPaymentScreen() {
        setScene(cashPaymentScreen.getScene());
    }

    public void toLoginScreen() {
        setScene(loginScreen.getScene());
    }

    public void toSignUpScreen() {
        setScene(signUpScreen.getScene());
    }

    public void toModifyCashScreen() {
        setScene(modifyCashScreen.getScene());
    }

    public void toCashierDashboard() {
        setScene(cashierDashboard.getScene());
    }

    public void toSellerDashboard() {
        setScene(sellerDashboard.getScene());
    }

    public void toOwnerDashboard() { setScene(ownerDashboard.getScene()); }

    public Screen getHistoryScreen() {
        return historyScreen;
    }

    public void toModifyItemDetailsScreen() {
        setScene(modifyItemDetailsScreen.getScene());
    }


    public void toAssignRolesScreen() {
        setScene(assignRolesScreen.getScene());
    }

    public SaveCard getSaveCardScreen() {
        return saveCard;
    }

    public ChangeScreen getChangeScreen() {
        return changeScreen;
    }

    public String getCardName(){
        return this.cardName;
    }

    public String getCardNumber(){
        return this.cardNumber;
    }

    public void setCardName(String cardName){
        this.cardName = cardName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /* updating data */
    public void resetShoppingBasket() {
        for(Snack each: getSnacks()){
            each.setShoppingBasket(0);
        }
    }

    public void setUserDetails(String userid, String username, String password, String role) {
        this.userDetails[0] = userid;
        this.userDetails[1] = username;
        this.userDetails[2] = password;
        this.userDetails[3] = role;
    }

    public void logOut(){
        resetShoppingBasket();
        setUserDetails("1", "anon", null, "customer");
    }

    public void cashierTransactionSummary(){
        this.dbQuery.cashierReportSummary();
    }

    public void userDetailsSummary(){
        this.dbQuery.ownerUserSummary();
    }
    public void cancelledSummary(){
        this.dbQuery.cancelledTransactionSummary();
    }

    public void cashierChangeSummary(){
        this.dbQuery.generateAvailableChangeFile();
    }

    public void sellerItemSoldSummary(){
        this.dbQuery.generateSummaryItems();
    }

    public void sellerAvailableItemSummary(){
        this.dbQuery.sellerList();
    }

    public void executePurchase(double paid, double change, String method) {
        // Update database
        List<Integer> snacks = new ArrayList<>();
        double total = 0;
        for (Snack each: getSnacks()) {
            getDbQuery().changeItemStock(each.getName(), - each.getShoppingBasket());
            if(each.getShoppingBasket() > 0) {
                try{
                    getDbQuery().addPurchaseSnack(each.getCode(), each.getShoppingBasket());
                } catch(ItemDoesntExistException ex){
                    ex.printStackTrace();
                } catch(InvalidInputSpecifiedException ex){
                    ex.printStackTrace();
                }
                snacks.add(each.getCode());
                total += each.getShoppingBasket() * each.getCost();
            }
        }
        try{
            this.dbQuery.recordTransactions(this.userDetails[1], snacks, total, paid, change, method);
        }
        catch(InvalidInputSpecifiedException ex) {
            ex.printStackTrace();
        } catch(ItemDoesntExistException ex) {
            ex.printStackTrace();
        } catch(UserNotFoundException ex) {
            ex.printStackTrace();
        } catch(InvalidPaymentMethodException ex){
            ex.printStackTrace();
        }

        resetShoppingBasket();
    }

    public void setSnacks(ArrayList<Snack> snacks) {
        this.snacks = snacks;
    }

    public void setIdle(boolean idle){
        this.Idle = idle;
    }

    public void setNoChange(boolean noChange) {
        this.noChange = noChange;
    }

    public void setPaying(boolean paying) {
        this.paying = paying;
    }


}
