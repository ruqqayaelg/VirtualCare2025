package ui.patient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Prescription;
import model.Patient;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;
import service.PharmacyIntegrationService;


import java.time.LocalDate;



public class PatientPrescriptionsController {
    private final PharmacyIntegrationService pharmacyService =
            new PharmacyIntegrationService();

    @FXML private TableView<Prescription> prescriptionTable;
    @FXML private TableColumn<Prescription, String> providerColumn;
    @FXML private TableColumn<Prescription, String> medicationColumn;
    @FXML private TableColumn<Prescription, String> dosageColumn;
    @FXML private TableColumn<Prescription, String> issueDateColumn;
    @FXML private TableColumn<Prescription, String> endDateColumn;

    @FXML private Button renewBtn;
    @FXML private Button backBtn;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {

        // FIXED NAME
        Patient p = (Patient) ServiceRegistry.authenticationController.getLoggedInUser();

        providerColumn.setCellValueFactory(c -> {
            String providerId = c.getValue().getProviderId();

            if (providerId == null) {
                return new javafx.beans.property.SimpleStringProperty("Unknown");
            }

            var provider = ServiceRegistry.userDb.findById(providerId);

            String name = (provider != null)
                    ? provider.getName()
                    : "Unknown";

            return new javafx.beans.property.SimpleStringProperty(name);
        });


        medicationColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getMedication()
                ));

        dosageColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDosage()
                ));

        issueDateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getIssueDate().toString()
                ));

        endDateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEndDate().toString()
                ));

        // FIX: use correct prescription manager reference
        prescriptionTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.prescriptionManager.getPrescriptionsForPatient(p.getId())
        ));
    }

    @FXML
    private void renewPrescription() {

        Prescription selected = prescriptionTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("No Prescription Selected",
                    "Please select a prescription first.");
            return;
        }

        if (LocalDate.now().isAfter(selected.getEndDate())) {
            showAlert("Cannot Renew Prescription",
                    "Prescription period has ended. Please contact your provider.");
            return;
        }

        // ✔ Enforce dispensing limit
        try {
            pharmacyService.dispensePrescription(selected);
        } catch (IllegalStateException e) {
            showAlert("Dispensing Limit Reached",
                    "You have reached the allowed number of dispensings for this prescription this month.");
            return;
        }

        // ✔ Renew prescription
        ServiceRegistry.prescriptionManager.renewPrescription(selected);

        showAlert("Prescription Renewed",
                "Your prescription has been successfully renewed and dispensed!");

        prescriptionTable.refresh();
    }



    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("patient/PatientDashboard.fxml");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
