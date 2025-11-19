package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {

    @FXML private TabPane tabPanePrincipal;
    @FXML private Label lblUsuarioActual;

    // Tabs (we load their content programmatically)
    @FXML private Tab tabInicio;
    @FXML private Tab tabAdmin;
    @FXML private Tab tabRutas;
    @FXML private Tab tabEstadisticas;
    @FXML private Tab tabMapa;

    private Usuario usuarioLogueado;

    public static Usuario usuarioGlobal; // mantiene tu estructura original

    @FXML
    public void initialize() {
        // Cargar los views y guardar el FXMLLoader en userData para poder recuperar controllers
        try {
            FXMLLoader fInicio = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/inicio.fxml"));
            Parent pInicio = fInicio.load();
            pInicio.setUserData(fInicio);
            tabInicio.setContent(pInicio);

            FXMLLoader fAdmin = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/admin.fxml"));
            Parent pAdmin = fAdmin.load();
            pAdmin.setUserData(fAdmin);
            tabAdmin.setContent(pAdmin);

            FXMLLoader fRutas = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/rutas.fxml"));
            Parent pRutas = fRutas.load();
            pRutas.setUserData(fRutas);
            tabRutas.setContent(pRutas);

            FXMLLoader fEst = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/estadisticas.fxml"));
            Parent pEst = fEst.load();
            pEst.setUserData(fEst);
            tabEstadisticas.setContent(pEst);

            FXMLLoader fMapa = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/mapa.fxml"));
            Parent pMapa = fMapa.load();
            pMapa.setUserData(fMapa);
            tabMapa.setContent(pMapa);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Este método lo llama LoginController */
    public void setUsuario(Usuario usuario) {
        this.usuarioLogueado = usuario;
        usuarioGlobal = usuario;

        lblUsuarioActual.setText("Usuario: " + usuario.getNombre() + " (" + usuario.getRol() + ")");

        configurarAccesos();
        enviarUsuarioAControllers();
    }

    /** Bloquea tabs según el rol */
    private void configurarAccesos() {
        if (usuarioLogueado == null) return;

        boolean esAdmin = usuarioLogueado.getRol().equalsIgnoreCase("ADMINISTRADOR");

        for (Tab t : tabPanePrincipal.getTabs()) {
            if (t.getText().equals("Administración")) t.setDisable(!esAdmin);
            if (t.getText().equals("Estadísticas")) t.setDisable(!esAdmin);
        }
    }

    /** Enviar usuario a cada controller que lo necesite */
    private void enviarUsuarioAControllers() {

        // Inicio
        Parent pInicio = (Parent) tabInicio.getContent();
        InicioController inicio = FXMLLoaderHelper.getController(pInicio);
        if (inicio != null) inicio.setUsuario(usuarioLogueado);

        // Administración
        Parent pAdmin = (Parent) tabAdmin.getContent();
        AdminController admin = FXMLLoaderHelper.getController(pAdmin);
        if (admin != null) admin.setUsuario(usuarioLogueado);

        // Rutas
        Parent pRutas = (Parent) tabRutas.getContent();
        RutasController rutas = FXMLLoaderHelper.getController(pRutas);
        if (rutas != null) rutas.setUsuario(usuarioLogueado);

        // Estadísticas
        Parent pEst = (Parent) tabEstadisticas.getContent();
        EstadisticasController est = FXMLLoaderHelper.getController(pEst);
        if (est != null) est.setUsuario(usuarioLogueado);

        // Mapa
        Parent pMapa = (Parent) tabMapa.getContent();
        MapaController mapa = FXMLLoaderHelper.getController(pMapa);
        if (mapa != null) mapa.setUsuario(usuarioLogueado);
    }
}
