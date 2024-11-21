package edu.repaso.xml.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hotel {
    private Integer id;
    private String nombre;

    private String telefono;
    
    private Direccion direccion;
    public Integer getId() {
        return id;
    }
    
    public Hotel() {
    }
    
    public Hotel(Integer id, String nombre, Direccion direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @JsonProperty("ubicacion")
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Hotel [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion + "]";
    }
    
    

}
