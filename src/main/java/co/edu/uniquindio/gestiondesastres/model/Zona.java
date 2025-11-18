package co.edu.uniquindio.gestiondesastres.model;

import java.util.ArrayList;

public class Zona {
    private String nombre;
    private String tipo;
    private String nivelRiesgo;
    private ArrayList<Recurso> recursos = new ArrayList<>(); 

    public Zona(String nombre, String tipo, String nivelRiesgo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivelRiesgo = nivelRiesgo;
    }

    public void asignarRecurso(Recurso recurso){
        recursos.add(recurso);
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }
}
