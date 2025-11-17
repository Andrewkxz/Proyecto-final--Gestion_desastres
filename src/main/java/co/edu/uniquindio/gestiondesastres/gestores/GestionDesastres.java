package co.edu.uniquindio.gestiondesastres.gestores;

import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Zona;

public class GestionDesastres {
    private static GestionDesastres instancia;

    private GestionUsuarios gestionUsuarios;
    private GestionZonas gestionZonas;
    private GestionRecursos gestionRecursos;
    private GestionRutas gestionRutas;

    private GestionDesastres() {
        this.gestionUsuarios = new GestionUsuarios("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\usuarios.csv");
        this.gestionZonas = new GestionZonas("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\zonas.csv");
        this.gestionRecursos = new GestionRecursos("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\recursos.csv");
        this.gestionRutas = new GestionRutas("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\rutas.csv");
    }

    public static GestionDesastres getInstancia() {
        if (instancia == null) {
            instancia = new GestionDesastres();
        }
        return instancia;
    }

    public Usuario login(String nombre, String contrasena){
        return gestionUsuarios.login(nombre, contrasena);
    }

    public boolean registrarUsuario(String nombre, String contrasena, String rol){
        return gestionUsuarios.registrarUsuario(nombre, contrasena, rol);
    }

    private boolean esAdmin(Usuario usuario){
        return usuario != null && usuario.getRol().equals("ADMINISTRADOR");
    }

    private boolean esOperador(Usuario usuario){
        return usuario != null && usuario.getRol().equals("OPERADOR");
    }

    public List<Zona> listarZonas(Usuario usuario){
        return gestionZonas.getZonas();
    }

    public boolean agregarZona(Usuario usuario, String nombre, String tipo, String riesgo){
        if(!esAdmin(usuario)) return false;
        return gestionZonas.agregarZona(nombre, tipo, riesgo);
    }

    public boolean eliminarZona(Usuario usuario, String nombre){
        if(!esAdmin(usuario)) return false;
        return gestionZonas.eliminarZona(nombre);
    }

    public Zona buscarZona(String nombre){
        return gestionZonas.buscarZona(nombre);
    }

    public List<Recurso> listarRecursos(Usuario usuario){
        return gestionRecursos.getRecursos();
    }

    public boolean agregarRecurso(Usuario usuario, String tipo, int cantidad, String estado){
        if(!esAdmin(usuario)) return false;
        return gestionRecursos.agregarRecurso(tipo, cantidad, estado);
    }

    public boolean actualizarCantidadRecurso(Usuario usuario, String id, int nuevaCantidad){
        if(!esAdmin(usuario)) return false;
        return gestionRecursos.actualizarCantidad(id, nuevaCantidad);
    }

    public boolean asignarRecursoAZona(Usuario usuario, String idRecurso, String nombreZona){
        if(!esAdmin(usuario)) return false;

        Zona zona = gestionZonas.buscarZona(nombreZona);
        if(zona == null) return false;

        return gestionRecursos.asignarRecursoAZona(idRecurso, zona);
    }

    public boolean agregarRuta(Usuario usuario, String origen, String destino, double distancia){
        if(!esAdmin(usuario)) return false;
        return gestionRutas.agregarRuta(origen, destino, distancia);
    }

    public boolean eliminarRuta(Usuario usuario, String origen, String destino){
        if(!esAdmin(usuario)) return false;
        return gestionRutas.eliminarRuta(origen, destino);
    }

    public boolean actualizarDistanciaRuta(Usuario usuario, String origen, String destino, double nuevaDistancia){
        if(!esAdmin(usuario)) return false;
        return gestionRutas.actualizarDistancia(origen, destino, nuevaDistancia);
    }

    public List<String> listarRutas(Usuario usuario){
        return gestionRutas.listarRutas();
    }

    public List<String> calcularRutaMasCorta(Usuario usuario, String origen, String destino){
        return gestionRutas.getGrafo().obtenerCaminoMasCorto(origen, destino);
    }


    public boolean actualizarRiesgoZona(Usuario usuario, String nombre, String nuevoTipo, String nuevoRiesgo){
        if(!esOperador(usuario)) return false;
        return gestionZonas.actualizarZona(nombre, nuevoTipo, nuevoRiesgo);
    }

    public boolean coordinarDistribucion(Usuario usuario, String idRecurso, String zonaDestino){
        if(!esOperador(usuario)) return false;
        Zona zona = gestionZonas.buscarZona(zonaDestino);
        if(zona == null) return false;
        return gestionRecursos.asignarRecursoAZona(idRecurso, zona);
    }
}
