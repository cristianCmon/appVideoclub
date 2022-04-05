package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import com.example.appvideoclub.Modelo.Cliente;
import com.example.appvideoclub.Modelo.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.util.List;

public class EmpleadoController extends PadreController{

    @FXML
    TableView tblClientes;
    @FXML
    TableColumn cId,cNombre,cDNI,cTelefono,cDireccion,cOP;
    @FXML
    Pane paneNuevoCliente;
    @FXML
    protected void addCliente(){
        if(paneNuevoCliente.isVisible()){
            paneNuevoCliente.setVisible(false);
        }else {
            paneNuevoCliente.setVisible(true);
        }

    }
    @FXML
    protected void btnCrearCliente(){

    }

    public void cargarDatos() {
        List<Cliente> clientes=vc.getAllClientes();
        cId.setCellValueFactory(new PropertyValueFactory<>("idcliente"));
        cNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cDNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        cTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        cDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cOP.setCellFactory(new Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>>() {
            @Override
            public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                final TableCell<Cliente, Void> cell = new TableCell<Cliente, Void>() {
                    private final Button btnDelete = new Button();
                    private final Button btnEdit=new Button();

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            //Creating a graphic (image)
                            Image img = new Image(String.valueOf(getClass().getResource("/images/trash.png")));
                            ImageView view = new ImageView(img);
                            view.setFitHeight(20);
                            view.setPreserveRatio(true);
                            //Setting a graphic to the button
                            btnDelete.setGraphic(view);
                            Image imgEdit = new Image(String.valueOf(getClass().getResource("/images/edit.png")));
                            ImageView viewEdit = new ImageView(imgEdit);
                            viewEdit.setFitHeight(20);
                            viewEdit.setPreserveRatio(true);
                            //Setting a graphic to the button
                            btnEdit.setGraphic(viewEdit);
                            HBox pane = new HBox(btnDelete, btnEdit);
                            //Añadimos funcionalidad a los botones
                            btnDelete.setOnAction((ActionEvent event) -> {
                              Cliente cliente= (Cliente) tblClientes.getItems().get(getIndex());
                              vc.borrarCliente(cliente.getIdcliente());

                            });
                            btnEdit.setOnAction((ActionEvent event) -> {

                            });
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        });

        ObservableList<Cliente> clientes1= FXCollections.observableArrayList();

        for (int i = 0; i < clientes.size(); i++) {
            clientes1.add(clientes.get(i));
        }
        tblClientes.setItems(clientes1);
    }
}
