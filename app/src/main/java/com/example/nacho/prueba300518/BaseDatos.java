package com.example.nacho.prueba300518;

import java.util.ArrayList;
import java.util.Date;

public class BaseDatos {

    private static ArrayList<Equipos> equipos = new ArrayList<>();
    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public static ArrayList<Equipos> getEquipos() {
        return equipos;
    }

    public static Usuario buscarUsuario (String usuario){
        for (Usuario us: usuarios){
            if (us.getUsuario().equals(usuario)){
                return us;
            }
        }
        return null;
    }

    public static boolean guardarCliente(Equipos e){
        boolean rep = false;
        for (int x = 0; x < equipos.size(); ++x){
            if (equipos.get(x).getSerie().equals(e.getSerie())){
                equipos.remove(x);
                rep = true;
            }
        }
        if (!rep) {
            return equipos.add(e);
        } else {
            return false;
        }
    }

    public static boolean equipoAsignado(String serial) {
        for (int x = 0; x < equipos.size(); ++x){
            if (equipos.get(x).getSerie().equals(serial)){
                equipos.remove(x);
                return true;
            }
        }
        return false;
    }

}
