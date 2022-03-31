package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    public void cargarPerfiles(){
        ObservableList<String> data= FXCollections.observableArrayList();
        List<String> lista = vc.getPerfiles();
        for (int i = 0; i < lista.size(); i++) {
            data.add(lista.get(i));
        }
        selectPerfil.setItems(data);
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
