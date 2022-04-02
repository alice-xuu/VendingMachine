package vendingMachine.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import vendingMachine.Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;


public class CardPaymentScreen extends Screen {
    private Button back_btn;
    private Button pay_btn;
    private final TextField CardholderName;
    private final PasswordField CreditCardNumber;
    private final Label message;
    private JSONArray creditCards;

    public CardPaymentScreen(Controller controller){
        super(controller);
        controller.setPaying(true);
        back_btn = new Button("back");
        pay_btn = new Button("pay");
        message = new Label("");

        JSONParser parser = new JSONParser();
        try{
            creditCards = (JSONArray) parser.parse(new FileReader("credit_cards.json"));
        } catch(Exception e){
            e.printStackTrace();
        }

        CreditCardNumber = new PasswordField();
        CardholderName = new TextField();


        pay_btn.setOnAction(e -> {
            controller.seconds = 0;
            String name = CardholderName.getText();
            String number = CreditCardNumber.getText();

            for (Object currentCard : creditCards){

                if (((JSONObject) currentCard).get("name").equals(name) && ((JSONObject) currentCard).get("number").equals(number)){
                    controller.executePurchase(0.00, 0.00, "card");
                    controller.toChooseSnacksScreen();

                    if (!controller.getUserDetails()[0].equals("1")){

                        /* Check if card already exists */
                        if (!controller.getDbQuery().specificCardExists(
                                controller.getUserDetails()[0],
                                (String)((JSONObject) currentCard).get("name"),
                                (String)((JSONObject) currentCard).get("number"))
                        ) {
                            /* Ask if they want credit card detail saved */
                            controller.setCardName((String)((JSONObject) currentCard).get("name"));
                            controller.setCardNumber((String)((JSONObject) currentCard).get("number"));

                            Stage stage = new Stage();
                            stage.setTitle("Save Card");
                            stage.setScene(controller.getSaveCardScreen().getScene());
                            stage.setX(250);
                            stage.setY(200);
                            stage.show();
                        }
                    }
                }

                else{
                    message.setText("Invalid Card");
                    message.setTextFill(Color.rgb(210, 39, 30));
                }

                CreditCardNumber.clear();
                CardholderName.clear();
            }
        });

        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        pay_btn.setLayoutX(350);
        pay_btn.setLayoutY(510);

        CardholderName.setLayoutX(130);
        CardholderName.setLayoutY(115);
        CreditCardNumber.setLayoutX(130);
        CreditCardNumber.setLayoutY(150);
        message.setLayoutX(130);
        message.setLayoutY(185);

        this.addNode(back_btn);
        this.addNode(pay_btn);
        this.addNode(CardholderName);
        this.addNode(CreditCardNumber);
        this.addNode(message);

        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        pay_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        // Set screen transitions
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setPaying(false);
            controller.toCheckoutScreen();
        });
    }



    @Override
    public Scene getScene() {
        controller.setPaying(true);
        message.setText("");

        CardholderName.setText("");
        CreditCardNumber.setText("");

        if (controller.getDbQuery().cardExists(controller.getUserDetails()[0])){
            ArrayList<String> cardDetails = controller.getDbQuery().getCard(controller.getUserDetails()[0]);
            CardholderName.setText(cardDetails.get(0));
            CreditCardNumber.setText(cardDetails.get(1));
        }else{
            CardholderName.setPromptText("Card holder name");
            CreditCardNumber.setPromptText("Card number");
        }

        return super.getScene();
    }

}
