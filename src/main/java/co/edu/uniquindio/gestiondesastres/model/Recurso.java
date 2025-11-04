package co.edu.uniquindio.gestiondesastres.model;

public class Recurso {
    private String tipoRecurso;
    private int cantidadDisponible;
    private String zona;

    public Recurso(String tipoRecurso, int cantidadDisponible, String zona) {
        this.tipoRecurso = tipoRecurso;
        this.cantidadDisponible = cantidadDisponible;
        this.zona = zona;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public String getZona() {
        return zona;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "tipoRecurso='" + tipoRecurso + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                ", zona='" + zona + '\'' +
                '}';
    }
}
