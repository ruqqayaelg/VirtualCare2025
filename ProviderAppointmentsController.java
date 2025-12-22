package ui.provider;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;
import model.HealthcareProvider;
import model.enums.AppointmentStatus;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class ProviderAppointmentsController {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> patientColumn;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    @FXML
    public void initialize() {

        patientColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPatient().getName()
                ));

        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getDate().toString()
                ));

        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getTime()
                ));

        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getStatus().name()
                ));

        HealthcareProvider provider =
                (HealthcareProvider) DashboardNavigator.getCurrentUser();

        // ✅ FIX: use manager, not DB directly
        appointmentsTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.appointmentManager
                                .getAllAppointments()
                                .stream()
                                .filter(a ->
                                        a.getProvider().getId()
                                                .equals(provider.getId()))
                                .toList()
                )
        );
    }

    @FXML
    private void cancelAppointment() {

        Appointment selected =
                appointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        // ✅ Only scheduled appointments can be cancelled
        if (selected.getStatus() == AppointmentStatus.SCHEDULED) {
            selected.setStatus(AppointmentStatus.CANCELLED);
            ServiceRegistry.appointmentManager.save();
            appointmentsTable.refresh();
        }
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("provider/ProviderDashboard.fxml");
    }
}
