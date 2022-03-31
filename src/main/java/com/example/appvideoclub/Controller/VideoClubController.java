package com.example.appvideoclub.Controller;

import com.example.appvideoclub.Modelo.Conexion;
import com.example.appvideoclub.Modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getPerfiles(){
        List<String> perfiles = new ArrayList<>();
        Conexion cn=new Conexion("localhost","videoclub","root","");
        Connection conn=cn.conectar();
        try{
            String consulta = "SELECT rol FROM roles";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(consulta);
            while (rs.next()){
                perfiles.add(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perfiles;
    }

    public String crearUsuario(String nombre, String pass, String perfil){
        String mensaje="Error al crear el usuario.";
        Conexion cn=new Conexion("localhost","videoclub","root","");
        Connection conn=cn.conectar();
        String sql="INSERT INTO usuarios (nombre,password,idrol) VALUES (?,?,?)";
        int idrol;
        try{
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setString(1,nombre);
            stm.setString(2,pass);
            String sqlPerfil="SELECT idroles FROM roles WHERE rol=?";
            PreparedStatement statement = conn.prepareStatement(sqlPerfil);
            statement.setString(1,perfil);
            ResultSet rs=statement.executeQuery();
            if(rs.next()) {
                idrol = rs.getInt("idroles");
                stm.setInt(3,idrol);
                int resultset = stm.executeUpdate();
                if(resultset>0){
                    mensaje = "Usuario creado";
                } else {
                    mensaje = "Error al crear el usuario.";
                }
            }
            statement.close();
            stm.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
            mensaje="Error al conectar con la base de datos";
        }
        return mensaje;
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
