package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
           if(vc.getRol().equals("ADMIN")){
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/admin-view.fxml"));
               Parent root = null;
               try {
                   root = loader.load();
                   AdminController adminController=loader.getController();
                   adminController.setVc(vc);
                   Scene scene=new Scene(root);
                   Stage stage=new Stage();
                   stage.initModality(Modality.APPLICATION_MODAL);
                   stage.setScene(scene);
                   stage.show();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }else{
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/empleado-view.fxml"));
               Parent root = null;
               try {
                   root = loader.load();
                   EmpleadoController empleadoController=loader.getController();
                   empleadoController.setVc(vc);
                   Scene scene=new Scene(root);
                   Stage stage=new Stage();
                   stage.initModality(Modality.APPLICATION_MODAL);
                   stage.setScene(scene);
                   stage.show();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }else{
            texto="error en el login";
        }

        txtMsg.setText(texto);
    }
}