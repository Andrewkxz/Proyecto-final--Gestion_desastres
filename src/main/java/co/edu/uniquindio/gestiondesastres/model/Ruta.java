package co.edu.uniquindio.gestiondesastres.model;

public class Ruta {
    private String id;
    private String origen;
    private String destino;
    private double distancia;
    private String estado;

    public Ruta(String id, String origen, String destino, double distancia, String estado) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", distancia=" + distancia +
                '}';
    }
}
