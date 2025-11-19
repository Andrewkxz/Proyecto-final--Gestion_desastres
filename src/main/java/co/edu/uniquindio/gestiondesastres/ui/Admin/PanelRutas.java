package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.ui.Util.DialogosUtil;
import co.edu.uniquindio.gestiondesastres.ui.Util.ValidadorUI;
import co.edu.uniquindio.gestiondesastres.ui.Componentes.CampoNumerico;

/**
 * Panel para la gestión de rutas
 * Permite agregar, actualizar, eliminar rutas y calcular caminos más cortos
 */
public class PanelRutas extends JPanel {

    private Usuario administrador;
    private GestionDesastres gestionDesastres;
    private VentanaAdministrador ventanaParent;

    private JTextField campoOrigen;
    private JTextField campoDestino;
    private CampoNumerico campoDistancia;
    private JTable tablaRutas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnActualizarDistancia;
    private JButton btnEliminar;
    private JButton btnVisualizarGrafo;
    private JButton btnCaminoMasCorto;

    public PanelRutas(Usuario administrador, GestionDesastres gestionDesastres, VentanaAdministrador ventana) {
        this.administrador = administrador;
        this.gestionDesastres = gestionDesastres;
        this.ventanaParent = ventana;

        inicializarComponentes();
        cargarRutas();
    }

    /**
     * Inicializa los componentes del panel
     */
    private void inicializarComponentes() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de entrada
        JPanel panelEntrada = crearPanelEntrada();
        this.add(panelEntrada, BorderLayout.NORTH);

        // Panel de tabla
        JPanel panelTabla = crearPanelTabla();
        this.add(panelTabla, BorderLayout.CENTER);
    }

    /**
     * Crea el panel de entrada de datos
     */
    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Agregar Nueva Ruta"));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Origen
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Origen:"), gbc);

        gbc.gridx = 1;
        campoOrigen = new JTextField(15);
        campoOrigen.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(campoOrigen, gbc);

        // Destino
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Destino:"), gbc);

        gbc.gridx = 3;
        campoDestino = new JTextField(15);
        campoDestino.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(campoDestino, gbc);

        // Distancia
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(new JLabel("Distancia (km):"), gbc);

        gbc.gridx = 5;
        campoDistancia = new CampoNumerico(true, false, 8);
        panel.add(campoDistancia, gbc);

        // Botón Agregar
        gbc.gridx = 6;
        gbc.gridy = 0;
        btnAgregar = new JButton("Agregar Ruta");
        btnAgregar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnAgregar.setPreferredSize(new Dimension(120, 30));
        btnAgregar.addActionListener(e -> agregarRuta());
        panel.add(btnAgregar, gbc);

        return panel;
    }

    /**
     * Crea el panel con la tabla de rutas
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Rutas Actuales"));

        // Crear tabla
        modeloTabla = new DefaultTableModel(new String[] { "Origen", "Destino", "Distancia (km)" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRutas = new JTable(modeloTabla);
        tablaRutas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaRutas.setRowHeight(25);
        tablaRutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRutas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaRutas.getTableHeader().setBackground(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(tablaRutas);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnCaminoMasCorto = new JButton("Camino Más Corto");
        btnCaminoMasCorto.setFont(new Font("Arial", Font.PLAIN, 11));
        btnCaminoMasCorto.setBackground(new Color(50, 150, 200));
        btnCaminoMasCorto.setForeground(Color.WHITE);
        btnCaminoMasCorto.setFocusPainted(false);
        btnCaminoMasCorto.addActionListener(e -> calcularCaminoMasCorto());
        panelBotones.add(btnCaminoMasCorto);

        btnActualizarDistancia = new JButton("Actualizar Distancia");
        btnActualizarDistancia.setFont(new Font("Arial", Font.PLAIN, 11));
        btnActualizarDistancia.addActionListener(e -> actualizarDistanciaRuta());
        panelBotones.add(btnActualizarDistancia);

        btnEliminar = new JButton("Eliminar Ruta");
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarRuta());
        panelBotones.add(btnEliminar);

        btnVisualizarGrafo = new JButton("Visualizar Grafo");
        btnVisualizarGrafo.setFont(new Font("Arial", Font.PLAIN, 11));
        btnVisualizarGrafo.setBackground(new Color(50, 150, 100));
        btnVisualizarGrafo.setForeground(Color.WHITE);
        btnVisualizarGrafo.setFocusPainted(false);
        btnVisualizarGrafo.addActionListener(e -> visualizarGrafoConZonas());
        panelBotones.add(btnVisualizarGrafo);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Agrega una nueva ruta
     */
    private void agregarRuta() {
        String origen = campoOrigen.getText().trim();
        String destino = campoDestino.getText().trim();
        String distanciaStr = campoDistancia.getText().trim();

        // Validaciones
        if (!ValidadorUI.esValido(origen)) {
            DialogosUtil.mostrarError("Error", "El origen no puede estar vacío");
            campoOrigen.requestFocus();
            return;
        }

        if (!ValidadorUI.esValido(destino)) {
            DialogosUtil.mostrarError("Error", "El destino no puede estar vacío");
            campoDestino.requestFocus();
            return;
        }

        if (origen.equals(destino)) {
            DialogosUtil.mostrarError("Error", "Origen y destino no pueden ser iguales");
            campoDestino.requestFocus();
            return;
        }

        if (!ValidadorUI.esNumeroDecimal(distanciaStr)) {
            DialogosUtil.mostrarError("Error", "La distancia debe ser un número decimal");
            campoDistancia.requestFocus();
            return;
        }

        double distancia = ValidadorUI.toDouble(distanciaStr);
        if (!ValidadorUI.esPositivo(distancia)) {
            DialogosUtil.mostrarError("Error", "La distancia debe ser mayor que 0");
            campoDistancia.requestFocus();
            return;
        }

        // Delegar a GestionDesastres
        boolean agregada = gestionDesastres.agregarRuta(administrador, origen, destino, distancia);

        if (agregada) {
            DialogosUtil.mostrarExito("Éxito", "Ruta agregada correctamente");
            limpiarCamposRuta();
            cargarRutas();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo agregar la ruta (¿ya existe?)");
        }
    }

    /**
     * Actualiza la distancia de una ruta existente
     */
    private void actualizarDistanciaRuta() {
        int fila = tablaRutas.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una ruta");
            return;
        }

        String origen = (String) modeloTabla.getValueAt(fila, 0);
        String destino = (String) modeloTabla.getValueAt(fila, 1);

        String nuevaDistanciaStr = JOptionPane.showInputDialog(
                this,
                "Ingrese la nueva distancia (km):",
                "Actualizar Distancia",
                JOptionPane.QUESTION_MESSAGE);

        if (nuevaDistanciaStr == null)
            return;

        if (!ValidadorUI.esNumeroDecimal(nuevaDistanciaStr)) {
            DialogosUtil.mostrarError("Error", "La distancia debe ser un número decimal válido");
            return;
        }

        double nuevaDistancia = ValidadorUI.toDouble(nuevaDistanciaStr);
        if (!ValidadorUI.esPositivo(nuevaDistancia)) {
            DialogosUtil.mostrarError("Error", "La distancia debe ser mayor que 0");
            return;
        }

        boolean actualizada = gestionDesastres.actualizarDistanciaRuta(administrador, origen, destino, nuevaDistancia);

        if (actualizada) {
            DialogosUtil.mostrarExito("Éxito", "Distancia actualizada correctamente");
            cargarRutas();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo actualizar la distancia");
        }
    }

    /**
     * Elimina una ruta
     */
    private void eliminarRuta() {
        int fila = tablaRutas.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una ruta para eliminar");
            return;
        }

        String origen = (String) modeloTabla.getValueAt(fila, 0);
        String destino = (String) modeloTabla.getValueAt(fila, 1);

        if (DialogosUtil.mostrarConfirmacion("Confirmar",
                "¿Eliminar ruta " + origen + " → " + destino + "?")) {

            boolean eliminada = gestionDesastres.eliminarRuta(administrador, origen, destino);

            if (eliminada) {
                DialogosUtil.mostrarExito("Éxito", "Ruta eliminada correctamente");
                cargarRutas();
            } else {
                DialogosUtil.mostrarError("Error", "No se pudo eliminar la ruta");
            }
        }
    }

    /**
     * Calcula el camino más corto usando Dijkstra
     */
    private void calcularCaminoMasCorto() {
        // Obtener todos los vértices disponibles
        List<String> vertices = obtenerVerticesDisponibles();

        if (vertices.isEmpty()) {
            DialogosUtil.mostrarAdvertencia("Sin datos", "No hay rutas registradas en el sistema");
            return;
        }

        // Seleccionar origen
        String origen = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el origen:",
                "Camino Más Corto - Origen",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vertices.toArray(),
                vertices.get(0));

        if (origen == null)
            return;

        // Seleccionar destino
        String destino = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el destino:",
                "Camino Más Corto - Destino",
                JOptionPane.QUESTION_MESSAGE,
                null,
                vertices.toArray(),
                vertices.get(0));

        if (destino == null)
            return;

        if (origen.equals(destino)) {
            DialogosUtil.mostrarAdvertencia("Error", "El origen y destino no pueden ser iguales");
            return;
        }

        // Calcular camino usando Dijkstra
        try {
            LinkedList<String> camino = gestionDesastres.getGestionRutas().getGrafo()
                    .obtenerCaminoMasCorto(origen, destino);

            if (camino.isEmpty()) {
                DialogosUtil.mostrarAdvertencia("Sin camino",
                        "No existe un camino entre " + origen + " y " + destino);
                return;
            }

            // Calcular distancia total
            double distanciaTotal = calcularDistanciaTotal(camino);

            // Mostrar resultado
            StringBuilder resultado = new StringBuilder();
            resultado.append("Camino más corto de ").append(origen).append(" a ").append(destino).append(":\n\n");

            for (int i = 0; i < camino.size(); i++) {
                resultado.append(camino.get(i));
                if (i < camino.size() - 1) {
                    resultado.append(" → ");
                }
            }

            resultado.append("\n\nDistancia total: ").append(String.format("%.2f", distanciaTotal)).append(" km");

            JTextArea textArea = new JTextArea(resultado.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Arial", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 150));

            JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Resultado - Algoritmo de Dijkstra",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            DialogosUtil.mostrarError("Error", "Error al calcular el camino: " + e.getMessage());
        }
    }

    /**
     * Calcula la distancia total de un camino
     */
    private double calcularDistanciaTotal(LinkedList<String> camino) {
        double distanciaTotal = 0.0;

        for (int i = 0; i < camino.size() - 1; i++) {
            String origen = camino.get(i);
            String destino = camino.get(i + 1);

            // Buscar la distancia en el grafo
            var vertice = gestionDesastres.getGestionRutas().getGrafo().obtenerVertice(origen);
            if (vertice != null) {
                for (var arista : vertice.getAdyacentes()) {
                    if (arista.getDestino().getNombre().equals(destino)) {
                        distanciaTotal += arista.getPeso();
                        break;
                    }
                }
            }
        }

        return distanciaTotal;
    }

    /**
     * Obtiene lista de vértices disponibles en el grafo
     */
    private List<String> obtenerVerticesDisponibles() {
        List<String> vertices = new java.util.ArrayList<>();
        var grafo = gestionDesastres.getGestionRutas().getGrafo();

        for (var vertice : grafo.obtenerVertices()) {
            vertices.add(vertice.getNombre());
        }

        return vertices;
    }

    /**
     * Visualiza el grafo mostrando zonas con niveles de riesgo
     */
    private void visualizarGrafoConZonas() {
        try {
            // Crear ventana de visualización
                VisualizadorGrafoZonas.mostrar(
                    gestionDesastres.getGestionRutas().getGrafo(),
                    gestionDesastres.getGestionZonas().getZonas());
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error", "No se pudo visualizar el grafo: " + e.getMessage());
        }
    }

    /**
     * Carga las rutas en la tabla
     */
    private void cargarRutas() {
        modeloTabla.setRowCount(0);
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

                modeloTabla.addRow(new Object[] { origen, destino, distancia });
            }
        }
    }

    /**
     * Limpia los campos de entrada
     */
    private void limpiarCamposRuta() {
        campoOrigen.setText("");
        campoDestino.setText("");
        campoDistancia.setText("");
        campoOrigen.requestFocus();
    }

    /**
     * Actualiza el panel
     */
    public void actualizar() {
        cargarRutas();
    }
}