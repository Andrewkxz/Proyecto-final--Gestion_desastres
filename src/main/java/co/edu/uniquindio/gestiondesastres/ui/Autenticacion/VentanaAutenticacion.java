package co.edu.uniquindio.gestiondesastres.ui.Autenticacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Administrador;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.ui.Util.DialogosUtil;
import co.edu.uniquindio.gestiondesastres.ui.Util.ValidadorUI;

public class VentanaAutenticacion extends JFrame {
    
    private GestionDesastres gestionDesastres;
    private JPanel panelPrincipal;
    private JTextField campoNombre;
    private JPasswordField campoContrasena;
    private JPasswordField campoContrasenaConfirm;
    private JButton btnLogin;
    private JButton btnCrearAdmin;
    private JLabel lblEstado;
    private ActionListener alLoginExitoso;
    private Usuario usuarioActual;
    private boolean esCreacion = false;

    public VentanaAutenticacion(GestionDesastres gestionDesastres) {
        this.gestionDesastres = gestionDesastres;
        this.usuarioActual = null;

        inicializarComponentes();
        determinarModo();
        configurarVentana();
    }

    /**
     * Inicializa todos los componentes visuales
     */
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(240, 240, 240));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Desastres - Sistema de Administración");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);

        // Separador visual
        JSeparator sep = new JSeparator();
        gbc.gridy = 1;
        gbc.ipady = 5;
        panelPrincipal.add(sep, gbc);

        // Etiqueta estado
        lblEstado = new JLabel("");
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEstado.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        gbc.ipady = 0;
        panelPrincipal.add(lblEstado, gbc);

        // Label y campo: Nombre
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        panelPrincipal.add(lblNombre, gbc);

        gbc.gridx = 1;
        campoNombre = new JTextField(20);
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(campoNombre, gbc);

        // Label y campo: Contraseña
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblContrasena = new JLabel("Contraseña:");
        panelPrincipal.add(lblContrasena, gbc);

        gbc.gridx = 1;
        campoContrasena = new JPasswordField(20);
        campoContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(campoContrasena, gbc);

        // Label y campo: Confirmar contraseña (oculto inicialmente)
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblConfirm = new JLabel("Confirmar:");
        panelPrincipal.add(lblConfirm, gbc);

        gbc.gridx = 1;
        campoContrasenaConfirm = new JPasswordField(20);
        campoContrasenaConfirm.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(campoContrasenaConfirm, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(new Color(240, 240, 240));

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 12));
        btnLogin.addActionListener(e -> procesarLogin());
        panelBotones.add(btnLogin);

        btnCrearAdmin = new JButton("Crear Administrador");
        btnCrearAdmin.setPreferredSize(new Dimension(140, 35));
        btnCrearAdmin.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCrearAdmin.addActionListener(e -> procesarCreacion());
        panelBotones.add(btnCrearAdmin);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panelPrincipal.add(panelBotones, gbc);

        this.add(panelPrincipal);
    }

    /**
     * Determina si debe mostrar login o creación
     */
    private void determinarModo() {
        java.util.List<Usuario> usuarios = gestionDesastres.getGestionUsuarios().getUsuarios();
        boolean hayAdmins = usuarios.stream().anyMatch(u -> u.getRol().equals("ADMINISTRADOR"));

        if (hayAdmins) {
            lblEstado.setText("Ingrese sus credenciales para continuar");
            btnCrearAdmin.setText("Crear otro Administrador");
            btnCrearAdmin.setVisible(false);
            campoContrasenaConfirm.setVisible(false);
            this.setSize(450, 300);
            esCreacion = false;
        } else {
            lblEstado.setText("No hay administradores. Cree el primer administrador del sistema.");
            btnLogin.setVisible(false);
            campoContrasenaConfirm.setVisible(true);
            esCreacion = true;
        }
    }

    /**
     * Procesa el login del administrador
     */
    private void procesarLogin() {
        String nombre = campoNombre.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());

        // Validaciones
        if (!ValidadorUI.esValido(nombre)) {
            DialogosUtil.mostrarError("Error", "El nombre no puede estar vacío");
            campoNombre.requestFocus();
            return;
        }

        if (!ValidadorUI.esValido(contrasena)) {
            DialogosUtil.mostrarError("Error", "La contraseña no puede estar vacía");
            campoContrasena.requestFocus();
            return;
        }

        // Intentar autenticar
        Usuario usuario = gestionDesastres.login(nombre, contrasena);

        if (usuario != null && usuario.getRol().equals("ADMINISTRADOR")) {
            this.usuarioActual = usuario;
            DialogosUtil.mostrarExito("Éxito", "Bienvenido " + nombre);
            notificarLoginExitoso(usuario);
            this.dispose();
        } else {
            DialogosUtil.mostrarError("Error", "Nombre, contraseña o rol incorrectos");
            limpiarCampos();
        }
    }

    /**
     * Procesa la creación de un nuevo administrador
     */
    private void procesarCreacion() {
        String nombre = campoNombre.getText().trim();
        String contrasena = new String(campoContrasena.getPassword());
        String contrasenaConfirm = new String(campoContrasenaConfirm.getPassword());

        // Validaciones
        if (!ValidadorUI.esValido(nombre)) {
            DialogosUtil.mostrarError("Error", "El nombre no puede estar vacío");
            campoNombre.requestFocus();
            return;
        }

        if (nombre.length() < 3 || nombre.length() > 50) {
            DialogosUtil.mostrarError("Error", "El nombre debe tener entre 3 y 50 caracteres");
            campoNombre.requestFocus();
            return;
        }

        if (!ValidadorUI.esValido(contrasena)) {
            DialogosUtil.mostrarError("Error", "La contraseña no puede estar vacía");
            campoContrasena.requestFocus();
            return;
        }

        if (contrasena.length() < 4) {
            DialogosUtil.mostrarError("Error", "La contraseña debe tener al menos 4 caracteres");
            campoContrasena.requestFocus();
            return;
        }

        if (!contrasena.equals(contrasenaConfirm)) {
            DialogosUtil.mostrarError("Error", "Las contraseñas no coinciden");
            limpiarCampos();
            campoContrasena.requestFocus();
            return;
        }

        // Crear administrador
        boolean creado = gestionDesastres.getGestionUsuarios().registrarAdministrador(nombre, contrasena);

        if (creado) {
            DialogosUtil.mostrarExito("Éxito", "Administrador creado exitosamente");
            this.usuarioActual = gestionDesastres.login(nombre, contrasena);
            notificarLoginExitoso(usuarioActual);
            this.dispose();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo crear el administrador (¿ya existe?)");
            limpiarCampos();
        }
    }

    /**
     * Limpia todos los campos de entrada
     */
    private void limpiarCampos() {
        campoNombre.setText("");
        campoContrasena.setText("");
        campoContrasenaConfirm.setText("");
        campoNombre.requestFocus();
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        this.setTitle("Autenticación - Sistema de Gestión de Desastres");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 380);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Establece listener para cuando el login sea exitoso
     */
    public void setOnLoginExitoso(ActionListener listener) {
        this.alLoginExitoso = listener;
    }

    /**
     * Notifica que el login fue exitoso
     */
    private void notificarLoginExitoso(Usuario usuario) {
        if (alLoginExitoso != null) {
            alLoginExitoso.actionPerformed(new java.awt.event.ActionEvent(this, 0, usuario.getNombre()));
        }
    }

    /**
     * Obtiene el usuario autenticado
     */
    public Usuario getUsuario() {
        return this.usuarioActual;
    }
}