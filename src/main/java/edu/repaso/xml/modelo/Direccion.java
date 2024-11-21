package edu.repaso.xml.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Direccion {
    private String detalles;
    private double latitud;
    private double longitud;

    public Direccion() {
    }

    public Direccion(String detalles, double latitud, double longitud) {
        this.detalles = detalles;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @JsonProperty("direccion_completa")
    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Direccion [detalles=" + detalles + ", latitud=" + latitud + ", longitud=" + longitud + "]";
    }

}
