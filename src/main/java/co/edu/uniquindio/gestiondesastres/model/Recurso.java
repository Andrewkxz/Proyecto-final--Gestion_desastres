package co.edu.uniquindio.gestiondesastres.model;

public class Recurso {
    private String id;
    private String tipoRecurso;
    private int cantidadDisponible;
    private String estado;

    public Recurso(String id, String tipoRecurso, int cantidadDisponible, String estado) {
        this.id = id;
        this.tipoRecurso = tipoRecurso;
        this.cantidadDisponible = cantidadDisponible;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "tipoRecurso='" + tipoRecurso + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                ", estado='" + estado + '\'' +
                '}';
    }
}
