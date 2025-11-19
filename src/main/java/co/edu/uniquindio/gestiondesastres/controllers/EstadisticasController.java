package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Zona;
import co.edu.uniquindio.gestiondesastres.model.Recurso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class EstadisticasController implements Initializable {

    @FXML private Label lblUsuarioEstadisticas;

    @FXML private PieChart graficaRecursos;
    @FXML private BarChart<String, Number> graficaEvacuaciones;
    @FXML private TableView<ResumenFila> tablaResumen;
    @FXML private TableColumn<ResumenFila, String> colMetrica;
    @FXML private TableColumn<ResumenFila, String> colValor;
    @FXML private TableColumn<ResumenFila, String> colCambio;

    private Usuario usuario;
    private final GestionDesastres gestion = GestionDesastres.getInstancia();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // preparar la tabla
        colMetrica.setCellValueFactory(new PropertyValueFactory<>("metrica"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colCambio.setCellValueFactory(new PropertyValueFactory<>("cambio"));

        tablaResumen.setItems(FXCollections.observableArrayList());
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario != null && lblUsuarioEstadisticas != null) {
            lblUsuarioEstadisticas.setText("Estadísticas de: " + usuario.getNombre());
        }
    }

    @FXML
    private void actualizarEstadisticas() {
        List<Zona> zonas = gestion.listarZonas(usuario);
        List<Recurso> recursos = gestion.listarRecursos(usuario);

        int totalZonas = zonas == null ? 0 : zonas.size();
        int totalRecursos = recursos == null ? 0 : recursos.size();
        int recursosAsignados = 0;
        if (zonas != null) {
            recursosAsignados = zonas.stream().mapToInt(z -> z.getRecursos() == null ? 0 : z.getRecursos().size()).sum();
        }

        // Pie chart: recursos por tipo
        Map<String, Integer> contadorTipos = new HashMap<>();
        if (recursos != null) {
            for (Recurso r : recursos) {
                contadorTipos.merge(r.getTipoRecurso(), 1, Integer::sum);
            }
        }
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        contadorTipos.forEach((k, v) -> pieData.add(new PieChart.Data(k + " (" + v + ")", v)));
        graficaRecursos.setData(pieData);

        // Bar chart: evacuaciones (estimadas) por zona — usar nivelRiesgo como proxy
        graficaEvacuaciones.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        if (zonas != null) {
            for (Zona z : zonas) {
                int estimado = switch (z.getNivelRiesgo().toLowerCase()) {
                    case "alto" -> 300;
                    case "medio" -> 150;
                    default -> 50;
                };
                serie.getData().add(new XYChart.Data<>(z.getNombre(), estimado));
            }
        }
        graficaEvacuaciones.getData().add(serie);

        // Tabla resumen
        ObservableList<ResumenFila> filas = FXCollections.observableArrayList();
        filas.add(new ResumenFila("Total zonas", String.valueOf(totalZonas), "-"));
        filas.add(new ResumenFila("Total recursos", String.valueOf(totalRecursos), "-"));
        filas.add(new ResumenFila("Recursos asignados", String.valueOf(recursosAsignados), "-"));
        tablaResumen.setItems(filas);
    }

    public static class ResumenFila {
        private final String metrica;
        private final String valor;
        private final String cambio;

        public ResumenFila(String metrica, String valor, String cambio) {
            this.metrica = metrica;
            this.valor = valor;
            this.cambio = cambio;
        }

        public String getMetrica() { return metrica; }
        public String getValor() { return valor; }
        public String getCambio() { return cambio; }
    }
}
