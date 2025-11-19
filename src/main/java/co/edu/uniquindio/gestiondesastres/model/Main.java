package co.edu.uniquindio.gestiondesastres.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    // El recurso está en el classpath bajo /co/edu/..../views/login.fxml
    FXMLLoader loader = new FXMLLoader(getClass().getResource(
        "/co/edu/uniquindio/gestiondesastres/views/login.fxml"
    ));

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Sistema Gestión de Desastres - Login");
        stage.show();
    }

    public static void main(String[] args) {
        // Pasar args al launch es más robusto
        launch(args);
    }

}
