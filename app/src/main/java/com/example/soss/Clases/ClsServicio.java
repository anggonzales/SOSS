package com.example.soss.Clases;

public class ClsServicio {
    private String id;
    private String Nombre;
    private double Latidud;
    private double Longitud;

    public ClsServicio() {
    }

    public ClsServicio(String id, String nombre) {
        this.id = id;
        Nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getLatidud() {
        return Latidud;
    }

    public void setLatidud(double latidud) {
        Latidud = latidud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }
}
