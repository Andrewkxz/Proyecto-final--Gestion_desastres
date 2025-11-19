package co.edu.uniquindio.gestiondesastres.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FXMLLoaderHelper {

    @SuppressWarnings("unchecked")
    public static <T> T getController(Parent parent) {
        if (parent == null) return null;

        Object data = parent.getUserData();
        if (data instanceof FXMLLoader loader) {
            return (T) loader.getController();
        }
        return null;
    }
}