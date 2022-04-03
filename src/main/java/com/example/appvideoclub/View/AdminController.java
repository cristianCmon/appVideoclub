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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
    private ObservableList<ObservableList> usuarios;

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
                col.prefWidthProperty().bind(tblUsuarios.widthProperty().multiply(0.30));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tblUsuarios.getColumns().addAll(col);
            }
            //Añadimos una columna para las acciones
            TableColumn<UsuarioDTO, Void> colBtn = new TableColumn("OP");
            colBtn.prefWidthProperty().bind(tblUsuarios.widthProperty().multiply(0.10));

            Callback<TableColumn<UsuarioDTO, Void>, TableCell<UsuarioDTO, Void>> cellFactory = new Callback<TableColumn<UsuarioDTO, Void>, TableCell<UsuarioDTO, Void>>() {
                @Override
                public TableCell<UsuarioDTO, Void> call(final TableColumn<UsuarioDTO, Void> param) {
                    final TableCell<UsuarioDTO, Void> cell = new TableCell<UsuarioDTO, Void>() {

                        private final Button btn = new Button("Delete");


                        {
                            btn.setOnAction((ActionEvent event) -> {
                                System.out.println(getTableView().getItems().get(getIndex()).toString());

                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
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
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }

                System.out.println("Row [1] added "+row );
                usuarios.add(row);

            }

            //FINALLY ADDED TO TableView
            tblUsuarios.setItems(usuarios);

        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        }

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
        }
    }

    @FXML
    protected void btnSalir(ActionEvent evt){

    }
}
