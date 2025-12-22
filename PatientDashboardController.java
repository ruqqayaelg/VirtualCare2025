package ui.patient;

import javafx.fxml.FXML;
import ui.common.DashboardNavigator;

public class PatientDashboardController {

    @FXML
    private void goToAppointments() {
        DashboardNavigator.navigateTo("patient/PatientAppointments.fxml");
    }

    @FXML
    private void goToBills() {
        DashboardNavigator.navigateTo("patient/PatientBills.fxml");
    }

    @FXML
    private void goToEHR() {
        DashboardNavigator.navigateTo("patient/PatientEHR.fxml");
    }

    @FXML
    private void goToPrescriptions() {
        DashboardNavigator.navigateTo("patient/PatientPrescriptions.fxml");
    }

    @FXML
    private void goToFeedback() {
        DashboardNavigator.navigateTo("patient/PatientFeedback.fxml");
    }

    @FXML
    private void logout() {
        DashboardNavigator.navigateTo("common/LoginView.fxml");
    }

    // ⭐ NEW: Open profile page
    @FXML
    private void openProfile() {
        DashboardNavigator.navigateTo("patient/PatientProfile.fxml");
    }
}
