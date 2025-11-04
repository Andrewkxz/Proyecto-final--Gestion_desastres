package co.edu.uniquindio.gestiondesastres.model;

public class Arista {
    private Vertice destino;
    private double peso;

    public Arista(Vertice destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "destino -> " + destino.getNombre() + ", peso: " + "(" + peso + "km)";
    }
}
