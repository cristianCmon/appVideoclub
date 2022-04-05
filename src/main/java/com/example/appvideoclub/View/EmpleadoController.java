package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import com.example.appvideoclub.Modelo.Cliente;
import com.example.appvideoclub.Modelo.Pelicula;
import com.example.appvideoclub.Modelo.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.List;

public class EmpleadoController extends PadreController{
    @FXML
    protected MenuButton menuUsr;
    @FXML
    TableView tblClientes, tblPeliculas;
    @FXML
    TableColumn cId,cNombre,cDNI,cTelefono,cDireccion,cOP, cTitulo, cArg, cDuracion, cGenero;
    @FXML
    Pane paneNuevoCliente, paneNuevaPelicula;
    @FXML
    TextField txtNombre,txtDNI,txtTelefono,txtDireccion, txtTitulo, txtDuracion;
    @FXML
    TextArea txtArgumento;
    @FXML
    ChoiceBox choiceGenero;
    @FXML
    Button btnAccion, btnAccionPelicula;
    private Cliente clienteSelecionado;
    private Pelicula peliculaSeleccionada;
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
    protected void addPelicula(){
        if(paneNuevaPelicula.isVisible()){
            paneNuevaPelicula.setVisible(false);
        } else {
            paneNuevaPelicula.setVisible(true);
        }
    }

    @FXML
    protected void btnCrearPelicula(){
        if(peliculaSeleccionada==null){
            boolean peliculaCreada = vc.crearPelicula(txtTitulo, txtArgumento,txtDuracion,choiceGenero);
            if(peliculaCreada){
            txtTitulo.setText("");
            txtArgumento.setText("");
            txtDuracion.setText("");
            choiceGenero.getItems().get(0);
            cargarPeliculas();
            }
        } else {
            if(vc.editarPelicula(peliculaSeleccionada.getIdpelicula(),txtTitulo.getText(),txtArgumento.getText(),txtDuracion.getText(),choiceGenero.getItems().get(0))){
                peliculaSeleccionada=null;
                paneNuevaPelicula.setVisible(false);
                txtTitulo.setText("");
                txtArgumento.setText("");
                txtDuracion.setText("");
                //choiceGenero.setItems();
            }
        }
    }

    @FXML
    protected void cargarPeliculas(){
        List<Pelicula> peliculas = vc.getAllPeliculas();
        cTitulo.setCellFactory(new PropertyValueFactory<>("titulo"));
        cArg.setCellFactory(new PropertyValueFactory<>("argumento"));
        cDuracion.setCellFactory(new PropertyValueFactory<>("duracion"));
        cGenero.setCellFactory(new PropertyValueFactory<>("genero"));
        ObservableList<String> data = FXCollections.observableArrayList();
        List<String> listaGeneros = vc.getAllGeneros();
        for (int i = 0; i < listaGeneros.size(); i++) {
            data.add(listaGeneros.get(i));
        }
        choiceGenero.setItems(data);

        cOP.setCellFactory(new Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>>() {
            @Override
            public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                final TableCell<Cliente, Void> cell = new TableCell<Cliente, Void>() {
                    private final Button btnDelete = new Button();
                    private final Button btnEdit = new Button();

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
                                Pelicula pelicula = (Pelicula) tblPeliculas.getItems().get(getIndex());
                                if (vc.borrarPelicula(pelicula.getIdpelicula())) {
                                    cargarPeliculas();
                                }

                            });
                            btnEdit.setOnAction((ActionEvent event) -> {
                                Pelicula pelicula = (Pelicula) tblPeliculas.getItems().get(getIndex());
                                peliculaSeleccionada = pelicula;
                                paneNuevaPelicula.setVisible(true);
                                txtTitulo.setText(pelicula.getTitulo());
                                txtArgumento.setText(pelicula.getArgumento());
                                txtDuracion.setText(String.valueOf(pelicula.getDuracion()));
                                btnAccionPelicula.setText("Editar Pelicula");

                            });
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        });
        ObservableList<Pelicula> peliculas1= FXCollections.observableArrayList();

        for (int i = 0; i < peliculas.size(); i++) {
            peliculas1.add(peliculas.get(i));
        }
        tblPeliculas.setItems(peliculas1);

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


}
