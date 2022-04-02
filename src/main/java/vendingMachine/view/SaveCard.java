package vendingMachine.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vendingMachine.Controller;

public class SaveCard extends Screen {

    private Button yes;
    private Button no;
    private Text message;
//    private Scene smallStage;

    public SaveCard(Controller controller){

        super(controller);
        this.removeNode(cancel_btn);

        yes = new Button("Yes");
        yes.setLayoutY(150);
        yes.setLayoutX(100);
        yes.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");

        no = new Button("No");
        no.setLayoutY(150);
        no.setLayoutX(150);
        no.setStyle("-fx-background-color: #07575b; -fx-text-fill: white;");

        message = new Text("Do you want to save this Card?");
        message.setStyle("-fx-font-size: 22px");
        message.setFill(Color.rgb(7, 87, 91));
        message.setLayoutX(50);
        message.setLayoutY(50);

        this.addNode(yes);
        this.addNode(no);
        this.addNode(message);

        yes.setOnAction( e -> {
            controller.seconds = 0;

            controller.getDbQuery().insertCreditCard(
                    Integer.parseInt(controller.getUserDetails()[0]),
                    controller.getCardName(),
                    controller.getCardNumber()
            );

            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
        });

        no.setOnAction( e -> {
            controller.seconds = 0;
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
        });
    }

//    public Scene getSmallScene() {
//        if (smallStage == null){
//            smallStage = new Scene(getGroup(), 320, 300);
//        }
//        return smallStage;
//    }
}
