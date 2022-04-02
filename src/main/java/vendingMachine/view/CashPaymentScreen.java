package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.stage.Stage;
import vendingMachine.Controller;
import vendingMachine.model.*;

public class CashPaymentScreen extends Screen {
    private Button back_btn;
    private Button pay_btn;

    private final Label message;
    private final Label current;
    private final Label remaining;

    private Label fivec_label;
    private Label tenc_label;

    private Label twentyc_label;
    private Label fiftyc_label;
    private Label oned_label;
    private Label twod_label;
    private Label fived_label;
    private Label tend_label;
    private Label twentyd_label;
    private Label fiftyd_label;
    private Label hundred_label;

    private final TextField fivec_text;
    private final TextField tenc_text;
    private final TextField twentyc_text;
    private final TextField fiftyc_text;
    private final TextField oned_text;
    private final TextField twod_text;
    private final TextField fived_text;
    private final TextField tend_text;
    private final TextField twentyd_text;
    private final TextField fiftyd_text;
    private final TextField hundred_text;

    private Button fivec_btn;
    private Button tenc_btn;
    private Button twentyc_btn;
    private Button fiftyc_btn;
    private Button oned_btn;
    private Button twod_btn;
    private Button fived_btn;
    private Button tend_btn;
    private Button twentyd_btn;
    private Button fiftyd_btn;
    private Button hundred_btn;

    List<Integer> quantities;

    List<Integer> original_quantities;

    List<TextField> texts;
    List<Label> labels;
    List<Button> buttons;

    // ArrayList<Snack> snacks, DatabaseQuery databasequery, Stage primaryStage, ChooseSnacksScreen chooseScreen
    public CashPaymentScreen(Controller controller){
        super(controller);

        DatabaseQuery dq = controller.getDbQuery();
        controller.setPaying(true);

        back_btn = new Button("back");
        pay_btn = new Button("pay");
        message = new Label("");

        fivec_label= new Label("5c");
        fivec_text = new TextField();
        fivec_btn = new Button("enter");

        tenc_label = new Label("10c");
        tenc_text = new TextField();
        tenc_btn = new Button("enter");

        twentyc_label = new Label("20c");
        twentyc_text = new TextField();
        twentyc_btn = new Button("enter");

        fiftyc_label = new Label("50c");
        fiftyc_text = new TextField();
        fiftyc_btn = new Button("enter");

        oned_label = new Label("$1");
        oned_text = new TextField();
        oned_btn = new Button("enter");

        twod_label = new Label("$2");
        twod_text = new TextField();
        twod_btn = new Button("enter");

        fived_label = new Label("$5");
        fived_text = new TextField();
        fived_btn = new Button("enter");

        tend_label = new Label("$10");
        tend_text = new TextField();
        tend_btn = new Button("enter");

        twentyd_label = new Label("$20");
        twentyd_text = new TextField();
        twentyd_btn = new Button("enter");

        fiftyd_label = new Label("$50");
        fiftyd_text = new TextField();
        fiftyd_btn = new Button("enter");

        hundred_label = new Label("$100");
        hundred_text = new TextField();
        hundred_btn = new Button("enter");

        texts = Arrays.asList(fivec_text, tenc_text, twentyc_text, fiftyc_text, oned_text,
                twod_text, fived_text, tend_text, twentyd_text, fiftyd_text, hundred_text);
        labels = Arrays.asList(fivec_label, tenc_label, twentyc_label, fiftyc_label, oned_label,
                twod_label, fived_label, tend_label, twentyd_label, fiftyd_label, hundred_label);
        buttons = Arrays.asList(fivec_btn, tenc_btn, twentyc_btn, fiftyc_btn, oned_btn,
                twod_btn, fived_btn, tend_btn, twentyd_btn, fiftyd_btn, hundred_btn);

        for (int i = 0; i < texts.size(); i++) {
            labels.get(i).setLayoutX(30);
            labels.get(i).setLayoutY(45 + 40*i);
            this.addNode(labels.get(i));
            texts.get(i).setPromptText("enter quantity");
            texts.get(i).setLayoutX(70);
            texts.get(i).setLayoutY(45 + 40*i);
            this.addNode(texts.get(i));
            buttons.get(i).setLayoutX(250);
            buttons.get(i).setLayoutY(45 + 40*i);
            this.addNode(buttons.get(i));
        }

        quantities = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0);
        current = new Label("Inserted = $0.00");
        current.setLayoutX(30);
        current.setLayoutY(480);
        this.addNode(current);

        List<Double> calculations = dq.calculateCash(quantities, getTotalCost());
        String remainingS = String.format("$%.2f", calculations.get(1));

        remaining = new Label("Remaining = " + remainingS);
        remaining.setLayoutX(30);
        remaining.setLayoutY(495);
        this.addNode(remaining);

        message.setText("");


        EventHandler<ActionEvent> fivec_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = fivec_text.getText();
            update_calculations(dq, 0, quantities, quantity_string);
        };
        EventHandler<ActionEvent> tenc_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = tenc_text.getText();
            update_calculations(dq, 1, quantities, quantity_string);
        };
        EventHandler<ActionEvent> twentyc_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = twentyc_text.getText();
            update_calculations(dq, 2, quantities, quantity_string);
        };
        EventHandler<ActionEvent> fiftyc_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = fiftyc_text.getText();
            update_calculations(dq, 3, quantities, quantity_string);
        };
        EventHandler<ActionEvent> oned_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = oned_text.getText();
            update_calculations(dq, 4, quantities, quantity_string);
        };
        EventHandler<ActionEvent> twod_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = twod_text.getText();
            update_calculations(dq, 5, quantities, quantity_string);
        };
        EventHandler<ActionEvent> fived_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = fived_text.getText();
            update_calculations(dq, 6, quantities, quantity_string);
        };
        EventHandler<ActionEvent> tend_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = tend_text.getText();
            update_calculations(dq, 7, quantities, quantity_string);
        };
        EventHandler<ActionEvent> twentyd_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = twentyd_text.getText();
            update_calculations(dq, 8, quantities, quantity_string);
        };
        EventHandler<ActionEvent> fiftyd_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = fiftyd_text.getText();
            update_calculations(dq, 9, quantities, quantity_string);
        };
        EventHandler<ActionEvent> hundred_Handler = e -> {
            controller.seconds = 0;
            String quantity_string = hundred_text.getText();
            update_calculations(dq, 10, quantities, quantity_string);
        };

        fivec_btn.setOnAction(fivec_Handler);
        tenc_btn.setOnAction(tenc_Handler);
        twentyc_btn.setOnAction(twentyc_Handler);
        fiftyc_btn.setOnAction(fiftyc_Handler);
        oned_btn.setOnAction(oned_Handler);
        twod_btn.setOnAction(twod_Handler);
        fived_btn.setOnAction(fived_Handler);
        tend_btn.setOnAction(tend_Handler);
        twentyd_btn.setOnAction(twentyd_Handler);
        fiftyd_btn.setOnAction(fiftyd_Handler);
        hundred_btn.setOnAction(hundred_Handler);

        EventHandler<ActionEvent> pay_Handler = e -> {
            controller.seconds = 0;
            controller.setNoChange(false);

            try {
                List<Double> new_calculation = dq.calculateCash(quantities, getTotalCost());
                List<Integer> change = dq.calculateChange(new_calculation.get(1), quantities, original_quantities);

                message.setText("Transaction successful.");
                message.setTextFill(Color.rgb(0, 150, 50));

                // Display the change screen
                controller.getChangeScreen().setQuantities(change);
                Stage stage = new Stage();
                stage.setTitle("Change Screen");
                stage.setScene(controller.getChangeScreen().getScene());
                stage.setX(200);
                stage.setY(200);
                stage.show();

                controller.executePurchase(new_calculation.get(0), new_calculation.get(1), "cash");
                controller.toChooseSnacksScreen();

            } catch (NotEnoughMoneyException notEnoughMoneyException) {
                message.setText("Not enough money inserted, please enter remaining\namount or cancel transaction.");
                message.setTextFill(Color.rgb(190, 0, 0));
            } catch (NoAvailableChangeException noAvailableChangeException) {
                controller.setNoChange(true);
                message.setText("No available change, please insert different\nnotes/coins or cancel transaction.");
                message.setTextFill(Color.rgb(190, 0, 0));
            }
        };
        pay_btn.setOnAction(pay_Handler);


        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        pay_btn.setLayoutX(350);
        pay_btn.setLayoutY(510);

        message.setLayoutX(30);
        message.setLayoutY(515);

        this.addNode(back_btn);
        this.addNode(pay_btn);
        this.addNode(message);

        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        pay_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        // Set screen transitions
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setPaying(false);
            controller.setNoChange(false);
            controller.toCheckoutScreen();
        });
    }

    /*
    public void setPayEvent(EventHandler<ActionEvent> eventHandler) {

        pay_btn.setOnAction(eventHandler);
    }

     */

    public void update_calculations(DatabaseQuery dq, int index, List<Integer> quantities, String quantity_string){
        try{
            int quantity = Integer.parseInt(quantity_string);
            if (quantity < 0){
                throw new InvalidInputSpecifiedException("Please specify a positive integer");
            }
            quantities.set(index, quantity);
            message.setText("Quantity added successfully");
            message.setTextFill(Color.rgb(0, 150, 50));
            List<Double> new_calculation = dq.calculateCash(quantities, getTotalCost());
            String currentS = String.format("$%.2f", new_calculation.get(0));

            current.setText("Inserted = " + currentS);
            if (new_calculation.get(1) < 0){
                Double value = Math.abs(new_calculation.get(1));
                String remainingS = String.format("-$%.2f", value);
                remaining.setText("Remaining = " + remainingS);
            }
            else{
                String remainingS = String.format("$%.2f", new_calculation.get(1));
                remaining.setText("Remaining = " + remainingS);
            }
        }
        catch (NumberFormatException numberFormatException) {
            message.setText("Invalid quantity");
            message.setTextFill(Color.rgb(190, 0, 0));
        }
        catch (InvalidInputSpecifiedException invalidinput){
            message.setText(invalidinput.getMessage());
            message.setTextFill(Color.rgb(190, 0, 0));
        }
    }

    public Double getTotalCost(){
        Double total = 0.00;
        for (Snack snack : controller.getSnacks()) {
            total += snack.getCost()*snack.getShoppingBasket();
        }
        return total;
    }

    @Override
    public Scene getScene() {
        controller.setPaying(true);
        for (int i = 0; i < texts.size(); i++) {
            texts.get(i).setText("");
        }

        original_quantities = controller.getDbQuery().getQuantities();

        message.setText("");

        quantities = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0);
        current.setText("Inserted = $0.00");

        List<Double> calculations = controller.getDbQuery().calculateCash(quantities, getTotalCost());
        String remainingS = String.format("$%.2f", calculations.get(1));

        remaining.setText("Remaining = " + remainingS);

        return super.getScene();
    }

}
