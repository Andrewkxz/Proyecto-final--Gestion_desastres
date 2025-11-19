package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Zona;
import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.ui.Util.DialogosUtil;
import co.edu.uniquindio.gestiondesastres.ui.Util.ValidadorUI;
import co.edu.uniquindio.gestiondesastres.ui.Componentes.ComboBoxRiesgo;

/**
 * Panel para la gestión de zonas
 * Permite crear, editar, eliminar zonas y asignar recursos a ellas
 */
public class PanelZonas extends JPanel {

    private Usuario administrador;
    private GestionDesastres gestionDesastres;
    private VentanaAdministrador ventanaParent;

    private JTextField campoNombre;
    private JTextField campoTipo;
    private ComboBoxRiesgo comboRiesgo;
    private JComboBox<String> comboRecursos;
    private JTable tablaZonas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnAsignarRecurso;
    private JButton btnVerRecursos;
    private JButton btnEditarZona;
    private JButton btnEliminarZona;

    public PanelZonas(Usuario administrador, GestionDesastres gestionDesastres, VentanaAdministrador ventana) {
        this.administrador = administrador;
        this.gestionDesastres = gestionDesastres;
        this.ventanaParent = ventana;

        inicializarComponentes();
        cargarZonas();
        actualizarComboRecursos();
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
        panel.setBorder(BorderFactory.createTitledBorder("Crear Nueva Zona"));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre de Zona
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre de Zona:"), gbc);

        gbc.gridx = 1;
        campoNombre = new JTextField(15);
        campoNombre.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(campoNombre, gbc);

        // Tipo de Zona
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 3;
        campoTipo = new JTextField(15);
        campoTipo.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(campoTipo, gbc);

        // Nivel de Riesgo
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(new JLabel("Nivel de Riesgo:"), gbc);

        gbc.gridx = 5;
        comboRiesgo = new ComboBoxRiesgo();
        panel.add(comboRiesgo, gbc);

        // Botón Agregar
        gbc.gridx = 6;
        gbc.gridy = 0;
        btnAgregar = new JButton("Crear Zona");
        btnAgregar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnAgregar.setPreferredSize(new Dimension(110, 30));
        btnAgregar.addActionListener(e -> agregarZona());
        panel.add(btnAgregar, gbc);

        // Separador
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.ipady = 5;
        panel.add(new JSeparator(), gbc);
        gbc.ipady = 0;
        gbc.gridwidth = 1;

        // Panel de asignación de recursos
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Asignar Recurso:"), gbc);

        gbc.gridx = 1;
        comboRecursos = new JComboBox<>();
        comboRecursos.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(comboRecursos, gbc);

        gbc.gridx = 2;
        btnAsignarRecurso = new JButton("Asignar a Zona");
        btnAsignarRecurso.setFont(new Font("Arial", Font.PLAIN, 11));
        btnAsignarRecurso.setPreferredSize(new Dimension(120, 30));
        btnAsignarRecurso.addActionListener(e -> asignarRecursoAZona());
        panel.add(btnAsignarRecurso, gbc);

        gbc.gridx = 3;
        btnVerRecursos = new JButton("Ver Recursos");
        btnVerRecursos.setFont(new Font("Arial", Font.PLAIN, 11));
        btnVerRecursos.setPreferredSize(new Dimension(110, 30));
        btnVerRecursos.addActionListener(e -> verRecursosZona());
        panel.add(btnVerRecursos, gbc);

        return panel;
    }

    /**
     * Crea el panel con la tabla de zonas
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Zonas Actuales"));

        // Crear tabla
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Tipo", "Riesgo", "Recursos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaZonas = new JTable(modeloTabla);
        tablaZonas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaZonas.setRowHeight(25);
        tablaZonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaZonas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaZonas.getTableHeader().setBackground(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(tablaZonas);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnEditarZona = new JButton("Editar Zona");
        btnEditarZona.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEditarZona.setBackground(new Color(70, 130, 180));
        btnEditarZona.setForeground(Color.WHITE);
        btnEditarZona.setFocusPainted(false);
        btnEditarZona.addActionListener(e -> editarZona());
        panelBotones.add(btnEditarZona);

        btnEliminarZona = new JButton("Eliminar Zona");
        btnEliminarZona.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEliminarZona.setForeground(Color.WHITE);
        btnEliminarZona.setBackground(new Color(200, 50, 50));
        btnEliminarZona.setFocusPainted(false);
        btnEliminarZona.addActionListener(e -> eliminarZona());
        panelBotones.add(btnEliminarZona);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Agrega una nueva zona
     */
    private void agregarZona() {
        String nombre = campoNombre.getText().trim();
        String tipo = campoTipo.getText().trim();
        String riesgo = comboRiesgo.getNivelRiesgo();

        // Validaciones
        if (!ValidadorUI.esValido(nombre)) {
            DialogosUtil.mostrarError("Error", "El nombre de la zona no puede estar vacío");
            campoNombre.requestFocus();
            return;
        }

        if (nombre.length() < 3 || nombre.length() > 50) {
            DialogosUtil.mostrarError("Error", "El nombre debe tener entre 3 y 50 caracteres");
            campoNombre.requestFocus();
            return;
        }

        if (!ValidadorUI.esValido(tipo)) {
            DialogosUtil.mostrarError("Error", "El tipo de zona no puede estar vacío");
            campoTipo.requestFocus();
            return;
        }

        if (tipo.length() < 2 || tipo.length() > 50) {
            DialogosUtil.mostrarError("Error", "El tipo debe tener entre 2 y 50 caracteres");
            campoTipo.requestFocus();
            return;
        }

        // Delegar a GestionDesastres
        boolean agregada = gestionDesastres.agregarZona(administrador, nombre, tipo, riesgo);

        if (agregada) {
            DialogosUtil.mostrarExito("Éxito", "Zona creada correctamente");
            limpiarCamposZona();
            cargarZonas();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo crear la zona (¿ya existe?)");
        }
    }

    /**
     * Edita una zona existente
     */
    private void editarZona() {
        int fila = tablaZonas.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una zona para editar");
            return;
        }

        String nombreZona = (String) modeloTabla.getValueAt(fila, 0);
        Zona zona = gestionDesastres.buscarZona(nombreZona);

        if (zona == null) {
            DialogosUtil.mostrarError("Error", "No se encontró la zona");
            return;
        }

        // Opciones de edición
        String[] opciones = {"Editar Tipo", "Editar Nivel de Riesgo", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "¿Qué desea editar de la zona: " + nombreZona + "?",
            "Editar Zona",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        switch (seleccion) {
            case 0: // Editar Tipo
                editarTipoZona(nombreZona, zona.getTipo());
                break;
            case 1: // Editar Nivel de Riesgo
                editarRiesgoZona(nombreZona, zona.getNivelRiesgo());
                break;
            default: // Cancelar
                break;
        }
    }

    /**
     * Edita el tipo de una zona
     */
    private void editarTipoZona(String nombreZona, String tipoActual) {
        String nuevoTipo = (String) JOptionPane.showInputDialog(
            this,
            "Tipo actual: " + tipoActual + "\nIngrese el nuevo tipo:",
            "Editar Tipo de Zona",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            tipoActual
        );

        if (nuevoTipo == null || nuevoTipo.trim().isEmpty()) {
            return;
        }

        nuevoTipo = nuevoTipo.trim();

        if (nuevoTipo.length() < 2 || nuevoTipo.length() > 50) {
            DialogosUtil.mostrarError("Error", "El tipo debe tener entre 2 y 50 caracteres");
            return;
        }

        Zona zona = gestionDesastres.buscarZona(nombreZona);
        boolean actualizado = gestionDesastres.getGestionZonas().actualizarZona(
            nombreZona, 
            nuevoTipo, 
            zona.getNivelRiesgo()
        );

        if (actualizado) {
            DialogosUtil.mostrarExito("Éxito", "Tipo de zona actualizado correctamente");
            cargarZonas();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo actualizar el tipo de zona");
        }
    }

    /**
     * Edita el nivel de riesgo de una zona
     */
    private void editarRiesgoZona(String nombreZona, String riesgoActual) {
        String[] nivelesRiesgo = {"Bajo", "Medio", "Alto", "Crítico"};
        
        String nuevoRiesgo = (String) JOptionPane.showInputDialog(
            this,
            "Nivel de riesgo actual: " + riesgoActual + "\nSeleccione el nuevo nivel:",
            "Editar Nivel de Riesgo",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nivelesRiesgo,
            riesgoActual
        );

        if (nuevoRiesgo == null) {
            return;
        }

        Zona zona = gestionDesastres.buscarZona(nombreZona);
        boolean actualizado = gestionDesastres.getGestionZonas().actualizarZona(
            nombreZona, 
            zona.getTipo(), 
            nuevoRiesgo
        );

        if (actualizado) {
            DialogosUtil.mostrarExito("Éxito", "Nivel de riesgo actualizado correctamente");
            cargarZonas();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo actualizar el nivel de riesgo");
        }
    }

    /**
     * Elimina una zona
     */
    private void eliminarZona() {
        int fila = tablaZonas.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una zona para eliminar");
            return;
        }

        String nombreZona = (String) modeloTabla.getValueAt(fila, 0);

        if (DialogosUtil.mostrarConfirmacion("Confirmar", 
            "¿Está seguro de eliminar la zona: " + nombreZona + "?")) {

            boolean eliminada = gestionDesastres.getGestionZonas().eliminarZona(nombreZona);

            if (eliminada) {
                DialogosUtil.mostrarExito("Éxito", "Zona eliminada correctamente");
                cargarZonas();
            } else {
                DialogosUtil.mostrarError("Error", "No se pudo eliminar la zona");
            }
        }
    }

    /**
     * Asigna un recurso a una zona
     */
    private void asignarRecursoAZona() {
        int filaZona = tablaZonas.getSelectedRow();

        if (filaZona == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una zona");
            return;
        }

        if (comboRecursos.getSelectedIndex() == -1 || comboRecursos.getItemCount() == 0) {
            DialogosUtil.mostrarAdvertencia("Sin Recursos", "No hay recursos disponibles para asignar");
            return;
        }

        String nombreZona = (String) modeloTabla.getValueAt(filaZona, 0);
        String itemSeleccionado = (String) comboRecursos.getSelectedItem();
        
        if (itemSeleccionado == null) return;
        
        // Extraer el ID del recurso del formato "REC-1 - Ambulancia"
        String idRecurso = itemSeleccionado.split(" - ")[0].trim();

        boolean asignado = gestionDesastres.asignarRecursoAZona(administrador, idRecurso, nombreZona);

        if (asignado) {
            DialogosUtil.mostrarExito("Éxito", "Recurso asignado a la zona correctamente");
            cargarZonas();
            actualizarComboRecursos();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo asignar el recurso a la zona");
        }
    }

    /**
     * Muestra los recursos de una zona
     */
    private void verRecursosZona() {
        int fila = tablaZonas.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione una zona");
            return;
        }

        String nombreZona = (String) modeloTabla.getValueAt(fila, 0);
        Zona zona = gestionDesastres.buscarZona(nombreZona);

        if (zona == null) {
            DialogosUtil.mostrarError("Error", "No se encontró la zona");
            return;
        }

        StringBuilder recursos = new StringBuilder("Recursos en zona " + nombreZona + ":\n\n");
        java.util.ArrayList<Recurso> recursosZona = zona.getRecursos();

        if (recursosZona.isEmpty()) {
            recursos.append("No hay recursos asignados");
        } else {
            for (Recurso r : recursosZona) {
                recursos.append("- ").append(r.getTipoRecurso())
                    .append(" (ID: ").append(r.getId()).append(")\n");
            }
        }

        JTextArea textArea = new JTextArea(recursos.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Recursos de la Zona", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Carga las zonas en la tabla
     */
    private void cargarZonas() {
        modeloTabla.setRowCount(0);
        List<Zona> zonas = gestionDesastres.listarZonas(administrador);

        for (Zona zona : zonas) {
            int cantidadRecursos = zona.getRecursos().size();
            modeloTabla.addRow(new Object[]{
                zona.getNombre(),
                zona.getTipo(),
                zona.getNivelRiesgo(),
                cantidadRecursos
            });
        }
    }

    /**
     * Actualiza el combo de recursos disponibles
     * Muestra todos los recursos con cantidad > 0 (no solo "Operativo")
     */
    private void actualizarComboRecursos() {
        comboRecursos.removeAllItems();
        List<Recurso> todosRecursos = gestionDesastres.getGestionRecursos().getRecursos();

        for (Recurso recurso : todosRecursos) {
            // Mostrar recursos que tengan cantidad disponible
            if (recurso.getCantidadDisponible() > 0) {
                comboRecursos.addItem(recurso.getId() + " - " + recurso.getTipoRecurso() + 
                                     " (Disponible: " + recurso.getCantidadDisponible() + ")");
            }
        }
        
        if (comboRecursos.getItemCount() == 0) {
            comboRecursos.addItem("(No hay recursos disponibles)");
        }
    }

    /**
     * Limpia los campos de entrada
     */
    private void limpiarCamposZona() {
        campoNombre.setText("");
        campoTipo.setText("");
        comboRiesgo.setNivelRiesgo("Bajo");
        campoNombre.requestFocus();
    }

    /**
     * Actualiza el panel
     */
    public void actualizar() {
        cargarZonas();
        actualizarComboRecursos();
    }
}