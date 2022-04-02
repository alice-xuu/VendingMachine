package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import vendingMachine.Controller;
import vendingMachine.model.InvalidUserException;
import vendingMachine.model.Snack;

import java.util.ArrayList;

public class OwnerDashboardScreen extends Screen {

    private Button logout_btn;
    private Button MoneyDashboard_btn;
    private Button EditUser_btn;
    private Button report1_btn;
    private Button report2_btn;
    private Button ModifyItems_btn;
    private Button AvailableItems_btn;
    private Button SummaryItems_btn;
    private Button TransactionSummary_btn;
    private Button AvailableChange_btn;


    private final Label message;


    public OwnerDashboardScreen(Controller controller){
        super(controller);

        this.removeNode(cancel_btn);

        logout_btn = new Button("logout");
        EditUser_btn = new Button("Modify Users");
        MoneyDashboard_btn = new Button("Modify Cash");
        report1_btn = new Button("Report: User Details");
        report2_btn = new Button("Report: Cancelled Transactions");
        ModifyItems_btn = new Button ("Modify Items");
        AvailableItems_btn = new Button("Report: Available Items");
        SummaryItems_btn = new Button("Report: Summary of Items Sold");
        TransactionSummary_btn = new Button("Report: Transaction Summary");
        AvailableChange_btn = new Button("Report: Available Change");

        message = new Label("");

        ModifyItems_btn.setLayoutX(80);
        ModifyItems_btn.setLayoutY(130 - 100);
        EditUser_btn.setLayoutX(80);
        EditUser_btn.setLayoutY(180 - 100);
        MoneyDashboard_btn.setLayoutX(80);
        MoneyDashboard_btn.setLayoutY(230 - 100);
        report1_btn.setLayoutX(80);
        report1_btn.setLayoutY(280 - 100);
        report2_btn.setLayoutX(80);
        report2_btn.setLayoutY(330 - 100);
        AvailableItems_btn.setLayoutX(80);
        AvailableItems_btn.setLayoutY(380 - 100);
        SummaryItems_btn.setLayoutX(80);
        SummaryItems_btn.setLayoutY(430 - 100);
        TransactionSummary_btn.setLayoutX(80);
        TransactionSummary_btn.setLayoutY(480 - 100);
        AvailableChange_btn.setLayoutX(80);
        AvailableChange_btn.setLayoutY(530 - 100);

        message.setLayoutX(80);
        message.setLayoutY(380 + 100);
        logout_btn.setLayoutX(5);
        logout_btn.setLayoutY(5);

        this.addNode(EditUser_btn);
        this.addNode(logout_btn);
        this.addNode(MoneyDashboard_btn);
        this.addNode(ModifyItems_btn);
        this.addNode(report1_btn);
        this.addNode(report2_btn);
        this.addNode(AvailableItems_btn);
        this.addNode(SummaryItems_btn);
        this.addNode(AvailableChange_btn);
        this.addNode(TransactionSummary_btn);
        this.addNode(message);

        logout_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        MoneyDashboard_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        EditUser_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        ModifyItems_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        report1_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        report2_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        AvailableItems_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        SummaryItems_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        TransactionSummary_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        AvailableChange_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");


        // Set screen transitions
        logout_btn.setOnAction(e -> {
                    controller.logOut();
                    controller.toChooseSnacksScreen();
                }
        );
        MoneyDashboard_btn.setOnAction(e ->{
                controller.seconds = 0;
                controller.toModifyCashScreen();});
        EditUser_btn.setOnAction(e ->{
                controller.seconds = 0;
                controller.toAssignRolesScreen();});
        ModifyItems_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toModifyItemDetailsScreen();});

        report1_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.userDetailsSummary();
            message.setText("The User Summary CSV file can\n be found in the build folder which is\n located in home directory");
        });
        report2_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.cancelledSummary();
            message.setText("The Cancelled Transactions Summary CSV file can\n be found in the build folder which is\n located in home directory");
        });
        AvailableItems_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.sellerAvailableItemSummary();
            message.setText("The Available Items CSV file can\n be found in the build folder which is\n located in home directory");
        });
        SummaryItems_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.sellerItemSoldSummary();
            message.setText("The Summary of Items Sold CSV file can\n be found in the build folder which is\n located in home directory");
        });
        TransactionSummary_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.cashierTransactionSummary();
            message.setText("The Transaction Summary CSV file can\n be found in the build folder which is\n located in home directory");
        });
        AvailableChange_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.cashierChangeSummary();
            message.setText("The Available Change CSV file can\n be found in the build folder which is\n located in home directory");
        });

    }

    @Override
    public Scene getScene() {
        message.setText("");
        return super.getScene();
    }
}
