package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ChoiceBox<String> choiceRol;

    private final GestionDesastres gestion = GestionDesastres.getInstancia();
    // referencia opcional al controlador de login para notificar creación
    private LoginController caller;

    @FXML
    private void initialize() {
        choiceRol.getItems().addAll("ADMINISTRADOR", "OPERADOR");
        choiceRol.setValue("OPERADOR");
    }

    @FXML
    private void onRegistrar() {
        String u = txtUsuario.getText().trim();
        String c = txtContrasena.getText().trim();
        String r = choiceRol.getValue();

        if (u.isEmpty() || c.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Complete todos los campos").show();
            return;
        }

        boolean ok = gestion.registrarUsuario(u, c, r);
        new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                ok ? "Usuario creado" : "Error: usuario ya existe").show();

        if (ok) ((Stage) txtUsuario.getScene().getWindow()).close();
        if (ok && caller != null) {
            caller.onRegistered(u, c);
        }
    }

    @FXML
    private void onCancelar() {
        ((Stage) txtUsuario.getScene().getWindow()).close();
    }

    public void setCaller(LoginController caller) {
        this.caller = caller;
    }
}