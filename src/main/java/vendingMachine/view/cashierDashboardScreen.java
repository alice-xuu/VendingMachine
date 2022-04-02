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

public class cashierDashboardScreen extends Screen {

    private Button logout_btn;
    private Button MoneyDashboard_btn;
    private Button report1_btn;
    private Button report2_btn;

    private final Label message;


    public cashierDashboardScreen(Controller controller){
        super(controller);

        this.removeNode(cancel_btn);

        logout_btn = new Button("logout");
        MoneyDashboard_btn = new Button("Modify Cash");
        report1_btn = new Button("Report: Transaction Summary");
        report2_btn = new Button("Report: Available Change");
        message = new Label("");


        MoneyDashboard_btn.setLayoutX(80);
        MoneyDashboard_btn.setLayoutY(130);
        report1_btn.setLayoutX(80);
        report1_btn.setLayoutY(180);
        report2_btn.setLayoutX(80);
        report2_btn.setLayoutY(230);
        message.setLayoutX(80);
        message.setLayoutY(300);
        logout_btn.setLayoutX(5);
        logout_btn.setLayoutY(5);

        this.addNode(logout_btn);
        this.addNode(MoneyDashboard_btn);
        this.addNode(report1_btn);
        this.addNode(report2_btn);
        this.addNode(message);

        logout_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        MoneyDashboard_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        report1_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");
        report2_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white; -fx-pref-width:250px; -fx-pref-height:32px;");


        // Set screen transitions
        logout_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.logOut();
            controller.toChooseSnacksScreen();
            }
        );
        MoneyDashboard_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toModifyCashScreen();
        });
        report1_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.cashierTransactionSummary();
            message.setText("The Transaction Summary CSV file can\n be found in the build folder which is\n located in home directory");
        });
        report2_btn.setOnAction(e -> {
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
