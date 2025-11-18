package co.edu.uniquindio.gestiondesastres.model;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Hashtable;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import com.mxgraph.util.mxConstants;
import com.mxgraph.model.mxCell;

public class VisualizarGrafo {

    public static void mostrar(GrafoDirigido grafo) {
        mostrar(grafo, null);
    }

    public static void mostrar(GrafoDirigido grafo, List<String> caminoResaltado) {
        Graph<String, DefaultWeightedEdge> miGrafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Agregar vértices
        for (Vertice vertice : grafo.obtenerVertices()) {
            miGrafo.addVertex(vertice.getNombre());
        }

        // Agregar aristas con pesos
        for (Vertice vertice : grafo.obtenerVertices()) {
            for (Arista arista : vertice.getAdyacentes()) {
                DefaultWeightedEdge edge = miGrafo.addEdge(vertice.getNombre(), arista.getDestino().getNombre());
                miGrafo.setEdgeWeight(edge, arista.getPeso());
            }
        }

        // Crear adaptador gráfico
        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(miGrafo);
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.WHITE);

        // Aplicar estilos personalizados
        aplicarEstilos(graphAdapter);

        // Layout jerárquico (vertical, más claro)
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.setInterHierarchySpacing(100);
        layout.setIntraCellSpacing(80);
        layout.execute(graphAdapter.getDefaultParent());
        // Centrar el grafo en pantalla
        graphComponent.zoomTo(1.2, true);
        graphComponent.getGraphControl().setTranslate(new Point(150, 50));

        // Mostrar pesos sobre las aristas
        for (DefaultWeightedEdge edge : miGrafo.edgeSet()) {
            String origen = miGrafo.getEdgeSource(edge);
            String destino = miGrafo.getEdgeTarget(edge);
            double peso = miGrafo.getEdgeWeight(edge);

            // Mostrar formato: "origen → destino (X km)"
            graphAdapter.getEdgeToCellMap().get(edge)
                    .setValue(origen + " → " + destino + " (" + peso + " km)");
        }

        // Resaltar camino más corto (si existe)
        if (caminoResaltado != null && caminoResaltado.size() > 1) {
            resaltarCamino(graphAdapter, caminoResaltado);
        }

        // Mostrar ventana
        JFrame frame = new JFrame("Visualización del Grafo Dirigido");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(graphComponent);
        frame.setSize(950, 700);
        frame.setVisible(true);
    }

    // -------------------- ESTILOS --------------------
    private static void aplicarEstilos(mxGraph graph) {
        mxStylesheet stylesheet = graph.getStylesheet();

        // Estilo de vértices
        Map<String, Object> vertexStyle = new Hashtable<>();
        vertexStyle.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        vertexStyle.put(mxConstants.STYLE_ROUNDED, true);
        vertexStyle.put(mxConstants.STYLE_FILLCOLOR, "#A7C7E7");
        vertexStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        vertexStyle.put(mxConstants.STYLE_STROKECOLOR, "#2C3E50");
        vertexStyle.put(mxConstants.STYLE_FONTSIZE, 14);
        vertexStyle.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        vertexStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        vertexStyle.put(mxConstants.STYLE_SPACING, 8);

        // Estilo de aristas
        Map<String, Object> edgeStyle = new Hashtable<>();
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#7F8C8D");
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#2C3E50");
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ECF0F1");
        edgeStyle.put(mxConstants.STYLE_ROUNDED, true);
        edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW);

        stylesheet.putCellStyle("VERTEX", vertexStyle);
        stylesheet.putCellStyle("EDGE", edgeStyle);

        graph.setStylesheet(stylesheet);
    }

    // -------------------- CAMINO RESALTADO --------------------
    private static void resaltarCamino(JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter, List<String> camino) {
        Set<String> conexiones = new HashSet<>();

        for (int i = 0; i < camino.size() - 1; i++) {
            conexiones.add(camino.get(i) + "->" + camino.get(i + 1));
        }

        Object[] edges = graphAdapter.getChildEdges(graphAdapter.getDefaultParent());
        for (Object edge : edges) {
            mxCell cell = (mxCell) edge;
            String source = cell.getSource().getValue().toString();
            String target = cell.getTarget().getValue().toString();
            String key = source + "->" + target;

            if (conexiones.contains(key)) {
                // Estilo del camino resaltado
                cell.setStyle("strokeColor=red;strokeWidth=3;fontColor=red;endArrow=block;rounded=true");
            }
        }
    }
}
