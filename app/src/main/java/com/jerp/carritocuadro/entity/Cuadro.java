package com.jerp.carritocuadro.entity;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Cuadro {

    private String titulo_cuadro;
    private String artista;
    private String descripcion;
    private String ano_realizacion;
    private String genero;
    private Double precio;

    public Cuadro(){}

    public Cuadro(String titulo_cuadro, String artista, String descripcion, String ano_realizacion, String genero, Double precio) {
        this.titulo_cuadro = titulo_cuadro;
        this.artista = artista;
        this.descripcion = descripcion;
        this.ano_realizacion = ano_realizacion;
        this.genero = genero;
        this.precio = precio;
    }

    public String getTitulo_cuadro() {
        return titulo_cuadro;
    }

    public void setTitulo_cuadro(String titulo_cuadro) {
        this.titulo_cuadro = titulo_cuadro;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAno_realizacion() {
        return ano_realizacion;
    }

    public void setAno_realizacion(String ano_realizacion) { this.ano_realizacion = ano_realizacion; }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }
}
