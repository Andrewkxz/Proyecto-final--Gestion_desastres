package co.edu.uniquindio.gestiondesastres;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestUtils {
    public static String copiarArchivosTest(String rutaOriginal, String nombreArchivoDestino){
        try{
            Path origen = Paths.get(rutaOriginal);
            Path destinoDir = Paths.get("target/test-data/");
            Path destino = destinoDir.resolve(nombreArchivoDestino);

            Files.createDirectories(destinoDir);
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

            return destino.toString();
        } catch (Exception e){
            throw new RuntimeException("Error al copiar archivo de prueba: " + rutaOriginal, e);
        }
    }
}
