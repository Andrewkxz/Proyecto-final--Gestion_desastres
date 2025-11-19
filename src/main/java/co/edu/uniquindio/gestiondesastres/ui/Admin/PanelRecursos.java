package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import co.edu.uniquindio.gestiondesastres.model.Usuario;
import co.edu.uniquindio.gestiondesastres.model.Recurso;
import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.ui.Util.DialogosUtil;
import co.edu.uniquindio.gestiondesastres.ui.Util.ValidadorUI;
import co.edu.uniquindio.gestiondesastres.ui.Util.FormatoUtil;
import co.edu.uniquindio.gestiondesastres.ui.Componentes.CampoNumerico;

/**
 * Panel para la gestión de recursos
 * Permite agregar, actualizar y eliminar recursos
 */
public class PanelRecursos extends JPanel {

    private Usuario administrador;
    private GestionDesastres gestionDesastres;
    private VentanaAdministrador ventanaParent;
    
    private JTextField campoTipo;
    private CampoNumerico campoCantidad;
    private JComboBox<String> comboEstado;
    private JTable tablaRecursos;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    public PanelRecursos(Usuario administrador, GestionDesastres gestionDesastres, VentanaAdministrador ventana) {
        this.administrador = administrador;
        this.gestionDesastres = gestionDesastres;
        this.ventanaParent = ventana;

        inicializarComponentes();
        cargarRecursos();
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
        panel.setBorder(BorderFactory.createTitledBorder("Agregar Nuevo Recurso"));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tipo de Recurso
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tipo de Recurso:"), gbc);

        gbc.gridx = 1;
        campoTipo = new JTextField(15);
        campoTipo.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(campoTipo, gbc);

        // Cantidad
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 3;
        campoCantidad = new CampoNumerico(false, false, 8);
        panel.add(campoCantidad, gbc);

        // Estado
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 5;
        comboEstado = new JComboBox<>(new String[]{"Disponible", "Dañado", "En Mantenimiento", "Operativo", "Agotado"});
        comboEstado.setFont(new Font("Arial", Font.PLAIN, 11));
        comboEstado.setSelectedIndex(0);
        panel.add(comboEstado, gbc);

        // Botón Agregar
        gbc.gridx = 6;
        gbc.gridy = 0;
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnAgregar.setPreferredSize(new Dimension(100, 30));
        btnAgregar.addActionListener(e -> agregarRecurso());
        panel.add(btnAgregar, gbc);

        return panel;
    }

    /**
     * Crea el panel con la tabla de recursos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Recursos Actuales"));

        // Crear tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Tipo", "Cantidad", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRecursos = new JTable(modeloTabla);
        tablaRecursos.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaRecursos.setRowHeight(25);
        tablaRecursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRecursos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaRecursos.getTableHeader().setBackground(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(tablaRecursos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones de tabla
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        btnActualizar = new JButton("Actualizar Cantidad");
        btnActualizar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnActualizar.addActionListener(e -> actualizarRecurso());
        panelBotones.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarRecurso());
        panelBotones.add(btnEliminar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Agrega un nuevo recurso
     */
    private void agregarRecurso() {
        String tipo = campoTipo.getText().trim();
        String cantidad = campoCantidad.getText().trim();
        String estado = (String) comboEstado.getSelectedItem();

        // Validaciones
        if (!ValidadorUI.esValido(tipo)) {
            DialogosUtil.mostrarError("Error", "El tipo de recurso no puede estar vacío");
            campoTipo.requestFocus();
            return;
        }

        if (tipo.length() < 2 || tipo.length() > 50) {
            DialogosUtil.mostrarError("Error", "El tipo debe tener entre 2 y 50 caracteres");
            campoTipo.requestFocus();
            return;
        }

        if (!ValidadorUI.esNumeroEntero(cantidad)) {
            DialogosUtil.mostrarError("Error", "La cantidad debe ser un número entero");
            campoCantidad.requestFocus();
            return;
        }

        int cantidadInt = ValidadorUI.toInteger(cantidad);
        if (!ValidadorUI.esNoNegativo(cantidadInt)) {
            DialogosUtil.mostrarError("Error", "La cantidad no puede ser negativa");
            campoCantidad.requestFocus();
            return;
        }

        // Delegar a GestionDesastres
        boolean agregado = gestionDesastres.agregarRecurso(administrador, tipo, cantidadInt, estado);

        if (agregado) {
            DialogosUtil.mostrarExito("Éxito", "Recurso agregado correctamente");
            limpiarCamposRecurso();
            cargarRecursos();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo agregar el recurso");
        }
    }

    /**
     * Actualiza la cantidad de un recurso existente
     */
    private void actualizarRecurso() {
        int fila = tablaRecursos.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione un recurso de la tabla");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        
        String nuevaCantidadStr = JOptionPane.showInputDialog(
            this,
            "Ingrese la nueva cantidad:",
            "Actualizar Cantidad",
            JOptionPane.QUESTION_MESSAGE
        );

        if (nuevaCantidadStr == null) return;

        if (!ValidadorUI.esNumeroEntero(nuevaCantidadStr)) {
            DialogosUtil.mostrarError("Error", "Debe ingresar un número entero válido");
            return;
        }

        int nuevaCantidad = ValidadorUI.toInteger(nuevaCantidadStr);
        if (!ValidadorUI.esNoNegativo(nuevaCantidad)) {
            DialogosUtil.mostrarError("Error", "La cantidad no puede ser negativa");
            return;
        }

        boolean actualizado = gestionDesastres.actualizarCantidadRecurso(administrador, id, nuevaCantidad);

        if (actualizado) {
            DialogosUtil.mostrarExito("Éxito", "Cantidad actualizada correctamente");
            cargarRecursos();
        } else {
            DialogosUtil.mostrarError("Error", "No se pudo actualizar el recurso");
        }
    }

    /**
     * Elimina un recurso
     */
    private void eliminarRecurso() {
        int fila = tablaRecursos.getSelectedRow();

        if (fila == -1) {
            DialogosUtil.mostrarAdvertencia("Seleccionar", "Seleccione un recurso para eliminar");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        String tipo = (String) modeloTabla.getValueAt(fila, 1);

        if (DialogosUtil.mostrarConfirmacion("Confirmar", "¿Eliminar recurso " + tipo + " (ID: " + id + ")?")) {
            // Usar el método de GestionRecursos a través de GestionDesastres
            boolean eliminado = gestionDesastres.getGestionRecursos().eliminarRecurso(id);
            
            if (eliminado) {
                DialogosUtil.mostrarExito("Éxito", "Recurso eliminado correctamente");
                cargarRecursos();
            } else {
                DialogosUtil.mostrarError("Error", "No se pudo eliminar el recurso");
            }
        }
    }

    /**
     * Carga los recursos en la tabla
     */
    private void cargarRecursos() {
        modeloTabla.setRowCount(0);
        List<Recurso> recursos = gestionDesastres.listarRecursos(administrador);

        for (Recurso recurso : recursos) {
            modeloTabla.addRow(new Object[]{
                recurso.getId(),
                recurso.getTipoRecurso(),
                FormatoUtil.formatoCantidad(recurso.getCantidadDisponible()),
                recurso.getEstado()
            });
        }
    }

    /**
     * Limpia los campos de entrada
     */
    private void limpiarCamposRecurso() {
        campoTipo.setText("");
        campoCantidad.limpiar();
        comboEstado.setSelectedIndex(0);
        campoTipo.requestFocus();
    }

    /**
     * Actualiza el panel
     */
    public void actualizar() {
        cargarRecursos();
    }
}