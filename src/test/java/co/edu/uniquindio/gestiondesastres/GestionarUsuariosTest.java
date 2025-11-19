package co.edu.uniquindio.gestiondesastres;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.gestiondesastres.gestores.GestionUsuarios;
import co.edu.uniquindio.gestiondesastres.model.Usuario;

public class GestionarUsuariosTest {
    private GestionUsuarios gestionUsuarios;

    @Before
    public void setup(){
        String copia = TestUtils.copiarArchivosTest(
            "src/main/java/co/edu/uniquindio/gestiondesastres/datos/usuarios.csv",
            "usuarios_test_copia.csv"
        );

        gestionUsuarios = new GestionUsuarios(copia);
    }

    @Test
    public void testLoginCorrecto(){
        Usuario usuario = gestionUsuarios.login("andres", "1023");
        assertNotNull("El usuario debería autenticarse",usuario);
    }

    @Test 
    public void testLoginIncorrecto(){
        Usuario usuario = gestionUsuarios.login("admin", "contrasena_incorrecta");
        assertNull("El login debería fallar",usuario);
    }

    @Test 
    public void testRegistrarUsuario(){
        boolean exito = gestionUsuarios.registrarUsuario("nuevo_usuario", "pass123", "OPERADOR");
        assertTrue("Debe permitir registrar", exito);

        Usuario usuario = gestionUsuarios.login("nuevo_usuario", "pass123");
        assertNotNull("El usuario recién registrado debe autenticarse", usuario);
    }
    
}
