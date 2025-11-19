package co.edu.uniquindio.gestiondesastres;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Administrador;

public class AdministradorTest {
    private Administrador administrador;
    private GestionDesastres gestionDesastres;

    @Before
    public void setup(){
        gestionDesastres = GestionDesastres.getInstancia();
        administrador = (Administrador) gestionDesastres.login("andres", "1023");
        administrador.setGestionDesastres(gestionDesastres);
    }

    @Test
    public void testAgregarRecurso(){
        boolean exito = administrador.agregarRecurso("Agua", 10, "Disponible");
        assert(exito);

        assertTrue(gestionDesastres.getGestionRecursos().buscarPorTipo("Agua") != null);
    }

    @Test
    public void testAsignarRecursoAZona(){
        gestionDesastres.agregarZona(administrador, "ZonaF", "Urbana", "Alto");
        
        boolean exito = administrador.asignarRecursoAZona("REC-2", "ZonaF");
        assertTrue("Debe permitir asignar recurso a zona", exito);
    }

    @Test
    public void testGenerarInformeZonas(){
        var informe = administrador.generarInformeZonas();
        assertNotNull(informe);
        assertFalse(informe.isEmpty());
    }
}
