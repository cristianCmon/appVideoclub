package com.example.appvideoclub.Modelo;

public class Pelicula {
    private String titulo;
    private String argumento;
    private int duracion;
    private int genero;

    public Pelicula(String titulo, String argumento, int duracion, int genero) {
        this.titulo = titulo;
        this.argumento = argumento;
        this.duracion = duracion;
        this.genero = genero;
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

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }
}
