package co.edu.uniquindio.gestiondesastres.model;

import java.util.List;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;

public class Administrador extends Usuario implements UsuarioSistema {
    private GestionDesastres gestionDesastres;
    public Administrador(String nombre, String contrasena) {
        super(nombre, contrasena);
    }

    public Administrador(String id, String nombre, String contrasena) {
        super(id, nombre, contrasena);
    }

    @Override
    public String getRol() {
        return "ADMINISTRADOR";
    }

    @Override
    public void setGestionDesastres(GestionDesastres gestionDesastres) {
        this.gestionDesastres = gestionDesastres;
    }

    public boolean agregarRecurso(String tipo, int cantidad, String estado){
        return gestionDesastres.agregarRecurso(this, tipo, cantidad, estado);
    }

    public boolean actualizarCantidad(String id, int cantidad){
        return gestionDesastres.actualizarCantidadRecurso(this, id, cantidad);
    }

    public boolean asignarRecursoAZona(String idRecurso, String nombreZona){
        return gestionDesastres.asignarRecursoAZona(this, idRecurso, nombreZona);
    }

    public boolean agregarRuta(String origen, String destino, double distancia){
        return gestionDesastres.agregarRuta(this, origen, destino, distancia);
    }

    public boolean eliminarRuta(String origen, String destino){
        return gestionDesastres.eliminarRuta(this, origen, destino);
    }

    public boolean actualizarDistanciaRuta(String origen, String destino, double nuevaDistancia){
        return gestionDesastres.actualizarDistanciaRuta(this, origen, destino, nuevaDistancia);
    }

    public List<String> generarInformeZonas(){
        return gestionDesastres.listarZonas(this).stream().map(z -> z.getNombre() + " - Riesgo: " + z.getNivelRiesgo()).toList();
    }

    public List<String> generarInformeRecursos(){
        return gestionDesastres.listarRecursos(this).stream().map(r -> r.getId() + "| " + r.getTipoRecurso() + " - Cantidad: " + r.getCantidadDisponible() + " - Estado: " + r.getEstado()).toList();
    }

    public List<String> generarInformeRutas(){
        return gestionDesastres.listarRutas(this);
    }

    
}
