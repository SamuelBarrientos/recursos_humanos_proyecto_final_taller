package main.java.com.magcorporation.kinal.controller;


import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn; 
import main.java.com.magcorporation.kinal.dto.AspiranteDTO;
import main.java.com.magcorporation.kinal.exception.AspiranteException;
import main.java.com.magcorporation.kinal.model.UsuarioModel;
import main.java.com.magcorporation.kinal.repository.AspiranteRepository;
import main.java.com.magcorporation.kinal.service.AspiranteService;
import main.java.com.magcorporation.kinal.service.AuthService;
import main.java.com.magcorporation.kinal.util.SceneManager;

public class DashboardController {

    @FXML private Label lblBienvenida;
    @FXML private Label lblRol;

    @FXML private TableView<AspiranteDTO> tblAspirantes;
    @FXML private TableColumn<AspiranteDTO, String> colId;
    @FXML private TableColumn<AspiranteDTO, String> colNombre;
    @FXML private TableColumn<AspiranteDTO, String> colApellido;
    @FXML private TableColumn<AspiranteDTO, String> colCorreo;
    @FXML private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML private TableColumn<AspiranteDTO, String> colDireccion;
    @FXML private TableColumn<AspiranteDTO, String> colEdad;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEdad;

    private final AspiranteService aspiranteService = new AspiranteService(new AspiranteRepository());
    private final ObservableList<AspiranteDTO> aspirantes = FXCollections.observableArrayList();
    private String idSeleccionado;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarAspirantes();

        UsuarioModel usuario = AuthService.getUsuarioAutenticado();
        if (usuario != null) {
            lblBienvenida.setText("Bienvenido: " + usuario.getNombre() + " " + usuario.getApellido());
            lblRol.setText("Rol de Acceso: " + usuario.getNombreRol());

            SceneManager.showAlert(
                Alert.AlertType.INFORMATION,
                "Inicio de Sesión",
                "Sesión iniciada con éxito.\nRol del usuario: " + usuario.getNombreRol()
            );
        }
    }

    @FXML
    private void handleCerrarSesion() {
        AuthService.logout();
        SceneManager.showLoginView();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idAspirante"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        tblAspirantes.setItems(aspirantes);
        tblAspirantes.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> {
            if (actual != null) {
                mostrarEnFormulario(actual);
            }
        });
    }

    private void cargarAspirantes() {
        try {
            aspirantes.setAll(aspiranteService.listar());
        } catch (AspiranteException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void mostrarEnFormulario(AspiranteDTO dto) {
        idSeleccionado = dto.getIdAspirante();
        txtNombre.setText(dto.getNombre());
        txtApellido.setText(dto.getApellido());
        txtCorreo.setText(dto.getCorreo());
        txtTelefono.setText(dto.getTelefono());
        txtDireccion.setText(dto.getDireccion());
        txtEdad.setText(dto.getEdad());
    }

    private AspiranteDTO leerFormulario(String id) {
        return new AspiranteDTO(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), txtEdad.getText());
    }

    @FXML
    private void handleAgregarAspirante() {
        try {
            aspiranteService.agregar(leerFormulario(null));
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Exito", "Aspirante agregado correctamente.");
            handleLimpiar();
            cargarAspirantes();
        } catch (AspiranteException e) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aspirantes", e.getMessage());
        }
    }

    @FXML
    private void handleActualizarAspirante() {
        try {
            aspiranteService.actualizar(leerFormulario(idSeleccionado));
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Exito", "Aspirante actualizado correctamente.");
            handleLimpiar();
            cargarAspirantes();
        } catch (AspiranteException e) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aspirantes", e.getMessage());
        }
    }

    @FXML
    private void handleEliminarAspirante() {
        if (idSeleccionado == null) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aspirantes", "Seleccione un aspirante de la tabla para eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar al aspirante seleccionado?", ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {
            try {
                aspiranteService.eliminar(idSeleccionado);
                SceneManager.showAlert(Alert.AlertType.INFORMATION, "Exito", "Aspirante eliminado correctamente.");
                handleLimpiar();
                cargarAspirantes();
            } catch (AspiranteException e) {
                SceneManager.showAlert(Alert.AlertType.WARNING, "Aspirantes", e.getMessage());
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        idSeleccionado = null;
        tblAspirantes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }
}
