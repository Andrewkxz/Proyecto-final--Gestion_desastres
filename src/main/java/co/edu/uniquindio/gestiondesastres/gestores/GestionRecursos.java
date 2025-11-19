package co.edu.uniquindio.gestiondesastres.gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.model.Zona;

public class GestionRecursos {
    private List<Recurso> recursos = new ArrayList<>();
    private String rutaArchivo;

    public GestionRecursos(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
        cargarRecursos();
    }

    private void cargarRecursos(){
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            boolean primera = true;

            while((linea = br.readLine()) != null){
                if(primera){
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");

                if(partes.length ==4){
                    String id = partes[0].trim();
                    String tipo = partes[1].trim();
                    int cantidad = Integer.parseInt(partes[2].trim());
                    String estado = partes[3].trim();

                    recursos.add(new Recurso(id, tipo, cantidad, estado));
                }
            }
        } catch (Exception e){
            System.out.println("Error cargando recursos: " + e.getMessage());
        }
    }

    private void guardarRecursos(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
            bw.write("id,tipo,cantidad,estado");
            bw.newLine();

            for(Recurso recurso : recursos){
                bw.write(recurso.getId() + "," + recurso.getTipoRecurso() + "," + recurso.getCantidadDisponible() + "," + recurso.getEstado());
                bw.newLine();
            }
        } catch (Exception e){
            System.out.println("Error guardado recursos: " + e.getMessage());
        }
    }

    public List<Recurso> getRecursos(){
        return recursos;
    }

    public List<Recurso> buscarPorTipo(String tipo){
        List<Recurso> resultado = new ArrayList<>();

        for(Recurso recurso : recursos){
            if(recurso.getTipoRecurso().equalsIgnoreCase(tipo)){
                resultado.add(recurso);
            }
        }
        return resultado;
    }

    public List<Recurso> getDisponibles(){
        List<Recurso> disponibles = new ArrayList<>();

        for(Recurso recurso : recursos){
            if(recurso.getEstado().equalsIgnoreCase("operativo") && recurso.getCantidadDisponible() > 0){
                disponibles.add(recurso);
            }
        }
        return disponibles;
    }

    public boolean agregarRecurso(String tipo, int cantidad, String estado){
        String id = generarID();
        Recurso recurso = new Recurso(id, tipo, cantidad, estado);
        recursos.add(recurso);
        guardarRecursos();
        return true;
    }

    public boolean eliminarRecurso(String id){
        for(Recurso recurso : recursos){
            if(recurso.getId().equals(id)){
                recursos.remove(recurso);
                guardarRecursos();
                return true;
            }
        }
        return false;
    }

    public boolean actualizarEstado(String id, String nuevoEstado){
        for(Recurso recurso : recursos){
            if(recurso.getId().equals(id)){
                recurso.setEstado(nuevoEstado);
                guardarRecursos();
                return true;
            }
        }
        return false;
    }

    public boolean actualizarCantidad(String id, int nuevaCantidad){
        for(Recurso recurso : recursos){
            if(recurso.getId().equals(id)){
                recurso.setCantidadDisponible(nuevaCantidad);
                guardarRecursos();
                return true;
            }
        }
        return false;
    }

    public boolean asignarRecursoAZona(String idRecurso, Zona zona){
        if(zona == null){
            System.out.println("La zona no existe.");
            return false;
        }

        for(Recurso recurso : recursos){
            if(recurso.getId().equals(idRecurso)){
                if(recurso.getCantidadDisponible() <= 0){
                    System.out.println("No hay cantidad disponible del recurso: " + idRecurso);
                    return false;
                }
                zona.asignarRecurso(recurso);
                recurso.setCantidadDisponible(recurso.getCantidadDisponible() - 1);

                if(recurso.getCantidadDisponible() == 0){
                    recurso.setEstado("Agotado");
                }
                guardarRecursos();
                return true;
            }
        }
        System.out.println("Recurso no encontrado: " + idRecurso);
        return false;
    }

    private String generarID(){
        return "REC-" + (recursos.size() + 1);
    }

    public List<String> listarRecursos(){
        List<String> lista = new ArrayList<>();
        for(Recurso recurso : recursos){
            lista.add(recurso.getId() + " | " + recurso.getTipoRecurso() + " | Cantidad: " + recurso.getCantidadDisponible() + " |Estado: " + recurso.getEstado());
        }
        return lista;
    }
}
