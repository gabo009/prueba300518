package com.example.nacho.prueba300518;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Usuario {
    private String usuario, nombre, apellido, departamento,clave;
    private ArrayList<Equipos> cargo = new ArrayList<>();

    public Usuario(String usuario, String nombre, String apellido, String departamento, String clave) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
        this.clave = clave;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getClave() {
        return clave;
    }

    public ArrayList<Equipos> getCargo() {
        return cargo;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public boolean agregarequipo (Equipos eq){
        return cargo.add(eq);
    }
    public  boolean quitarequipo (String serie){
        int x;
        for (x = 0; x < cargo.size(); ++x){
            if (cargo.get(x).getSerie().equals(serie)){
                cargo.remove(x);
                return true;
            }
        }
        return false;
    }
}
