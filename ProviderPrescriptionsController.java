package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HealthcareProvider;
import model.Patient;
import model.Prescription;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

import java.time.LocalDate;

public class ProviderPrescriptionsController {

    // ===================== TABLE =====================
    @FXML private TableView<Prescription> prescriptionsTable;
    @FXML private TableColumn<Prescription, String> rxIdColumn;
    @FXML private TableColumn<Prescription, String> patientIdColumn;
    @FXML private TableColumn<Prescription, String> patientNameColumn;
    @FXML private TableColumn<Prescription, String> medColumn;
    @FXML private TableColumn<Prescription, String> dosageColumn;
    @FXML private TableColumn<Prescription, LocalDate> issueColumn;
    @FXML private TableColumn<Prescription, LocalDate> endColumn;

    // ===================== INPUTS =====================
    @FXML private ComboBox<Patient> patientCombo;          // ✅ NEW
    @FXML private TextField medicationField;
    @FXML private TextField dosageField;
    @FXML private ComboBox<Integer> dispensingLimitCombo;
    @FXML private Label errorLabel;

    // ===================== INITIALIZE =====================
    @FXML
    public void initialize() {

        // Table bindings
        rxIdColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        medColumn.setCellValueFactory(new PropertyValueFactory<>("medication"));
        dosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        issueColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        prescriptionsTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.prescriptionManager.findAll()
        ));

        // Load provider patients into dropdown
        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        patientCombo.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.userDb.findPatientsByProvider(provider.getId())
                )
        );

        // Display "Name (ID)" in dropdown
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
    }

    // ===================== ISSUE PRESCRIPTION =====================
    @FXML
    private void issuePrescription() {

        errorLabel.setVisible(false);

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        Patient selectedPatient = patientCombo.getValue();
        String medication = medicationField.getText().trim();
        String dosage = dosageField.getText().trim();

        // Validation
        if (selectedPatient == null || medication.isEmpty() || dosage.isEmpty()) {
            errorLabel.setText("Please select a patient and fill all fields.");
            errorLabel.setVisible(true);
            return;
        }

        int dispensingLimit =
                dispensingLimitCombo.getValue() != null
                        ? dispensingLimitCombo.getValue()
                        : 1;

        // Issue prescription
        ServiceRegistry.prescriptionManager.issuePrescription(
                selectedPatient.getId(),
                selectedPatient.getName(),
                medication,
                dosage,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                dispensingLimit,
                provider.getId()
        );

        // Refresh table
        prescriptionsTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.prescriptionManager.findAll()
        ));

        // Clear inputs
        patientCombo.getSelectionModel().clearSelection();
        medicationField.clear();
        dosageField.clear();
        dispensingLimitCombo.getSelectionModel().clearSelection();
    }

    // ===================== DELETE =====================
    @FXML
    private void deletePrescription() {

        Prescription selected =
                prescriptionsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            errorLabel.setText("Please select a prescription to delete.");
            errorLabel.setVisible(true);
            return;
        }

        ServiceRegistry.prescriptionManager.delete(selected.getPrescriptionId());
        prescriptionsTable.getItems().remove(selected);
        errorLabel.setVisible(false);
    }

    // ===================== NAVIGATION =====================
    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
