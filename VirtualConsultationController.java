package ui.consultation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Appointment;
import model.enums.AppointmentStatus;
import service.ServiceRegistry;
import ui.common.DashboardNavigator;

public class VirtualConsultationController {

    @FXML private Label sessionInfoLabel;
    @FXML private TextArea notesArea;

    private Appointment appointment;

    @FXML
    public void initialize() {

        // Correct method name
        appointment = DashboardNavigator.getSelectedAppointment();

        if (appointment == null) {
            sessionInfoLabel.setText("ERROR: No appointment selected.");
            return;
        }

        // Correct updated method name
        appointment.updateStatus(AppointmentStatus.IN_PROGRESS);
        ServiceRegistry.appointmentManager.save();

        sessionInfoLabel.setText(
                "📹 Virtual Consultation Started\n" +
                        "Provider: " + appointment.getProvider().getName() + "\n" +
                        "Patient: " + appointment.getPatient().getName() + "\n" +
                        "Status: IN_PROGRESS"
        );
    }

    @FXML
    private void endConsultation() {

        if (appointment == null) return;

        // Correct method name
        appointment.setConsultationNotes(notesArea.getText().trim());

        // Correct method name
        appointment.updateStatus(AppointmentStatus.COMPLETED);

        // Correct save method
        ServiceRegistry.appointmentManager.save();

        sessionInfoLabel.setText(
                "✔ Consultation Completed\nNotes saved."
        );

        DashboardNavigator.navigateTo("ui/provider/ProviderDashboard.fxml");
    }

    @FXML
    private void goBack() {
        DashboardNavigator.navigateTo("ui/provider/ProviderDashboard.fxml");
    }
}
