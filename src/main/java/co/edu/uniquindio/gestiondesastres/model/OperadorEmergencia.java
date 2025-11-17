package co.edu.uniquindio.gestiondesastres.model;

import java.util.List;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;

public class OperadorEmergencia extends Usuario implements UsuarioSistema {
    private GestionDesastres gestionDesastres;
    public OperadorEmergencia(String nombre, String contrasena) {
        super(nombre, contrasena);
    }

    public OperadorEmergencia(String id, String nombre, String contrasena) {
        super(id, nombre, contrasena);
    }

    @Override
    public String getRol() {
        return "OPERADOR";
    }

    @Override
    public void setGestionDesastres(GestionDesastres gestionDesastres) {
        this.gestionDesastres = gestionDesastres;
    }

    public List<Zona> obtenerZonas(){
        return gestionDesastres.listarZonas(this);
    }

    public boolean actualizarRiesgo(String nombreZona, String nuevoTipo, String nuevoRiesgo){
        return gestionDesastres.actualizarRiesgoZona(this, nombreZona, nuevoTipo, nuevoRiesgo);
    }

    public boolean enviarRecurso(String idRecurso, String zonaDestino){
        return gestionDesastres.coordinarDistribucion(this, idRecurso, zonaDestino);
    }

    public String evaluarPrioridad(Zona zona){
        String riesgo = zona.getNivelRiesgo().toLowerCase();

        return switch (riesgo) {
            case "alto" -> "EVACACIÓN INMEDIATA";
            case "medio" -> "MONITOREO CONSTANTE";
            case "bajo" -> "EVACUACIÓN NO NECESARIA";
            default -> "RIESGO DESCONOCIDO";
        };
    }
    
}
