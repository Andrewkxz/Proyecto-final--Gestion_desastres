package co.edu.uniquindio.gestiondesastres.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.edu.uniquindio.gestiondesastres.model.Usuario;

public class GestionUsuarios {
    private Map<String, Usuario> usuarios = new HashMap<>();

    public void cargarUsuario(String rutaArchivo){
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            boolean primerLinea = true;
            while((linea = br.readLine()) != null){
                if(primerLinea){
                    primerLinea = false;
                    continue; // Saltar la línea de encabezado
                }
                String[] partes = linea.split(",");
                if(partes.length == 2){
                    usuarios.put(partes[0], new Usuario(partes[0].trim(), partes[1].trim()));
                    }
                }
        } catch (IOException e){
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }

    public Usuario autenticar(String nombre, String contrasena){
        Usuario usuario = usuarios.get(nombre);
        if(usuario != null && usuario.getContrasena().equals(contrasena)){
            return usuario;
        } else {
            return null;
        }
    }
}

