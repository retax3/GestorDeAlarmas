package com.example.gestordealarmas;

public class Alarma {
    private int id;
    private boolean estado;
    private String id_alarma,latitud,longitud,nombre;

    public Alarma (int id,String id_alarma,String nombre,boolean estado,String latitud,String longitud){
        this.estado=estado;
        this.id=id;
        this.id_alarma=id_alarma;
        this.latitud=latitud;
        this.longitud=longitud;
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getId_alarma() {
        return id_alarma;
    }

    public void setId_alarma(String id_alarma) {
        this.id_alarma = id_alarma;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
