package co.edu.uniquindio.gestiondesastres.model;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        GrafoDirigido grafo = new GrafoDirigido();

        grafo.agregarArista("Bogota", "Medellin", 8.5);
        grafo.agregarArista("Medellin", "Cali", 6.2);
        grafo.agregarArista("Bogota", "Cali", 14.5);
        grafo.agregarArista("Cali", "Cartagena", 10.0);
        grafo.agregarArista("Medellin", "Cartagena", 9.0);

        grafo.mostrarGrafo();

        // Calcular camino más corto
        List<String> camino = grafo.obtenerCaminoMasCorto("Bogota", "Cartagena");

        // Visualizar con el camino resaltado
        VisualizarGrafo.mostrar(grafo, camino);
    }
}
