package main.java.com.magcorporation.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public SceneManager(Stage stage) {
        SceneManager.stage = stage;
    }

    private static void switchScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo cargar la vista: " + fxmlPath + "\nDetalle: " + e.getMessage());
        }
    }

    public static void showLoginView() {
        switchScene("/main/resources/view/login-view.fxml", "Proyecto Final - Inicio de Sesión");
    }

    public static void showRegistroView() {
        switchScene("/main/resources/view/registro-view.fxml", "Proyecto Final - Registro");
    }

    public static void showDashboardView() {
        switchScene("/main/resources/view/dashboard-view.fxml", "Proyecto Final - Panel Principal");
    }

    public static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}