package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import com.example.appvideoclub.Modelo.Registro;
import com.example.appvideoclub.Modelo.TipodatosResultset;
import com.example.appvideoclub.Modelo.Usuario;
import com.example.appvideoclub.Modelo.UsuarioDTO;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController extends PadreController {
    @FXML
    protected TextField txtNombre;
    @FXML
    protected ChoiceBox selectPerfil;
    @FXML
    protected PasswordField pass,repass;
    @FXML
    protected Label lblMensaje;
    @FXML
    protected MenuButton menuUsr;

    @FXML
    protected TableView tblUsuarios;

    //TABLE VIEW AND DATA
    private ObservableList<UsuarioDTO> usuarios;

    public void cargarPerfiles() {
        menuUsr.setText(vc.nombreUsuario());
        ObservableList<String> data = FXCollections.observableArrayList();
        usuarios=FXCollections.observableArrayList();
        List<String> perfile = vc.getPerfiles();
        for (int i = 0; i < perfile.size(); i++) {
            data.add(perfile.get(i));
        }
        selectPerfil.setItems(data);
        try {
            ResultSet rs = vc.getAllUsuarios();

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.prefWidthProperty().bind(tblUsuarios.widthProperty().multiply(0.25));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
                tblUsuarios.getColumns().addAll(col);
            }
            //Añadimos una columna para las acciones
            TableColumn<UsuarioDTO, Void> colBtn = new TableColumn("OP");
            colBtn.prefWidthProperty().bind(tblUsuarios.widthProperty().multiply(0.25));

            Callback<TableColumn<UsuarioDTO, Void>, TableCell<UsuarioDTO, Void>> cellFactory = new Callback<TableColumn<UsuarioDTO, Void>, TableCell<UsuarioDTO, Void>>() {
                @Override
                public TableCell<UsuarioDTO, Void> call(final TableColumn<UsuarioDTO, Void> param) {
                    final TableCell<UsuarioDTO, Void> cell = new TableCell<UsuarioDTO, Void>() {
                        private final Button btn = new Button();
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
                                btn.setGraphic(view);
                                setGraphic(btn);
                                Image imgEdit = new Image(String.valueOf(getClass().getResource("/images/edit.png")));
                                ImageView viewEdit = new ImageView(imgEdit);
                                viewEdit.setFitHeight(20);
                                viewEdit.setPreserveRatio(true);
                                //Setting a graphic to the button
                                btnEdit.setGraphic(viewEdit);
                                HBox pane = new HBox(btn, btnEdit);

                                //Añadimos funcionalidad a los botones
                                btn.setOnAction((ActionEvent event) -> {
                                    vc.borrarUsuario(getTableView().getItems().get(getIndex()).getIdusuarios());
                                    actualizarDatosTabla();
                                });
                                btnEdit.setOnAction((ActionEvent event)->{
                                    editar(getTableView().getItems().get(getIndex()).getNombre());
                                    System.out.println("Editar");
                                });
                                setGraphic(pane);
                            }
                        }
                    };
                    return cell;
                }
            };

            colBtn.setCellFactory(cellFactory);

            tblUsuarios.getColumns().add(colBtn);



            /********************************
             * Data added to ObservableList *
             ********************************/

            while(rs.next()){
                //Iterate Row
                UsuarioDTO usuarioDTO=new UsuarioDTO(rs.getInt("idusuarios"),rs.getString("nombre"),rs.getString("rol"));
                usuarios.add(usuarioDTO);

            }

            //FINALLY ADDED TO TableView
            tblUsuarios.setItems(usuarios);

        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    private void editar(String nombre) {
        this.txtNombre.setText(nombre);
    }

    @FXML
    protected void btnCrearUsuario(){
        String nombre=txtNombre.getText();
        String perfil= (String) selectPerfil.getValue();
        String password=pass.getText();
        String repassword=repass.getText();
        if(nombre.equals("") || perfil==null || ! password.equals(repassword)){
            lblMensaje.setText("Datos incorrectos");
        }else{
            String msg = vc.crearUsuario(nombre,password,perfil);
            lblMensaje.setText(msg);
            txtNombre.setText("");
            pass.setText("");
            repass.setText("");
            actualizarDatosTabla();

        }
    }

    private void actualizarDatosTabla() {
        ResultSet rs = vc.getAllUsuarios();
        usuarios.clear();
        try {
            while (rs.next()) {
                UsuarioDTO usuarioDTO=new UsuarioDTO(rs.getInt("idusuarios"),rs.getString("nombre"),rs.getString("rol"));
                usuarios.add(usuarioDTO);
            }
            tblUsuarios.setItems(usuarios);
        }catch (Exception exception){
            exception.printStackTrace();
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
}
