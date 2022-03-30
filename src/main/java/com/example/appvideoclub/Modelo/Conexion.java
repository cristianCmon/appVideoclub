package com.example.appvideoclub.Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String url;
    private String usr;
    private String pass;
    private Connection cn;

    public Conexion(String url, String bbdd, String usr, String pass) {
        this.url = "jdbc:mysql://"+url+":3306/"+bbdd+"";
        this.usr = usr;
        this.pass = pass;
    }

    public Connection conectar(){
        try {
            if(cn==null){
                Class.forName("com.mysql.cj.jdbc.Driver");
                cn= DriverManager.getConnection(url,usr,pass);
            }else if(cn.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                cn= DriverManager.getConnection(url,usr,pass);
            }
        }   catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectarme a la bbdd "+url);
            return null;

        }
        return cn;
    }

}
