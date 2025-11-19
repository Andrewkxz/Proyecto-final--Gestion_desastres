package co.edu.uniquindio.gestiondesastres.ui.Admin;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uniquindio.gestiondesastres.model.GrafoDirigido;
import co.edu.uniquindio.gestiondesastres.model.Vertice;
import co.edu.uniquindio.gestiondesastres.model.Arista;
import co.edu.uniquindio.gestiondesastres.model.Zona;

/**
 * Visualizador de grafo que muestra las zonas con sus niveles de riesgo
 * y las rutas entre ellas con representación gráfica mejorada
 */
public class VisualizadorGrafoZonas extends JFrame {

    private GrafoDirigido grafo;
    private List<Zona> zonas;
    private Map<String, Point> posicionesNodos;
    private Map<String, String> nivelesRiesgo;
    private PanelGrafico panelGrafico;

    /**
     * Constructor privado para uso interno
     */
    private VisualizadorGrafoZonas(GrafoDirigido grafo, List<Zona> zonas) {
        this.grafo = grafo;
        this.zonas = zonas;
        this.posicionesNodos = new HashMap<>();
        this.nivelesRiesgo = new HashMap<>();
        
        inicializar();
    }

    /**
     * Método estático para mostrar el visualizador
     */
    public static void mostrar(GrafoDirigido grafo, List<Zona> zonas) {
        SwingUtilities.invokeLater(() -> {
            VisualizadorGrafoZonas visualizador = new VisualizadorGrafoZonas(grafo, zonas);
            visualizador.setVisible(true);
        });
    }

    /**
     * Inicializa el visualizador
     */
    private void inicializar() {
        setTitle("Visualización del Grafo - Zonas y Rutas");
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout principal
        setLayout(new BorderLayout(10, 10));

        // Mapear zonas a sus niveles de riesgo
        mapearNivelesRiesgo();

        // Calcular posiciones de los nodos
        calcularPosiciones();

        // Crear panel gráfico
        panelGrafico = new PanelGrafico();
        panelGrafico.setBackground(Color.WHITE);
        add(panelGrafico, BorderLayout.CENTER);

        // Panel de información
        add(crearPanelInfo(), BorderLayout.SOUTH);

        // Panel superior con título
        add(crearPanelTitulo(), BorderLayout.NORTH);
    }

    /**
     * Crea el panel de título
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 100, 150));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Grafo de Rutas y Zonas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo);

        return panel;
    }

    /**
     * Mapea cada nodo a su nivel de riesgo
     */
    private void mapearNivelesRiesgo() {
        for (Zona zona : zonas) {
            nivelesRiesgo.put(zona.getNombre(), zona.getNivelRiesgo());
        }
    }

    /**
     * Calcula las posiciones de los nodos en forma circular
     */
    private void calcularPosiciones() {
        List<Vertice> vertices = grafo.getVertices();
        int numVertices = vertices.size();
        
        if (numVertices == 0) return;

        int centerX = 450;
        int centerY = 320;
        int radius = 220;

        for (int i = 0; i < numVertices; i++) {
            double angulo = 2 * Math.PI * i / numVertices - Math.PI / 2;
            int x = centerX + (int) (radius * Math.cos(angulo));
            int y = centerY + (int) (radius * Math.sin(angulo));
            posicionesNodos.put(vertices.get(i).getNombre(), new Point(x, y));
        }
    }

    /**
     * Crea el panel de información y leyenda
     */
    private JPanel crearPanelInfo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Panel de leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelLeyenda.setBackground(new Color(240, 240, 240));

        JLabel lblTituloLeyenda = new JLabel("Leyenda - Niveles de Riesgo:");
        lblTituloLeyenda.setFont(new Font("Arial", Font.BOLD, 12));
        panelLeyenda.add(lblTituloLeyenda);

        // Bajo
        panelLeyenda.add(crearEtiquetaLeyenda("Bajo", new Color(76, 175, 80)));
        
        // Medio
        panelLeyenda.add(crearEtiquetaLeyenda("Medio", new Color(255, 152, 0)));
        
        // Alto
        panelLeyenda.add(crearEtiquetaLeyenda("Alto", new Color(244, 67, 54)));
        
        // Crítico
        panelLeyenda.add(crearEtiquetaLeyenda("Crítico", new Color(139, 0, 0)));
        
        // Sin zona
        panelLeyenda.add(crearEtiquetaLeyenda("Sin Zona", Color.GRAY));

        panel.add(panelLeyenda, BorderLayout.CENTER);

        // Panel de estadísticas
        JPanel panelEstadisticas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEstadisticas.setBackground(new Color(240, 240, 240));
        
        int totalNodos = grafo.getVertices().size();
        int totalAristas = contarAristas();
        
        JLabel lblEstadisticas = new JLabel(
            String.format("Total de Nodos: %d  |  Total de Rutas: %d", totalNodos, totalAristas)
        );
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 11));
        panelEstadisticas.add(lblEstadisticas);

        panel.add(panelEstadisticas, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea una etiqueta de leyenda con color
     */
    private JPanel crearEtiquetaLeyenda(String texto, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(new Color(240, 240, 240));

        // Cuadro de color
        JPanel cuadroColor = new JPanel();
        cuadroColor.setBackground(color);
        cuadroColor.setPreferredSize(new Dimension(20, 20));
        cuadroColor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Arial", Font.PLAIN, 11));

        panel.add(cuadroColor);
        panel.add(lblTexto);

        return panel;
    }

    /**
     * Cuenta el número total de aristas en el grafo
     */
    private int contarAristas() {
        int contador = 0;
        for (Vertice vertice : grafo.getVertices()) {
            contador += vertice.getAdyacentes().size();
        }
        return contador;
    }

    /**
     * Obtiene el color según el nivel de riesgo
     */
    private Color obtenerColorRiesgo(String nombreNodo) {
        String riesgo = nivelesRiesgo.get(nombreNodo);
        
        if (riesgo == null) {
            return Color.GRAY;
        }

        switch (riesgo) {
            case "Bajo":
                return new Color(76, 175, 80);
            case "Medio":
                return new Color(255, 152, 0);
            case "Alto":
                return new Color(244, 67, 54);
            case "Crítico":
                return new Color(139, 0, 0);
            default:
                return Color.GRAY;
        }
    }

    /**
     * Panel personalizado para dibujar el grafo
     */
    private class PanelGrafico extends JPanel {
        
        public PanelGrafico() {
            setPreferredSize(new Dimension(900, 600));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Activar antialiasing para mejor calidad
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // Dibujar aristas primero (para que queden debajo de los nodos)
            dibujarAristas(g2d);

            // Dibujar nodos encima
            dibujarNodos(g2d);
        }

        /**
         * Dibuja las aristas del grafo con flechas y pesos
         */
        private void dibujarAristas(Graphics2D g2d) {
            g2d.setStroke(new BasicStroke(2.5f));

            for (Vertice vertice : grafo.getVertices()) {
                Point posOrigen = posicionesNodos.get(vertice.getNombre());
                if (posOrigen == null) continue;

                for (Arista arista : vertice.getAdyacentes()) {
                    Point posDestino = posicionesNodos.get(arista.getDestino().getNombre());
                    if (posDestino == null) continue;

                    // Dibujar línea de la arista
                    g2d.setColor(new Color(80, 80, 80));
                    g2d.drawLine(posOrigen.x, posOrigen.y, posDestino.x, posDestino.y);

                    // Dibujar flecha direccional
                    dibujarFlecha(g2d, posOrigen, posDestino);

                    // Dibujar peso de la arista (distancia)
                    dibujarPesoArista(g2d, posOrigen, posDestino, arista.getPeso());
                }
            }
        }

        /**
         * Dibuja una flecha en la dirección de la arista
         */
        private void dibujarFlecha(Graphics2D g2d, Point inicio, Point fin) {
            double dx = fin.x - inicio.x;
            double dy = fin.y - inicio.y;
            double angulo = Math.atan2(dy, dx);

            // Calcular punto de la flecha (cerca del nodo destino)
            int longitudFlecha = 35;
            int xFlecha = fin.x - (int) (longitudFlecha * Math.cos(angulo));
            int yFlecha = fin.y - (int) (longitudFlecha * Math.sin(angulo));

            // Dibujar triángulo de la flecha
            int tamanoFlecha = 10;
            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            xPoints[0] = fin.x - (int) (42 * Math.cos(angulo));
            yPoints[0] = fin.y - (int) (42 * Math.sin(angulo));

            xPoints[1] = xFlecha - (int) (tamanoFlecha * Math.cos(angulo - Math.PI / 6));
            yPoints[1] = yFlecha - (int) (tamanoFlecha * Math.sin(angulo - Math.PI / 6));

            xPoints[2] = xFlecha - (int) (tamanoFlecha * Math.cos(angulo + Math.PI / 6));
            yPoints[2] = yFlecha - (int) (tamanoFlecha * Math.sin(angulo + Math.PI / 6));

            g2d.setColor(new Color(60, 60, 60));
            g2d.fillPolygon(xPoints, yPoints, 3);
        }

        /**
         * Dibuja el peso (distancia) de una arista
         */
        private void dibujarPesoArista(Graphics2D g2d, Point inicio, Point fin, double peso) {
            int midX = (inicio.x + fin.x) / 2;
            int midY = (inicio.y + fin.y) / 2;
            
            String textoPeso = String.format("%.1f km", peso);
            
            // Fondo blanco para el texto
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(textoPeso);
            int textHeight = fm.getHeight();
            
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(midX - textWidth / 2 - 4, midY - textHeight / 2 - 2, 
                             textWidth + 8, textHeight + 4, 5, 5);
            
            // Borde del fondo
            g2d.setColor(new Color(100, 100, 100));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRoundRect(midX - textWidth / 2 - 4, midY - textHeight / 2 - 2, 
                             textWidth + 8, textHeight + 4, 5, 5);
            
            // Texto del peso
            g2d.setColor(Color.BLACK);
            g2d.drawString(textoPeso, midX - textWidth / 2, midY + 4);
        }

        /**
         * Dibuja los nodos del grafo con sus etiquetas
         */
        private void dibujarNodos(Graphics2D g2d) {
            final int RADIO_NODO = 38;

            for (Vertice vertice : grafo.getVertices()) {
                Point pos = posicionesNodos.get(vertice.getNombre());
                if (pos == null) continue;

                // Dibujar sombra del nodo
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillOval(pos.x - RADIO_NODO + 3, pos.y - RADIO_NODO + 3, 
                            RADIO_NODO * 2, RADIO_NODO * 2);

                // Dibujar círculo del nodo con color según riesgo
                Color colorNodo = obtenerColorRiesgo(vertice.getNombre());
                g2d.setColor(colorNodo);
                g2d.fillOval(pos.x - RADIO_NODO, pos.y - RADIO_NODO, 
                            RADIO_NODO * 2, RADIO_NODO * 2);

                // Dibujar borde del nodo
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawOval(pos.x - RADIO_NODO, pos.y - RADIO_NODO, 
                            RADIO_NODO * 2, RADIO_NODO * 2);

                // Dibujar nombre del nodo
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g2d.getFontMetrics();
                String nombre = vertice.getNombre();
                
                // Truncar nombre si es muy largo
                if (nombre.length() > 10) {
                    nombre = nombre.substring(0, 9) + "...";
                }
                
                int textWidth = fm.stringWidth(nombre);
                g2d.drawString(nombre, pos.x - textWidth / 2, pos.y - 6);

                // Dibujar nivel de riesgo debajo del nombre
                String riesgo = nivelesRiesgo.get(vertice.getNombre());
                if (riesgo != null) {
                    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                    fm = g2d.getFontMetrics();
                    textWidth = fm.stringWidth(riesgo);
                    g2d.drawString(riesgo, pos.x - textWidth / 2, pos.y + 8);
                } else {
                    g2d.setFont(new Font("Arial", Font.ITALIC, 9));
                    String sinZona = "Sin zona";
                    fm = g2d.getFontMetrics();
                    textWidth = fm.stringWidth(sinZona);
                    g2d.drawString(sinZona, pos.x - textWidth / 2, pos.y + 8);
                }
            }
        }
    }
}