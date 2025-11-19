package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Zona;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ListView;

public class InicioController {

    @FXML private TableView<Zona> tablaZonasAfectadas;

    @FXML private TableColumn<Zona, String> colZona;
    @FXML private TableColumn<Zona, String> colRiesgo;
    @FXML private TableColumn<Zona, String> colEstado;
    @FXML private TableColumn<Zona, String> colRecursos;

    @FXML private Label lblTotalRecursos;
    @FXML private Label lblEquiposActivos;
        @FXML private ListView<String> listPermisos;

    private GestionDesastres gestion = GestionDesastres.getInstancia();
        private Usuario usuario;

    @FXML
    public void initialize() {

        colZona.setCellValueFactory(z ->
                new javafx.beans.property.SimpleStringProperty(z.getValue().getNombre())
        );
        colRiesgo.setCellValueFactory(z ->
                new javafx.beans.property.SimpleStringProperty(z.getValue().getNivelRiesgo())
        );
        colEstado.setCellValueFactory(z ->
                new javafx.beans.property.SimpleStringProperty(z.getValue().getTipo())
        );
        colRecursos.setCellValueFactory(z ->
                new javafx.beans.property.SimpleStringProperty(
                        z.getValue().getRecursos().size() + " recursos"
                )
        );

        cargarDatos();
    }

    private void cargarDatos() {
                tablaZonasAfectadas.getItems().setAll(gestion.listarZonas(usuario));
                lblTotalRecursos.setText("" + gestion.listarRecursos(usuario).size());
        lblEquiposActivos.setText("0");
    }

        /** Recibe el usuario desde el controlador principal */
        public void setUsuario(Usuario usuario) {
                this.usuario = usuario;
                // actualizar vista si ya está inicializada
                if (tablaZonasAfectadas != null) {
                        cargarDatos();
                }
                // actualizar lista de permisos/actions
                if (listPermisos != null) {
                        listPermisos.getItems().clear();
                        if (usuario == null) return;
                        String rol = usuario.getRol() == null ? "" : usuario.getRol().toUpperCase();
                        if (rol.equals("ADMINISTRADOR")) {
                                listPermisos.getItems().addAll(
                                                "Gestionar recursos (crear/editar/eliminar)",
                                                "Asignar recursos a zonas", 
                                                "Definir rutas de transporte", 
                                                "Generar informes sobre zonas y recursos"
                                );
                        } else if (rol.equals("OPERADOR")) {
                                listPermisos.getItems().addAll(
                                                "Monitorear estado de ubicaciones y actualizar situación", 
                                                "Priorizar evacuaciones según urgencia", 
                                                "Coordinar distribución de recursos entre zonas"
                                );
                        } else {
                                listPermisos.getItems().addAll("Ver información básica");
                        }
                }
        }
}
