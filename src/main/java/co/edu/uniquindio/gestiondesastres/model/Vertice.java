package co.edu.uniquindio.gestiondesastres.model;

import java.util.LinkedList;

public class Vertice {
    private String nombre;
    private LinkedList<Arista> adyacentes;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<Arista> getAdyacentes() {
        return adyacentes;
    }

    public void agregarArista(Arista arista) {
        adyacentes.add(arista);
    }

    @Override
    public String toString() {
        return "Vertice{" +
                "nombre='" + nombre;
    }
}
