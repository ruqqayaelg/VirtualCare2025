package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.HealthcareProvider;
import model.Patient;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class ProviderPatientsController {

    @FXML private TableView<Patient> patientsTable;

    @FXML private TableColumn<Patient, String> idColumn;
    @FXML private TableColumn<Patient, String> nameColumn;
    @FXML private TableColumn<Patient, String> emailColumn;

    // ✅ NEW COLUMN
    @FXML private TableColumn<Patient, String> historyColumn;

    @FXML
    public void initialize() {

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        // ✅ BIND MEDICAL HISTORY
        historyColumn.setCellValueFactory(
                new PropertyValueFactory<>("medicalHistory")
        );

        // ✅ ONLY show provider’s patients
        patientsTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.userDb.findPatientsByProvider(provider.getId())
                )
        );
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
