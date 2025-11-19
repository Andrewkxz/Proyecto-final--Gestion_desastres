package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.model.Zona;
import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.ui.Util.FormatoUtil;

public class PanelVisualizacion extends JPanel {

    private Usuario administrador;
    private GestionDesastres gestionDesastres;

    private JTabbedPane panelTabs;
    private JTable tablaRecursos;
    private JTable tablaZonas;
    private JTable tablaRutas;
    private DefaultTableModel modeloRecursos;
    private DefaultTableModel modeloZonas;
    private DefaultTableModel modeloRutas;
    private JLabel lblEstadisticas;
    private JButton btnRefrescar;

    public PanelVisualizacion(Usuario administrador, GestionDesastres gestionDesastres) {
        this.administrador = administrador;
        this.gestionDesastres = gestionDesastres;

        inicializarComponentes();
        actualizar();
    }

    /**
     * Inicializa los componentes del panel
     */
    private void inicializarComponentes() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Barra de herramientas
        JPanel barraHerramientas = crearBarraHerramientas();
        this.add(barraHerramientas, BorderLayout.NORTH);

        // Panel de pestañas
        panelTabs = new JTabbedPane();
        panelTabs.setFont(new Font("Arial", Font.BOLD, 12));

        crearTablaRecursos();
        crearTablaZonas();
        crearTablaRutas();

        panelTabs.addTab("Recursos", new JScrollPane(tablaRecursos));
        panelTabs.addTab("Zonas", new JScrollPane(tablaZonas));
        panelTabs.addTab("Rutas", new JScrollPane(tablaRutas));

        this.add(panelTabs, BorderLayout.CENTER);

        // Panel de estadísticas (pie)
        JPanel panelEstadisticas = crearPanelEstadisticas();
        this.add(panelEstadisticas, BorderLayout.SOUTH);
    }

    /**
     * Crea la barra de herramientas
     */
    private JPanel crearBarraHerramientas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEtchedBorder());

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnRefrescar.setPreferredSize(new Dimension(120, 30));
        btnRefrescar.addActionListener(e -> actualizar());
        panel.add(btnRefrescar);

        return panel;
    }

    /**
     * Crea la tabla de recursos
     */
    private void crearTablaRecursos() {
        modeloRecursos = new DefaultTableModel(
            new String[]{"ID", "Tipo", "Cantidad", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRecursos = new JTable(modeloRecursos);
        tablaRecursos.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaRecursos.setRowHeight(25);
        tablaRecursos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaRecursos.getTableHeader().setBackground(new Color(200, 200, 200));
    }

    /**
     * Crea la tabla de zonas
     */
    private void crearTablaZonas() {
        modeloZonas = new DefaultTableModel(
            new String[]{"Nombre", "Tipo", "Nivel de Riesgo", "Recursos Asignados"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaZonas = new JTable(modeloZonas);
        tablaZonas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaZonas.setRowHeight(25);
        tablaZonas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaZonas.getTableHeader().setBackground(new Color(200, 200, 200));
    }

    /**
     * Crea la tabla de rutas
     */
    private void crearTablaRutas() {
        modeloRutas = new DefaultTableModel(
            new String[]{"Origen", "Destino", "Distancia (km)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRutas = new JTable(modeloRutas);
        tablaRutas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaRutas.setRowHeight(25);
        tablaRutas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaRutas.getTableHeader().setBackground(new Color(200, 200, 200));
    }

    /**
     * Crea el panel de estadísticas
     */
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Estadísticas del Sistema"));
        panel.setBackground(new Color(240, 240, 240));

        lblEstadisticas = new JLabel("");
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstadisticas.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(lblEstadisticas, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Carga los datos en las tablas
     */
    private void cargarDatos() {
        cargarRecursos();
        cargarZonas();
        cargarRutas();
        actualizarEstadisticas();
    }

    /**
     * Carga los recursos en la tabla
     */
    private void cargarRecursos() {
        modeloRecursos.setRowCount(0);
        java.util.List<Recurso> recursos = gestionDesastres.listarRecursos(administrador);

        for (Recurso recurso : recursos) {
            modeloRecursos.addRow(new Object[]{
                recurso.getId(),
                recurso.getTipoRecurso(),
                FormatoUtil.formatoCantidad(recurso.getCantidadDisponible()),
                recurso.getEstado()
            });
        }
    }

    /**
     * Carga las zonas en la tabla
     */
    private void cargarZonas() {
        modeloZonas.setRowCount(0);
        java.util.List<Zona> zonas = gestionDesastres.listarZonas(administrador);

        for (Zona zona : zonas) {
            int cantidadRecursos = zona.getRecursos().size();
            modeloZonas.addRow(new Object[]{
                zona.getNombre(),
                zona.getTipo(),
                zona.getNivelRiesgo(),
                cantidadRecursos
            });
        }
    }

    /**
     * Carga las rutas en la tabla
     */
    private void cargarRutas() {
        modeloRutas.setRowCount(0);
        java.util.List<String> rutas = gestionDesastres.listarRutas(administrador);

        for (String ruta : rutas) {
            // Formato: "origen->destino(X km)"
            String[] partes = ruta.split("->");
            if (partes.length >= 2) {
                String origen = partes[0].trim();
                String resto = partes[1].trim();

                String destino = "";
                String distancia = "";

                int indiceParentesis = resto.indexOf("(");
                if (indiceParentesis > 0) {
                    destino = resto.substring(0, indiceParentesis).trim();
                    int indiceCierre = resto.indexOf(")");
                    distancia = resto.substring(indiceParentesis + 1, indiceCierre).replace("km", "").trim();
                }

                modeloRutas.addRow(new Object[]{origen, destino, distancia});
            }
        }
    }

    /**
     * Actualiza las estadísticas
     */
    private void actualizarEstadisticas() {
        int totalRecursos = modeloRecursos.getRowCount();
        int totalZonas = modeloZonas.getRowCount();
        int totalRutas = modeloRutas.getRowCount();

        String estadisticas = String.format(
            "Total de Recursos: %d | Total de Zonas: %d | Total de Rutas: %d",
            totalRecursos, totalZonas, totalRutas
        );

        lblEstadisticas.setText(estadisticas);
    }

    /**
     * Actualiza toda la información del panel
     */
    public void actualizar() {
        cargarDatos();
    }
}