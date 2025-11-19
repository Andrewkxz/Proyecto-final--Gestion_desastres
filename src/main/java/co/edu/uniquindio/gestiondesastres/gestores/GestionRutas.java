package co.edu.uniquindio.gestiondesastres.gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Arista;
import co.edu.uniquindio.gestiondesastres.model.GrafoDirigido;
import co.edu.uniquindio.gestiondesastres.model.Vertice;

public class GestionRutas {
    private String rutaArchivo;
    private GrafoDirigido grafo;

    public GestionRutas(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
        this.grafo = new GrafoDirigido();
        cargarRutas();
    }

    public GrafoDirigido getGrafo(){
        return grafo;
    }

    private void cargarRutas(){
        grafo = new GrafoDirigido();

        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            boolean primera = true;

            while((linea = br.readLine()) != null){
                if(primera){
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if(partes.length == 3){
                    String origen = partes[0];
                    String destino = partes[1];
                    double distancia = Double.parseDouble(partes[2]);

                    grafo.agregarArista(origen, destino, distancia);
                }
            }
        } catch (Exception e){
            System.out.println("Error al cargar rutas: " + e.getMessage());
        }
    }

    private void guardarRutas(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
            bw.write("origen,destino,distancia");
            bw.newLine();

            for(Vertice vertice : grafo.obtenerVertices()){
                for(Arista arista : vertice.getAdyacentes()){
                    bw.write(vertice.getNombre() + "," + arista.getDestino().getNombre() + "," + arista.getPeso());
                    bw.newLine();
                }
            }
        } catch (Exception e){
            System.out.println("Error al gurdar rutas: " + e.getMessage());
        }
    }

    public boolean agregarRuta(String origen, String destino, double distancia){
        if(grafo.existeArista(origen, destino)){
            return false;
        }

        grafo.agregarArista(origen, destino, distancia);
        guardarRutas();
        return true;
    }

    public boolean eliminarRuta(String origen, String destino){
        if(!grafo.existeArista(origen, destino)){
            return false;
        }

        grafo.eliminarArista(origen, destino);
        guardarRutas();
        return true;
    }

    public boolean actualizarDistancia(String origen, String destino, double nuevaDistancia){
        if(!grafo.existeArista(origen, destino)){
            return false;
        }

        grafo.modificarPeso(origen, destino, nuevaDistancia);
        guardarRutas();
        return true;
    }

    public List<String> listarRutas(){
        List<String> lista = new ArrayList<>();
        
        for(Vertice vertice : grafo.obtenerVertices()){
            for(Arista arista : vertice.getAdyacentes()){
                lista.add(vertice.getNombre() + "->" + arista.getDestino().getNombre() + "(" + arista.getPeso() + " km)");
            }
        }
        return lista;
    }
}
