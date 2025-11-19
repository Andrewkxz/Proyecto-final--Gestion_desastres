package co.edu.uniquindio.gestiondesastres;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.gestiondesastres.gestores.GestionZonas;
import co.edu.uniquindio.gestiondesastres.model.Zona;

public class GestionarZonasTest {
    private GestionZonas gestionZonas;

    @Before
    public void setup(){
        String copia = TestUtils.copiarArchivosTest(
            "src/main/java/co/edu/uniquindio/gestiondesastres/datos/zonas.csv",
            "zonas_test_copia.csv"
        );

        gestionZonas = new GestionZonas(copia);
    }

    @Test
    public void testAgregarZona(){
        boolean exito = gestionZonas.agregarZona("ZonaTest", "Urbana", "Alto");
        assertTrue(exito);

        Zona z = gestionZonas.buscarZona("ZonaTest");
        assertTrue("La zona debe existir después de agregarla", z != null);
    }

    @Test
    public void testEliminarZona(){
        gestionZonas.agregarZona("ZonaEliminar", "Rural", "Medio");
        boolean exito = gestionZonas.eliminarZona("ZonaEliminar");
        assertTrue(exito);

        assertNull(gestionZonas.buscarZona("ZonaEliminar"));
    }
}
