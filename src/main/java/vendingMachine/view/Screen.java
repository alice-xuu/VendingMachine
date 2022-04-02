package vendingMachine.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import vendingMachine.Controller;
import vendingMachine.model.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.TimerTask;

public abstract class Screen {
    private Group group;
    private Scene scene = null;
    private final double SCREEN_WIDTH = 400;
    private final double SCREEN_HEIGHT = 550;

    public Button cancel_btn;
    public Controller controller;

    public Screen(Controller controller) {
        group = new Group();
        this.controller = controller;

        this.cancel_btn = new Button("cancel");
        this.cancel_btn.setLayoutX(340);
        this.cancel_btn.setLayoutY(5);
        addNode(cancel_btn);
        cancel_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        cancel_btn.setOnAction(e -> {
            controller.cancelTransaction();;
        });
    }

    public void addNode(Node node) {
        group.getChildren().add(node);
    }

    public void removeNode (Node node) {
        group.getChildren().remove(node);
    }

    public boolean checkNode(Node node){
        return group.getChildren().contains(node);
    }

    public Scene getScene() {
        if (scene == null) {
            scene = new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        return scene;
    }

    public Group getGroup(){
        return group;
    }
}
