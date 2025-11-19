package co.edu.uniquindio.gestiondesastres;

import javax.swing.SwingUtilities;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.ui.Autenticacion.VentanaAutenticacion;
import co.edu.uniquindio.gestiondesastres.ui.Admin.VentanaAdministrador;
import co.edu.uniquindio.gestiondesastres.model.Usuario;

public class Main {

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar sistema de gestión (Singleton)
                GestionDesastres gestionDesastres = GestionDesastres.getInstancia();
                
                // Crear y mostrar ventana de autenticación
                VentanaAutenticacion ventanaAuth = new VentanaAutenticacion(gestionDesastres);
                
                // Al hacer login exitoso, abrir ventana principal
                ventanaAuth.setOnLoginExitoso(e -> {
                    Usuario usuario = ventanaAuth.getUsuario();
                    
                    if (usuario != null) {
                        // Abrir ventana de administrador
                        new VentanaAdministrador(usuario, gestionDesastres);
                        
                        // Cerrar ventana de autenticación (ya se cierra sola con dispose())
                    }
                });
                
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}