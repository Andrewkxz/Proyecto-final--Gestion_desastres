package co.edu.uniquindio.gestiondesastres;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Usuario;


public class GestionDesastresTest {
   private GestionDesastres gestionDesastres;

   @Before
   public void setup(){
    gestionDesastres = GestionDesastres.getInstancia();
   }

   @Test
   public void testRutaMasCorta(){

    Usuario usuario = gestionDesastres.login("andres", "1023");

    var ruta1 = gestionDesastres.agregarRuta(usuario, "Armenia", "Bogota", 12);
    var ruta2 = gestionDesastres.agregarRuta(usuario, "Bogota", "Cali", 8);
    var ruta3 = gestionDesastres.agregarRuta(usuario, "Armenia", "Cali", 25);
    var ruta4 = gestionDesastres.agregarRuta(usuario, "Cali", "ZonaA", 10);

    assertNotNull(ruta1);
    assertNotNull(ruta2);
    assertNotNull(ruta3);
    assertNotNull(ruta4);
    
    var camino = gestionDesastres.calcularRutaMasCorta(usuario,"Armenia", "Cali");
    assertNotNull(camino);
    assertFalse(camino.isEmpty());
   }
}
