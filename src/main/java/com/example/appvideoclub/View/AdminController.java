package com.example.appvideoclub.View;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

public class AdminController extends PadreController {
    @FXML
    protected TextField txtNombre;
    @FXML
    protected ChoiceBox selectPerfil;
    @FXML
    protected PasswordField pass,repass;
    @FXML
    protected Label lblMensaje,lblPass,lblRepass;
    @FXML
    protected MenuButton menuUsr;
    @FXML
    protected Button btnAccion;
    @FXML
    protected TableView tblUsuarios;

    //TABLE VIEW AND DATA
    private ObservableList<UsuarioDTO> usuarios;
    private UsuarioDTO usuarioEditar;

    public void cargarPerfiles() {
        menuUsr.setText(vc.nombreUsuario());
        ObservableList<String> data = FXCollections.observableArrayList();
        List<String> perfile = vc.getPerfiles();
        for (int i = 0; i < perfile.size(); i++) {
            data.add(perfile.get(i));
        }
        selectPerfil.setItems(data);
        usuarios=FXCollections.observableArrayList();
        try {
            ResultSet rs = vc.getAllUsuarios();

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
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
                                btnEdit.setOnAction((ActionEvent event) -> {
                                    usuarioEditar=getTableView().getItems().get(getIndex());
                                    editar();
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

    private void editar() {
        this.txtNombre.setText(usuarioEditar.getNombre());
        for (int i = 0; i < selectPerfil.getItems().size(); i++) {
            if(selectPerfil.getItems().get(i).equals(usuarioEditar.getRol())){
                selectPerfil.getSelectionModel().select(i);
            }
        }
        pass.setVisible(false);
        repass.setVisible(false);
        lblPass.setVisible(false);
        lblRepass.setVisible(false);
        btnAccion.setText("Editar Usuario");
        btnAccion.getStyleClass().removeAll();
        btnAccion.getStyleClass().add("bnt");
        btnAccion.getStyleClass().add("btn-warning");
    }

    @FXML
    protected void btnCrearUsuario(){
        String nombre=txtNombre.getText();
        String perfil= (String) selectPerfil.getValue();
        if(usuarioEditar!=null){
            if(perfil.equals(usuarioEditar.getRol())){
                lblMensaje.setText("No hay cambios");
            }else{
                String msg=vc.editarUsuario(usuarioEditar.getIdusuarios(),perfil);
                usuarioEditar=null;
                lblRepass.setVisible(true);
                lblPass.setVisible(true);
                btnAccion.setText("Crear Usuario");
                btnAccion.getStyleClass().removeAll();
                btnAccion.getStyleClass().add("btn");
                btnAccion.getStyleClass().add("btn-success");
                pass.setVisible(true);
                repass.setVisible(true);

            }
        }else{
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
            }
        }
        txtNombre.setText("");
        pass.setText("");
        repass.setText("");
        actualizarDatosTabla();
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
