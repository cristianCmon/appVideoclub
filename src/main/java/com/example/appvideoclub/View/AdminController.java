package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import com.example.appvideoclub.Modelo.UsuarioDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    protected TableView tblUsuarios;
    @FXML
    protected TableColumn cid,cnombre,crol,cop;

    public void cargarPerfiles(){
        ObservableList<String> data= FXCollections.observableArrayList();
        List<String> lista = vc.getPerfiles();
        for (int i = 0; i < lista.size(); i++) {
            data.add(lista.get(i));
        }
        selectPerfil.setItems(data);
        ResultSet rs=vc.getAllUsuarios();
        cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        crol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        ObservableList<UsuarioDTO> usuarios = FXCollections.observableArrayList();

        try {
            while(rs.next()) {
                usuarios.add(new UsuarioDTO(rs.getInt("id"), rs.getString("nombre"), rs.getString("rol")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblUsuarios.setItems(usuarios);
    }

    @FXML
    protected void btnCrearUsuario(){
        String nombre = txtNombre.getText();
        String perfil = (String) selectPerfil.getValue();
        String password = pass.getText();
        String repassword = repass.getText();
        if(nombre.equals("") || perfil==null || !password.equals(repassword)){
            lblMensaje.setText("Datos incorrectos");
        } else {
            lblMensaje.setText("");
            String msg = vc.crearUsuario(nombre,password,perfil);
            lblMensaje.setText(msg);
        }
    }
}
