package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import com.example.appvideoclub.Modelo.*;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmpleadoController extends PadreController{
    @FXML
    protected MenuButton menuUsr;
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn cId,cNombre,cDNI,cTelefono,cDireccion,cOP;

    @FXML
    Pane paneformulariopeliculas,panettableviewpeliculas,paneNuevoCliente,paneDevolver,paneAlquiler;

    @FXML
    Pane Peliculas;
    @FXML
    Label milabel;
    @FXML
    TextField txtNombre,txtDNI,txtTelefono,txtDireccion;
    @FXML
    Button btnAccion;
    private Cliente clienteSelecionado;
    @FXML
    ComboBox cbCliente,cbPelicula;


    private Tabla tabl;
    //private Paneformulario panef;
    private AnchorPaneTableView anchorview;


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
        if(clienteSelecionado==null) {
            boolean clienteCreado = vc.crearCliente(txtNombre.getText(), txtDNI.getText(), txtTelefono.getText(), txtDireccion.getText());
            if (clienteCreado) {
                txtNombre.setText("");
                txtDNI.setText("");
                txtTelefono.setText("");
                txtDireccion.setText("");
                cargarDatos();
            } else {

            }
        }else{
            if(vc.editarCliente(clienteSelecionado.getIdcliente(),txtNombre.getText(),txtDNI.getText(),txtTelefono.getText(),txtDireccion.getText())){
                clienteSelecionado=null;
                paneNuevoCliente.setVisible(false);
                txtNombre.setText("");
                txtDNI.setText("");
                txtDireccion.setText("");
                txtTelefono.setText("");
                btnAccion.setText("Crear Cliente");
                cargarDatos();
            }else{

            }

        }
    }
    @FXML
    protected void btnSalir(ActionEvent evt){
        Stage stageP = (Stage) this.menuUsr.getScene().getWindow();
        stageP.close();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/views/login-view.fxml"));

        Parent root = null;
        try {
            root = loader.load();
            Scene scene=new Scene(root,320,240);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            Stage stage=new Stage();
            stage.setTitle("App VideoClub");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void cargarPeliculas() {

        tabl = vc.cargarpeliculas();
        anchorview = null;
        Paneformulario panef = null;
        try {
            anchorview = new AnchorPaneTableView(tabl, milabel);
            panef = anchorview.getPaneform();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        panettableviewpeliculas.getChildren().addAll(anchorview.getTableView());

        paneformulariopeliculas.getChildren().addAll(panef.getAnchor());

        ArrayList<Button> botn = panef.getBotones();
        botn.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                actualizarBtn(botn);
            }
        });
        botn.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String titulo = anchorview.getPaneform().getTextos().get(1).getText();
                Integer duracion = Integer.valueOf(anchorview.getPaneform().getTextos().get(3).getText());
                String genero = anchorview.getPaneform().getTextos().get(4).getText();

                vc.crearPelicula(titulo,duracion,genero);
                cargarPeliculas();
            }
        });
        botn.get(3).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(! anchorview.getPaneform().getTextos().get(0).getText().equals("")) {
                    Integer idpelicula = Integer.valueOf(anchorview.getPaneform().getTextos().get(0).getText());
                    vc.borrarPelicula(idpelicula);
                    cargarPeliculas();
                }
            }
        });

        botn.get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Integer idpelicula = Integer.valueOf(anchorview.getPaneform().getTextos().get(0).getText());
                String titulo = anchorview.getPaneform().getTextos().get(1).getText();
                String argumento = anchorview.getPaneform().getTextos().get(2).getText();
                Integer duracion = Integer.valueOf(anchorview.getPaneform().getTextos().get(3).getText());
                String genero = anchorview.getPaneform().getTextos().get(4).getText();
                vc.editarPelicula(idpelicula,titulo,argumento,duracion,genero);
                actualizarBtn(botn);
                cargarPeliculas();
            }
        });
        borrarCampos(anchorview.getPaneform().getTextos());
    }
    @FXML
    protected void cargarDatosAlquileres(){
        //Cargamos datos de los clientes
        ObservableList<String> clienteObservableList=FXCollections.observableArrayList();
        clienteObservableList.add("Juan");
        clienteObservableList.add("Pedro");
        cbCliente.setItems(clienteObservableList);
        //Cargamos datos de las películas
        ObservableList<String> peliculasObservableList=FXCollections.observableArrayList();
        peliculasObservableList.add("El último Emperador");
        peliculasObservableList.add("Rambo I");
        cbPelicula.setItems(peliculasObservableList);
        //Cargamos datos de los alquileres

    }
    @FXML
    protected void devolverPelicula(){
        if(paneDevolver.isVisible()){
            paneDevolver.setVisible(false);
        }else{
            paneDevolver.setVisible(true);
        }
        paneAlquiler.setVisible(false);
    }
    @FXML
    protected void nuevoAlquiler(){
        if(paneAlquiler.isVisible()){
            paneAlquiler.setVisible(false);
        }else{
            paneAlquiler.setVisible(true);
        }
        paneDevolver.setVisible(false);
    }



    public void cargarDatos() {
        menuUsr.setText(vc.nombreUsuario());
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
                              if(vc.borrarCliente(cliente.getIdcliente())){
                                  cargarDatos();
                              }

                            });
                            btnEdit.setOnAction((ActionEvent event) -> {
                                Cliente cliente= (Cliente) tblClientes.getItems().get(getIndex());
                                clienteSelecionado=cliente;
                                paneNuevoCliente.setVisible(true);
                                txtNombre.setText(cliente.getNombre());
                                txtDNI.setText(cliente.getDNI());
                                txtDireccion.setText(cliente.getDireccion());
                                txtTelefono.setText(cliente.getTelefono());
                                btnAccion.setText("Editar Cliente");

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

    private void actualizarBtn(ArrayList<Button> botn){

        for (int i = 0; i < botn.size(); i++) {
            if(botn.get(i).isDisabled()==true){
                botn.get(i).setDisable(false);
            }else {
                botn.get(i).setDisable(true);
            }
        }
    }
    private void borrarCampos(ArrayList<TextField> textFields){
        for (int i = 0; i < textFields.size(); i++) {
            textFields.get(i).setText("");
        }
    }
}
