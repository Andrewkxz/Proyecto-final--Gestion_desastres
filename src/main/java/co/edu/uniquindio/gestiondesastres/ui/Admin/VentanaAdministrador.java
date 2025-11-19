package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import java.awt.*;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;

public class VentanaAdministrador extends JFrame {

    private Usuario administrador;
    private GestionDesastres gestionDesastres;
    private JTabbedPane panelTabs;
    private PanelRecursos panelRecursos;
    private PanelZonas panelZonas;
    private PanelRutas panelRutas;
    private PanelVisualizacion panelVisualizacion;

    public VentanaAdministrador(Usuario administrador, GestionDesastres gestionDesastres) {
        this.administrador = administrador;
        this.gestionDesastres = gestionDesastres;

        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Inicializa los componentes principales
     */
    private void inicializarComponentes() {
        // Barra de herramientas superior
        JPanel barraHerramientas = crearBarraHerramientas();

        // Panel de pestañas
        panelTabs = new JTabbedPane();
        panelTabs.setFont(new Font("Arial", Font.BOLD, 12));

        // Crear paneles
        panelRecursos = new PanelRecursos(administrador, gestionDesastres, this);
        panelZonas = new PanelZonas(administrador, gestionDesastres, this);
        panelRutas = new PanelRutas(administrador, gestionDesastres, this);
        panelVisualizacion = new PanelVisualizacion(administrador, gestionDesastres);

        // Agregar pestañas
        panelTabs.addTab("Recursos", panelRecursos);
        panelTabs.addTab("Zonas", panelZonas);
        panelTabs.addTab("Rutas", panelRutas);
        panelTabs.addTab("Visualización", panelVisualizacion);

        // Agregar componentes a la ventana
        this.add(barraHerramientas, BorderLayout.NORTH);
        this.add(panelTabs, BorderLayout.CENTER);
    }

    /**
     * Crea la barra de herramientas superior
     */
    private JPanel crearBarraHerramientas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(new Color(50, 100, 150));
        panel.setBorder(BorderFactory.createEtchedBorder());

        // Etiqueta de bienvenida
        JLabel lblBienvenida = new JLabel("Administrador: " + administrador.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));
        lblBienvenida.setForeground(Color.WHITE);
        panel.add(lblBienvenida);

        // Separador visual
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(2, 30));
        panel.add(sep);

        // Botón refrescar
        JButton btnRefrescar = new JButton("Refrescar Datos");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnRefrescar.addActionListener(e -> refrescarTodosPaneles());
        panel.add(btnRefrescar);

        // Botón salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 11));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(200, 50, 50));
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> salirDelSistema());
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Refresca todos los paneles
     */
    private void refrescarTodosPaneles() {
        panelRecursos.actualizar();
        panelZonas.actualizar();
        panelRutas.actualizar();
        panelVisualizacion.actualizar();
    }

    /**
     * Sale del sistema
     */
    private void salirDelSistema() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Desea salir del sistema?",
            "Confirmación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        this.setTitle("Sistema de Gestión de Desastres - Administrador: " + administrador.getNombre());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Obtiene el administrador actual
     */
    public Usuario getAdministrador() {
        return administrador;
    }

    /**
     * Obtiene la instancia de GestionDesastres
     */
    public GestionDesastres getGestionDesastres() {
        return gestionDesastres;
    }
}