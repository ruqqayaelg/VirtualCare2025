package ui.patient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.Appointment;
import model.Patient;
import model.enums.AppointmentStatus;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class PatientAppointmentsController {

    @FXML private TableView<Appointment> appointmentsTable;

    @FXML
    public void initialize() {

        Patient patient = (Patient) DashboardNavigator.getCurrentUser();

        appointmentsTable.setItems(
                FXCollections.observableArrayList(
                        ServiceRegistry.appointmentDb.findAll().stream()
                                .filter(a -> a.getPatientId() != null) // ✅ NULL CHECK
                                .filter(a -> patient.getId().equals(a.getPatientId())) // ✅ SAFE
                                .toList()
                )
        );
    }
    @FXML
    private void bookAppointment() {
        DashboardNavigator.navigateTo("ui/patient/BookAppointment.fxml");
    }
    @FXML
    private void cancelAppointment() {

        Appointment selected =
                appointmentsTable.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        if (selected.getStatus() == AppointmentStatus.SCHEDULED) {
            selected.setStatus(AppointmentStatus.CANCELLED);
            ServiceRegistry.appointmentDb.save();
            appointmentsTable.refresh();
        }
    }



    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("ui/patient/PatientDashboard.fxml");
    }
}
