package co.edu.uniquindio.gestiondesastres.model;

public class Ubicacion {
    private String nombre;
    private String nivelRiesgo;

    public Ubicacion(String nombre, String nivelRiesgo) {
        this.nombre = nombre;
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    @Override
    public String toString() {
        return nombre + "(" + nivelRiesgo + ")";
    }
}
