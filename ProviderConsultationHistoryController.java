package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;
import model.HealthcareProvider;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class ProviderConsultationHistoryController {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> patientColumn;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    @FXML
    public void initialize() {

        // Patient name
        patientColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPatient().getName()
                )
        );

        // Date
        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDate().toString()
                )
        );

        // Time
        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTime()
                )
        );

        // Status
        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getStatus().name()
                )
        );

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        // ✅ SAFE filtering (NO database method needed)
        appointmentsTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.appointmentDb.findAll()
                                .stream()
                                .filter(a -> a.getProvider().getId().equals(provider.getId()))
                                .toList()
                )
        );
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
