package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import vendingMachine.Controller;
import vendingMachine.model.DatabaseQuery;
import vendingMachine.model.InvalidInputSpecifiedException;
import vendingMachine.model.Snack;

import java.util.Arrays;
import java.util.List;

public class ModifyItemDetailsScreen extends Screen {
    private Button back_btn;

    private ComboBox<String> snacks_drop;
    private ComboBox<String> details;

    private final TextField input_text;
    private Button enter_btn;

    private final Label message;
    private final Label item_details_label;
    private final Label item_details;

    public ModifyItemDetailsScreen(Controller controller) {
        super(controller);
        this.removeNode(cancel_btn);

        DatabaseQuery dq = controller.getDbQuery();

        back_btn = new Button("back");
        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        this.addNode(back_btn);
        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            if(controller.getUserDetails()[3].equals("seller")){
                controller.toSellerDashboard();}
            else if(controller.getUserDetails()[3].equals("owner")){
                controller.toOwnerDashboard();}
        });

        Text screen_heading = new Text("Fill or modify item details");
        screen_heading.setLayoutX(100);
        screen_heading.setLayoutY(80);
        screen_heading.setStyle("-fx-text-fill: #f76a6f; -fx-font-size: 20px;");
        this.addNode(screen_heading);

        Text item_heading = new Text("Choose an item to modify");
        item_heading.setLayoutX(100);
        item_heading.setLayoutY(120);
        item_heading.setStyle("-fx-text-fill: #f76a6f; -fx-font-size: 14px;");
        this.addNode(item_heading);

        item_details_label = new Label("Item details");
        item_details_label.setLayoutX(100);
        item_details_label.setLayoutY(180);
        item_details_label.setStyle("-fx-text-fill: #f76a6f; -fx-font-size: 14px;");
        this.addNode(item_details_label);

        item_details = new Label("");
        item_details.setLayoutX(100);
        item_details.setLayoutY(200);
        item_details.setStyle("-fx-text-fill: #f76a6f; -fx-font-size: 12px;");
        this.addNode(item_details);

        Text detail_heading = new Text("Choose a detail to modify");
        detail_heading.setLayoutX(100);
        detail_heading.setLayoutY(320);
        detail_heading.setStyle("-fx-text-fill: #f76a6f; -fx-font-size: 14px;");
        this.addNode(detail_heading);

        details = new ComboBox<>();
        List<String> detailNames = Arrays.asList("name", "code", "category", "quantity", "price");
        for (String eachDetail : detailNames) {
            details.getItems().add(eachDetail);
        }
        details.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        details.setLayoutX(100);
        details.setLayoutY(340);
        this.addNode(details);

        input_text = new TextField();
        input_text.setLayoutX(100);
        input_text.setLayoutY(380);
        input_text.setPromptText("Enter new value");
        this.addNode(input_text);

        enter_btn = new Button("Enter");
        enter_btn.setLayoutX(100);
        enter_btn.setLayoutY(420);
        this.addNode(enter_btn);

        message = new Label("");
        message.setTextFill(Color.rgb(190, 0, 0));
        message.setLayoutX(100);
        message.setLayoutY(486);
        this.addNode(message);

    }

    public Scene getScene(){
        message.setText("");
        input_text.setText("");
        DatabaseQuery dq = controller.getDbQuery();
        snacks_drop = new ComboBox<>();
        for (Snack eachSnack : controller.getSnacks()) {
            snacks_drop.getItems().add(eachSnack.getName());
        }
        snacks_drop.setStyle("-fx-background-color: #C4DFE6; -fx-text-fill: white;");
        snacks_drop.setLayoutX(100);
        snacks_drop.setLayoutY(140);
        this.addNode(snacks_drop);

        EventHandler<ActionEvent> snack_drop_Handler = e -> {
            controller.seconds = 0;
            String snackName = snacks_drop.getValue();
            for (Snack snack : controller.getSnacks()) {
                if (snack.getName().equals(snackName)) {
                    item_details.setText(String.format("Name: %s\nCode: %d\nCategory: %s\nQuantity: %d\nPrice: %.2f", snack.getName(), snack.getCode(), snack.getCategory(), dq.itemStock(snack.getName()), snack.getCost()));
                }
            }
        };

        snacks_drop.setOnAction(snack_drop_Handler);

        EventHandler<ActionEvent> enter_Handler = e -> {
            controller.seconds = 0;
            String snackName = snacks_drop.getValue();
            String detail = details.getValue();
            String input = input_text.getText();

            try{
                dq.modifyItemDetails(snackName, detail, input);
                message.setText("Update successful");
                message.setTextFill(Color.rgb(0, 150, 50));

                // update controller snacks list
                controller.setSnacks(dq.getAllSnacks());
                // update dropdown
                snacks_drop.getItems().clear();
                for (Snack eachSnack : controller.getSnacks()) {
                    snacks_drop.getItems().add(eachSnack.getName());
                }
                // update item details gui
                item_details.setText("");
                input_text.setText("");

            } catch (InvalidInputSpecifiedException invalidInputSpecifiedException) {
                message.setText(invalidInputSpecifiedException.getMessage());
                message.setTextFill(Color.rgb(190, 0, 0));
            }

        };

        enter_btn.setOnAction(enter_Handler);

        return super.getScene();

    }


}
