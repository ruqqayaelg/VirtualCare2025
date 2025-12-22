package ui.patient;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Patient;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class PatientProfileController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextArea historyField;
    @FXML private TextField contactField;
    @FXML private Label messageLabel;

    @FXML private Button editBtn;
    @FXML private Button saveBtn;

    private Patient patient;

    @FXML
    public void initialize() {
        patient = (Patient) DashboardNavigator.getCurrentUser();

        nameField.setText(patient.getName());
        emailField.setText(patient.getEmail());
        historyField.setText(patient.getMedicalHistory());
        contactField.setText(patient.getContactInfo());
    }

    @FXML
    private void enableEditing() {
        nameField.setEditable(true);
        emailField.setEditable(true);
        historyField.setEditable(true);
        contactField.setEditable(true);

        saveBtn.setDisable(false);
        editBtn.setDisable(true);
    }

    @FXML
    private void saveChanges() {

        patient.setName(nameField.getText().trim());
        patient.setEmail(emailField.getText().trim());
        patient.setMedicalHistory(historyField.getText().trim());
        patient.setContactInfo(contactField.getText().trim());

        ServiceRegistry.userDb.save();

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Profile updated successfully!");

        editBtn.setDisable(false);
        saveBtn.setDisable(true);

        nameField.setEditable(false);
        emailField.setEditable(false);
        historyField.setEditable(false);
        contactField.setEditable(false);
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("patient/PatientDashboard.fxml");
    }
}
