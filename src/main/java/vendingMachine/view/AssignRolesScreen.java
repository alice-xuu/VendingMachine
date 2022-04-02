package vendingMachine.view;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import vendingMachine.Controller;
import vendingMachine.model.User;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;



import java.util.List;

public class AssignRolesScreen extends Screen {
    private final ObservableList<User> users;
    private final TableView<User> table;
    private Button back_btn;


    private void resetUsers() {
        users.removeAll(users);
        users.addAll(controller.getDbQuery().getAllUsers());
        table.refresh();
    }

    public AssignRolesScreen(Controller controller) {
        super(controller);
        users = FXCollections.observableList(controller.getDbQuery().getAllUsers());


        back_btn = new Button("back");
        back_btn.setLayoutX(5);
        back_btn.setLayoutY(5);
        this.addNode(back_btn);
        back_btn.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        back_btn.setOnAction(e -> {
            controller.seconds = 0;
            controller.toOwnerDashboard();});


        table = new TableView<>();
        final Text label = new Text("Assign user roles");
        label.setFont(new Font("Arial", 20));
        label.setFill(Color.rgb(7, 87, 91));

        final Text instruction = new Text("Double click the role of any user to edit role");
        instruction.setFont(new Font("Arial", 12));
        instruction.setFill(Color.rgb(7, 87, 91));

        table.setEditable(true);


        TableColumn<User, Integer> userIdCol = new TableColumn<>("User Id");
        userIdCol.setMinWidth(100);
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(100);
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setMinWidth(100);
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(ComboBoxTableCell.forTableColumn(User.CASHIER, User.CUSTOMER, User.OWNER, User.SELLER));
        roleCol.setOnEditCommit(
                event -> {
                    controller.seconds = 0;
                    event.getTableView()
                            .getItems()
                            .get(event.getTablePosition().getRow())
                            .setRole(event.getNewValue());
                }
        );

        table.setItems(users);
        table.getColumns().add(userIdCol);
        table.getColumns().add(usernameCol);
        table.getColumns().add(roleCol);

        Button save = new Button("Save");
        save.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");
        save.setOnAction(event -> {
            controller.seconds = 0;
            for (User user: users) {
                controller.getDbQuery().assignUserRole(user.getUserId(), user.getRole());
            }
        });

        Button reset = new Button("Reset");
        reset.setStyle("-fx-background-color: #07575B; -fx-text-fill: white;");

        reset.setOnAction(event -> {
            controller.seconds = 0;
            resetUsers();
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(save, reset);
        hbox.setSpacing(10);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, instruction, table, hbox);
        vbox.setLayoutX(40);
        vbox.setLayoutY(40);
        this.addNode(vbox);
    }


    @Override
    public Scene getScene() {
//        users = FXCollections.observableList(controller.getDbQuery().getAllUsers());
        resetUsers();

        return super.getScene();
    }
}
