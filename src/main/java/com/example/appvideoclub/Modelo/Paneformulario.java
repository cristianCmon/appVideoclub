package com.example.appvideoclub.Modelo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class Paneformulario {
    ArrayList<Button> botones;
    ArrayList<Label> etiquetas;
    ArrayList<TextField> textos;
    AnchorPane anchor;
    Stage stage;
    Tabla tabla;
    int row;

    public Paneformulario(Tabla tabla, Stage stag, int row) throws SQLException {
        super();

        this.row=row;
        this.tabla=tabla;
        this.stage=stag;
        Label lab=null;
        TextField tes=null;
        Button bot=null;
        String [] textbotones= new String[] {"First", "Previous", "Next", "Final", "Insert", "Edit", "Update", "Delete"};
        botones=new ArrayList<>();
        etiquetas=new ArrayList<>();
        textos=new ArrayList<>();
        double contx=22;
        double conty=22;
        ArrayList<CampoTabla> reg=tabla.getRegistro(row);
        //int j;
        for (int i = 0; i < reg.size(); i++) {
            if (conty>300){
                conty=22;
                contx+=300;
            }
            lab=new Label();
            lab.setText(reg.get(i).getColumName());
            lab.setLayoutX(contx);
            lab.setLayoutY(conty);
            tes=new TextField();
            tes.setText(String.valueOf(reg.get(i).getValor()));
            tes.setLayoutX(contx+100);
            tes.setLayoutY(conty);
            conty+=34;
            etiquetas.add(lab);
            textos.add(tes);
        }
        conty=340;
        contx=22;
        for (int i = 0; i < 8; i++) {
            bot=new Button();
            bot.setText(textbotones[i]);
            bot.setLayoutX(contx);
            bot.setLayoutY(conty);
            botones.add(bot);
            contx+=90;

        }

        anchor=new AnchorPane();

        anchor.getChildren().addAll(etiquetas);
        anchor.getChildren().addAll(textos);
        anchor.getChildren().addAll(botones);


    }

    public AnchorPane getAnchor() {
        return anchor;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
