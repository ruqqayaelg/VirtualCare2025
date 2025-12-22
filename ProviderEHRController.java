package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListCell;
import model.*;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderEHRController {

    @FXML private ComboBox<Patient> patientCombo;
    @FXML private TextField diagnosisField;
    @FXML private TextField treatmentField;
    @FXML private TextArea notesField;
    @FXML private Label statusLabel;
    @FXML private Button deleteEhrBtn;

    private Patient selectedPatient;

    @FXML
    public void initialize() {

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        // ✅ SAFE: patients already assigned OR already have EHRs
        List<Patient> eligiblePatients =
                ServiceRegistry.userDb.getAllPatients()
                        .stream()
                        .filter(p ->
                                provider.getId().equals(p.getAssignedProviderId()) ||
                                        ServiceRegistry.ehrManager.getEHR(p.getId()) != null
                        )
                        .collect(Collectors.toList());

        patientCombo.setItems(FXCollections.observableArrayList(eligiblePatients));

        // Display Name (ID)
        patientCombo.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Patient p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getName() + " (" + p.getId() + ")");
            }
        });

        patientCombo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Patient p, boolean empty) {
                super.updateItem(p, empty);
                setText(empty || p == null ? null : p.getName() + " (" + p.getId() + ")");
            }
        });

        patientCombo.setOnAction(e -> onPatientSelected());
        deleteEhrBtn.setDisable(true);
    }

    private void onPatientSelected() {

        selectedPatient = patientCombo.getValue();
        if (selectedPatient == null) return;

        EHR record = ServiceRegistry.ehrManager.getEHR(selectedPatient.getId());

        if (record != null) {
            diagnosisField.setText(record.getDiagnosis());
            treatmentField.setText(record.getTreatment());
            notesField.setText(record.getNotes());
            statusLabel.setText("Editing existing EHR.");
            deleteEhrBtn.setDisable(false);
        } else {
            diagnosisField.clear();
            treatmentField.clear();
            notesField.clear();
            statusLabel.setText("Creating new EHR.");
            deleteEhrBtn.setDisable(true);
        }
    }

    @FXML
    private void saveEHR() {

        if (selectedPatient == null) {
            statusLabel.setText("Please select a patient.");
            return;
        }

        if (diagnosisField.getText().isBlank() ||
                treatmentField.getText().isBlank()) {

            statusLabel.setText("Diagnosis and Treatment are required.");
            return;
        }

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        // Assign provider clinically (once)
        if (selectedPatient.getAssignedProviderId() == null) {
            selectedPatient.setAssignedProviderId(provider.getId());
            ServiceRegistry.userDb.save();
        }

        ServiceRegistry.ehrManager.saveEHR(
                selectedPatient.getId(),
                provider.getId(),
                diagnosisField.getText(),
                treatmentField.getText(),
                notesField.getText()
        );

        deleteEhrBtn.setDisable(false);
        statusLabel.setText("EHR saved successfully.");
    }

    @FXML
    private void deleteEHR() {

        if (selectedPatient == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "This will permanently delete the EHR.",
                ButtonType.OK, ButtonType.CANCEL);

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ServiceRegistry.ehrManager.deleteEHR(selectedPatient.getId());
            diagnosisField.clear();
            treatmentField.clear();
            notesField.clear();
            deleteEhrBtn.setDisable(true);
            statusLabel.setText("EHR deleted.");
        }
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
