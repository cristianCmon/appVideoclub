package com.example.appvideoclub.Controller;

import com.example.appvideoclub.Modelo.Cliente;
import com.example.appvideoclub.Modelo.Conexion;
import com.example.appvideoclub.Modelo.Tabla;
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
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
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

    public List<String> getPerfiles() {
        List<String> perfiles=new ArrayList<>();
        Conexion cn=new Conexion("localhost","videoclub","root","");
        Connection conn=cn.conectar();
        String sql="SELECT rol FROM videoclub.roles;";
        try {
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery(sql);
            while(rs.next()){
                perfiles.add(rs.getString("rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  perfiles;
    }

    public String crearUsuario(String nombre, String password, String perfil) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlPerfil="select idroles from roles where rol=?";
        int idPerfil;
        String mensaje="";
        try {
            PreparedStatement stm=conn.prepareStatement(sqlPerfil);
            stm.setString(1,perfil);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
             idPerfil=rs.getInt("idroles");
             String sqlInsert="insert into usuarios (nombre,password,idrol) value (?,?,?)";
             PreparedStatement statement=conn.prepareStatement(sqlInsert);
             statement.setString(1,nombre);
             statement.setString(2,password);
             statement.setInt(3,idPerfil);
             int resultInsert=statement.executeUpdate();
             if(resultInsert==1){
                 mensaje="Usuario creado correctamente";
             }else{
                 mensaje="Error al crear el usuario";
             }
             statement.close();
             stm.close();
             conn.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            mensaje="Error de conexión a la BBDD "+e.getErrorCode();
        }


        return mensaje;
    }

    public ResultSet getAllUsuarios(){
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sql="select idusuarios,nombre,rol from usuarios T0 inner join roles T1 on T0.idrol=T1.idroles;";
        try {
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery(sql);
            //stm.close();
            //conn.close();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void borrarUsuario(int idusuarios) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sql="delete from usuarios where idusuarios=?;";

        try {
            PreparedStatement stm=conn.prepareStatement(sql);
            stm.setInt(1,idusuarios);
            stm.executeUpdate();
            //stm.close();
            //conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String editarUsuario(int idusuarios, String perfil) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlPerfil="select idroles from roles where rol=?";
        int idPerfil;
        String mensaje="";
        try {
            PreparedStatement stm=conn.prepareStatement(sqlPerfil);
            stm.setString(1,perfil);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                idPerfil=rs.getInt("idroles");
                String sqlUpdate="update  usuarios set idrol=? where idusuarios=?";
                PreparedStatement statement=conn.prepareStatement(sqlUpdate);
                statement.setInt(1,idPerfil);
                statement.setInt(2,idusuarios);
                int resultInsert=statement.executeUpdate();
                if(resultInsert==1){
                    mensaje="Usuario actualizado correctamente";
                }else{
                    mensaje="Error al actualizar el usuario";
                }
                statement.close();
                stm.close();
                conn.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            mensaje="Error de conexión a la BBDD "+e.getErrorCode();
        }


        return mensaje;
    }

    public List<Cliente> getAllClientes() {
        List<Cliente> list=new ArrayList<>();
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlSelect="select * from clientes";
        try {
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery(sqlSelect);
            while(rs.next()){
                list.add(new Cliente(rs.getInt("idcliente"),rs.getString("nombre"),rs.getString("DNI"),rs.getString("telefono"),rs.getString("direccion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean borrarCliente(int idcliente) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlDelete="delete from clientes where idcliente=?";
        int result = 0;
        try {
            PreparedStatement stm=conn.prepareStatement(sqlDelete);
            stm.setInt(1,idcliente);
            result=stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean crearCliente(String nombre, String dni, String telefono, String direccion) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlInsert="insert into clientes (nombre,DNI,telefono,direccion) value (?,?,?,?)";
        int result = 0;
        try {
            PreparedStatement stm=conn.prepareStatement(sqlInsert);
            stm.setString(1,nombre);
            stm.setString(2,dni);
            stm.setString(3,telefono);
            stm.setString(4,direccion);
            result=stm.executeUpdate();

            stm.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean editarCliente(int idcliente, String nombre, String dni, String telefono, String direccion) {
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlEditar="UPDATE clientes SET nombre = ?, telefono = ?, dni = ?, direccion = ? WHERE (idcliente = ?);";
        int result=0;
        try {
            PreparedStatement stm=conn.prepareStatement(sqlEditar);
            stm.setString(1,nombre);
            stm.setString(2,telefono);
            stm.setString(3,dni);
            stm.setString(4,direccion);
            stm.setInt(5,idcliente);
            result=stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result>0){
            return true;
        }else{
            return false;
        }

    }
    public Tabla cargarpeliculas(){
        Tabla mitabl=null;
        Conexion cn=new Conexion("localhost","videoclub","root","queteden");
        Connection conn=cn.conectar();
        String sqlSelect="select * from peliculas";
        try {
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery(sqlSelect);
            mitabl=new Tabla(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mitabl;

    }
}
