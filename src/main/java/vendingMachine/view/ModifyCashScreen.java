package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import vendingMachine.Controller;
import vendingMachine.model.DatabaseQuery;
import vendingMachine.model.InvalidInputSpecifiedException;

public class ModifyCashScreen extends Screen {
    private Button back_btn;

    private ComboBox<String> coins;
    private final Label coins_label;
    private final TextField coins_text;
    private Button coins_btn;

    private final Label coins_msg;

    private ComboBox<String> notes;
    private final Label notes_label;
    private final TextField notes_text;
    private Button notes_btn;

    private final Label notes_msg;


    public ModifyCashScreen(Controller controller) {
        super(controller);
        DatabaseQuery dq = controller.getDbQuery();

        this.removeNode(cancel_btn);

        back_btn = new Button("back");
        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        this.addNode(back_btn);
        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        // back to cashier dashboard?
        // back_btn.setOnAction(e -> controller.toCheckoutScreen());

        //dropdown for coins
        coins = new ComboBox<>();
        coins.getItems().add("5c");
        coins.getItems().add("10c");
        coins.getItems().add("20c");
        coins.getItems().add("50c");
        coins.getItems().add("$1");
        coins.getItems().add("$2");

        coins_label = new Label("Coins");
        coins_text = new TextField();
        coins_btn = new Button("enter");
        coins_msg = new Label("");

        //dropdown for cash
        notes = new ComboBox<>();
        notes.getItems().add("$5");
        notes.getItems().add("$10");
        notes.getItems().add("$20");
        notes.getItems().add("$50");
        notes.getItems().add("$100");

        notes_label = new Label("Notes");
        notes_text = new TextField();
        notes_btn = new Button("enter");
        notes_msg = new Label("");


        List<TextField> texts = Arrays.asList(coins_text, notes_text);
        List<Label> labels = Arrays.asList(coins_label, notes_label);
        List<Button> buttons = Arrays.asList(coins_btn, notes_btn);
        List<Label> msgs = Arrays.asList(coins_msg, notes_msg);

        coins.setLayoutX(50);
        coins.setLayoutY(70);
        this.addNode(coins);

        notes.setLayoutX(50);
        notes.setLayoutY(170);
        this.addNode(notes);

        for (int i = 0; i < texts.size(); i++) {
            labels.get(i).setLayoutX(50);
            labels.get(i).setLayoutY(50 + 100*i);
            this.addNode(labels.get(i));

            texts.get(i).setPromptText("enter quantity");
            texts.get(i).setLayoutX(150);
            texts.get(i).setLayoutY(70 + 100*i);
            this.addNode(texts.get(i));

            buttons.get(i).setLayoutX(300);
            buttons.get(i).setLayoutY(70 + 100*i);
            this.addNode(buttons.get(i));

            msgs.get(i).setLayoutX(150);
            msgs.get(i).setLayoutY(110 + 100*i);
            this.addNode(msgs.get(i));
        }

        coins.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        notes.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        coins_btn.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: black;");
        notes_btn.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: black;");



        EventHandler<ActionEvent> coins_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = coins_text.getText();
            String coin = coins.getValue();

            try {
                if (coin == null){
                    throw new InvalidInputSpecifiedException("Please select a coin value");
                }
                int quantity = Integer.parseInt(quantity_string);
                if (quantity < 0){
                    throw new InvalidInputSpecifiedException("Please specify a positive integer");
                }

                dq.modifyCash(quantity, coin);
                coins_msg.setText("Quantity updated successfully for " + coin);
                coins_msg.setTextFill(Color.rgb(0, 150, 50));
                coins_text.setText("");

            } catch (NumberFormatException numberFormatException) {
                coins_msg.setText("Invalid quantity");
                coins_msg.setTextFill(Color.rgb(190, 0, 0));
            }
            catch (InvalidInputSpecifiedException invalidinput){
                coins_msg.setText(invalidinput.getMessage());
                coins_msg.setTextFill(Color.rgb(190, 0, 0));
            }
        };

        EventHandler<ActionEvent> notes_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = notes_text.getText();
            String note = notes.getValue();

            try {
                if (note == null){
                    throw new InvalidInputSpecifiedException("Please select a note value");
                }
                int quantity = Integer.parseInt(quantity_string);
                if (quantity < 0){
                    throw new InvalidInputSpecifiedException("Please specify a positive integer");
                }
                dq.modifyCash(quantity, note);
                notes_msg.setText("Quantity updated successfully for " + note);
                notes_msg.setTextFill(Color.rgb(0, 150, 50));
                notes_text.setText("");

            } catch (NumberFormatException numberFormatException) {
                notes_msg.setText("Invalid quantity");
                notes_msg.setTextFill(Color.rgb(190, 0, 0));
            }
            catch (InvalidInputSpecifiedException invalidinput){
                notes_msg.setText(invalidinput.getMessage());
                notes_msg.setTextFill(Color.rgb(190, 0, 0));
            }

        };

        coins_btn.setOnAction(coins_Handler);
        notes_btn.setOnAction(notes_Handler);
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            if(controller.getUserDetails()[3].equals("cashier")){
            controller.toCashierDashboard();}
            else if(controller.getUserDetails()[3].equals("owner")){
                controller.toOwnerDashboard();}
        });


    }

    @Override
    public Scene getScene() {
        coins_msg.setText("");
        notes_msg.setText("");
        notes_text.setText("");
        coins_text.setText("");

        return super.getScene();
    }

}
