
package main.java.com.magcorporation.kinal.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.magcorporation.kinal.dto.RegistroDTO;
import main.java.com.magcorporation.kinal.service.UsuarioService;
import main.java.com.magcorporation.kinal.util.SceneManager;

public class RegistroController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;

    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize() {
        cmbRol.setItems(FXCollections.observableArrayList("USER", "ADMIN"));
        cmbRol.getSelectionModel().select("USER");
    }

    @FXML
    private void handleRegistrar() {
        try {
            RegistroDTO dto = new RegistroDTO(
                txtNombre.getText(),
                txtApellido.getText(),
                txtCorreo.getText(),
                txtPassword.getText(),
                cmbRol.getValue()
            );

            if (usuarioService.registrar(dto)) {
                SceneManager.showAlert(Alert.AlertType.INFORMATION, "Exito", "Usuario registrado correctamente.");
                SceneManager.showLoginView();
            }
        } catch (IllegalArgumentException e) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Validacion", e.getMessage());
        } catch (Exception e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Error", "Error al registrar: " + e.getMessage());
        }
    }

    @FXML
    private void handleIrLogin() {
        SceneManager.showLoginView();
    }
}