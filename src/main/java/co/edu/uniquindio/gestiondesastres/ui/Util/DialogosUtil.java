package co.edu.uniquindio.gestiondesastres.ui.Util;

import javax.swing.*;

/**
 * Clase utilitaria para mostrar diálogos reutilizables
 * Proporciona métodos estáticos para mostrar diferentes tipos de mensajes y confirmaciones
 */
public class DialogosUtil {

    /**
     * Muestra diálogo de éxito
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarExito(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra diálogo de error
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Muestra diálogo de advertencia
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarAdvertencia(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Muestra diálogo de confirmación
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @return true si el usuario selecciona "Sí", false si selecciona "No"
     */
    public static boolean mostrarConfirmacion(String titulo, String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return opcion == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra diálogo para entrada de texto
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @return El texto ingresado por el usuario, o null si cancela
     */
    public static String mostrarEntrada(String titulo, String mensaje) {
        return JOptionPane.showInputDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Muestra diálogo para entrada de número entero
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @return El número ingresado, o -1 si cancela o el valor es inválido
     */
    public static int mostrarEntradaNumero(String titulo, String mensaje) {
        String input = mostrarEntrada(titulo, mensaje);
        if (input == null) return -1;
        
        if (!ValidadorUI.esNumeroEntero(input)) {
            mostrarError("Error", "Debe ingresarse un número entero válido");
            return -1;
        }
        return ValidadorUI.toInteger(input);
    }

    /**
     * Muestra diálogo para entrada de número decimal
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @return El número ingresado, o -1.0 si cancela o el valor es inválido
     */
    public static double mostrarEntradaDecimal(String titulo, String mensaje) {
        String input = mostrarEntrada(titulo, mensaje);
        if (input == null) return -1.0;
        
        if (!ValidadorUI.esNumeroDecimal(input)) {
            mostrarError("Error", "Debe ingresarse un número decimal válido");
            return -1.0;
        }
        return ValidadorUI.toDouble(input);
    }

    /**
     * Muestra diálogo para seleccionar de una lista
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @param opciones Array de opciones a mostrar
     * @return El elemento seleccionado, o null si cancela
     */
    public static Object mostrarSeleccion(String titulo, String mensaje, Object[] opciones) {
        return JOptionPane.showInputDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
    }

    /**
     * Valida y muestra error si el campo de texto es inválido
     * @param campo Nombre del campo
     * @param valor Valor a validar
     * @param minimo Longitud mínima
     * @param maximo Longitud máxima
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarCampoTexto(String campo, String valor, int minimo, int maximo) {
        if (!ValidadorUI.esValido(valor)) {
            mostrarError("Campo Vacío", "El campo " + campo + " no puede estar vacío");
            return false;
        }
        if (!ValidadorUI.longitudValida(valor, minimo, maximo)) {
            mostrarError("Longitud Inválida", 
                "El campo " + campo + " debe tener entre " + minimo + " y " + maximo + " caracteres");
            return false;
        }
        return true;
    }

    /**
     * Valida y muestra error si el campo numérico es inválido
     * @param campo Nombre del campo
     * @param valor Valor a validar
     * @param minimo Valor mínimo permitido
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarCampoNumerico(String campo, String valor, int minimo) {
        if (!ValidadorUI.esNumeroEntero(valor)) {
            mostrarError("Formato Inválido", "El campo " + campo + " debe ser un número entero");
            return false;
        }
        int numero = ValidadorUI.toInteger(valor);
        if (numero < minimo) {
            mostrarError("Valor Inválido", 
                "El campo " + campo + " debe ser mayor o igual a " + minimo);
            return false;
        }
        return true;
    }

    /**
     * Valida y muestra error si el campo decimal es inválido
     * @param campo Nombre del campo
     * @param valor Valor a validar
     * @param minimo Valor mínimo permitido
     * @return true si es válido, false en caso contrario
     */
    public static boolean validarCampoDecimal(String campo, String valor, double minimo) {
        if (!ValidadorUI.esNumeroDecimal(valor)) {
            mostrarError("Formato Inválido", "El campo " + campo + " debe ser un número decimal");
            return false;
        }
        double numero = ValidadorUI.toDouble(valor);
        if (numero < minimo) {
            mostrarError("Valor Inválido", 
                "El campo " + campo + " debe ser mayor que " + minimo);
            return false;
        }
        return true;
    }

    /**
     * Muestra un diálogo con información
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarInformacion(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra diálogo con opciones Sí/No/Cancelar
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @return 0 para Sí, 1 para No, 2 para Cancelar
     */
    public static int mostrarConfirmacionExtendida(String titulo, String mensaje) {
        return JOptionPane.showConfirmDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }

    /**
     * Muestra un área de texto grande (para mensajes largos)
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarMensajeLargo(String titulo, String mensaje) {
        JTextArea textArea = new JTextArea(10, 40);
        textArea.setText(mensaje);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JOptionPane.showMessageDialog(
            null,
            scrollPane,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra diálogo de espera/carga
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     */
    public static void mostrarCargando(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra un diálogo con lista seleccionable
     * @param titulo Título del diálogo
     * @param mensaje Mensaje a mostrar
     * @param items Elementos de la lista
     * @return Índice del elemento seleccionado, o -1 si cancela
     */
    public static int mostrarSeleccionLista(String titulo, String mensaje, String[] items) {
        Object seleccion = JOptionPane.showInputDialog(
            null,
            mensaje,
            titulo,
            JOptionPane.QUESTION_MESSAGE,
            null,
            items,
            items[0]
        );
        
        if (seleccion == null) return -1;
        
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(seleccion)) {
                return i;
            }
        }
        return -1;
    }
}