package co.edu.uniquindio.gestiondesastres.model;

public class Usuario {
    private String nombre;
    private String contrasena;

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre='" + nombre + '\'' + ", contrasena='" + contrasena + '\'' + '}';
    }

}
