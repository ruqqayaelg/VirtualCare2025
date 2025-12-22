package ui.patient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;
import model.Patient;
import model.enums.AppointmentStatus;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

import java.time.LocalDate;

public class PatientConsultationHistoryController {

    @FXML private TableView<Appointment> historyTable;
    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> providerColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    @FXML private Label reminderLabel;

    @FXML
    public void initialize() {

        Patient patient = (Patient) DashboardNavigator.getCurrentUser();

        // Status Detection
        ServiceRegistry.appointmentManager.autoDetectMissedAppointments();

        // Set column bindings
        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString())
        );

        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTime())
        );

        providerColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getProvider().getName()
                )
        );

        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(formatStatus(c.getValue().getStatus()))
        );

        // Load patient history (ALL completed, cancelled, missed)
        historyTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.appointmentManager.getAllAppointments()
                        .stream()
                        .filter(a -> a.getPatient().equals(patient))
                        .filter(a -> a.getStatus() != AppointmentStatus.SCHEDULED)
                        .toList()
        ));

        // Apply cell colors
        statusColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }
                setText(status);
                applyStatusColor(this, status);
            }
        });

        // REMINDER: Show recent missed appointments
        boolean hasRecentMissed = ServiceRegistry.appointmentManager.getAllAppointments()
                .stream()
                .filter(a -> a.getPatient().equals(patient))
                .anyMatch(a ->
                        a.getStatus() == AppointmentStatus.MISSED &&
                                a.getDate().isAfter(LocalDate.now().minusDays(2))
                );

        if (hasRecentMissed) {
            reminderLabel.setText("⚠ You recently missed a consultation.");
        }
    }

    private String formatStatus(AppointmentStatus status) {
        return status.name();
    }

    private void applyStatusColor(TableCell<?, ?> cell, String status) {

        switch (status) {
            case "MISSED" -> cell.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            case "COMPLETED" -> cell.setStyle("-fx-text-fill: green;");
            case "CANCELLED" -> cell.setStyle("-fx-text-fill: grey;");
            case "IN_PROGRESS" -> cell.setStyle("-fx-text-fill: orange;");
            default -> cell.setStyle("-fx-text-fill: black;");
        }
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("ui/patient/PatientDashboard.fxml");
    }
}
