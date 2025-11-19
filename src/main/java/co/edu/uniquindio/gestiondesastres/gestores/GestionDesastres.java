package co.edu.uniquindio.gestiondesastres.gestores;

import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Zona;

/**
 * Clase fachada principal que coordina todas las operaciones del sistema
 * Implementa patrón Singleton
 */
public class GestionDesastres {
    private static GestionDesastres instancia;

    private GestionUsuarios gestionUsuarios;
    private GestionZonas gestionZonas;
    private GestionRecursos gestionRecursos;
    private GestionRutas gestionRutas;

    /**
     * Constructor privado - Singleton
     */
    private GestionDesastres() {
        this.gestionUsuarios = new GestionUsuarios("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\usuarios.csv");
        this.gestionZonas = new GestionZonas("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\zonas.csv");
        this.gestionRecursos = new GestionRecursos("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\recursos.csv");
        this.gestionRutas = new GestionRutas("src\\main\\java\\co\\edu\\uniquindio\\gestiondesastres\\datos\\rutas.csv");
    }

    /**
     * Obtiene la instancia única de GestionDesastres
     * @return La instancia singleton
     */
    public static GestionDesastres getInstancia() {
        if (instancia == null) {
            instancia = new GestionDesastres();
        }
        return instancia;
    }

    // =============================================
    // GETTERS PARA ACCEDER A LOS GESTORES
    // =============================================

    /**
     * Obtiene el gestor de recursos
     * @return GestionRecursos
     */
    public GestionRecursos getGestionRecursos() {
        return gestionRecursos;
    }

    /**
     * Obtiene el gestor de rutas
     * @return GestionRutas
     */
    public GestionRutas getGestionRutas() {
        return gestionRutas;
    }

    /**
     * Obtiene el gestor de usuarios
     * @return GestionUsuarios
     */
    public GestionUsuarios getGestionUsuarios() {
        return gestionUsuarios;
    }

    /**
     * Obtiene el gestor de zonas
     * @return GestionZonas
     */
    public GestionZonas getGestionZonas() {
        return gestionZonas;
    }

    // =============================================
    // MÉTODOS DE AUTENTICACIÓN
    // =============================================

    /**
     * Autentica un usuario
     * @param nombre Nombre del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario login(String nombre, String contrasena) {
        return gestionUsuarios.login(nombre, contrasena);
    }

    /**
     * Registra un nuevo administrador
     * @param nombre Nombre del administrador
     * @param contrasena Contraseña del administrador
     * @return true si se registró exitosamente, false en caso contrario
     */
    public boolean registrarAdministrador(String nombre, String contrasena) {
        return gestionUsuarios.registrarAdministrador(nombre, contrasena);
    }

    /**
     * Registra un nuevo operador de emergencia
     * @param nombre Nombre del operador
     * @param contrasena Contraseña del operador
     * @return true si se registró exitosamente, false en caso contrario
     */
    public boolean registrarOperador(String nombre, String contrasena) {
        return gestionUsuarios.registrarOperador(nombre, contrasena);
    }

    // =============================================
    // MÉTODOS DE VALIDACIÓN DE ROLES
    // =============================================

    /**
     * Verifica si un usuario es administrador
     * @param usuario Usuario a verificar
     * @return true si es administrador, false en caso contrario
     */
    private boolean esAdmin(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("ADMINISTRADOR");
    }

    /**
     * Verifica si un usuario es operador de emergencia
     * @param usuario Usuario a verificar
     * @return true si es operador, false en caso contrario
     */
    private boolean esOperador(Usuario usuario) {
        return usuario != null && usuario.getRol().equals("OPERADOR");
    }

    // =============================================
    // MÉTODOS DE GESTIÓN DE ZONAS
    // =============================================

    /**
     * Lista todas las zonas
     * @param usuario Usuario que solicita la operación
     * @return Lista de zonas
     */
    public List<Zona> listarZonas(Usuario usuario) {
        return gestionZonas.getZonas();
    }

    /**
     * Agrega una nueva zona
     * @param usuario Usuario administrador
     * @param nombre Nombre de la zona
     * @param tipo Tipo de zona
     * @param riesgo Nivel de riesgo
     * @return true si se agregó exitosamente, false en caso contrario
     */
    public boolean agregarZona(Usuario usuario, String nombre, String tipo, String riesgo) {
        if (!esAdmin(usuario)) return false;
        return gestionZonas.agregarZona(nombre, tipo, riesgo);
    }

    /**
     * Elimina una zona
     * @param usuario Usuario administrador
     * @param nombre Nombre de la zona a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarZona(Usuario usuario, String nombre) {
        if (!esAdmin(usuario)) return false;
        return gestionZonas.eliminarZona(nombre);
    }

    /**
     * Busca una zona por nombre
     * @param nombre Nombre de la zona
     * @return La zona encontrada, o null si no existe
     */
    public Zona buscarZona(String nombre) {
        return gestionZonas.buscarZona(nombre);
    }

    /**
     * Actualiza el riesgo de una zona
     * @param usuario Usuario operador
     * @param nombre Nombre de la zona
     * @param nuevoTipo Nuevo tipo de zona
     * @param nuevoRiesgo Nuevo nivel de riesgo
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarRiesgoZona(Usuario usuario, String nombre, String nuevoTipo, String nuevoRiesgo) {
        if (!esOperador(usuario)) return false;
        return gestionZonas.actualizarZona(nombre, nuevoTipo, nuevoRiesgo);
    }

    // =============================================
    // MÉTODOS DE GESTIÓN DE RECURSOS
    // =============================================

    /**
     * Lista todos los recursos
     * @param usuario Usuario que solicita la operación
     * @return Lista de recursos
     */
    public List<Recurso> listarRecursos(Usuario usuario) {
        return gestionRecursos.getRecursos();
    }

    /**
     * Agrega un nuevo recurso
     * @param usuario Usuario administrador
     * @param tipo Tipo de recurso
     * @param cantidad Cantidad disponible
     * @param estado Estado del recurso
     * @return true si se agregó exitosamente, false en caso contrario
     */
    public boolean agregarRecurso(Usuario usuario, String tipo, int cantidad, String estado) {
        if (!esAdmin(usuario)) return false;
        return gestionRecursos.agregarRecurso(tipo, cantidad, estado);
    }

    /**
     * Actualiza la cantidad de un recurso
     * @param usuario Usuario administrador
     * @param id ID del recurso
     * @param nuevaCantidad Nueva cantidad
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarCantidadRecurso(Usuario usuario, String id, int nuevaCantidad) {
        if (!esAdmin(usuario)) return false;
        return gestionRecursos.actualizarCantidad(id, nuevaCantidad);
    }

    /**
     * Asigna un recurso a una zona
     * @param usuario Usuario administrador
     * @param idRecurso ID del recurso
     * @param nombreZona Nombre de la zona
     * @return true si se asignó exitosamente, false en caso contrario
     */
    public boolean asignarRecursoAZona(Usuario usuario, String idRecurso, String nombreZona) {
        if (!esAdmin(usuario)) return false;

        Zona zona = gestionZonas.buscarZona(nombreZona);
        if (zona == null) return false;

        return gestionRecursos.asignarRecursoAZona(idRecurso, zona);
    }

    /**
     * Coordina la distribución de recursos (operador)
     * @param usuario Usuario operador
     * @param idRecurso ID del recurso
     * @param zonaDestino Nombre de la zona destino
     * @return true si se coordinó exitosamente, false en caso contrario
     */
    public boolean coordinarDistribucion(Usuario usuario, String idRecurso, String zonaDestino) {
        if (!esOperador(usuario)) return false;
        Zona zona = gestionZonas.buscarZona(zonaDestino);
        if (zona == null) return false;
        return gestionRecursos.asignarRecursoAZona(idRecurso, zona);
    }

    // =============================================
    // MÉTODOS DE GESTIÓN DE RUTAS
    // =============================================

    /**
     * Agrega una nueva ruta
     * @param usuario Usuario administrador
     * @param origen Ubicación de origen
     * @param destino Ubicación de destino
     * @param distancia Distancia en km
     * @return true si se agregó exitosamente, false en caso contrario
     */
    public boolean agregarRuta(Usuario usuario, String origen, String destino, double distancia) {
        if (!esAdmin(usuario)) return false;
        return gestionRutas.agregarRuta(origen, destino, distancia);
    }

    /**
     * Elimina una ruta
     * @param usuario Usuario administrador
     * @param origen Ubicación de origen
     * @param destino Ubicación de destino
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public boolean eliminarRuta(Usuario usuario, String origen, String destino) {
        if (!esAdmin(usuario)) return false;
        return gestionRutas.eliminarRuta(origen, destino);
    }

    /**
     * Actualiza la distancia de una ruta
     * @param usuario Usuario administrador
     * @param origen Ubicación de origen
     * @param destino Ubicación de destino
     * @param nuevaDistancia Nueva distancia en km
     * @return true si se actualizó exitosamente, false en caso contrario
     */
    public boolean actualizarDistanciaRuta(Usuario usuario, String origen, String destino, double nuevaDistancia) {
        if (!esAdmin(usuario)) return false;
        return gestionRutas.actualizarDistancia(origen, destino, nuevaDistancia);
    }

    /**
     * Lista todas las rutas
     * @param usuario Usuario que solicita la operación
     * @return Lista de rutas formateadas como String
     */
    public List<String> listarRutas(Usuario usuario) {
        return gestionRutas.listarRutas();
    }

    /**
     * Calcula la ruta más corta entre dos ubicaciones
     * @param usuario Usuario que solicita la operación
     * @param origen Ubicación de origen
     * @param destino Ubicación de destino
     * @return Lista con el camino más corto, o lista vacía si no existe ruta
     */
    public List<String> calcularRutaMasCorta(Usuario usuario, String origen, String destino) {
        return gestionRutas.getGrafo().obtenerCaminoMasCorto(origen, destino);
    }
}