package vendingMachine.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import vendingMachine.Controller;

import java.util.ArrayList;
import java.util.List;

public class HistoryScreen extends Screen {
    TableView<List<StringProperty>> transactionTable;
    private final double ROW_HEIGHT = 40;
    private final double COLUMN_WIDTH = 75;
    private final double HEADING_HEIGHT = 30;

    public HistoryScreen(Controller controller) {
        super(controller);
        removeNode(cancel_btn);
    }

    private ObservableList<List<StringProperty>> toObservableList(List<List<String>> rows) {

        List<List<StringProperty>> observableRows = new ArrayList<>();
        List<StringProperty> observableRow;
        for (List<String> row: rows) {
            observableRow = new ArrayList<>();
            for (String str: row) {
                observableRow.add(new SimpleStringProperty(str));
            }
            observableRows.add(observableRow);
        }

        return FXCollections.observableArrayList(observableRows);
    }

    @Override
    public Scene getScene() {
        List<List<String>> transactionHistory = controller.getDbQuery().getPurchaseHistory(Integer.parseInt(controller.getUserDetails()[0]));
        List<String> headings = transactionHistory.remove(0);


        ObservableList<List<StringProperty>> observableHistory = toObservableList(transactionHistory);
        // Set the items of the table
        transactionTable = new TableView<>();
        transactionTable.setItems(observableHistory);

        // Specify how to get the value of each column in a row
        int numCols = headings.size();
        TableColumn<List<StringProperty>, String> column;
        for (int colIndex = 0; colIndex < numCols; ++colIndex) {
            final int finalColIndex = colIndex;

            column = new TableColumn<>(headings.get(colIndex));
            column.setCellValueFactory(row -> row.getValue().get(finalColIndex));

            transactionTable.getColumns().add(column);
        }


        // Set table dimensions
        transactionTable.setFixedCellSize(ROW_HEIGHT);
        // Use all of the table space for columns
        transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox tableContainer = new HBox(transactionTable);
        tableContainer.setLayoutX(50);
        tableContainer.setLayoutY(50);
//        tableContainer.setPrefSize(COLUMN_WIDTH * 2 + 30, (ROW_HEIGHT * 5) + HEADING_HEIGHT);
        this.addNode(tableContainer);

        return super.getScene();
    }

}
