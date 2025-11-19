package co.edu.uniquindio.gestiondesastres.gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Administrador;
import co.edu.uniquindio.gestiondesastres.model.OperadorEmergencia;
import co.edu.uniquindio.gestiondesastres.model.Usuario;

public class GestionUsuarios {
    private List<Usuario> usuarios = new ArrayList<>();
    private String rutaArchivo = null;

    public GestionUsuarios() {}

    public GestionUsuarios(String rutaArchivo){
        this.rutaArchivo = rutaArchivo;
        cargarUsuarios();
    }

    private void cargarUsuarios(){
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primera = true;

            while((linea = br.readLine()) != null){
                if(primera){
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(",");

                if(partes.length == 4){
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    String contrasena = partes[2].trim();
                    String rol = partes[3].trim();

                    Usuario usuario;

                    switch(rol.toUpperCase()){
                        case "ADMINISTRADOR":
                            usuario = new Administrador(id, nombre, contrasena);
                            break;
                        case "OPERADOR":
                            usuario = new OperadorEmergencia(id, nombre, contrasena);
                            break;
                        default:
                            System.out.println("Rol desconocido: " + rol);
                            continue; // Salta roles desconocidos
                    }
                    usuarios.add(usuario);
                }
            }
        } catch (Exception e){
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public Usuario login(String nombre, String contrasena){
        for(Usuario usuario : usuarios){
            if(usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getContrasena().equals(contrasena)){
                return usuario;
            }
        }
        return null;
    }

    public boolean registrarAdministrador(String nombre, String contrasena){
        return registrarUsuario(nombre, contrasena, "ADMINISTRADOR");
    }

    public boolean registrarOperador(String nombre, String contrasena){
        return registrarUsuario(nombre, contrasena, "OPERADOR");
    }

    public boolean registrarUsuario(String nombre, String contrasena, String rol){
        if(existeUsuario(nombre)){
            System.out.println("El usuario ya existe: " + nombre);
            return false;
        }

        String nuevoId = generarID();
        Usuario nuevoUsuario;

        if(rol.equalsIgnoreCase("ADMINISTRADOR")){
            nuevoUsuario = new Administrador(nuevoId, nombre, contrasena);
        } else {
            nuevoUsuario = new OperadorEmergencia(nuevoId, nombre, contrasena);
        }
        usuarios.add(nuevoUsuario);
        guardarUsuarios();
        return true;
    }

    private String generarID(){
        return "U" + (usuarios.size() + 1);
    }

    public boolean existeUsuario(String nombre){
        return usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
    }

    public void guardarUsuarios(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
            bw.write("id,usuario,contrasena,rol");
            bw.newLine();

            for(Usuario usuario : usuarios){
                bw.write(usuario.getId() + "," + usuario.getNombre() + "," + usuario.getContrasena() + "," + usuario.getRol());
                bw.newLine();
            }
        } catch (Exception e){
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public List<Usuario> getUsuarios(){
        return usuarios;
    }
}

