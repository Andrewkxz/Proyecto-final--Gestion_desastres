package co.edu.uniquindio.gestiondesastres.ui.Util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Clase utilitaria para formatear datos para presentación en UI
 * Proporciona métodos estáticos para formatear números, fechas, monedas, etc.
 */
public class FormatoUtil {

    private static final DecimalFormat df2 = new DecimalFormat("#.##");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Formatea un número decimal a 2 cifras significativas
     * Ejemplo: 3.14159 → "3.14"
     * @param valor Número a formatear
     * @return Número formateado como String
     */
    public static String formatoDecimal(double valor) {
        return df2.format(valor);
    }

    /**
     * Formatea un número como moneda (sin símbolo)
     * Ejemplo: 1234.56 → "1,234.56"
     * @param valor Número a formatear
     * @return Número formateado como String
     */
    public static String formatoMoneda(double valor) {
        DecimalFormat moneda = new DecimalFormat("#,##0.00");
        return moneda.format(valor);
    }

    /**
     * Formatea distancia en km
     * Ejemplo: 5.5 → "5.5 km"
     * @param distancia Distancia a formatear
     * @return Distancia formateada con unidad
     */
    public static String formatoDistancia(double distancia) {
        return formatoDecimal(distancia) + " km";
    }

    /**
     * Formatea cantidad con separadores de miles
     * Ejemplo: 1234567 → "1,234,567"
     * @param cantidad Cantidad a formatear
     * @return Cantidad formateada como String
     */
    public static String formatoCantidad(int cantidad) {
        return String.format("%,d", cantidad);
    }

    /**
     * Formatea la fecha y hora actual
     * Formato: dd/MM/yyyy HH:mm
     * @param fecha Fecha a formatear
     * @return Fecha y hora formateadas
     */
    public static String formatoFechaHora(LocalDateTime fecha) {
        return fecha.format(dtf);
    }

    /**
     * Formatea una fecha
     * Formato: dd/MM/yyyy
     * @param fecha Fecha a formatear
     * @return Fecha formateada
     */
    public static String formatoFecha(Date fecha) {
        return sdf.format(fecha);
    }

    /**
     * Capitaliza la primera letra de un String
     * Ejemplo: "hOLA" → "Hola"
     * @param texto Texto a capitalizar
     * @return Texto con primera letra capitalizada
     */
    public static String capitalizarPrimera(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return texto.charAt(0) + texto.substring(1).toLowerCase();
    }

    /**
     * Convierte String a mayúsculas para visualización
     * @param texto Texto a convertir
     * @return Texto en mayúsculas
     */
    public static String aMayusculas(String texto) {
        return texto != null ? texto.toUpperCase() : "";
    }

    /**
     * Convierte String a minúsculas
     * @param texto Texto a convertir
     * @return Texto en minúsculas
     */
    public static String aMinusculas(String texto) {
        return texto != null ? texto.toLowerCase() : "";
    }

    /**
     * Trunca un String a una longitud máxima
     * Ejemplo: "Hola mundo muy largo" (10) → "Hola mun..."
     * @param texto Texto a truncar
     * @param longitud Longitud máxima
     * @return Texto truncado
     */
    public static String truncar(String texto, int longitud) {
        if (texto == null) return "";
        if (texto.length() <= longitud) return texto;
        return texto.substring(0, longitud) + "...";
    }

    /**
     * Formatea información de recurso para mostrar en tabla
     * @param id ID del recurso
     * @param tipo Tipo de recurso
     * @param cantidad Cantidad disponible
     * @param estado Estado del recurso
     * @return String formateado
     */
    public static String formatoRecurso(String id, String tipo, int cantidad, String estado) {
        return String.format("%s | %s | Cantidad: %s | Estado: %s",
            id, tipo, formatoCantidad(cantidad), estado);
    }

    /**
     * Formatea información de zona para mostrar en tabla
     * @param nombre Nombre de la zona
     * @param tipo Tipo de zona
     * @param riesgo Nivel de riesgo
     * @return String formateado
     */
    public static String formatoZona(String nombre, String tipo, String riesgo) {
        return String.format("%s - Tipo: %s - Riesgo: %s",
            nombre, tipo, riesgo);
    }

    /**
     * Formatea información de ruta para mostrar en tabla
     * @param origen Ubicación de origen
     * @param destino Ubicación de destino
     * @param distancia Distancia en km
     * @return String formateado (Origen → Destino (X km))
     */
    public static String formatoRuta(String origen, String destino, double distancia) {
        return String.format("%s → %s (%s)",
            origen, destino, formatoDistancia(distancia));
    }

    /**
     * Limpia espacios en blanco excesivos
     * Ejemplo: "Hola  mundo   aquí" → "Hola mundo aquí"
     * @param texto Texto a limpiar
     * @return Texto limpio
     */
    public static String limpiarEspacios(String texto) {
        if (texto == null) return "";
        return texto.trim().replaceAll("\\s+", " ");
    }

    /**
     * Convierte porcentaje a String con símbolo
     * Ejemplo: 75.5 → "75.50%"
     * @param valor Valor del porcentaje (0-100)
     * @return Porcentaje formateado
     */
    public static String formatoPorcentaje(double valor) {
        return String.format("%.2f%%", valor);
    }

    /**
     * Formatea un número largo como número de archivo/ID
     * Ejemplo: 123456789 → "123,456,789"
     * @param numero Número a formatear
     * @return Número formateado
     */
    public static String formatoNumeroGrande(long numero) {
        return String.format("%,d", numero);
    }

    /**
     * Convierte booleano a texto legible
     * Ejemplo: true → "Sí", false → "No"
     * @param valor Valor booleano
     * @return "Sí" o "No"
     */
    public static String formatoBooleano(boolean valor) {
        return valor ? "Sí" : "No";
    }

    /**
     * Formatea duración en minutos:segundos
     * Ejemplo: 125 segundos → "02:05"
     * @param segundos Duración en segundos
     * @return Duración formateada MM:SS
     */
    public static String formatoDuracion(int segundos) {
        int minutos = segundos / 60;
        int secs = segundos % 60;
        return String.format("%02d:%02d", minutos, secs);
    }

    /**
     * Formatea un número como número de teléfono (formato básico)
     * Ejemplo: 3001234567 → "300-123-4567"
     * @param telefono Número de teléfono
     * @return Teléfono formateado
     */
    public static String formatoTelefono(String telefono) {
        if (telefono == null || telefono.length() != 10) return telefono;
        return telefono.substring(0, 3) + "-" + 
               telefono.substring(3, 6) + "-" + 
               telefono.substring(6);
    }

    /**
     * Convierte bytes a formato legible (KB, MB, GB)
     * Ejemplo: 1048576 bytes → "1.00 MB"
     * @param bytes Tamaño en bytes
     * @return Tamaño legible
     */
    public static String formatoTamano(long bytes) {
        if (bytes <= 0) return "0 B";
        final String[] unidades = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.2f %s", 
            bytes / Math.pow(1024, digitGroups), 
            unidades[digitGroups]);
    }

    /**
     * Formatea una dirección para mostrar en una línea
     * @param calle Nombre de la calle
     * @param numero Número de la calle
     * @param ciudad Ciudad
     * @return Dirección formateada
     */
    public static String formatoDireccion(String calle, String numero, String ciudad) {
        return String.format("%s %s, %s", calle, numero, ciudad);
    }

    /**
     * Formatea un código de estado con descripción
     * @param codigo Código del estado
     * @param descripcion Descripción del estado
     * @return Código y descripción formateados
     */
    public static String formatoCodigo(String codigo, String descripcion) {
        return String.format("[%s] - %s", codigo, descripcion);
    }

    /**
     * Convierte número a letra (para valores pequeños)
     * Ejemplo: 1 → "Uno", 2 → "Dos"
     * @param numero Número (1-10)
     * @return Número en letras
     */
    public static String numeroALetra(int numero) {
        switch(numero) {
            case 1: return "Uno";
            case 2: return "Dos";
            case 3: return "Tres";
            case 4: return "Cuatro";
            case 5: return "Cinco";
            case 6: return "Seis";
            case 7: return "Siete";
            case 8: return "Ocho";
            case 9: return "Nueve";
            case 10: return "Diez";
            default: return String.valueOf(numero);
        }
    }

    /**
     * Formatea un nombre completo capitalizando correctamente
     * Ejemplo: "juan perez" → "Juan Perez"
     * @param nombre Nombre completo
     * @return Nombre formateado
     */
    public static String formatoNombreCompleto(String nombre) {
        if (nombre == null || nombre.isEmpty()) return "";
        String[] palabras = nombre.trim().split(" ");
        StringBuilder resultado = new StringBuilder();
        
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return resultado.toString().trim();
    }

    /**
     * Formatea un nivel de riesgo con color (representado por emoji)
     * @param nivel Nivel de riesgo
     * @return Nivel con representación visual
     */
    public static String formatoNivelRiesgo(String nivel) {
        switch(nivel.toLowerCase()) {
            case "bajo": return "🟢 Bajo";
            case "medio": return "🟡 Medio";
            case "alto": return "🔴 Alto";
            case "crítico": return "⚫ Crítico";
            default: return nivel;
        }
    }

    /**
     * Formatea estado de recurso con representación
     * @param estado Estado del recurso
     * @return Estado formateado
     */
    public static String formatoEstadoRecurso(String estado) {
        switch(estado.toLowerCase()) {
            case "disponible": return "✓ Disponible";
            case "dañado": return "✗ Dañado";
            case "en mantenimiento": return "⚙ En Mantenimiento";
            case "operativo": return "▶ Operativo";
            case "agotado": return "○ Agotado";
            default: return estado;
        }
    }

    /**
     * Formatea un rango de números
     * Ejemplo: 1, 100 → "1 - 100"
     * @param minimo Valor mínimo
     * @param maximo Valor máximo
     * @return Rango formateado
     */
    public static String formatoRango(int minimo, int maximo) {
        return String.format("%d - %d", minimo, maximo);
    }

    /**
     * Formatea un hora en formato 12 horas
     * @param hora Hora (0-23)
     * @param minuto Minuto (0-59)
     * @return Hora formateada (ej: 02:30 PM)
     */
    public static String formatoHora12(int hora, int minuto) {
        String ampm = hora >= 12 ? "PM" : "AM";
        int hora12 = hora % 12;
        if (hora12 == 0) hora12 = 12;
        return String.format("%02d:%02d %s", hora12, minuto, ampm);
    }
}