package co.edu.uniquindio.gestiondesastres.model;

import java.util.UUID;

public abstract class Usuario {
    private String id;
    private String nombre;
    private String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.id = UUID.randomUUID().toString(); //Asigna un ID único automáticamente
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Usuario(String id, String nombre, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public abstract String getRol();

    @Override
    public String toString() {
        return nombre + " (" + getRol() + ")";
    }

}
