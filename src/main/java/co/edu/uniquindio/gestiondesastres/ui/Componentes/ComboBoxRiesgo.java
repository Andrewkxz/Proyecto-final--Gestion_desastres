package co.edu.uniquindio.gestiondesastres.ui.Componentes;

import javax.swing.*;
import java.awt.*;

/**
 * ComboBox personalizado para seleccionar niveles de riesgo
 * Proporciona opciones predefinidas: Bajo, Medio, Alto, Crítico
 */
public class ComboBoxRiesgo extends JComboBox<String> {

    private static final String[] NIVELES_RIESGO = {
        "Bajo",
        "Medio",
        "Alto",
        "Crítico"
    };

    /**
     * Constructor por defecto
     * Crea un ComboBox con los niveles de riesgo predefinidos
     */
    public ComboBoxRiesgo() {
        super(NIVELES_RIESGO);
        configurarAspecto();
    }

    /**
     * Configura el aspecto visual del ComboBox
     */
    private void configurarAspecto() {
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setPreferredSize(new Dimension(150, 25));
        this.setSelectedIndex(0); // Selecciona "Bajo" por defecto
    }

    /**
     * Obtiene el nivel de riesgo seleccionado
     * @return El nivel de riesgo seleccionado ("Bajo", "Medio", "Alto", "Crítico")
     */
    public String getNivelRiesgo() {
        Object seleccionado = this.getSelectedItem();
        return seleccionado != null ? (String) seleccionado : "Bajo";
    }

    /**
     * Establece el nivel de riesgo a mostrar
     * @param nivel El nivel de riesgo a establecer
     */
    public void setNivelRiesgo(String nivel) {
        if (nivel != null) {
            for (int i = 0; i < NIVELES_RIESGO.length; i++) {
                if (NIVELES_RIESGO[i].equals(nivel)) {
                    this.setSelectedIndex(i);
                    return;
                }
            }
        }
        // Si no encuentra coincidencia, selecciona "Bajo" por defecto
        this.setSelectedIndex(0);
    }

    /**
     * Valida que haya una selección válida
     * @return true si hay un nivel seleccionado, false en caso contrario
     */
    public boolean esValido() {
        return this.getSelectedIndex() != -1;
    }

    /**
     * Obtiene el índice del nivel de riesgo seleccionado
     * @return El índice (0=Bajo, 1=Medio, 2=Alto, 3=Crítico)
     */
    public int getIndiceRiesgo() {
        return this.getSelectedIndex();
    }

    /**
     * Obtiene todos los niveles de riesgo disponibles
     * @return Array con los niveles disponibles
     */
    public static String[] getNiveles() {
        return NIVELES_RIESGO.clone();
    }

    /**
     * Obtiene el nivel de riesgo por índice
     * @param indice El índice del nivel
     * @return El nivel de riesgo en ese índice
     */
    public static String getNivelPorIndice(int indice) {
        if (indice >= 0 && indice < NIVELES_RIESGO.length) {
            return NIVELES_RIESGO[indice];
        }
        return "Bajo";
    }

    /**
     * Verifica si un nivel es válido
     * @param nivel El nivel a verificar
     * @return true si el nivel existe en la lista, false en caso contrario
     */
    public static boolean esNivelValido(String nivel) {
        if (nivel == null) return false;
        for (String nivelDisponible : NIVELES_RIESGO) {
            if (nivelDisponible.equals(nivel)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la cantidad total de niveles disponibles
     * @return La cantidad de niveles
     */
    public static int getCantidadNiveles() {
        return NIVELES_RIESGO.length;
    }

    /**
     * Reinicia el ComboBox al valor por defecto (Bajo)
     */
    public void reiniciar() {
        this.setSelectedIndex(0);
    }

    /**
     * Obtiene una descripción del nivel de riesgo actual
     * @return Una descripción legible del nivel
     */
    public String obtenerDescripcion() {
        String nivel = getNivelRiesgo();
        switch(nivel) {
            case "Bajo":
                return "Riesgo Bajo - Situación controlada";
            case "Medio":
                return "Riesgo Medio - Requiere atención";
            case "Alto":
                return "Riesgo Alto - Requiere acción inmediata";
            case "Crítico":
                return "Riesgo Crítico - Emergencia en progreso";
            default:
                return "Nivel desconocido";
        }
    }

    /**
     * Obtiene el color representativo del nivel de riesgo
     * @return El color correspondiente al nivel
     */
    public Color obtenerColor() {
        String nivel = getNivelRiesgo();
        switch(nivel) {
            case "Bajo":
                return new Color(0, 128, 0); // Verde
            case "Medio":
                return new Color(255, 165, 0); // Naranja
            case "Alto":
                return new Color(255, 0, 0); // Rojo
            case "Crítico":
                return new Color(128, 0, 0); // Rojo oscuro
            default:
                return Color.BLACK;
        }
    }

    /**
     * Obtiene el número de prioridad del nivel (1=Bajo, 4=Crítico)
     * @return El número de prioridad
     */
    public int obtenerPrioridad() {
        String nivel = getNivelRiesgo();
        switch(nivel) {
            case "Bajo":
                return 1;
            case "Medio":
                return 2;
            case "Alto":
                return 3;
            case "Crítico":
                return 4;
            default:
                return 0;
        }
    }

    /**
     * Compara dos niveles de riesgo
     * @param otro Otro nivel para comparar
     * @return -1 si este es menor, 0 si son iguales, 1 si este es mayor
     */
    public int compararCon(String otro) {
        int prioridad1 = obtenerPrioridad();
        int prioridad2 = 0;
        
        switch(otro) {
            case "Bajo": prioridad2 = 1; break;
            case "Medio": prioridad2 = 2; break;
            case "Alto": prioridad2 = 3; break;
            case "Crítico": prioridad2 = 4; break;
        }
        
        return Integer.compare(prioridad1, prioridad2);
    }

    /**
     * Obtiene un emoji representativo del nivel
     * @return El emoji del nivel de riesgo
     */
    public String obtenerEmoji() {
        String nivel = getNivelRiesgo();
        switch(nivel) {
            case "Bajo":
                return "verde";
            case "Medio":
                return "amarillo";
            case "Alto":
                return "rojo";
            case "Crítico":
                return "negro";
            default:
                return "null";
        }
    }
}