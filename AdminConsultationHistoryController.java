package ui.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Appointment;
import model.HealthcareProvider;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class AdminConsultationHistoryController {

    @FXML private ComboBox<HealthcareProvider> providerBox;
    @FXML private TableView<Appointment> historyTable;

    @FXML private TableColumn<Appointment, String> dateColumn;
    @FXML private TableColumn<Appointment, String> timeColumn;
    @FXML private TableColumn<Appointment, String> patientColumn;
    @FXML private TableColumn<Appointment, String> statusColumn;

    @FXML
    public void initialize() {

        providerBox.setItems(FXCollections.observableArrayList(
                ServiceRegistry.providerManager.getAllProviders()
        ));

        dateColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));

        timeColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTime()));

        patientColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getPatient().getName()
                ));

        statusColumn.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getStatus().name()
                ));
    }

    @FXML
    private void loadHistory() {

        HealthcareProvider provider = providerBox.getValue();

        if (provider == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No Provider Selected");
            alert.setContentText("Please choose a provider to load consultation history.");
            alert.show();
            return;
        }

        historyTable.setItems(FXCollections.observableArrayList(
                ServiceRegistry.appointmentManager.getAllAppointments()
                        .stream()
                        .filter(a -> a.getProvider().equals(provider))
                        .toList()
        ));
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("admin/AdminDashboard.fxml");
    }
}
