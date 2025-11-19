package co.edu.uniquindio.gestiondesastres.controllers;

import co.edu.uniquindio.gestiondesastres.gestores.GestionDesastres;
import co.edu.uniquindio.gestiondesastres.MainApplication;
import javafx.scene.Parent;
import co.edu.uniquindio.gestiondesastres.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblError;

    private GestionDesastres gestion = GestionDesastres.getInstancia();

    // llamado desde el botón "Entrar"
    @FXML
    private void onLogin(ActionEvent event) {
        performLogin();
    }

    // método reutilizable para iniciar sesión (puede ser llamado tras registro)
    public void performLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        Usuario u = gestion.login(usuario, contrasena);

        if (u == null) {
            lblError.setText("Usuario o contraseña incorrectos");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/main.fxml"));
            Scene scene = new Scene(loader.load(), 900, 650);

            // aplicar CSS global si existe
            var css = MainApplication.class.getResource("/co/edu/uniquindio/gestiondesastres/styles/styles.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());

            // Pasar usuario al controlador principal
            MainController controller = loader.getController();
            controller.setUsuario(u);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Sistema de Gestión de Desastres");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error cargando la interfaz principal");
        }
    }

    @FXML
    private void onOpenRegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestiondesastres/views/registro.fxml"));
            Parent parent = loader.load();
            RegistroController rc = loader.getController();
            rc.setCaller(this);

            Stage dialog = new Stage();
            dialog.initOwner(txtUsuario.getScene().getWindow());
            dialog.setTitle("Registro de usuario");
            dialog.setScene(new Scene(parent, 400, 280));
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error abriendo el formulario de registro");
        }
    }

    @FXML
    private void onSalir(ActionEvent event) {
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.close();
    }

    // llamado por RegistroController después de crear usuario: rellena campos y hace login automático
    public void onRegistered(String usuario, String contrasena) {
        txtUsuario.setText(usuario);
        txtContrasena.setText(contrasena);
        performLogin();
    }
}