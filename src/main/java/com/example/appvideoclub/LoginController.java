package com.example.appvideoclub;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label txtMsg;
    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginButtonClick() {
        VideoClubController vc=new VideoClubController();
        String texto="";
        String nombre=txtNombre.getText();
        String pass=txtPassword.getText();
        if(vc.login(nombre,pass)){
           texto="login ok "+vc.nombreUsuario()+" "+vc.getRol();
        }else{
            texto="error en el login";
        }

        txtMsg.setText(texto);
    }
}