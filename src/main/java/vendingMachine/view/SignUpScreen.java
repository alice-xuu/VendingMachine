package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import vendingMachine.Controller;
import vendingMachine.model.InvalidInputSpecifiedException;
import vendingMachine.model.UsernameAlreadyExistsException;
import vendingMachine.model.PasswordVerificationMismatchException;
import vendingMachine.model.PasswordTooShortException;
import vendingMachine.model.UserNotFoundException;
import java.util.List;

public class SignUpScreen extends Screen {
    private Button back_btn;
    private Button submit_btn;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final PasswordField passwordValidation;
    private final Label message;

    public SignUpScreen(Controller controller){
        super(controller);

        back_btn = new Button("back");
        submit_btn = new Button("submit");
        message = new Label("");

        usernameField = new TextField();
        usernameField.setPromptText("Enter a Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter a Password");

        passwordValidation = new PasswordField();
        passwordValidation.setPromptText("Validate your Password");

        usernameField.setLayoutX(123);
        usernameField.setLayoutY(115);
        passwordField.setLayoutX(123);
        passwordField.setLayoutY(150);
        passwordValidation.setLayoutX(123);
        passwordValidation.setLayoutY(185);
        message.setLayoutX(100);
        message.setLayoutY(216);

        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        submit_btn.setLayoutX(223);
        submit_btn.setLayoutY(240);

        this.addNode(back_btn);
        this.addNode(submit_btn);
        this.addNode(usernameField);
        this.addNode(passwordField);
        this.addNode(passwordValidation);
        this.addNode(message);

        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        submit_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        EventHandler<ActionEvent> AccountCreationHandler = e -> {
            controller.seconds = 0;
            message.setText("");
            if(!controller.getUserDetails()[0].equals("1")) {
                message.setLayoutX(50);
                message.setText("Cannot create a new account when already logged in");
                message.setTextFill(Color.rgb(210, 39, 30));
            } else {
                try{
                    boolean created = controller.getDbQuery().createUser(usernameField.getText(), passwordField.getText(), passwordValidation.getText());
                    if (created) {
                        List<String> userDeet = controller.getDbQuery().getUser(usernameField.getText());
                        usernameField.clear();

                        controller.setUserDetails(userDeet.get(0), userDeet.get(1), userDeet.get(2), "customer");
                        controller.resetShoppingBasket();
                        controller.toChooseSnacksScreen();
                    } else{
                        message.setLayoutX(90);
                        message.setText("Invalid attempt to create an account");
                        message.setTextFill(Color.rgb(210, 39, 30));
                    }
                } catch(InvalidInputSpecifiedException ex) {
                    message.setLayoutX(120);
                    message.setText("Invalid username/password");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } catch(UsernameAlreadyExistsException ex) {
                    message.setLayoutX(120);
                    message.setText("Invalid username/password");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } catch(PasswordTooShortException ex) {
                    message.setLayoutX(70);
                    message.setText("Password must be at least 8 characters long");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } catch(PasswordVerificationMismatchException ex) {
                    message.setLayoutX(120);
                    message.setText("Password's do not match");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } catch(UserNotFoundException ex) {
                    message.setLayoutX(120);
                    message.setText("Who are you?");
                    message.setTextFill(Color.rgb(210, 39, 30));
                }
            }
            passwordField.clear();
            passwordValidation.clear();
        };
        submit_btn.setOnAction(AccountCreationHandler);

        // Set screen transitions
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toChooseSnacksScreen();
        });
    }

    @Override
    public Scene getScene() {
        usernameField.clear();
        message.setText("");
        message.setLayoutX(100);
        return super.getScene();
    }

}
