package ui.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Patient;
import service.ServiceRegistry;

public class PatientRegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextArea medicalHistoryArea;
    @FXML private TextField contactField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void register() {

        if (firstNameField.getText().isBlank() ||
                lastNameField.getText().isBlank() ||
                emailField.getText().isBlank() ||
                passwordField.getText().isBlank()) {

            messageLabel.setText("All required fields must be filled.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String id = "P" + (ServiceRegistry.userDb.findAll().size() + 1);

        Patient patient = new Patient(
                id,
                firstNameField.getText() + " " + middleNameField.getText() + " " + lastNameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                medicalHistoryArea.getText(),
                contactField.getText()
        );

        ServiceRegistry.userDb.add(patient);

        messageLabel.setText("Registration successful! Your ID: " + id);
        messageLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void goBack() {
        ui.common.DashboardNavigator.navigateTo("common/LoginView.fxml");
    }
}
