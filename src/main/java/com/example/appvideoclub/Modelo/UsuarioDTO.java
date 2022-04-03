package com.example.appvideoclub.Modelo;

public class UsuarioDTO {
    private int idusuarios;
    private String nombre;
    private String rol;

    public UsuarioDTO(int idusuarios, String nombre, String rol) {
        this.idusuarios = idusuarios;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getIdusuarios() {
        return idusuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}
