package co.edu.uniquindio.gestiondesastres.ui.Util;

public class ValidadorUI {

    /**
     * Valida que un String no sea nulo ni vacío
     */
    public static boolean esValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Valida que un String sea un número entero válido
     */
    public static boolean esNumeroEntero(String texto) {
        if (!esValido(texto)) return false;
        try {
            Integer.parseInt(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que un String sea un número decimal válido
     */
    public static boolean esNumeroDecimal(String texto) {
        if (!esValido(texto)) return false;
        try {
            Double.parseDouble(texto.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que un número sea no negativo
     */
    public static boolean esNoNegativo(int valor) {
        return valor >= 0;
    }

    /**
     * Valida que un número decimal sea positivo (> 0)
     */
    public static boolean esPositivo(double valor) {
        return valor > 0;
    }

    /**
     * Valida que un número sea no negativo
     */
    public static boolean esNoNegativoDecimal(double valor) {
        return valor >= 0;
    }

    /**
     * Convierte String a entero de forma segura
     * Retorna -1 si la conversión falla
     */
    public static int toInteger(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Convierte String a double de forma segura
     * Retorna -1.0 si la conversión falla
     */
    public static double toDouble(String texto) {
        try {
            return Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }

    /**
     * Valida un String que debe estar en un rango de caracteres
     */
    public static boolean longitudValida(String texto, int minimo, int maximo) {
        if (!esValido(texto)) return false;
        int longitud = texto.trim().length();
        return longitud >= minimo && longitud <= maximo;
    }

    /**
     * Valida que un String contenga solo letras, números y espacios
     */
    public static boolean contieneSoloAlfanumerico(String texto) {
        if (!esValido(texto)) return false;
        return texto.trim().matches("^[a-zA-Z0-9\\s]+$");
    }

    /**
     * Valida que una opción esté seleccionada (no -1)
     */
    public static boolean esSeleccionadoCombo(int indice) {
        return indice != -1;
    }
}