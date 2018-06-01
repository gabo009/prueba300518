package com.example.nacho.prueba300518;

public class Equipos {
    private String serie, descripcion;
    private int valor;

    public Equipos(String serie, String descripcion, int valor) {
        this.serie = serie;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public String getSerie() {
        return serie;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return descripcion +": $"+ valor;
    }

}
