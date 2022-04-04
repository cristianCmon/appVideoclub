package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label txtMsg;
    @FXML
    private TextField txtNombre;
    @FXML
    private PasswordField txtPassword;

    @FXML
    protected void onLoginButtonClick(ActionEvent evt) {
        VideoClubController vc=new VideoClubController();
        String texto="";
        String nombre=txtNombre.getText();
        String pass=txtPassword.getText();
        if(vc.login(nombre,pass)){
            cargarVista(vc.getRol(),vc);
            Node source = (Node) evt.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            texto="error en el login";
        }
        txtMsg.setText(texto);
    }
    private void cargarVista(String perfil,VideoClubController vc){
        FXMLLoader loader;
        if(perfil.equals("ADMIN")){
            loader = new FXMLLoader(getClass().getResource("/views/admin-view.fxml"));
        }else{
            loader = new FXMLLoader(getClass().getResource("/views/empleado-view.fxml"));
        }
        Parent root = null;
        try {
            root = loader.load();
            PadreController controller=loader.getController();
            controller.setVc(vc);
            if(perfil.equals("ADMIN"))
                ((AdminController) controller).cargarPerfiles();
            Scene scene=new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            Stage stage=new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}