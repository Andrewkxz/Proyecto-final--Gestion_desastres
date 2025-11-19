package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Zona;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

/**
 * MapaController: renders a simple interactive graph on the Pane using data
 * available through GestionDesastres (no new model classes).
 *
 * Usage: click a node to set origin (green), click another node to set
 * destination (red) — the shortest path will be computed and highlighted.
 */
public class MapaController implements Initializable {

    @FXML private Pane panelMapa;
    @FXML private TreeView<String> arbolUbicaciones;
    @FXML private ToggleButton btnMostrarRutas;
    @FXML private ToggleButton btnMostrarRecursos;
    @FXML private Label lblMapaStatus;

    private Usuario usuario;
    private final GestionDesastres gestion = GestionDesastres.getInstancia();

    // mapping names to drawn nodes and edges
    private final Map<String, Circle> nodeMap = new HashMap<>();
    private final Map<String, Point2D> nodePos = new HashMap<>();
    private final Map<String, Line> edgeMap = new HashMap<>(); // key origin->dest

    private String origenSeleccionado = null;
    private String destinoSeleccionado = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (lblMapaStatus != null) {
            lblMapaStatus.setText("Mapa cargado.");
        }

        // redraw graph after pane has a size
        panelMapa.widthProperty().addListener((obs, oldV, newV) -> drawGraph());
        panelMapa.heightProperty().addListener((obs, oldV, newV) -> drawGraph());
    }

    private void drawGraph() {
        if (panelMapa == null) return;
        panelMapa.getChildren().clear();
        nodeMap.clear();
        nodePos.clear();
        edgeMap.clear();

        List<Zona> zonas = gestion.listarZonas(usuario);
        List<String> rutas = gestion.listarRutas(usuario);

        if (zonas == null || zonas.isEmpty()) {
            lblMapaStatus.setText("No hay zonas para mostrar");
            return;
        }

        // circular layout
        double w = Math.max(400, panelMapa.getWidth());
        double h = Math.max(300, panelMapa.getHeight());
        double cx = w / 2.0;
        double cy = h / 2.0;
        double radius = Math.min(w, h) / 2.5;
        int n = zonas.size();

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double x = cx + radius * Math.cos(angle);
            double y = cy + radius * Math.sin(angle);
            String nombre = zonas.get(i).getNombre();
            nodePos.put(nombre, new Point2D(x, y));
        }

        // draw edges first so nodes are on top
        for (String ruta : rutas) {
            // expected format: ORIGEN->DESTINO(peso km)
            try {
                String[] parts = ruta.split("->");
                if (parts.length < 2) continue;
                String origen = parts[0].trim();
                String rest = parts[1];
                int idx = rest.indexOf('(');
                String destino = (idx > 0) ? rest.substring(0, idx).trim() : rest.trim();

                Point2D p1 = nodePos.get(origen);
                Point2D p2 = nodePos.get(destino);
                if (p1 == null || p2 == null) continue;

                Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2);
                panelMapa.getChildren().add(line);
                edgeMap.put(origen + "->" + destino, line);

                // weight label
                String peso = "";
                int pstart = rest.indexOf('(');
                int pend = rest.indexOf(')');
                if (pstart >= 0 && pend > pstart) peso = rest.substring(pstart + 1, pend);
                Text t = new Text((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2, peso);
                t.setFill(Color.DARKBLUE);
                panelMapa.getChildren().add(t);
            } catch (Exception ex) {
                // ignore malformed lines
            }
        }

        // draw nodes
        for (Map.Entry<String, Point2D> e : nodePos.entrySet()) {
            String nombre = e.getKey();
            Point2D p = e.getValue();

            Circle c = new Circle(p.getX(), p.getY(), 18, Color.LIGHTBLUE);
            c.setStroke(Color.DARKBLUE);
            c.setStrokeWidth(2);
            Text label = new Text(p.getX() - 15, p.getY() + 5, nombre);

            c.setOnMouseClicked(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY) {
                    handleNodeClick(nombre);
                } else if (evt.getButton() == MouseButton.SECONDARY) {
                    clearSelection();
                }
            });

            nodeMap.put(nombre, c);
            panelMapa.getChildren().addAll(c, label);
        }

        lblMapaStatus.setText("Mapa dibujado (click: seleccionar origen/destino, click derecho: limpiar)");
        highlightPathIfSelected();
    }

    private void handleNodeClick(String nombre) {
        if (origenSeleccionado == null) {
            origenSeleccionado = nombre;
            nodeMap.get(nombre).setFill(Color.LIGHTGREEN);
            lblMapaStatus.setText("Origen: " + nombre + ". Seleccione destino.");
            return;
        }

        if (destinoSeleccionado == null) {
            destinoSeleccionado = nombre;
            nodeMap.get(nombre).setFill(Color.SALMON);
            lblMapaStatus.setText("Destino: " + nombre + ". Calculando ruta...");
            computeAndHighlightPath();
            return;
        }

        // both selected -> reset and start over
        clearSelection();
        origenSeleccionado = nombre;
        nodeMap.get(nombre).setFill(Color.LIGHTGREEN);
        lblMapaStatus.setText("Origen: " + nombre + ". Seleccione destino.");
    }

    private void clearSelection() {
        origenSeleccionado = null;
        destinoSeleccionado = null;
        // reset visuals
        nodeMap.values().forEach(c -> c.setFill(Color.LIGHTBLUE));
        edgeMap.values().forEach(l -> { l.setStroke(Color.GRAY); l.setStrokeWidth(2); });
        lblMapaStatus.setText("Selección limpiada.");
    }

    private void computeAndHighlightPath() {
        if (origenSeleccionado == null || destinoSeleccionado == null) return;

        List<String> camino = gestion.calcularRutaMasCorta(usuario, origenSeleccionado, destinoSeleccionado);
        if (camino == null || camino.isEmpty()) {
            lblMapaStatus.setText("No existe un camino entre " + origenSeleccionado + " y " + destinoSeleccionado);
            return;
        }

        // highlight nodes and edges in the path
        // reset first
        nodeMap.values().forEach(c -> c.setFill(Color.LIGHTBLUE));
        edgeMap.values().forEach(l -> { l.setStroke(Color.GRAY); l.setStrokeWidth(2); });

        for (String v : camino) {
            Circle c = nodeMap.get(v);
            if (c != null) c.setFill(Color.GOLD);
        }

        for (int i = 0; i < camino.size() - 1; i++) {
            String a = camino.get(i);
            String b = camino.get(i + 1);
            Line l = edgeMap.get(a + "->" + b);
            if (l == null) {
                // maybe stored as trimmed or missing; try reverse
                l = edgeMap.get(a + "->" + b);
            }
            if (l != null) {
                l.setStroke(Color.ORANGE);
                l.setStrokeWidth(4);
            }
        }

        lblMapaStatus.setText("Ruta mostrada: " + String.join(" -> ", camino));
    }

    private void highlightPathIfSelected() {
        if (origenSeleccionado != null && destinoSeleccionado != null) {
            computeAndHighlightPath();
        }
    }

    @FXML
    private void toggleRutas() {
        lblMapaStatus.setText(btnMostrarRutas.isSelected() ? "Mostrando rutas" : "Ocultando rutas");
    }

    @FXML
    private void toggleRecursos() {
        lblMapaStatus.setText(btnMostrarRecursos.isSelected() ? "Mostrando recursos" : "Ocultando recursos");
    }

    @FXML
    private void onCentrar() {
        // center not implemented; simply redraw to adapt to current size
        drawGraph();
    }

    /** Recibe el usuario desde MainController */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario != null && lblMapaStatus != null) {
            lblMapaStatus.setText("Mapa activo para: " + usuario.getNombre());
        }
        // schedule drawing
        drawGraph();
    }
}