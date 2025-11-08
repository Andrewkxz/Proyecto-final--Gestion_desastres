package co.edu.uniquindio.gestiondesastres;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Sistema de Gestión de Desastres Naturales");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        Graph<String, DefaultWeightedEdge> grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        grafo.addVertex("Bogota");
        grafo.addVertex("Medellin");
        grafo.addVertex("Cali");

        var e1 = grafo.addEdge("Bogota", "Medellin");
        grafo.setEdgeWeight(e1, 8.5);
        var e2 = grafo.addEdge("Medellin", "Cali");
        grafo.setEdgeWeight(e2, 6.2);

        var dijkstra = new DijkstraShortestPath<>(grafo);
        var path = dijkstra.getPath("Bogota", "Cali");

        System.out.println("Ruta más corta: " + path.getVertexList());
        System.out.println("Distancia total: " + path.getWeight());
    }
}