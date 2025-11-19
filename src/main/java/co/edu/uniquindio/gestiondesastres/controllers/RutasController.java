package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RutasController implements Initializable {

    @FXML private ComboBox<String> comboOrigen;
    @FXML private ComboBox<String> comboDestino;
    @FXML private ListView<String> listaRutas;
    

    private final GestionDesastres gestion = GestionDesastres.getInstancia();
    private Usuario usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuario = MainController.usuarioGlobal;

        gestion.listarZonas(usuario).forEach(z -> {
            comboOrigen.getItems().add(z.getNombre());
            comboDestino.getItems().add(z.getNombre());
        });

        listaRutas.getItems().addAll(gestion.listarRutas(usuario));
    }

    @FXML
    private void calcularRuta() {
        String o = comboOrigen.getValue();
        String d = comboDestino.getValue();

        if (o == null || d == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione origen y destino").show();
            return;
        }

        var camino = gestion.calcularRutaMasCorta(usuario, o, d);

        if (camino == null || camino.isEmpty()) {
            listaRutas.getItems().add("No hay ruta: " + o + " → " + d);
            return;
        }

        listaRutas.getItems().add("Ruta " + o + " → " + d + ": " + camino);
    }

    /** Recibe el usuario desde MainController */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // recargar las zonas y rutas con el nuevo usuario
        comboOrigen.getItems().clear();
        comboDestino.getItems().clear();
        gestion.listarZonas(usuario).forEach(z -> {
            comboOrigen.getItems().add(z.getNombre());
            comboDestino.getItems().add(z.getNombre());
        });
        listaRutas.getItems().setAll(gestion.listarRutas(usuario));
    }
}