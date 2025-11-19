package co.edu.uniquindio.gestiondesastres.gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Zona;

public class GestionZonas {
    private List<Zona> zonas = new ArrayList<>();
    private String rutaArchivo;

    public GestionZonas(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
        cargarZonas();
    }

    private void cargarZonas(){
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null){
                if(primera){
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if(partes.length == 3){
                    String nombre = partes[0].trim();
                    String tipo = partes[1].trim();
                    String riesgo = partes[2].trim();

                    zonas.add(new Zona(nombre, tipo, riesgo));
                }
            }
        } catch (Exception e){
            System.out.println("Error al cargar zonas: " + e.getMessage());
        }
    }

    private void guardarZonas(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
            bw.write("nombre,tipo,nivelRiesgo");
            bw.newLine();

            for(Zona zona : zonas){
                bw.write(zona.getNombre() + "," + zona.getTipo() + "," + zona.getNivelRiesgo());
                bw.newLine();
            }
        } catch (Exception e){
            System.out.println("Error al guardar zona: " + e.getMessage());
        }
    }

    public List<Zona> getZonas(){
        return zonas;
    }

    public Zona buscarZona(String nombre){
        for(Zona zona : zonas){
            if(zona.getNombre().equalsIgnoreCase(nombre)){
                return zona;
            }
        }
        return null;
    }

    public boolean agregarZona(String nombre, String tipo, String riesgo){
        if(buscarZona(nombre) != null){
            return false;
        }

        zonas.add(new Zona(nombre, tipo, riesgo));
        guardarZonas();
        return true;
    }

    public boolean eliminarZona(String nombre){
        Zona zona = buscarZona(nombre);
        if(zona != null){
            zonas.remove(zona);
            guardarZonas();
            return true;
        }
        return false;
    }

    public boolean actualizarZona(String nombre, String nuevoTipo, String nuevoRiesgo){
        Zona zona = buscarZona(nombre);
        if(zona == null){
            return false;
        }
        zona.setTipo(nuevoTipo);
        zona.setNivelRiesgo(nuevoRiesgo);
        guardarZonas();
        return true;
    }

    public List<String> listarZonas(){
        List<String> lista = new ArrayList<>();
        for(Zona zona : zonas){
            lista.add(zona.getNombre() + " | " + zona.getTipo() + " | " + zona.getNivelRiesgo());
        }
        return lista;
    }
}
