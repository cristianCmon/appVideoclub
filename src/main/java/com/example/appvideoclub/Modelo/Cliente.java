package com.example.appvideoclub.Modelo;

public class Cliente {
    private int idcliente;
    private String nombre;
    private String DNI;
    private String telefono;
    private String direccion;

    public Cliente(int idcliente, String nombre, String DNI, String telefono, String direccion) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.DNI = DNI;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
