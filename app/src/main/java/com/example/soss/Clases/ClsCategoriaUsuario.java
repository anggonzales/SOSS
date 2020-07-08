package com.example.soss.Clases;

public class ClsCategoriaUsuario {
    private String Id;
    private String IdUsuario;
    private String Nombre;


    public ClsCategoriaUsuario() {
    }

    public ClsCategoriaUsuario(String id, String idUsuario, String nombre) {
        Id = id;
        IdUsuario = idUsuario;
        Nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
