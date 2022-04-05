package com.example.appvideoclub.Modelo;

public class Pelicula {
    private int idpelicula;
    private String titulo;
    private String argumento;
    private int duracion;
    private String genero;

    public Pelicula(int idpelicula, String titulo, String argumento, int duracion, String genero) {
        this.idpelicula = idpelicula;
        this.titulo = titulo;
        this.argumento = argumento;
        this.duracion = duracion;
        this.genero = genero;
    }

    public int getIdpelicula() {
        return idpelicula;
    }

    public void setIdpelicula(int idpelicula) {
        this.idpelicula = idpelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArgumento() {
        return argumento;
    }

    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
