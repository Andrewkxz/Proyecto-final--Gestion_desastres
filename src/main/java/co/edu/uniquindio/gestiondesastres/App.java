package co.edu.uniquindio.gestiondesastres;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class App 
{
    public static void main( String[] args )
    {
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
