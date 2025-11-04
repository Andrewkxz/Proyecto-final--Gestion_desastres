package co.edu.uniquindio.gestiondesastres.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class GrafoDirigido {
    private Map<String, Vertice> vertices;

    public GrafoDirigido() {
        this.vertices = new HashMap<>();
    }

    public Map<String, Double> dijkstra(String origen){
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<VerticeDistancia> colaPrioridad = new PriorityQueue<>();

        for(String nombre: vertices.keySet()){
            distancias.put(nombre, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);
        colaPrioridad.add(new VerticeDistancia(origen, 0.0));

        while(!colaPrioridad.isEmpty()){
            VerticeDistancia actual = colaPrioridad.poll();
            Vertice verticeActual = vertices.get(actual.nombre);

            for(Arista arista : verticeActual.getAdyacentes()){
                String vecino = arista.getDestino().getNombre();
                double nuevaDistancia = distancias.get(actual.nombre) + arista.getPeso();

                if(nuevaDistancia < distancias.get(vecino)){
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, actual.nombre);
                    colaPrioridad.add(new VerticeDistancia(vecino, nuevaDistancia));
                }
            }
        }
        System.out.println("Algoritmo de Dijkstra desde el vértice: " + origen);
        for(String vertice : distancias.keySet()){
            System.out.println("Distancia mínima a " + vertice + " = " + distancias.get(vertice) + " km");
        }
        return distancias;
    }

    public LinkedList<String> obtenerCaminoMasCorto(String origen, String destino){
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        PriorityQueue<VerticeDistancia> colaPrioridad = new PriorityQueue<>();

        for(String nombre: vertices.keySet()){
            distancias.put(nombre, Double.POSITIVE_INFINITY);
        }
        distancias.put(origen, 0.0);
        colaPrioridad.add(new VerticeDistancia(origen, 0.0));

        while(!colaPrioridad.isEmpty()){
            VerticeDistancia actual = colaPrioridad.poll();
            Vertice verticeActual = vertices.get(actual.nombre);

            for(Arista arista : verticeActual.getAdyacentes()){
                String vecino = arista.getDestino().getNombre();
                double nuevaDistancia = distancias.get(actual.nombre) + arista.getPeso();

                if(nuevaDistancia < distancias.get(vecino)){
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, actual.nombre);
                    colaPrioridad.add(new VerticeDistancia(vecino, nuevaDistancia));
                }
            }
        }
        LinkedList<String> camino = new LinkedList<>();
        String paso = destino;

        if(!predecesores.containsKey(destino)){
            System.out.println("No existe un camino desde " + origen + " hasta " + destino);
            return camino;
        }

        while(paso != null){
            camino.addFirst(paso);
            paso = predecesores.get(paso);
        }

        System.out.println("Camino más corto desde " + origen + " hasta " + destino + ": " + camino);
        System.out.println("Distancia total: " + distancias.get(destino) + " km");
        return camino;
    }

    private static class VerticeDistancia implements Comparable<VerticeDistancia>{
        String nombre;
        double distancia;

        VerticeDistancia(String nombre, double distancia){
            this.nombre = nombre;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(VerticeDistancia otro){
            return Double.compare(this.distancia, otro.distancia);
        }
    }

    public void agregarVertice(String nombre){
        if(!vertices.containsKey(nombre)){
            vertices.put(nombre, new Vertice(nombre));
        }
    }

    public void agregarArista(String origen, String destino, double peso){
        agregarVertice(origen);
        agregarVertice(destino);

        Vertice verticeOrigen = vertices.get(origen);
        Vertice verticeDestino = vertices.get(destino);

        verticeOrigen.agregarArista(new Arista(verticeDestino, peso));
    }

    public Vertice obtenerVertice(String nombre){
        return vertices.get(nombre);
    }

    public Collection<Vertice> obtenerVertices(){
        return vertices.values();
    }

    public void mostrarGrafo(){
        System.out.println("Grafo Dirigido:");
        for(Vertice vertice : vertices.values()){
            System.out.println(vertice.getNombre() + " -> ");
            if(vertice.getAdyacentes().isEmpty()){
                System.out.println("No hay conexiones.");
            } else {
                for(Arista arista : vertice.getAdyacentes()){
                    System.out.println(arista.getDestino().getNombre() + "(" + arista.getPeso() + "km) ");
                }
                System.out.println();
            }
        }
    }

    public LinkedList<Vertice> getVertices(){
        return new LinkedList<>(vertices.values());
    }
}
