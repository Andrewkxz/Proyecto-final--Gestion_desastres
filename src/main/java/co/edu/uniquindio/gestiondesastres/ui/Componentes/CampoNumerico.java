package co.edu.uniquindio.gestiondesastres.ui.Componentes;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * JTextField personalizado que solo acepta números
 * Rechaza caracteres no numéricos y números negativos (configurable)
 */
public class CampoNumerico extends JTextField {

    private boolean permitirDecimales;
    private boolean permitirNegativos;
    private int limiteCaracteres;

    /**
     * Constructor para campo numérico de enteros sin negativos
     */
    public CampoNumerico() {
        this(false, false, 10);
    }

    /**
     * Constructor completo
     * @param permitirDecimales true para permitir números decimales
     * @param permitirNegativos true para permitir números negativos
     * @param limiteCaracteres número máximo de caracteres
     */
    public CampoNumerico(boolean permitirDecimales, boolean permitirNegativos, int limiteCaracteres) {
        super();
        this.permitirDecimales = permitirDecimales;
        this.permitirNegativos = permitirNegativos;
        this.limiteCaracteres = limiteCaracteres;

        configurarFiltro();
        configurarAspecto();
    }

    /**
     * Configura el filtro de documento para validar entrada
     */
    private void configurarFiltro() {
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) 
                    throws BadLocationException {
                if (str == null) return;

                // Validar cada carácter
                for (char c : str.toCharArray()) {
                    if (!esCaracterValido(c, offset, fb.getDocument().getLength())) {
                        return;
                    }
                }

                // Validar límite de caracteres
                if (fb.getDocument().getLength() + str.length() > limiteCaracteres) {
                    return;
                }

                super.insertString(fb, offset, str, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attr) 
                    throws BadLocationException {
                if (str == null) return;

                // Validar cada carácter
                for (char c : str.toCharArray()) {
                    if (!esCaracterValido(c, offset, fb.getDocument().getLength())) {
                        return;
                    }
                }

                super.replace(fb, offset, length, str, attr);
            }

            private boolean esCaracterValido(char c, int posicion, int longitudActual) {
                // Números son siempre válidos
                if (Character.isDigit(c)) {
                    return true;
                }

                // Punto decimal
                if (permitirDecimales && c == '.' && getText().indexOf('.') == -1) {
                    return posicion > 0; // No permitir punto al inicio
                }

                // Signo negativo
                if (permitirNegativos && c == '-' && posicion == 0) {
                    return getText().isEmpty(); // Solo al inicio
                }

                return false;
            }
        });
        this.setDocument(doc);
    }

    /**
     * Configura el aspecto visual del campo
     */
    private void configurarAspecto() {
        this.setFont(new Font("Arial", Font.PLAIN, 12));
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setPreferredSize(new Dimension(100, 25));
    }

    /**
     * Obtiene el valor como entero
     * Retorna 0 si el campo está vacío
     */
    public int getValorInt() {
        String texto = this.getText().trim();
        if (texto.isEmpty()) return 0;
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Obtiene el valor como double
     * Retorna 0.0 si el campo está vacío
     */
    public double getValorDouble() {
        String texto = this.getText().trim();
        if (texto.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Establece el valor como entero
     */
    public void setValor(int valor) {
        this.setText(String.valueOf(valor));
    }

    /**
     * Establece el valor como double
     */
    public void setValor(double valor) {
        this.setText(String.valueOf(valor));
    }

    /**
     * Valida que el valor esté en un rango
     */
    public boolean estaEnRango(int minimo, int maximo) {
        int valor = getValorInt();
        return valor >= minimo && valor <= maximo;
    }

    /**
     * Valida que el valor sea no negativo (>= 0)
     */
    public boolean esNoNegativo() {
        return getValorInt() >= 0;
    }

    /**
     * Valida que el valor sea positivo (> 0)
     */
    public boolean esPositivo() {
        return getValorDouble() > 0;
    }

    /**
     * Limpia el campo
     */
    public void limpiar() {
        this.setText("");
    }
}