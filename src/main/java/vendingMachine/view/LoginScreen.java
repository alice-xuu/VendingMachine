package vendingMachine.view;

import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import vendingMachine.Controller;
import vendingMachine.model.InvalidUserException;
import vendingMachine.model.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class LoginScreen extends Screen {

    private Button back;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Label message;
    private Button login;

    public LoginScreen(Controller controller){
        super(controller);

        back = new Button("back");
        login = new Button("login");

        usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        message = new Label("");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        usernameField.setLayoutX(123);
        usernameField.setLayoutY(115);
        passwordField.setLayoutX(123);
        passwordField.setLayoutY(150);
        message.setLayoutX(123);
        message.setLayoutY(180);
        back.setLayoutX(5);
        back.setLayoutY(5);
        login.setLayoutX(233);
        login.setLayoutY(200);

        this.addNode(back);
        this.addNode(login);
        this.addNode(usernameField);
        this.addNode(passwordField);
        this.addNode(message);

        back.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        login.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        // Set screen transitions
        login.setOnAction(e -> {
            controller.seconds = 0;
            ArrayList<String> cred = new ArrayList<>();
            cred.add(usernameField.getText());
            cred.add(passwordField.getText());

            try{
                controller.getDbQuery().VerifyUserCredentials(cred);
                List<String> details = controller.getDbQuery().getUser(cred.get(0));
                /* 0: user id
                    1: username
                    2: password
                    3: role
                */
                controller.setUserDetails(details.get(0), details.get(1), details.get(2), details.get(3));

                switch (details.get(3)) {
                    case "customer":
                        controller.resetShoppingBasket();
                        controller.toChooseSnacksScreen();
                        break;
                    case "cashier":
                        controller.resetShoppingBasket();
                        controller.toCashierDashboard();
                        break;
                    case "seller":
                        controller.toSellerDashboard();
                        break;
                    case "owner":
                        controller.resetShoppingBasket();
                        controller.toOwnerDashboard();
                        break;
                }

            }catch (InvalidUserException exp){
                message.setText("Invalid username/password");
                message.setTextFill(Color.rgb(210, 39, 30));

            } catch (UserNotFoundException exp){
                message.setText("User not found");
                message.setTextFill(Color.rgb(210, 39, 30));
            }
            passwordField.clear();
        });

        back.setOnAction(e -> {
            controller.seconds = 0;
            controller.toChooseSnacksScreen();
        });
    }

    @Override
    public Scene getScene() {
        usernameField.clear();
        passwordField.clear();
        message.setText("");
        return super.getScene();
    }
}
