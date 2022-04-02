package vendingMachine.view;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import vendingMachine.Controller;
import vendingMachine.model.Snack;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class CheckoutScreen extends Screen {
    private Button back_btn;
    private Button payByCard_btn;
    private Button payByCash_btn;
    private List<Text> rows = new ArrayList<>();
    private final Label message;


    public CheckoutScreen(Controller controller){
        super(controller);

        message = new Label("");
        message.setTextFill(Color.rgb(210, 39, 30));
        try{
            Double num = getTotalCost();
            String sum = String.format("Total: $%.2f", num);
            message.setText(sum);
        } catch(Exception e){
            message.setText("");
        }

        Text Heading = new Text("Checkout Summary");
        Heading.setStyle("-fx-font-size: 22px");
        Heading.setFill(Color.rgb(7, 87, 91));
        Heading.setLayoutX(5);
        Heading.setLayoutY(70);

        back_btn = new Button("back");
        payByCard_btn = new Button("pay by \n card");
        payByCash_btn = new Button("pay by \n cash");

        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        payByCard_btn.setLayoutX(267);
        payByCard_btn.setLayoutY(500);
        payByCash_btn.setLayoutX(337);
        payByCash_btn.setLayoutY(500);
        message.setLayoutX(40);
        message.setLayoutY(500);


        this.addNode(back_btn);
        this.addNode(payByCard_btn);
        this.addNode(payByCash_btn);
        this.addNode(Heading);
        this.addNode(message);

        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        payByCard_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        payByCash_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        // Set screen transitions
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toChooseSnacksScreen();
        });
        payByCard_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toCardPaymentScreen();
        });
        payByCash_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toCashPaymentScreen();
        });
    }

    public double getTotalCost(){
        double total = 0;
        for (Snack snack : controller.getSnacks()) {
            total += (snack.getCost()*snack.getShoppingBasket());
        }
        return total;
    }

    @Override
    public Scene getScene() {
        ArrayList<Snack> snacks = controller.getSnacks();

        try{
            Double num = getTotalCost();
            String sum = String.format("Total: $%.2f", num);
            message.setText(sum);
        } catch(Exception e){
            message.setText("");
        }
        if(rows.size() != 0){
            for(Text text : rows){
                this.removeNode(text);
            }
        }
        rows = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < snacks.size(); i++){
            if(snacks.get(i).getShoppingBasket() != 0){
                Text text = new Text(String.format("%d %s %s %.2f x%d", snacks.get(i).getCode(),
                            snacks.get(i).getCategory(), snacks.get(i).getName(),
                            snacks.get(i).getCost(), snacks.get(i).getShoppingBasket()));
                text.setLayoutX(40);
                text.setLayoutY((20*count)+100);
                this.addNode(text);
                rows.add(text);
                count++;
            }
        }

        return super.getScene();
    }
}
