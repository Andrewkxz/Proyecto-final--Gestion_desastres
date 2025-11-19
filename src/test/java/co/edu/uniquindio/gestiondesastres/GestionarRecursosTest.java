package co.edu.uniquindio.gestiondesastres;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.gestiondesastres.gestores.GestionRecursos;
import co.edu.uniquindio.gestiondesastres.gestores.GestionZonas;
import co.edu.uniquindio.gestiondesastres.model.Zona;

public class GestionarRecursosTest {
    private GestionRecursos gestionRecursos;
    private GestionZonas gestionZonas;

    @Before
    public void setup(){
        String copiaRecursos = TestUtils.copiarArchivosTest(
            "src/main/java/co/edu/uniquindio/gestiondesastres/datos/recursos.csv",
            "recursos_test_copia.csv"
        );

        String copiaZonas = TestUtils.copiarArchivosTest(
            "src/main/java/co/edu/uniquindio/gestiondesastres/datos/zonas.csv",
            "zonas_test_copia.csv"
        );

        gestionRecursos = new GestionRecursos(copiaRecursos);
        gestionZonas = new GestionZonas(copiaZonas);
    }

    @Test
    public void testAsignarRecursoAZona(){
        boolean zona = gestionZonas.agregarZona("Zona1", "Urbana", "Alto");
        assertTrue(zona);
        Zona zonaB = gestionZonas.buscarZona("Zona1");
        assertNotNull(zonaB);

        boolean exito = gestionRecursos.asignarRecursoAZona("R1", zonaB);
        assertTrue("Debe permitir asignar recurso a zona", exito);
    }
}
