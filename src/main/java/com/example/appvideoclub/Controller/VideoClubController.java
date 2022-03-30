package com.example.appvideoclub.Controller;

import com.example.appvideoclub.Modelo.Conexion;
import com.example.appvideoclub.Modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoClubController {
    private Usuario usuarioLogeado;

    public VideoClubController() {
        usuarioLogeado=null;
    }

    public Boolean login(String nombre, String pass){
        Conexion cn=new Conexion("localhost","videoclub","root","");
        Connection conn=cn.conectar();
        String sql="SELECT T0.idusuarios as id, T0.nombre, T1.rol FROM videoclub.usuarios T0 inner join roles T1 on T0.idrol=T1.idroles where nombre=? and password=?";
        try {
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setString(1,nombre);
            stm.setString(2,pass);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                int id=rs.getInt("id");
                String nom=rs.getString("nombre");
                String role=rs.getString("rol");
                Usuario usr=new Usuario(id,nom,role);
                usuarioLogeado=usr;
                stm.close();
                conn.close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return  false;
    }
    public String getRol(){
        if(usuarioLogeado!=null)
            return usuarioLogeado.getRol();
        else return "";
    }

    public String nombreUsuario(){
        if(usuarioLogeado!=null)
            return usuarioLogeado.getNombre();
        else return "";
    }
}
