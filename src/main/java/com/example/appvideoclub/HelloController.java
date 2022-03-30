package com.example.appvideoclub;

import com.example.appvideoclub.Controller.VideoClubController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        VideoClubController vc=new VideoClubController();
        String texto="";
        if(vc.login("admin","1234")) {
            texto="login ok "+vc.nombreUsuario()+" "+vc.getRol();
        } else {
            texto="error en el login";
        }
        welcomeText.setText(texto);
    }
}