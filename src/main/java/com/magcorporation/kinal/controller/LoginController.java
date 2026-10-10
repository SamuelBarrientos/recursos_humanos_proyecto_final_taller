
package main.java.com.magcorporation.kinal.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.magcorporation.kinal.dto.LoginDTO;
import main.java.com.magcorporation.kinal.service.AuthService;
import main.java.com.magcorporation.kinal.util.SceneManager;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        try {
            LoginDTO dto = new LoginDTO(txtCorreo.getText(), txtPassword.getText());
            authService.login(dto);
            SceneManager.showDashboardView();
        } catch (IllegalArgumentException e) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        } catch (Exception e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Error", "Error de conexion: " + e.getMessage());
        }
    }

    @FXML
    private void handleIrRegistro() {
        SceneManager.showRegistroView();
    }
}