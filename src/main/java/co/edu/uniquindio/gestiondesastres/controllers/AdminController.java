package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private TextField txtNuevaZona;
    @FXML private ComboBox<String> comboZonas;

    @FXML private TableView<Recurso> tablaRecursos;
    @FXML private TableColumn<Recurso, String> colId;
    @FXML private TableColumn<Recurso, String> colTipo;
    @FXML private TableColumn<Recurso, String> colCantidad;
    @FXML private TableColumn<Recurso, String> colEstado;

    @FXML private TableView<String> tablaEquipos;

    private final GestionDesastres gestion = GestionDesastres.getInstancia();
    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getId()));
        colTipo.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTipoRecurso()));
        colCantidad.setCellValueFactory(r -> new SimpleStringProperty("" + r.getValue().getCantidadDisponible()));
        colEstado.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getEstado()));

        cargarZonas();
        cargarRecursos();
    }

    private void cargarZonas() {
        comboZonas.getItems().clear();
        gestion.listarZonas(null).forEach(z -> comboZonas.getItems().add(z.getNombre()));
    }

    private void cargarRecursos() {
        tablaRecursos.getItems().setAll(gestion.listarRecursos(null));
    }

    /** Recibe el usuario desde MainController */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // refrescar datos dependientes del usuario
        cargarZonas();
        cargarRecursos();
    }

    @FXML
    private void onAgregarZona() {
        usuario = MainController.usuarioGlobal;
        String nombre = txtNuevaZona.getText().trim();

        boolean ok = gestion.agregarZona(usuario, nombre, "GENERAL", "medio");
        mostrar(ok, "Zona agregada correctamente", "No se pudo agregar");
        cargarZonas();
    }

    @FXML
    private void nuevoRecurso() {
        usuario = MainController.usuarioGlobal;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo recurso");
        dialog.setHeaderText("Tipo de recurso:");
        dialog.setContentText("Ingrese tipo:");

        dialog.showAndWait().ifPresent(tipo -> {
            gestion.agregarRecurso(usuario, tipo, 1, "operativo");
            cargarRecursos();
        });
    }

    @FXML
    private void asignarRecursos() {
        Recurso r = tablaRecursos.getSelectionModel().getSelectedItem();
        String zona = comboZonas.getValue();
        usuario = MainController.usuarioGlobal;

        if (r == null || zona == null) {
            alerta("Seleccione recurso y zona");
            return;
        }

        boolean ok = gestion.asignarRecursoAZona(usuario, r.getId(), zona);
        mostrar(ok, "Recurso asignado", "Error al asignar");
        cargarRecursos();
    }

    // ==== MÉTODOS FALTANTES PARA QUE EL FXML NO CRASHEE =====

    @FXML
    private void nuevoEquipo() {
        new Alert(Alert.AlertType.INFORMATION,
                "Funcionalidad pendiente: crear nuevo equipo.")
                .show();
    }

    @FXML
    private void generarReporte() {
        new Alert(Alert.AlertType.INFORMATION,
                "Funcionalidad pendiente: generar reporte.")
                .show();
    }

    // ========================================================

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).show();
    }

    private void mostrar(boolean ok, String exito, String fallo) {
        new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                ok ? exito : fallo).show();
    }
}
