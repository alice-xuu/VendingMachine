package vendingMachine.view;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import vendingMachine.Controller;
import vendingMachine.model.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChooseSnacksScreen extends Screen {
    private Button sign_up_btn;
    private Button login_btn;
    private Button logout_btn;
    private Button checkout_btn;
    private ComboBox<String> drinkSnacks;
    private ComboBox<String> candySnacks;
    private ComboBox<String> chipSnacks;
    private ComboBox<String> chocoSnacks;
    private final Label message;
    private final Label inBasket;
    private HBox hBoxDrinks;
    private HBox hBoxCandies;
    private HBox hBoxChips;
    private HBox hBoxChoco;
    private ComboBox<String> DrinksQuantity;
    private ComboBox<String> CandiesQuantity;
    private ComboBox<String> ChipsQuantity;
    private ComboBox<String> ChocoQuantity;
    private HBox hBoxDrinksQuantity;
    private HBox hBoxCandiesQuantity;
    private HBox hBoxChipsQuantity;
    private HBox hBoxChocoQuantity;

    // ArrayList<Snack> snacks, DatabaseQuery databasequery
    public ChooseSnacksScreen(Controller controller) {
        super(controller);

        DatabaseQuery dq = controller.getDbQuery();

        message = new Label("");
        message.setTextFill(Color.rgb(7, 87, 91));
        inBasket = new Label("(0)");
        inBasket.setTextFill(Color.rgb(7, 87, 91));

        //Buttons
        sign_up_btn = new Button("sign up");
        login_btn = new Button("login");
        logout_btn = new Button("logout");
        checkout_btn = new Button("checkout");
        Text drinks_heading = new Text("Drinks");
        Text candies_heading = new Text("Candies");
        Text chips_heading = new Text("Chips");
        Text choco_heading = new Text("Chocolates");

        cancel_btn.setLayoutX(5);
        sign_up_btn.setLayoutX(280);
        sign_up_btn.setLayoutY(5);
        login_btn.setLayoutX(350);
        login_btn.setLayoutY(5);
        logout_btn.setLayoutX(345);
        logout_btn.setLayoutY(5);
        checkout_btn.setLayoutX(325);
        checkout_btn.setLayoutY(510);
        drinks_heading.setLayoutX(5);
        drinks_heading.setLayoutY(75);
        candies_heading.setLayoutX(5);
        candies_heading.setLayoutY(175);
        chips_heading.setLayoutX(5);
        chips_heading.setLayoutY(275);
        choco_heading.setLayoutX(5);
        choco_heading.setLayoutY(375);
        message.setLayoutX(30);
        message.setLayoutY(500);
        inBasket.setLayoutX(300);
        inBasket.setLayoutY(515);

        this.addNode(sign_up_btn);
        this.addNode(login_btn);
        this.addNode(checkout_btn);
        this.addNode(drinks_heading);
        this.addNode(candies_heading);
        this.addNode(chips_heading);
        this.addNode(choco_heading);
        this.addNode(message);
        this.addNode(inBasket);

        logout_btn.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");
        login_btn.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");
        sign_up_btn.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");
        checkout_btn.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");
        drinks_heading.setStyle(" -fx-font-size: 20px;");
        candies_heading.setStyle("-fx-font-size: 20px;");
        chips_heading.setStyle(" -fx-font-size: 20px;");
        choco_heading.setStyle("-fx-font-size: 20px; -fx-text-inner-color: #f76a6f; ");
        chips_heading.setFill(Color.rgb(7, 87, 91));
        candies_heading.setFill(Color.rgb(7, 87, 91));
        drinks_heading.setFill(Color.rgb(7, 87, 91));
        choco_heading.setFill(Color.rgb(7, 87, 91));

        // Set screen transitions
        sign_up_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            controller.toSignUpScreen();
        });

        login_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            controller.toLoginScreen();
        });
        logout_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            controller.logOut();
            controller.toChooseSnacksScreen();
            }
        );
        checkout_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            controller.toCheckoutScreen();
        });

        // Create history button
        Button historyBtn = new Button("history");
        historyBtn.setLayoutX(5);
        historyBtn.setLayoutY(510);
        historyBtn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        this.addNode(historyBtn);

        // Create a new window with the history
        historyBtn.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(controller.getHistoryScreen().getScene());
            stage.setX(200);
            stage.setY(200);
            stage.show();
        });
    }


    @Override
    public Scene getScene() {
        message.setText("");
        inBasket.setText(String.format("(%d)", getBasketNum()));

        DatabaseQuery dq = controller.getDbQuery();
        try {
            drinkSnacks = new ComboBox<>();
            List<String> snacksItem = dq.getItemsOfCat("Drinks");
            for (String eachSnack : snacksItem) {
                drinkSnacks.getItems().add(eachSnack);
            }
            hBoxDrinks = new HBox(drinkSnacks);

            candySnacks = new ComboBox<>();
            snacksItem = dq.getItemsOfCat("Candies");
            for (String eachSnack : snacksItem) {
                candySnacks.getItems().add(eachSnack);
            }
            hBoxCandies = new HBox(candySnacks);

            chipSnacks = new ComboBox<>();
            snacksItem = dq.getItemsOfCat("Chips");
            for (String eachSnack : snacksItem) {
                chipSnacks.getItems().add(eachSnack);
            }
            hBoxChips = new HBox(chipSnacks);

            chocoSnacks = new ComboBox<>();
            snacksItem = dq.getItemsOfCat("Chocolates");
            for (String eachSnack : snacksItem) {
                chocoSnacks.getItems().add(eachSnack);
            }
            hBoxChoco = new HBox(chocoSnacks);
        } catch (InvalidCategorySpecifiedException ex) {
            message.setText("Error: Invalid category specified");
        }


        drinkSnacks.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white; -fx-pref-width:180px;");
        candySnacks.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white; -fx-pref-width:180px;");
        chipSnacks.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white; -fx-pref-width:180px;");
        chocoSnacks.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white; -fx-pref-width:180px;");

        hBoxDrinks.setLayoutX(5);
        hBoxDrinks.setLayoutY(100);
        hBoxCandies.setLayoutX(5);
        hBoxCandies.setLayoutY(200);
        hBoxChips.setLayoutX(5);
        hBoxChips.setLayoutY(300);
        hBoxChoco.setLayoutX(5);
        hBoxChoco.setLayoutY(400);
        this.addNode(hBoxDrinks);
        this.addNode(hBoxCandies);
        this.addNode(hBoxChips);
        this.addNode(hBoxChoco);

        DrinksQuantity = new ComboBox<>();
        CandiesQuantity = new ComboBox<>();
        ChipsQuantity = new ComboBox<>();
        ChocoQuantity = new ComboBox<>();

        // Set on quantity selection actions
        setOnQuantitySelection(drinkSnacks, DrinksQuantity, inBasket);
        setOnQuantitySelection(candySnacks, CandiesQuantity, inBasket);
        setOnQuantitySelection(chipSnacks, ChipsQuantity, inBasket);
        setOnQuantitySelection(chocoSnacks, ChocoQuantity, inBasket);

        // Set up creation of snack quantiy selection
        setOnSnackSelection(drinkSnacks, DrinksQuantity);
        setOnSnackSelection(candySnacks, CandiesQuantity);
        setOnSnackSelection(chipSnacks, ChipsQuantity);
        setOnSnackSelection(chocoSnacks, ChocoQuantity);


        DrinksQuantity.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        CandiesQuantity.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        ChipsQuantity.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        ChocoQuantity.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");

        hBoxDrinksQuantity = new HBox(DrinksQuantity);
        hBoxCandiesQuantity = new HBox(CandiesQuantity);
        hBoxChipsQuantity = new HBox(ChipsQuantity);
        hBoxChocoQuantity = new HBox(ChocoQuantity);

        hBoxDrinksQuantity.setLayoutX(280);
        hBoxCandiesQuantity.setLayoutX(280);
        hBoxChipsQuantity.setLayoutX(280);
        hBoxChocoQuantity.setLayoutX(280);

        hBoxDrinksQuantity.setLayoutY(100);
        hBoxCandiesQuantity.setLayoutY(200);
        hBoxChipsQuantity.setLayoutY(300);
        hBoxChocoQuantity.setLayoutY(400);

        this.addNode(hBoxDrinksQuantity);
        this.addNode(hBoxCandiesQuantity);
        this.addNode(hBoxChipsQuantity);
        this.addNode(hBoxChocoQuantity);

        if (!controller.getUserDetails()[0].equals("1")){
            if (checkNode(login_btn)){
                this.removeNode(login_btn);
            }

            if (!checkNode(logout_btn)){
                this.addNode(logout_btn);
            }

        } else{
            if (checkNode(logout_btn)){
                this.removeNode(logout_btn);
            }
            if (!checkNode(login_btn)){
                this.addNode(login_btn);
            }
        }


        return super.getScene();
    }

    private int getBasketNum() {
        return this.controller.getSnacks().stream().mapToInt(Snack::getShoppingBasket).sum();
    }

    private void setOnSnackSelection(ComboBox<String> categorySelection, ComboBox<String> snackQuantities) {
        categorySelection.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            int basketNum = 0;

            // Get number of snacks remaining from db
            String snackName = categorySelection.getValue();
            snackName = snackName.substring(0, snackName.lastIndexOf(" "));
            int numRemaining = controller.getDbQuery().itemStock(snackName);

            // Save the number of snacks in basket
            for (Snack snack: controller.getSnacks()) {
                if (snack.getName().equals(snackName)) {
                    basketNum = snack.getShoppingBasket();
                    break;
                }
            }

            // Set the options of the snack quantities
            List<String> range = IntStream.rangeClosed(0, numRemaining)
                    .boxed().map(integer -> Integer.toString(integer)).collect(Collectors.toList());
            snackQuantities.getItems().removeIf(i -> true); // Remove all
            snackQuantities.getItems().addAll(range);

            // Select quantity eaual to number in basket
            snackQuantities.getSelectionModel().select(basketNum);
        });
    }

    private void setOnQuantitySelection(ComboBox<String> categorySelection, ComboBox<String> snackQuantities, Label inBasket) {
        snackQuantities.setOnAction(e -> {
            controller.seconds = 0;
            controller.setIdle(false);
            // Get snack name
            String snackName = categorySelection.getValue();
            snackName = snackName.substring(0, snackName.lastIndexOf(" "));

            // Set the basket quantity
            for (Snack snack: controller.getSnacks()) {
                if (snack.getName().equals(snackName)) {
                    if (snackQuantities.getValue() != null) {
                        snack.setShoppingBasket(Integer.parseInt(snackQuantities.getValue()));
                    }
                    inBasket.setText(String.format("(%d)", getBasketNum()));

                    break;
                }
            }
        });
    }
}
