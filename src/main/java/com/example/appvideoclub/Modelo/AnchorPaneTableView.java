package com.example.appvideoclub.Modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnchorPaneTableView {
    private final Tabla tabla;
    private final TableView<ObservableList> tableView = new TableView<ObservableList>();
    private final List<String> columnNames = new ArrayList<>();
    private AnchorPane anchorp;
    private final Label returnlabel;


    public AnchorPaneTableView(Tabla tabla,  Label retlab) throws SQLException {
        super();
        this.tabla = tabla;
        returnlabel = retlab;
        buildData();

    }



    public TableView<ObservableList> getTableView() {
        return tableView;
    }

    private void buildData() throws SQLException {
        if (tabla.getSize() > 0) {
            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            for (int i = 0; i < tabla.getRegistro(0).size(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(tabla.getRegistro(0).get(i).getColumName());
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if (param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return null;
                    }
                });

                tableView.getColumns().addAll(col);
                this.columnNames.add(col.getText());
            }

            for (int j = 0; j < tabla.getSize(); j++) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 0; i < tabla.getRegistro(0).size(); i++) {

                    row.add(String.valueOf(tabla.getRegistro(j).get(i).getValor()));
                }
                data.add(row);
            }

            tableView.setItems(data);
            anchorp = new AnchorPane();
            anchorp.getChildren().add(tableView);
            tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    //Check whether item is selected and set value of selected item to Label
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                        ObservableList selectedCells = selectionModel.getSelectedCells();
                        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                        Object val = tablePosition.getTableColumn().getCellData(newValue);
                        returnlabel.setText(tablePosition.getRow() + getColumnNames().get(0) + val + tablePosition.getColumn() + getColumnNames().get(tablePosition.getColumn()));//( tablePosition.getRow() + getColumnNames().get(0));
                    }
                }
            });
        }
    }
    public List<String> getColumnNames() {
        return columnNames;
    }

    public AnchorPane getAnchorp() {
        return anchorp;
    }
}
