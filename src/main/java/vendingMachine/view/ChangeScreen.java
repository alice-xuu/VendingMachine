package vendingMachine.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import vendingMachine.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeScreen extends Screen {
    private final List<String> currencies = Arrays.asList(
            "5c","10c","20c","50c","$1","$2","$5","$10","$20","$50","$100");

    private List<Integer> quantities;
    private List<Label> labels;

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public ChangeScreen(Controller controller) {
        super(controller);
        labels = new ArrayList<>();

        Label label;
        for (int i = 0; i < currencies.size(); i++) {
            label = new Label();
            label.setLayoutX(30);
            label.setLayoutY(45 + 40 * i);

            labels.add(label);
            this.addNode(label);
            this.removeNode(cancel_btn);
        }

    }

    @Override
    public Scene getScene() {
        Label label;
        for (int i = 0; i < currencies.size(); i++) {
            label = labels.get(i);
            label.setText(String.format("\t%s:\t%d", currencies.get(i), quantities.get(i)));
        }

        return super.getScene();
    }
}
