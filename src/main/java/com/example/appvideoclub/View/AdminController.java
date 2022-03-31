package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    public void cargarPerfiles(){
        menuUsr.setText(vc.nombreUsuario());
        ObservableList<String> data=FXCollections.observableArrayList();
        List<String> perfile=vc.getPerfiles();
        for (int i = 0; i < perfile.size(); i++) {
            data.add(perfile.get(i));
        }
        selectPerfil.setItems(data);
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
